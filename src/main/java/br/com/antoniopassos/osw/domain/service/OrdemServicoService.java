package br.com.antoniopassos.osw.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.antoniopassos.osw.domain.exception.EntidadeNaoEncontradaException;
import br.com.antoniopassos.osw.domain.exception.NegocioException;
import br.com.antoniopassos.osw.domain.model.Cliente;
import br.com.antoniopassos.osw.domain.model.Comentario;
import br.com.antoniopassos.osw.domain.model.OrdemServico;
import br.com.antoniopassos.osw.domain.model.StatusOrdemServico;
import br.com.antoniopassos.osw.domain.repository.ClienteRepository;
import br.com.antoniopassos.osw.domain.repository.ComentarioRepository;
import br.com.antoniopassos.osw.domain.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long id) {
		OrdemServico ordemServico = buscar(id);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		Comentario comentario = new Comentario();

		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setOrdemServico(ordemServico);

		return comentarioRepository.save(comentario);
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}

}
