package com.academia.service;

import com.academia.exception.EntidadeNaoEncontradaException;
import com.academia.exception.RegraDeNegocioException;
import com.academia.model.Aluno;
import com.academia.model.Matricula;
import com.academia.model.Plano;
import com.academia.repository.AlunoRepository;
import com.academia.repository.MatriculaRepository;
import com.academia.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final PlanoRepository planoRepository;

    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Matrícula não encontrada com id: " + id));
    }

    public List<Matricula> buscarPorAluno(Long alunoId) {
        return matriculaRepository.findByAlunoId(alunoId);
    }

    public List<Matricula> buscarVencidas() {
        return matriculaRepository.findVencidas(LocalDate.now());
    }

    public List<Matricula> buscarVencendoEmDias(int dias) {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(dias);
        return matriculaRepository.findVencendoEntre(inicio, fim);
    }

    @Transactional
    public Matricula matricular(Long alunoId, Long planoId, Double valorPago) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado com id: " + alunoId));

        if (aluno.getStatus() == Aluno.StatusAluno.SUSPENSO) {
            throw new RegraDeNegocioException("Aluno suspenso não pode realizar nova matrícula.");
        }

        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Plano não encontrado com id: " + planoId));

        if (!plano.getAtivo()) {
            throw new RegraDeNegocioException("Este plano não está disponível no momento.");
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setPlano(plano);
        matricula.setValorPago(valorPago != null ? valorPago : plano.getValor());
        matricula.setDataInicio(LocalDate.now());
        matricula.setDataVencimento(LocalDate.now().plusMonths(plano.getDuracaoMeses()));

        // Ativa o aluno caso esteja inativo
        if (aluno.getStatus() == Aluno.StatusAluno.INATIVO) {
            aluno.setStatus(Aluno.StatusAluno.ATIVO);
            alunoRepository.save(aluno);
        }

        return matriculaRepository.save(matricula);
    }

    @Transactional
    public Matricula atualizarStatus(Long id, Matricula.StatusMatricula novoStatus) {
        Matricula matricula = buscarPorId(id);
        matricula.setStatus(novoStatus);
        return matriculaRepository.save(matricula);
    }

    @Transactional
    public void cancelar(Long id) {
        Matricula matricula = buscarPorId(id);
        matricula.setStatus(Matricula.StatusMatricula.CANCELADA);
        matriculaRepository.save(matricula);
    }

    @Transactional
    public void excluir(Long id) {
        Matricula matricula = buscarPorId(id);
        matriculaRepository.delete(matricula);
    }
}
