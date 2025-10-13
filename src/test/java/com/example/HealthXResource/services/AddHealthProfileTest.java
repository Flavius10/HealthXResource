package com.example.HealthXResource.services;


import entities.HealthProfile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repositories.HealthProfileRepository;
import services.HealthProfileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddHealthProfileTest {

    @Autowired
    private HealthProfileService healthProfileService;

    @Mock
    private HealthProfileRepository healthProfileRepository;


    @Test
    public void addHealthProfileWrongUserTests() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        assertThrows(Exception.class,
                () -> healthProfileService.addHealthProfile(healthProfile));
    }

    @Test
    public void addHealthProfileHealthProfileExistsTests() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
                .thenReturn(Optional.of(healthProfile));

        assertThrows(Exception.class,
                () -> healthProfileService.addHealthProfile(healthProfile));
    }

    @Test
    public void addHealthProfileHealthProfileDoesntExistTests() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
                .thenReturn(Optional.empty());

        healthProfileService.addHealthProfile(healthProfile);

        verify(healthProfileRepository).save(healthProfile);
    }
}

