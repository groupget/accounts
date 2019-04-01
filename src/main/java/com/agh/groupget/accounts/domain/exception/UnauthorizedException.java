package com.agh.groupget.accounts.domain.exception;

public final class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("You are not allowed to perform this operation");
    }
}
