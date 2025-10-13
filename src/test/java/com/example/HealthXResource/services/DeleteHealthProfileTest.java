package com.example.HealthXResource.services;

import entities.HealthProfile;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repositories.HealthProfileRepository;
import services.HealthProfileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteHealthProfileTest {


    @Autowired
    private HealthProfileService healthProfileService;

    @Mock
    private HealthProfileRepository healthProfileRepository;

    @Test
    public void deleteHealthProfileNonAdminTest() {
        assertThrows(Exception.class,
                () -> healthProfileService.deleteHealthProfile("john"));

        verify(healthProfileRepository, never()).delete(any());
    }

    @Test
    public void deleteHealthProfileAdminProfileNotPresentTest() {
        when(healthProfileRepository.findHealthProfileByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class,
                () -> healthProfileService.deleteHealthProfile("john"));
    }


    @Test
    public void deleteHealthProfileAdminProfileExistsTest() {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setUsername("john");

        when(healthProfileRepository.findHealthProfileByUsername("john"))
                .thenReturn(Optional.of(healthProfile));

        healthProfileService.deleteHealthProfile("john");

        verify(healthProfileRepository).delete(healthProfile);
    }

}
