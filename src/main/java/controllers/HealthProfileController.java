package controllers;


import entities.HealthProfile;
import org.springframework.web.bind.annotation.*;
import services.HealthProfileService;

@RestController
@RequestMapping("/profile")
public class HealthProfileController {

    private final HealthProfileService healthProfileService;

    public HealthProfileController(HealthProfileService healthProfileService) {
        this.healthProfileService = healthProfileService;
    }

    @PostMapping
    public void addHealthProfile(@PathVariable HealthProfile profile){
        this.healthProfileService.addHealthProfile(profile);
    }

    @GetMapping("/{username}")
    public HealthProfile findHealthProfile(@PathVariable String username) {
        return healthProfileService.findHealthProfile(username);
    }

    @DeleteMapping("/{username}")
    public void deleteHealthProfile(@PathVariable String username){
        this.healthProfileService.deleteHealthProfile(username);
    }

}
