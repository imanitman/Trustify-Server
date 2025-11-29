package com.trustify.trustify.service.Business;

import com.trustify.trustify.entity.Subcription;
import com.trustify.trustify.repository.Business.SubcriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubcriptionService {

    private final SubcriptionRepository subcriptionRepository;

    public Subcription saveSubcription(Subcription subcription) {
        return subcriptionRepository.save(subcription);
    }
}
