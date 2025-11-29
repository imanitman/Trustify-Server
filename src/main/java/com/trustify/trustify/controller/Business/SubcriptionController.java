package com.trustify.trustify.controller.Business;

import com.trustify.trustify.entity.Subcription;
import com.trustify.trustify.service.Business.SubcriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subcription")
public class SubcriptionController {

    private final SubcriptionService subcriptionService;

    @PostMapping("/save")
    public ResponseEntity<Object> newSubcriptionPage(@RequestBody Subcription subcription){
         Subcription savedSubcription = subcriptionService.saveSubcription(subcription);
         return ResponseEntity.ok(savedSubcription);
    }
}
