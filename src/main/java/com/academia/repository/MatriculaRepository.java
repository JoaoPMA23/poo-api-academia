package com.academia.repository;

import com.academia.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByAlunoId(Long alunoId);

    List<Matricula> findByPlanoId(Long planoId);

    List<Matricula> findByStatus(Matricula.StatusMatricula status);

    @Query("SELECT m FROM Matricula m WHERE m.dataVencimento < :hoje AND m.status = 'ATIVA'")
    List<Matricula> findVencidas(@Param("hoje") LocalDate hoje);

    @Query("SELECT m FROM Matricula m WHERE m.dataVencimento BETWEEN :inicio AND :fim")
    List<Matricula> findVencendoEntre(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
