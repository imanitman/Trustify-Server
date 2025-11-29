package com.trustify.trustify.controller.Business;

import com.trustify.trustify.dto.Req.CompanyDto;
import com.trustify.trustify.entity.Company;
import com.trustify.trustify.service.Business.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody CompanyDto companyDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            companyService.createPendingCompany(companyDto);
            response.put("success", true);
            response.put("message", "Verification code sent to " + companyDto.getWorkEmail());
            response.put("email", companyDto.getWorkEmail());
            response.put("expiresIn", "5 minutes");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            log.error("Error registering company", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(
            @RequestParam String email,
            @RequestParam String code) {

        Map<String, Object> response = new HashMap<>();

        try {
            Company company = companyService.verifyAndSaveCompany(email, code);
            response.put("success", true);
            response.put("message", "Company verified successfully!");
            response.put("companyId", company.getId());
            response.put("companyName", company.getName());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error verifying company", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/resend-code")
    public ResponseEntity<Map<String, Object>> resendCode(@RequestParam String email) {

        Map<String, Object> response = new HashMap<>();

        try {
            companyService.resendVerificationCode(email);

            response.put("success", true);
            response.put("message", "Verification code resent to " + email);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error resending verification code", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = new HashMap<>();

        try {
            Page<Company> companyPage = companyService.getAllCompanies(page, size);

            response.put("success", true);
            response.put("companies", companyPage.getContent()); // List<Company>
            response.put("currentPage", companyPage.getNumber());
            response.put("totalPages", companyPage.getTotalPages());
            response.put("totalItems", companyPage.getTotalElements());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error getting companies", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/industry/{name}")
    public ResponseEntity<Map<String, Object>> getCompaniesByIndustry(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Map<String, Object> response = new HashMap<>();
        try {
            Page<Company> companyPage = companyService.getCompaniesByIndustry(name, page, size); // List<Company>
            response.put("success", true);
            response.put("companies", companyPage.getContent()); // List<Company>
            response.put("currentPage", companyPage.getNumber());
            response.put("totalPages", companyPage.getTotalPages());
            response.put("totalItems", companyPage.getTotalElements());
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            log.error("Error getting companies by industry", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCompanyById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Company company = companyService.getCompanyById(id);
            response.put("success", true);
            response.put("company", company);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting company by id", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}