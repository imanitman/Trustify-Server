package com.trustify.trustify.repository.Business;

import com.trustify.trustify.entity.Subcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcriptionRepository extends JpaRepository<Subcription, Long> {
}
