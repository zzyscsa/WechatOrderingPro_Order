package com.scsa.order.dao;

import com.scsa.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:42
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster, String> {
}
