package services;

import entities.HealthMetric;
import entities.HealthProfile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import repositories.HealthMetricRepository;
import repositories.HealthProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthProfileService {

    private final HealthProfileRepository healthProfileRepository;
    private final HealthMetricRepository healthMetricRepository;

    public HealthProfileService(HealthProfileRepository healthProfileRepository,
                                HealthMetricRepository healthMetricRepository) {
        this.healthProfileRepository = healthProfileRepository;
        this.healthMetricRepository = healthMetricRepository;
    }

    public void addHealthMetric(HealthMetric healthMetric){
        Optional<HealthProfile> healthProfile =
                healthProfileRepository.findHealthProfileByUsername(healthMetric.getProfile().getUsername());

        healthProfile.ifPresentOrElse(
                profile ->
                {
                    healthMetric.setProfile(profile);
                    this.healthMetricRepository.save(healthMetric);
                },
                () -> {
                    throw new RuntimeException("Profile not found");
                }
        );
    }

    public List<HealthMetric> findHealthMetricHistory(String username){
        return this.healthMetricRepository.findHealthMetricHistory(username);
    }

    public void deleteHealMetricForUsers(String username){
        Optional<HealthProfile> profile =
                healthProfileRepository.findHealthProfileByUsername(username);

        profile.ifPresentOrElse(healthMetricRepository::deleteAllForUser,
                () -> {
                    throw new RuntimeException("Profile not found");
                }
        );
    }
}
