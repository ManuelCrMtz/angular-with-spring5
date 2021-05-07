package com.clientes.models.services;


import com.clientes.models.entity.Cliente;

import java.util.List;

public interface IClienteService {

	List<Cliente> findAll();

	void save(Cliente cliente);

	Cliente findById(Long id);

	void delete(Cliente cliente);

}
