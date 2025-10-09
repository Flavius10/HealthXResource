package repositories;

import entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {
    Optional<HealthProfile> findHealthProfileByUsername(String username);
}
