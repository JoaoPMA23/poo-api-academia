package com.academia.service;

import com.academia.exception.EntidadeNaoEncontradaException;
import com.academia.exception.RegraDeNegocioException;
import com.academia.model.Plano;
import com.academia.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;

    public List<Plano> listarTodos() {
        return planoRepository.findAll();
    }

    public List<Plano> listarAtivos() {
        return planoRepository.findByAtivo(true);
    }

    public Plano buscarPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Plano não encontrado com id: " + id));
    }

    @Transactional
    public Plano cadastrar(Plano plano) {
        if (planoRepository.existsByNomeIgnoreCase(plano.getNome())) {
            throw new RegraDeNegocioException("Já existe um plano com o nome: " + plano.getNome());
        }
        return planoRepository.save(plano);
    }

    @Transactional
    public Plano atualizar(Long id, Plano dadosAtualizados) {
        Plano plano = buscarPorId(id);

        if (!plano.getNome().equalsIgnoreCase(dadosAtualizados.getNome()) &&
                planoRepository.existsByNomeIgnoreCase(dadosAtualizados.getNome())) {
            throw new RegraDeNegocioException("Já existe outro plano com esse nome.");
        }

        plano.setNome(dadosAtualizados.getNome());
        plano.setDescricao(dadosAtualizados.getDescricao());
        plano.setDuracaoMeses(dadosAtualizados.getDuracaoMeses());
        plano.setValor(dadosAtualizados.getValor());
        plano.setAtivo(dadosAtualizados.getAtivo());

        return planoRepository.save(plano);
    }

    @Transactional
    public void excluir(Long id) {
        Plano plano = buscarPorId(id);
        if (!plano.getMatriculas().isEmpty()) {
            throw new RegraDeNegocioException("Não é possível excluir um plano com matrículas vinculadas.");
        }
        planoRepository.delete(plano);
    }
}
