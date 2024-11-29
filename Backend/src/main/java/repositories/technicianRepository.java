package repositories;

import entities.technicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface technicianRepository extends JpaRepository<technicianEntity,Integer> {
}
