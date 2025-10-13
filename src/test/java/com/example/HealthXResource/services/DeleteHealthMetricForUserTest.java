package com.example.HealthXResource.services;

import entities.HealthProfile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repositories.HealthMetricRepository;
import repositories.HealthProfileRepository;
import services.HealthMetricService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteHealthMetricForUserTest {

    @Autowired
    private HealthMetricService healthMetricService;

    @Mock
    private HealthMetricRepository healthMetricRepository;

    @Mock
    private HealthProfileRepository healthProfileRepository;

    @Test
    void deleteHealthMetricForUserWithAdminTest() {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("bill");

        when(healthProfileRepository.findHealthProfileByUsername("bill"))
                .thenReturn(Optional.of(profile));

        healthMetricService.deleteHealthMetricForUser("bill");

        verify(healthMetricRepository).deleteAllForUser(profile);
    }

    @Test
    void deleteHealthMetricForUserWithAdminProfileDoesntExistTest() {
        when(healthProfileRepository.findHealthProfileByUsername("bill"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class,
                () -> healthMetricService.deleteHealthMetricForUser("bill"));

        verify(healthMetricRepository, never()).deleteAllForUser(any());
    }

    @Test
    void deleteHealthMetricForUserWithNonAdminTest() {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("bill");

        when(healthProfileRepository.findHealthProfileByUsername("bill"))
                .thenReturn(Optional.of(profile));

        assertThrows(Exception.class,
                () ->  healthMetricService.deleteHealthMetricForUser("bill"));

        verify(healthMetricRepository, never()).deleteAllForUser(profile);
    }

}
