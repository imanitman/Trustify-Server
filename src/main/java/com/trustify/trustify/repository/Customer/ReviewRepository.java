package com.trustify.trustify.repository.Customer;

import com.trustify.trustify.entity.Company;
import com.trustify.trustify.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByCompanyId(Long companyId, Pageable pageable);

}
