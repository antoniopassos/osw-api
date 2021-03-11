package br.com.antoniopassos.osw.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antoniopassos.osw.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		var cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("Fulano");
		cliente1.setEmail("fulano@gmail.com");
		cliente1.setTelefone("+55 11 98888-8888");
		
		var cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Beltrano");
		cliente2.setEmail("beltrano@gmail.com");
		cliente2.setTelefone("+55 11 97777-7777");
		
		return Arrays.asList(cliente1, cliente2);
	}

}
