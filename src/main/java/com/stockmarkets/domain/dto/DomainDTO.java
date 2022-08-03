/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved.
 * The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-core
 * name        - ocsl-core
 * description - OCSL CORE Project
 * 2019
 */
package com.stockmarkets.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * The interface Domain dto.
 */
public interface DomainDTO {

    /**
     * Gets validation errors.
     *
     * @return the validation errors
     */
    @JsonIgnore
    default Optional<List<String>> getValidationErrors() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final javax.validation.Validator validator = factory.getValidator();
        return Optional
            .of(validator.validate(this))
            .filter(set -> set.size() > 0)
            .map(set -> set
                .stream()
                .map(violation -> violation
                    .getPropertyPath()
                    .toString() + " " + violation.getMessage())
                .collect(Collectors.toList())
            );
    }
}
