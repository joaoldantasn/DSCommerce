package com.jlucas.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jlucas.dscommerce.dto.ProductDTO;
import com.jlucas.dscommerce.entities.Product;
import com.jlucas.dscommerce.exceptions.DatabaseException;
import com.jlucas.dscommerce.exceptions.ResourceNotFoundException;
import com.jlucas.dscommerce.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		//Optional<Product> result = repository.findById(id);
		Product product = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		//Para converter produto e produtoDTO
		//ProductDTO dto = new ProductDTO(product);
		return new ProductDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(String name, Pageable pageable){
		//Para converter lista de produto em lista de produtoDTO
		Page<Product> result = repository.searchByName(name, pageable);
		return result.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto){
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto){
		try {
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado.");
		}
	
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id){
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
	}
}
