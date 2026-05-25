package com.academia.controller;

import com.academia.model.Instrutor;
import com.academia.service.InstrutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
@RequiredArgsConstructor
public class InstrutorController {

    private final InstrutorService instrutorService;

    // GET /instrutores
    @GetMapping
    public ResponseEntity<List<Instrutor>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Instrutor.StatusInstrutor status) {

        if (nome != null) return ResponseEntity.ok(instrutorService.buscarPorNome(nome));
        if (status != null) return ResponseEntity.ok(instrutorService.buscarPorStatus(status));
        return ResponseEntity.ok(instrutorService.listarTodos());
    }

    // GET /instrutores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(instrutorService.buscarPorId(id));
    }

    // POST /instrutores
    @PostMapping
    public ResponseEntity<Instrutor> cadastrar(@RequestBody @Valid Instrutor instrutor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instrutorService.cadastrar(instrutor));
    }

    // PUT /instrutores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Instrutor> atualizar(@PathVariable Long id, @RequestBody @Valid Instrutor instrutor) {
        return ResponseEntity.ok(instrutorService.atualizar(id, instrutor));
    }

    // DELETE /instrutores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        instrutorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // POST /instrutores/{id}/modalidades/{modalidadeId}
    @PostMapping("/{id}/modalidades/{modalidadeId}")
    public ResponseEntity<Instrutor> adicionarModalidade(
            @PathVariable Long id,
            @PathVariable Long modalidadeId) {
        return ResponseEntity.ok(instrutorService.adicionarModalidade(id, modalidadeId));
    }

    // DELETE /instrutores/{id}/modalidades/{modalidadeId}
    @DeleteMapping("/{id}/modalidades/{modalidadeId}")
    public ResponseEntity<Instrutor> removerModalidade(
            @PathVariable Long id,
            @PathVariable Long modalidadeId) {
        return ResponseEntity.ok(instrutorService.removerModalidade(id, modalidadeId));
    }
}
