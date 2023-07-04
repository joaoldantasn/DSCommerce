package com.jlucas.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlucas.dscommerce.dto.OrderDTO;
import com.jlucas.dscommerce.dto.OrderItemDTO;
import com.jlucas.dscommerce.entities.Order;
import com.jlucas.dscommerce.entities.OrderItem;
import com.jlucas.dscommerce.entities.OrderStatus;
import com.jlucas.dscommerce.entities.Product;
import com.jlucas.dscommerce.entities.User;
import com.jlucas.dscommerce.exceptions.ResourceNotFoundException;
import com.jlucas.dscommerce.repositories.OrderItemRepository;
import com.jlucas.dscommerce.repositories.OrderRepository;
import com.jlucas.dscommerce.repositories.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authservice;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		authservice.validateSelfOrAdmin(order.getClient().getId());
		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for(OrderItemDTO itemDTO : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDTO.getProductId());
			OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
			
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
}
