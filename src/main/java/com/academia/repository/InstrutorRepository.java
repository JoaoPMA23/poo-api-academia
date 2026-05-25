package com.academia.repository;

import com.academia.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    Optional<Instrutor> findByCpf(String cpf);

    Optional<Instrutor> findByCref(String cref);

    List<Instrutor> findByStatus(Instrutor.StatusInstrutor status);

    List<Instrutor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT i FROM Instrutor i JOIN i.modalidades m WHERE m.id = :modalidadeId")
    List<Instrutor> findByModalidadeId(@Param("modalidadeId") Long modalidadeId);

    boolean existsByCref(String cref);
}
