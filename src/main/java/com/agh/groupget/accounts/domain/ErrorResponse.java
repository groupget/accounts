package com.agh.groupget.accounts.domain;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public final class ErrorResponse {

    @NotEmpty
    public String code;

    @NotEmpty
    public String message;
}
