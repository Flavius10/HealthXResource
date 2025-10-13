package com.example.HealthXResource.services;

import entities.HealthProfile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repositories.HealthProfileRepository;
import services.HealthProfileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindHealthProfileTest {

    @Autowired
    private HealthProfileService healthProfileService;

    @Mock
    private HealthProfileRepository healthProfileRepository;

    @Test
    public void findHealthProfileWrongUserTests() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        when(healthProfileRepository.findHealthProfileByUsername("john"))
                .thenReturn(Optional.of(healthProfile));

        assertThrows(Exception.class,
                () -> healthProfileService.findHealthProfile("john"));
    }

    @Test
    public void findHealthProfileProfileDoesntExistTests() {
        when(healthProfileRepository.findHealthProfileByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class,
                () -> healthProfileService.findHealthProfile("john"));
    }

    @Test
    public void findHealthProfileProfileExistsTests() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        when(healthProfileRepository.findHealthProfileByUsername("john"))
                .thenReturn(Optional.of(healthProfile));

        HealthProfile result = healthProfileService.findHealthProfile("john");

        assertEquals(healthProfile, result);
    }

    @Test
    public void findHealthProfileProfileAdminTests() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        when(healthProfileRepository.findHealthProfileByUsername("john"))
                .thenReturn(Optional.of(healthProfile));

        HealthProfile result = healthProfileService.findHealthProfile("john");

        assertEquals(healthProfile, result);
    }

}
