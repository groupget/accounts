package com.agh.groupget.accounts.domain.exception;

public final class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("You are not allowed to perform this operation");
    }
}
