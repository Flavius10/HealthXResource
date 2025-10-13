package com.example.HealthXResource.services;

import entities.HealthMetric;
import entities.HealthProfile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repositories.HealthMetricRepository;
import repositories.HealthProfileRepository;
import services.HealthMetricService;
import services.HealthProfileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddHealthMetricTests {


    @Autowired
    private HealthMetricService healthMetricService;

    @Mock
    private HealthMetricRepository healthMetricRepository;

    @Mock
    private HealthProfileRepository healthProfileRepository;

    @Test
    public void addHealthMetricValidUserAuthenticatedTest(){
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        HealthMetric healthMetric = new HealthMetric();
        healthMetric.setProfile(healthProfile);

        when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
                .thenReturn(Optional.of(healthProfile));

        healthMetricService.addHealthMetric(healthMetric);

        verify(healthMetricRepository).save(healthMetric);
    }

    @Test
    void addHealthMetricValidUserAuthenticatedProfileDoesntExistTest() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        HealthMetric healthMetric = new HealthMetric();
        healthMetric.setProfile(healthProfile);

        when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class,
                () -> healthMetricService.addHealthMetric(healthMetric));

        verify(healthMetricRepository, never()).save(healthMetric);
    }

    @Test
    void addHealthMetricDifferentUserAuthenticatedTest() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("bill");

        HealthMetric healthMetric = new HealthMetric();
        healthMetric.setProfile(healthProfile);

        assertThrows(Exception.class,
                () -> healthMetricService.addHealthMetric(healthMetric));

        verify(healthMetricRepository, never()).save(healthMetric);
    }


}
