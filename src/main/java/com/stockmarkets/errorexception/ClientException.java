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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.http.HttpStatus;

/**
 * The type Client exception.
 */
public class ClientException {

    private static final String                    messageDefault = "Unhandled client exception. Please contact Support Team";
    private static final String                    message400     = "Request provided is not valid.";
    /**
     * The Builder.
     */
    public static final  Supplier<ClientException> builder        = ClientException::new;
    private              String                    message;
    private              HttpStatus                httpStatus;
    private              String                    sequence;
    private              List<String>              httpRecords;

    /**
     * With message client exception.
     *
     * @param message the message
     * @return the client exception
     */
    public ClientException withMessage(final String message) {
        this.message = message;
        return this;
    }

    /**
     * With status client exception.
     *
     * @param httpStatus the http status
     * @return the client exception
     */
    public ClientException withStatus(final HttpStatus httpStatus) {
        if (!httpStatus.is4xxClientError()) {
            throw new IllegalArgumentException("httpStatus has to be a part of client exception, 4xx");
        }
        this.httpStatus = httpStatus;
        return this;
    }

    /**
     * With sequence client exception.
     *
     * @param sequence the sequence
     * @return the client exception
     */
    public ClientException withSequence(final String sequence) {
        this.sequence = sequence;
        return this;
    }

    /**
     * With http record client exception.
     *
     * @param httpRecord the http record
     * @return the client exception
     */
    public ClientException withHttpRecord(final String httpRecord) {
        httpRecords = Collections.singletonList(httpRecord);
        return this;
    }

    /**
     * With http records client exception.
     *
     * @param httpRecords the http records
     * @return the client exception
     */
    public ClientException withHttpRecords(final List<String> httpRecords) {
        this.httpRecords = httpRecords;
        return this;
    }

    /**
     * Build general service exception.
     *
     * @return the general service exception
     */
    public GeneralServiceException build() {

        final GeneralServiceException target = new GeneralServiceException();

        httpStatus = Optional
            .ofNullable(httpStatus)
            .orElse(HttpStatus.BAD_REQUEST);

        target.setDomainMessage(Optional
            .ofNullable(message)
            .orElseGet(() -> {
                String message = messageDefault;
                if (httpStatus == HttpStatus.BAD_REQUEST) {
                    message = message400;
                } else {
                    message = messageDefault;
                }
                return message;
            }));

        target.setHttpStatus(httpStatus);
        target.setSequence(sequence);
        target.setHttpRecords(httpRecords);
        return target;
    }

}