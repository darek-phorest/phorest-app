package pl.szymonmilczarek.phorestapp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szymonmilczarek.phorestapp.dao.entity.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
}
