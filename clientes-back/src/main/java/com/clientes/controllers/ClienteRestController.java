package com.clientes.controllers;

import com.clientes.models.entity.Cliente;
import com.clientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		Cliente cliente;

		try {
			cliente = this.clienteService.findById(id);
		} catch (DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta en BD!");
			response.put("error", Objects.requireNonNull(e.getMessage())
					.concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la BD!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Map<String, Object> response = new HashMap<>();
		Cliente clienteNew;

		try {
			cliente.setCreateAt(new Date());
			clienteNew = this.clienteService.save(cliente);
		} catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el insert en BD!");
			response.put("error", Objects.requireNonNull(e.getMessage())
					.concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente a sido creado con exito");
		response.put("cliente", clienteNew);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		Cliente clienteActual = this.clienteService.findById(id);

		Cliente clienteUpdate;

		if(clienteActual == null) {
			response.put("mensaje", "Error, no se puede editar, el cliente ID: "
					.concat(id.toString()).concat(" no existe en la BD!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteUpdate = this.clienteService.save(clienteActual);
		} catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el update en BD!");
			response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente a sido actualizado con exito");
		response.put("cliente", clienteUpdate);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente currentCliente = this.clienteService.findById(id);
			this.clienteService.delete(currentCliente);
		} catch (DataAccessException e){
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la BD!"));
			response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente con ID: ".concat(id.toString()).concat(" eliminado con exito"));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
