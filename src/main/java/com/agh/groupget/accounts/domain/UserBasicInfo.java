package com.agh.groupget.accounts.domain;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Validated
public class UserBasicInfo {

    @NotEmpty(message = "username can't be empty")
    private final String username;

    @NotNull(message = "group can't be null")
    private final Set<String> groups;

    public UserBasicInfo(@NotEmpty(message = "username can't be empty") String username,
                         @NotEmpty(message = "group can't be null") Set<String> groups) {
        this.username = username;
        this.groups = groups;
    }

    public String username() {
        return username;
    }

    public Set<String> group() {
        return groups;
    }
}
