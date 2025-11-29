package com.trustify.trustify.repository.Business;

import com.trustify.trustify.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByContactEmail(String email);

    Company findByName(String name);

    Page<Company> findByIndustry(String name, Pageable pageable);
}
