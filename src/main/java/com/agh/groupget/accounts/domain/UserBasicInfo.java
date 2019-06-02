package com.agh.groupget.accounts.domain;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class UserBasicInfo {

    private final String username;
    private final Set<String> groups;

    public UserBasicInfo(String username, Set<String> groups) {
        this.username = username;
        this.groups = groups;
    }

    public String username() {
        return username;
    }

    public Set<String> groups() {
        return groups;
    }

    public boolean isUserInGroup(String groupName) {
        return groups.stream()
              .anyMatch(groupName::equals);
    }
}
