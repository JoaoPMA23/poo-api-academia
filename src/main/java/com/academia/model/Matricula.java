package com.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;

/**
 * Matricula representa o vínculo de um Aluno com a academia.
 * Relacionamentos:
 *  - ManyToOne com Aluno (um aluno tem várias matrículas - OneToMany pelo lado do Aluno)
 *  - ManyToOne com Plano
 */
@Entity
@Table(name = "tb_matricula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lado Many do relacionamento OneToMany com Aluno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    @NotNull(message = "Aluno é obrigatório")
    private Aluno aluno;

    // ManyToOne com Plano
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plano_id", nullable = false)
    @NotNull(message = "Plano é obrigatório")
    private Plano plano;

    @Column(nullable = false, updatable = false)
    private LocalDate dataInicio = LocalDate.now();

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Positive(message = "Valor pago deve ser positivo")
    private Double valorPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMatricula status = StatusMatricula.ATIVA;

    private String observacoes;

    @PrePersist
    public void calcularVencimento() {
        if (dataInicio != null && plano != null) {
            this.dataVencimento = dataInicio.plusMonths(plano.getDuracaoMeses());
        }
    }

    public enum StatusMatricula {
        ATIVA, VENCIDA, CANCELADA, SUSPENSA
    }
}
