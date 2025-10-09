package repositories;

import entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {
}
