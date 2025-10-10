package controllers;

import controllers.dto.HealthAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advice")
public class HealthAdviceController {

    private static final Logger logger =
            LoggerFactory.getLogger(HealthAdviceController.class);

    @PostMapping
    public void provideHealthAdvice(
            @RequestBody List<HealthAdvice> healthAdvices){
        healthAdvices.forEach(healthAdvice ->
                logger.info(healthAdvice.toString()));
    }

}
