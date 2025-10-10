package com.example.HealthXResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import entities.HealthMetric;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import services.HealthMetricService;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddHealthMetricTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private HealthMetricService healthMetricService;

    @Test
    @DisplayName("Considering an authenticated request, assert that the returned HTTP status" +
            " is HTTP 200 OK and the service method is called.")
    public void addHealthMetricTest() throws Exception {
        HealthMetric m = new HealthMetric();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJsonBody = ow.writeValueAsString(m);

        mvc.perform(
                        post("/metric")
                                .with(jwt())
                                .content(requestJsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Unauthenticated request should return 403 Forbidden")
    public void addHealthMetric_unauthenticated() throws Exception {
        HealthMetric metric = new HealthMetric();

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(metric);

        mvc.perform(post("/metric")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());

        verify(healthMetricService, never()).addHealthMetric(metric);
    }
}
