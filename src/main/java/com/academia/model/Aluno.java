package com.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aluno é uma extensão de Pessoa (herança).
 * Relacionamentos:
 *  - ManyToMany com Modalidade (um aluno pode praticar várias modalidades)
 *  - OneToMany com Matricula (um aluno tem várias matrículas ao longo do tempo)
 */
@Entity
@Table(name = "tb_aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluno extends Pessoa {

    @Positive(message = "Peso deve ser positivo")
    private Double peso;

    @Positive(message = "Altura deve ser positiva")
    private Double altura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAluno status = StatusAluno.ATIVO;

    // Relacionamento OneToMany com Matricula
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    // Relacionamento ManyToMany com Modalidade
    @ManyToMany
    @JoinTable(
            name = "tb_aluno_modalidade",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "modalidade_id")
    )
    private List<Modalidade> modalidades = new ArrayList<>();

    public enum StatusAluno {
        ATIVO, INATIVO, SUSPENSO
    }
}
