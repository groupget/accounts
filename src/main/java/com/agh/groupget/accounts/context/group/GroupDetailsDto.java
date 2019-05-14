package com.agh.groupget.accounts.context.group;

import java.util.Set;

public final class GroupDetailsDto {

    public String groupName;
    public String description;
    public Set<String> usernames;

    public GroupDetailsDto(String groupName, String description, Set<String> usernames) {
        this.groupName = groupName;
        this.description = description;
        this.usernames = usernames;
    }
}
