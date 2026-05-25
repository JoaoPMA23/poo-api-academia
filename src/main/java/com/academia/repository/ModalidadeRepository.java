package com.academia.repository;

import com.academia.model.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {

    Optional<Modalidade> findByNomeIgnoreCase(String nome);

    List<Modalidade> findByAtiva(Boolean ativa);

    List<Modalidade> findByTipo(Modalidade.TipoModalidade tipo);

    boolean existsByNomeIgnoreCase(String nome);
}
