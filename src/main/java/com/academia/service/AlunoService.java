package com.academia.service;

import com.academia.exception.EntidadeNaoEncontradaException;
import com.academia.exception.RegraDeNegocioException;
import com.academia.model.Aluno;
import com.academia.model.Modalidade;
import com.academia.repository.AlunoRepository;
import com.academia.repository.ModalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ModalidadeRepository modalidadeRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado com id: " + id));
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Aluno> buscarPorStatus(Aluno.StatusAluno status) {
        return alunoRepository.findByStatus(status);
    }

    @Transactional
    public Aluno cadastrar(Aluno aluno) {
        if (alunoRepository.existsByCpf(aluno.getCpf())) {
            throw new RegraDeNegocioException("Já existe um aluno cadastrado com o CPF: " + aluno.getCpf());
        }
        if (alunoRepository.existsByEmail(aluno.getEmail())) {
            throw new RegraDeNegocioException("Já existe um aluno cadastrado com o e-mail: " + aluno.getEmail());
        }
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno atualizar(Long id, Aluno dadosAtualizados) {
        Aluno aluno = buscarPorId(id);

        // Verifica duplicidade de CPF apenas se foi alterado
        if (!aluno.getCpf().equals(dadosAtualizados.getCpf()) &&
                alunoRepository.existsByCpf(dadosAtualizados.getCpf())) {
            throw new RegraDeNegocioException("CPF já está em uso por outro aluno.");
        }

        aluno.setNome(dadosAtualizados.getNome());
        aluno.setCpf(dadosAtualizados.getCpf());
        aluno.setEmail(dadosAtualizados.getEmail());
        aluno.setTelefone(dadosAtualizados.getTelefone());
        aluno.setDataNascimento(dadosAtualizados.getDataNascimento());
        aluno.setPeso(dadosAtualizados.getPeso());
        aluno.setAltura(dadosAtualizados.getAltura());
        aluno.setStatus(dadosAtualizados.getStatus());

        return alunoRepository.save(aluno);
    }

    @Transactional
    public void excluir(Long id) {
        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);
    }

    @Transactional
    public Aluno adicionarModalidade(Long alunoId, Long modalidadeId) {
        Aluno aluno = buscarPorId(alunoId);
        Modalidade modalidade = modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Modalidade não encontrada com id: " + modalidadeId));

        if (aluno.getModalidades().contains(modalidade)) {
            throw new RegraDeNegocioException("Aluno já está inscrito nessa modalidade.");
        }

        aluno.getModalidades().add(modalidade);
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno removerModalidade(Long alunoId, Long modalidadeId) {
        Aluno aluno = buscarPorId(alunoId);
        Modalidade modalidade = modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Modalidade não encontrada com id: " + modalidadeId));

        aluno.getModalidades().remove(modalidade);
        return alunoRepository.save(aluno);
    }
}
