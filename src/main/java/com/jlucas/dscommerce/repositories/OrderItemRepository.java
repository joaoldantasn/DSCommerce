package com.jlucas.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlucas.dscommerce.entities.OrderItem;
import com.jlucas.dscommerce.entities.OrderItemPK;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
