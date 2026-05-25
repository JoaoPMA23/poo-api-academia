package com.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Instrutor é uma extensão de Pessoa (herança).
 * Relacionamentos:
 *  - ManyToMany com Modalidade (um instrutor pode ministrar várias modalidades)
 */
@Entity
@Table(name = "tb_instrutor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instrutor extends Pessoa {

    @NotBlank(message = "CREF é obrigatório")
    @Column(nullable = false, unique = true)
    private String cref;

    @Positive(message = "Salário deve ser positivo")
    @Column(nullable = false)
    private Double salario;

    private String especialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInstrutor status = StatusInstrutor.ATIVO;

    // ManyToMany com Modalidade (um instrutor leciona várias modalidades)
    @ManyToMany
    @JoinTable(
            name = "tb_instrutor_modalidade",
            joinColumns = @JoinColumn(name = "instrutor_id"),
            inverseJoinColumns = @JoinColumn(name = "modalidade_id")
    )
    private List<Modalidade> modalidades = new ArrayList<>();

    public enum StatusInstrutor {
        ATIVO, INATIVO, FERIAS, AFASTADO
    }
}
