package br.com.antoniopassos.osw.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.antoniopassos.osw.api.model.OrdemServicoInput;
import br.com.antoniopassos.osw.api.model.OrdemServicoRepresentationModel;
import br.com.antoniopassos.osw.domain.model.OrdemServico;
import br.com.antoniopassos.osw.domain.repository.OrdemServicoRepository;
import br.com.antoniopassos.osw.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private OrdemServicoRepository ordemServicoRepositorio;

	@Autowired
	private ModelMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel create(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);

		return toModel(ordemServicoService.criar(ordemServico));
	}

	@GetMapping
	public List<OrdemServicoRepresentationModel> findAll() {
		return toCollectionModel(ordemServicoRepositorio.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoRepresentationModel> findById(@PathVariable Long id) {
		Optional<OrdemServico> ordemServico = ordemServicoRepositorio.findById(id);

		if (ordemServico.isPresent()) {
			OrdemServicoRepresentationModel model = toModel(ordemServico.get());

			return ResponseEntity.ok(model);
		}

		return ResponseEntity.notFound().build();
	}

	private OrdemServicoRepresentationModel toModel(OrdemServico ordemServico) {
		return mapper.map(ordemServico, OrdemServicoRepresentationModel.class);
	}

	private List<OrdemServicoRepresentationModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}

	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return mapper.map(ordemServicoInput, OrdemServico.class);
	}
}
