package com.agh.groupget.accounts.context.group;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Validated
final class CreateGroupRequest {

    @NotEmpty(message = "Group name can't be empty")
    public String groupName;

    public String description;

    @NotNull(message = "Usernames can't be null")
    public Set<String> usernames;
}
