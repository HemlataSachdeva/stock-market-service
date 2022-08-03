/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved.
 * The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-core
 * name        - ocsl-core
 * description - OCSL CORE Project
 * 2019
 */
package com.stockmarkets.domain.validation;

import com.stockmarkets.domain.dto.DomainDTO;
import com.stockmarkets.errorexception.ClientException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * The type Bean validation.
 */
@Component
public class BeanValidation {
    /**
     * Dto validation t.
     *
     * @param <T>       the type parameter
     * @param domainDTO the domain dto
     * @return the t
     */
    public <T extends DomainDTO> T dtoValidation(final T domainDTO) {
        domainDTO
            .getValidationErrors()
            .ifPresent((List<String> values) -> {
                throw ClientException.builder
                    .get()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withHttpRecords(values)
                    .build();
            });
        return domainDTO;
    }
}
