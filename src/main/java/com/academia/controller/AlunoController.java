package com.academia.controller;

import com.academia.model.Aluno;
import com.academia.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    // GET /alunos - Lista todos ou filtra por nome/status
    @GetMapping
    public ResponseEntity<List<Aluno>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Aluno.StatusAluno status) {

        if (nome != null) return ResponseEntity.ok(alunoService.buscarPorNome(nome));
        if (status != null) return ResponseEntity.ok(alunoService.buscarPorStatus(status));
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    // GET /alunos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    // POST /alunos - Cadastrar novo aluno
    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody @Valid Aluno aluno) {
        Aluno salvo = alunoService.cadastrar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // PUT /alunos/{id} - Atualizar aluno
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody @Valid Aluno aluno) {
        return ResponseEntity.ok(alunoService.atualizar(id, aluno));
    }

    // DELETE /alunos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // POST /alunos/{id}/modalidades/{modalidadeId} - Inscrever aluno em modalidade
    @PostMapping("/{id}/modalidades/{modalidadeId}")
    public ResponseEntity<Aluno> adicionarModalidade(
            @PathVariable Long id,
            @PathVariable Long modalidadeId) {
        return ResponseEntity.ok(alunoService.adicionarModalidade(id, modalidadeId));
    }

    // DELETE /alunos/{id}/modalidades/{modalidadeId} - Remover aluno de modalidade
    @DeleteMapping("/{id}/modalidades/{modalidadeId}")
    public ResponseEntity<Aluno> removerModalidade(
            @PathVariable Long id,
            @PathVariable Long modalidadeId) {
        return ResponseEntity.ok(alunoService.removerModalidade(id, modalidadeId));
    }
}
