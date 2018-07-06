package com.example.orderserver.Repository;

import com.example.orderserver.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
