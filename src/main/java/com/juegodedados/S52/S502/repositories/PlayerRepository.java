package com.juegodedados.S52.S502.repositories;

import com.juegodedados.S52.S502.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {

    Optional<PlayerModel> findByName(String name);
    Boolean existsByUsername(String username);
}

