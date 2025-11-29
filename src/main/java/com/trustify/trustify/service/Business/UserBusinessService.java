package com.trustify.trustify.service.Business;

import com.trustify.trustify.entity.UserBusiness;
import com.trustify.trustify.repository.Business.UserBusinessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserBusinessService {

    private final UserBusinessRepository userBusinessRepository;

    public UserBusiness createUserBusiness(UserBusiness userBusiness) {
        return userBusinessRepository.save(userBusiness);
    }
}
