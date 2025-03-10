package com.generation.gamestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.gamestore.model.Categoria;
import com.generation.gamestore.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> findAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Categoria> getById(@PathVariable Long id) { 
		return categoriaRepository.findById(id) 
				.map(resposta -> ResponseEntity.ok(resposta)) 
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
	}
	
	@GetMapping("/nome/{nome}") 
	public ResponseEntity<List<Categoria>> getByName(@PathVariable String nome) { 
		return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome)); 
	}
	
	@PostMapping 
	public ResponseEntity<Categoria> createCategory(@Valid @RequestBody Categoria categoria) { 
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaRepository.save(categoria));
	}
	 
	@PutMapping("/{id}") 
	public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody Categoria categoria) { 
		return categoriaRepository.findById(id)
				.map(resposta -> {
					resposta.setNome(categoria.getNome());
	            	resposta.setDescricao(categoria.getDescricao());
	                
	                return ResponseEntity.ok(categoriaRepository.save(resposta));
	            })
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
