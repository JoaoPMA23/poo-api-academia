package com.academia.controller;

import com.academia.model.Modalidade;
import com.academia.service.ModalidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modalidades")
@RequiredArgsConstructor
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    // GET /modalidades?ativas=true&tipo=YOGA
    @GetMapping
    public ResponseEntity<List<Modalidade>> listar(
            @RequestParam(required = false) Boolean ativas,
            @RequestParam(required = false) Modalidade.TipoModalidade tipo) {

        if (Boolean.TRUE.equals(ativas)) return ResponseEntity.ok(modalidadeService.listarAtivas());
        if (tipo != null) return ResponseEntity.ok(modalidadeService.buscarPorTipo(tipo));
        return ResponseEntity.ok(modalidadeService.listarTodas());
    }

    // GET /modalidades/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Modalidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modalidadeService.buscarPorId(id));
    }

    // POST /modalidades
    @PostMapping
    public ResponseEntity<Modalidade> cadastrar(@RequestBody @Valid Modalidade modalidade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modalidadeService.cadastrar(modalidade));
    }

    // PUT /modalidades/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Modalidade> atualizar(@PathVariable Long id, @RequestBody @Valid Modalidade modalidade) {
        return ResponseEntity.ok(modalidadeService.atualizar(id, modalidade));
    }

    // DELETE /modalidades/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        modalidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
