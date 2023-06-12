package com.jlucas.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlucas.dscommerce.dto.ProductDTO;
import com.jlucas.dscommerce.entities.Product;
import com.jlucas.dscommerce.repositories.ProductRepository;



@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		//Optional<Product> result = repository.findById(id);
		Product product = repository.findById(id).get();
		//Para converter produto e produtoDTO
		//ProductDTO dto = new ProductDTO(product);
		return new ProductDTO(product);
	}
}
