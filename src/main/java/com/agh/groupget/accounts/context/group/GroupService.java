package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.context.group.dto.GroupDetailsDto;
import com.agh.groupget.accounts.context.group.dto.InviteUserToGroupRequest;
import com.agh.groupget.accounts.domain.Invitation;
import com.agh.groupget.accounts.domain.InvitationRepository;
import com.agh.groupget.accounts.domain.exception.BusinessException;
import com.agh.groupget.accounts.domain.exception.ResourceNotFoundException;
import com.agh.groupget.accounts.infrastructure.CognitoRequestFactory;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
final class GroupService {

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
    private final CognitoRequestFactory cognitoRequestFactory;
    private final InvitationRepository invitationRepository;

    GroupService(AWSCognitoIdentityProvider awsCognitoIdentityProvider, CognitoRequestFactory cognitoRequestFactory,
                 InvitationRepository invitationRepository) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.cognitoRequestFactory = cognitoRequestFactory;
        this.invitationRepository = invitationRepository;
    }

    GroupDetailsDto groupDetails(String groupName) {
        GetGroupRequest groupRequest = cognitoRequestFactory.getGroupRequest(groupName);
        GetGroupResult group;
        try {
            group = awsCognitoIdentityProvider.getGroup(groupRequest);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Group " + groupName + " doesn't exist.");
        }
        String description = group.getGroup()
                                  .getDescription();
        Set<String> usernames = getGroupUsernames(groupName);
        return new GroupDetailsDto(groupName, description, usernames);
    }

    void inviteUserToGroup(String groupName, InviteUserToGroupRequest request) {
        String username = request.username;
        validateGroupExist(groupName);
        validateUserExists(username);
        if (invitationRepository.findByUsernameAndGroupName(groupName, username)
                                .isPresent()) {
            throw new BusinessException("User " + username + " is already invited to group " + groupName);
        }

        Invitation invitation = new Invitation();
        invitation.setUsername(username);
        invitation.setGroupName(groupName);
        invitationRepository.save(invitation);
    }

    void addUserToGroup(String groupName, String username) {
        if (invitationRepository.findByUsernameAndGroupName(username, groupName)
                                .isEmpty()) {
            throw new BusinessException("User " + username + " is not invited to group " + groupName);
        }

        AdminAddUserToGroupRequest awsRequest = cognitoRequestFactory.adminAddUserToGroupRequest(groupName, username);
        awsCognitoIdentityProvider.adminAddUserToGroup(awsRequest);
    }

    //todo: n+1...
    void createGroup(com.agh.groupget.accounts.context.group.dto.CreateGroupRequest request) {
        Optional.ofNullable(request.usernames)
                .ifPresent(this::validateUsersExist);
        createEmptyGroup(request);
        Optional.ofNullable(request.usernames)
                .ifPresent(usernames -> addUsersToGroup(request.groupName, usernames));
    }

    void deleteUserFromGroup(String groupName, String username) {
        AdminRemoveUserFromGroupRequest awsRequest = cognitoRequestFactory.adminRemoveUserFromGroupRequest(groupName, username);
        awsCognitoIdentityProvider.adminRemoveUserFromGroup(awsRequest);
    }

    private void createEmptyGroup(com.agh.groupget.accounts.context.group.dto.CreateGroupRequest request) {
        com.amazonaws.services.cognitoidp.model.CreateGroupRequest awsRequest =
                cognitoRequestFactory.createGroupRequest(request.groupName, request.description);
        try {
            awsCognitoIdentityProvider.createGroup(awsRequest);
        } catch (GroupExistsException e) {
            throw new BusinessException(e.getErrorMessage());
        }
    }

    private void addUsersToGroup(String groupName, Collection<String> usernames) {
        for (String username : usernames) {
            AdminAddUserToGroupRequest awsRequest = cognitoRequestFactory.adminAddUserToGroupRequest(groupName, username);
            awsCognitoIdentityProvider.adminAddUserToGroup(awsRequest);
        }
    }

    private void validateUserExists(String username) {
        validateUsersExist(Collections.singleton(username));
    }

    private void validateUsersExist(Collection<String> usernames) {
        for (String username : usernames) {
            AdminGetUserRequest awsRequest = cognitoRequestFactory.adminGetUserRequest(username);
            try {
                awsCognitoIdentityProvider.adminGetUser(awsRequest);
            } catch (UserNotFoundException e) {
                throw new ResourceNotFoundException("User " + username + " doesn't exist.");
            }
        }
    }

    private void validateGroupExist(String groupName) {
        GetGroupRequest getGroupRequest = cognitoRequestFactory.getGroupRequest(groupName);
        try {
            awsCognitoIdentityProvider.getGroup(getGroupRequest);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Group " + groupName + " doesn't exist.");
        }
    }

    private Set<String> getGroupUsernames(String groupName) {
        ListUsersInGroupRequest usersInGroupRequest = cognitoRequestFactory.listUsersInGroupRequest(groupName);
        return awsCognitoIdentityProvider.listUsersInGroup(usersInGroupRequest)
                                         .getUsers()
                                         .stream()
                                         .map(UserType::getUsername)
                                         .collect(Collectors.toSet());
    }
}
