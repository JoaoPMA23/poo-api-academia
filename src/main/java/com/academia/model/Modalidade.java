package com.academia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modalidade representa uma aula/atividade oferecida pela academia.
 * Ex: Musculação, Yoga, Spinning, Pilates, Boxe...
 * Relacionamentos:
 *  - ManyToMany com Aluno
 *  - ManyToMany com Instrutor
 */
@Entity
@Table(name = "tb_modalidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da modalidade é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    @Positive(message = "Capacidade máxima deve ser positiva")
    private Integer capacidadeMaxima;

    @Positive(message = "Duração deve ser positiva (em minutos)")
    private Integer duracaoMinutos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoModalidade tipo;

    private Boolean ativa = true;

    // Lado inverso do ManyToMany com Aluno
    @ManyToMany(mappedBy = "modalidades")
    private List<Aluno> alunos = new ArrayList<>();

    // Lado inverso do ManyToMany com Instrutor
    @ManyToMany(mappedBy = "modalidades")
    private List<Instrutor> instrutores = new ArrayList<>();

    public enum TipoModalidade {
        MUSCULACAO, CARDIO, YOGA, DANCA, LUTA, NATACAO, PILATES, OUTRO
    }
}
