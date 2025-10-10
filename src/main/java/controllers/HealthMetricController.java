package controllers;

import entities.HealthMetric;
import org.springframework.web.bind.annotation.*;
import services.HealthMetricService;

import java.util.List;

@RestController
@RequestMapping("/metric")
public class HealthMetricController {

    private final HealthMetricService healthMetricService;

    public HealthMetricController(HealthMetricService healthMetricService) {
        this.healthMetricService = healthMetricService;
    }

    @PostMapping
    public void addHealthMetric(
            @RequestBody HealthMetric healthMetric){
        this.healthMetricService.addHealthMetric(healthMetric);
    }

    @GetMapping("/{username}")
    public List<HealthMetric> findHealthMetricHistory(@PathVariable String username){
        return this.healthMetricService.findHealthMetricHistory(username);
    }

    @DeleteMapping("/{username}")
    public void deleteHealthMetricForUser(@PathVariable String username){
        this.healthMetricService.deleteHealthMetricForUser(username);
    }

}
