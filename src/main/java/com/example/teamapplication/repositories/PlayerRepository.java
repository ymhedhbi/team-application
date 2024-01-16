package com.example.teamapplication.repositories;

import com.example.teamapplication.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
