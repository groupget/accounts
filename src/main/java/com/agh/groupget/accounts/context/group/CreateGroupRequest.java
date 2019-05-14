package com.agh.groupget.accounts.context.group;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Validated
public final class CreateGroupRequest {

    //todo: must be public?
    @NotEmpty(message = "Group name can't be empty")
    public String groupName;

    public String description;

    public Set<String> usernames;
}
