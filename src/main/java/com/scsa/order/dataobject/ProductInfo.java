package com.scsa.order.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: SCSA
 * @Date: 2020/8/19 22:24
 */
@Entity
@Data
@NoArgsConstructor
@DynamicUpdate //自动更新update当前时间
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -5922041032806142307L;

    @Id
    private String productId;

    /** 名字 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 小图地址 */
    private String productIcon;

    /** 状态：0在家1下架；默认在架 */
    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}
