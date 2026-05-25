package com.academia.controller;

import com.academia.model.Matricula;
import com.academia.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    // GET /matriculas
    @GetMapping
    public ResponseEntity<List<Matricula>> listar(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Boolean vencidas,
            @RequestParam(required = false) Integer vencendoEmDias) {

        if (alunoId != null) return ResponseEntity.ok(matriculaService.buscarPorAluno(alunoId));
        if (Boolean.TRUE.equals(vencidas)) return ResponseEntity.ok(matriculaService.buscarVencidas());
        if (vencendoEmDias != null) return ResponseEntity.ok(matriculaService.buscarVencendoEmDias(vencendoEmDias));
        return ResponseEntity.ok(matriculaService.listarTodas());
    }

    // GET /matriculas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }

    // POST /matriculas - Realizar nova matrícula
    @PostMapping
    public ResponseEntity<Matricula> matricular(@RequestBody Map<String, Object> dados) {
        Long alunoId = Long.valueOf(dados.get("alunoId").toString());
        Long planoId = Long.valueOf(dados.get("planoId").toString());
        Double valorPago = dados.containsKey("valorPago") ? Double.valueOf(dados.get("valorPago").toString()) : null;
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.matricular(alunoId, planoId, valorPago));
    }

    // PATCH /matriculas/{id}/status - Atualizar status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Matricula> atualizarStatus(
            @PathVariable Long id,
            @RequestParam Matricula.StatusMatricula status) {
        return ResponseEntity.ok(matriculaService.atualizarStatus(id, status));
    }

    // PATCH /matriculas/{id}/cancelar
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        matriculaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE /matriculas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        matriculaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
