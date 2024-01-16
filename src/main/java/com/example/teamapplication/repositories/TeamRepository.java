package com.example.teamapplication.repositories;

import com.example.teamapplication.entities.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAll(Pageable pageable);

    @Query("select t from Team t where t.name like :kw")
    List<Team> searchTeam(@Param("kw") String keyword);
}
