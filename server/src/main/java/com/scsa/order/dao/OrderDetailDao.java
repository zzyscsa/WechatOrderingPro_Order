package com.scsa.order.dao;

import com.scsa.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:41
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {
}
