package com.trustify.trustify.dto.Res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResReviewDto {
    private String nameUser;
    private String nameCompany;
    private String title;
    private String description;
    private Integer rating;
    private String expDate;
}
