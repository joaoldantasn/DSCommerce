package com.jlucas.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlucas.dscommerce.dto.OrderDTO;
import com.jlucas.dscommerce.entities.Order;
import com.jlucas.dscommerce.exceptions.ResourceNotFoundException;
import com.jlucas.dscommerce.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new OrderDTO(order);
	}
}
