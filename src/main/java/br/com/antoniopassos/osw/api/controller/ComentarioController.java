package br.com.antoniopassos.osw.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.antoniopassos.osw.api.model.ComentarioInput;
import br.com.antoniopassos.osw.api.model.ComentarioModel;
import br.com.antoniopassos.osw.domain.exception.EntidadeNaoEncontradaException;
import br.com.antoniopassos.osw.domain.model.Comentario;
import br.com.antoniopassos.osw.domain.model.OrdemServico;
import br.com.antoniopassos.osw.domain.repository.OrdemServicoRepository;
import br.com.antoniopassos.osw.domain.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{id}/comentarios")
public class ComentarioController {

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long id) {
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));

		return toCollectionModel(ordemServico.getComentarios());
	}

	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long id, @Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = ordemServicoService.adicionarComentario(id, comentarioInput.getDescricao());

		return toModel(comentario);
	}

	private ComentarioModel toModel(Comentario comentario) {
		return mapper.map(comentario, ComentarioModel.class);
	}

}
