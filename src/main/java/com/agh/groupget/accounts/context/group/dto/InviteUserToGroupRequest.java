package com.agh.groupget.accounts.context.group.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public final class InviteUserToGroupRequest {

    @NotEmpty(message = "username can't be empty")
    public String username;
}
