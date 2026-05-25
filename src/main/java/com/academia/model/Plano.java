package com.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Plano representa os pacotes de assinatura da academia.
 * Ex: Mensal, Trimestral, Semestral, Anual.
 * Relacionamentos:
 *  - OneToMany com Matricula (um plano pode ter várias matrículas)
 */
@Entity
@Table(name = "tb_plano")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do plano é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    @Positive(message = "Duração em meses deve ser positiva")
    @Column(nullable = false)
    private Integer duracaoMeses;

    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false)
    private Double valor;

    private Boolean ativo = true;

    // OneToMany com Matricula (um plano tem várias matrículas)
    @OneToMany(mappedBy = "plano")
    private List<Matricula> matriculas = new ArrayList<>();
}
