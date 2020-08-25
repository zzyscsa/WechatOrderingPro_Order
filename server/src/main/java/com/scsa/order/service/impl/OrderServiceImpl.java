package com.scsa.order.service.impl;

import com.scsa.order.dao.OrderDetailDao;
import com.scsa.order.dao.OrderMasterDao;
import com.scsa.order.dataobject.OrderDetail;
import com.scsa.order.dataobject.OrderMaster;
import com.scsa.order.dto.OrderDTO;
import com.scsa.order.enums.OrderStatusEnum;
import com.scsa.order.enums.PayStatusEnum;
import com.scsa.order.enums.ResultEnum;
import com.scsa.order.exception.OrderException;
import com.scsa.order.service.OrderService;
import com.scsa.order.util.KeyUtil;
import com.scsa.product.client.ProductClient;
import com.scsa.product.common.DecreaseStockInput;
import com.scsa.product.common.ProductInfoOutPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:50
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        // 查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                                            .map(OrderDetail::getProductId)
                                            .collect(Collectors.toList());
        List<ProductInfoOutPut> productInfoOutPutList = productClient.listForOrder(productIdList);
        // 计算总价
        BigDecimal orderAmount = new BigDecimal("0");
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutPut productInfoOutPut : productInfoOutPutList) {
                if (productInfoOutPut.getProductId().equals(orderDetail.getProductId())) {
                    orderAmount = productInfoOutPut.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfoOutPut, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailDao.save(orderDetail);
                }
            }
        }

        // 扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                                            .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                                            .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1. 查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterDao.findById(orderId);
        if (!orderMasterOptional.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2. 判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //3. 修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterDao.save(orderMaster);

        //4. 构造OrderDTO对象
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
