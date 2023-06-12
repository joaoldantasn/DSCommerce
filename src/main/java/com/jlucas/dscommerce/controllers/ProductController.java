package com.jlucas.dscommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlucas.dscommerce.dto.ProductDTO;
import com.jlucas.dscommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	

	/*public String teste() {
		Optional<Product> result = repository.findById(1L);
		Product product = result.get();
		return product.getName();
	} */
	
	@GetMapping(value = "/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return dto;
	}
}
