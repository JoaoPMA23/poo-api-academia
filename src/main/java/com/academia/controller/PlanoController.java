package com.academia.controller;

import com.academia.model.Plano;
import com.academia.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService planoService;

    // GET /planos
    @GetMapping
    public ResponseEntity<List<Plano>> listar(@RequestParam(required = false) Boolean ativos) {
        if (Boolean.TRUE.equals(ativos)) return ResponseEntity.ok(planoService.listarAtivos());
        return ResponseEntity.ok(planoService.listarTodos());
    }

    // GET /planos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }

    // POST /planos
    @PostMapping
    public ResponseEntity<Plano> cadastrar(@RequestBody @Valid Plano plano) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planoService.cadastrar(plano));
    }

    // PUT /planos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizar(@PathVariable Long id, @RequestBody @Valid Plano plano) {
        return ResponseEntity.ok(planoService.atualizar(id, plano));
    }

    // DELETE /planos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        planoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
