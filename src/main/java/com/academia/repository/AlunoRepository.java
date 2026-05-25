package com.academia.repository;

import com.academia.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findByEmail(String email);

    List<Aluno> findByStatus(Aluno.StatusAluno status);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Aluno a JOIN a.modalidades m WHERE m.id = :modalidadeId")
    List<Aluno> findByModalidadeId(@Param("modalidadeId") Long modalidadeId);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
