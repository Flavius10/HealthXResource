package services;

import entities.HealthMetric;
import entities.HealthProfile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.HealthMetricRepository;
import repositories.HealthProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthMetricService {

    private final HealthMetricRepository healthMetricRepository;
    private final HealthProfileRepository healthProfileRepository;

    public HealthMetricService(HealthMetricRepository healthMetricRepository, HealthProfileRepository healthProfileRepository) {
        this.healthMetricRepository = healthMetricRepository;
        this.healthProfileRepository = healthProfileRepository;
    }

    @PreAuthorize("#healthMetric.profile.username == authentication.principal.claims['user_name']")
    public void addHealthMetric(HealthMetric healthMetric) {
        Optional<HealthProfile> profile = healthProfileRepository.findHealthProfileByUsername(healthMetric.getProfile().getUsername());

        profile.ifPresentOrElse(
                p ->
                {
                    healthMetric.setProfile(p);
                    healthMetricRepository.save(healthMetric);
                },
                () -> {
                    throw new RuntimeException("The profile doesn't exist");
                });

        ;
    }

    public List<HealthMetric> findHealthMetricHistory(String username) {
        return healthMetricRepository.findHealthMetricHistory(username);
    }

    @PreAuthorize("hasAuthority('admin')")
    public void deleteHealthMetricForUser(String username) {
        Optional<HealthProfile> profile = healthProfileRepository.findHealthProfileByUsername(username);

        profile.ifPresentOrElse(healthMetricRepository::deleteAllForUser,
                () -> {
                    throw new RuntimeException("The profile doesn't exist");
                }
        );
    }
}

