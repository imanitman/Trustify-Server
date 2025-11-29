package com.trustify.trustify.controller.Business;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feature")
public class FeatureController {

    @GetMapping("/")
    public String getFeature() {
        return "Feature";
    }
}
