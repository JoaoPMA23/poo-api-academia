package com.academia.service;

import com.academia.exception.EntidadeNaoEncontradaException;
import com.academia.exception.RegraDeNegocioException;
import com.academia.model.Instrutor;
import com.academia.model.Modalidade;
import com.academia.repository.InstrutorRepository;
import com.academia.repository.ModalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final ModalidadeRepository modalidadeRepository;

    public List<Instrutor> listarTodos() {
        return instrutorRepository.findAll();
    }

    public Instrutor buscarPorId(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Instrutor não encontrado com id: " + id));
    }

    public List<Instrutor> buscarPorNome(String nome) {
        return instrutorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Instrutor> buscarPorStatus(Instrutor.StatusInstrutor status) {
        return instrutorRepository.findByStatus(status);
    }

    @Transactional
    public Instrutor cadastrar(Instrutor instrutor) {
        if (instrutorRepository.existsByCref(instrutor.getCref())) {
            throw new RegraDeNegocioException("Já existe um instrutor com o CREF: " + instrutor.getCref());
        }
        return instrutorRepository.save(instrutor);
    }

    @Transactional
    public Instrutor atualizar(Long id, Instrutor dadosAtualizados) {
        Instrutor instrutor = buscarPorId(id);

        if (!instrutor.getCref().equals(dadosAtualizados.getCref()) &&
                instrutorRepository.existsByCref(dadosAtualizados.getCref())) {
            throw new RegraDeNegocioException("CREF já está em uso por outro instrutor.");
        }

        instrutor.setNome(dadosAtualizados.getNome());
        instrutor.setCpf(dadosAtualizados.getCpf());
        instrutor.setEmail(dadosAtualizados.getEmail());
        instrutor.setTelefone(dadosAtualizados.getTelefone());
        instrutor.setDataNascimento(dadosAtualizados.getDataNascimento());
        instrutor.setCref(dadosAtualizados.getCref());
        instrutor.setSalario(dadosAtualizados.getSalario());
        instrutor.setEspecialidade(dadosAtualizados.getEspecialidade());
        instrutor.setStatus(dadosAtualizados.getStatus());

        return instrutorRepository.save(instrutor);
    }

    @Transactional
    public void excluir(Long id) {
        Instrutor instrutor = buscarPorId(id);
        instrutorRepository.delete(instrutor);
    }

    @Transactional
    public Instrutor adicionarModalidade(Long instrutorId, Long modalidadeId) {
        Instrutor instrutor = buscarPorId(instrutorId);
        Modalidade modalidade = modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Modalidade não encontrada com id: " + modalidadeId));

        if (instrutor.getModalidades().contains(modalidade)) {
            throw new RegraDeNegocioException("Instrutor já está vinculado a essa modalidade.");
        }

        instrutor.getModalidades().add(modalidade);
        return instrutorRepository.save(instrutor);
    }

    @Transactional
    public Instrutor removerModalidade(Long instrutorId, Long modalidadeId) {
        Instrutor instrutor = buscarPorId(instrutorId);
        Modalidade modalidade = modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Modalidade não encontrada com id: " + modalidadeId));

        instrutor.getModalidades().remove(modalidade);
        return instrutorRepository.save(instrutor);
    }
}
