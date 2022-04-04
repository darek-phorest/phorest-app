package pl.szymonmilczarek.phorestapp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szymonmilczarek.phorestapp.dao.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
