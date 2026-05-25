package com.academia.service;

import com.academia.exception.EntidadeNaoEncontradaException;
import com.academia.exception.RegraDeNegocioException;
import com.academia.model.Modalidade;
import com.academia.repository.ModalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;

    public List<Modalidade> listarTodas() {
        return modalidadeRepository.findAll();
    }

    public List<Modalidade> listarAtivas() {
        return modalidadeRepository.findByAtiva(true);
    }

    public Modalidade buscarPorId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Modalidade não encontrada com id: " + id));
    }

    public List<Modalidade> buscarPorTipo(Modalidade.TipoModalidade tipo) {
        return modalidadeRepository.findByTipo(tipo);
    }

    @Transactional
    public Modalidade cadastrar(Modalidade modalidade) {
        if (modalidadeRepository.existsByNomeIgnoreCase(modalidade.getNome())) {
            throw new RegraDeNegocioException("Já existe uma modalidade com o nome: " + modalidade.getNome());
        }
        return modalidadeRepository.save(modalidade);
    }

    @Transactional
    public Modalidade atualizar(Long id, Modalidade dadosAtualizados) {
        Modalidade modalidade = buscarPorId(id);

        if (!modalidade.getNome().equalsIgnoreCase(dadosAtualizados.getNome()) &&
                modalidadeRepository.existsByNomeIgnoreCase(dadosAtualizados.getNome())) {
            throw new RegraDeNegocioException("Já existe outra modalidade com esse nome.");
        }

        modalidade.setNome(dadosAtualizados.getNome());
        modalidade.setDescricao(dadosAtualizados.getDescricao());
        modalidade.setCapacidadeMaxima(dadosAtualizados.getCapacidadeMaxima());
        modalidade.setDuracaoMinutos(dadosAtualizados.getDuracaoMinutos());
        modalidade.setTipo(dadosAtualizados.getTipo());
        modalidade.setAtiva(dadosAtualizados.getAtiva());

        return modalidadeRepository.save(modalidade);
    }

    @Transactional
    public void excluir(Long id) {
        Modalidade modalidade = buscarPorId(id);
        modalidadeRepository.delete(modalidade);
    }
}
