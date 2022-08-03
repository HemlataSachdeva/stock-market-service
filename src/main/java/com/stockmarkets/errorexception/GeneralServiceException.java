/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved.
 * The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-core
 * name        - ocsl-core
 * description - OCSL CORE Project
 * 2019
 */
package com.stockmarkets.errorexception;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * The type General service exception.
 */
@Getter
@Setter
public class GeneralServiceException extends RuntimeException {

    private static final long serialVersionUID = -8606430177738661770L;

    private String        domainMessage;
    private String        sequence;
    private HttpStatus    httpStatus;
    private List<String>  httpRecords;
    private List<String>  logRecords;

    /**
     * Instantiates a new General service exception.
     */
    protected GeneralServiceException() {
        super(new RuntimeException());
    }

}
