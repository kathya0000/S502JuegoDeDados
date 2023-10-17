package com.juegodedados.S52.S502.repositories;

import com.juegodedados.S52.S502.models.GameModel;
import com.juegodedados.S52.S502.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository  extends JpaRepository<GameModel, Long> {
    List<GameModel> findByPlayerModel(PlayerModel playerModel);
    List<GameModel> findByPlayerModelAndWinTrue(PlayerModel playerModel);

    List<GameModel> findByPlayerModelId(Long playerId);
    void deleteByPlayerModelId(Long playerId);
}
