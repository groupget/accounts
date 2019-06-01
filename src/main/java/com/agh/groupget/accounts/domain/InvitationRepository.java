package com.agh.groupget.accounts.domain;

import java.util.Set;

public interface InvitationRepository {

    void inviteUserToGroup(String groupName, String username);

    Set<String> findUserInvitations(String username);

    boolean doesInvicationExist(String groupName, String username);
}
