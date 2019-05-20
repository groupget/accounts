package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.domain.exception.BusinessException;
import com.agh.groupget.accounts.domain.exception.ResourceNotFoundException;
import com.agh.groupget.accounts.infrastructure.CognitoRequestFactory;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
final class GroupService {

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
    private final CognitoRequestFactory cognitoRequestFactory;

    GroupService(AWSCognitoIdentityProvider awsCognitoIdentityProvider, CognitoRequestFactory cognitoRequestFactory) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.cognitoRequestFactory = cognitoRequestFactory;
    }

    Set<String> userGroups(String username) {
        AdminListGroupsForUserRequest awsRequest = cognitoRequestFactory.adminListGroupsForUserRequest(username);
        return awsCognitoIdentityProvider.adminListGroupsForUser(awsRequest)
                                         .getGroups()
                                         .stream()
                                         .map(GroupType::getGroupName)
                                         .collect(Collectors.toSet());
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

    //todo: n+1...
    void createGroup(CreateGroupRequest request) {
        validateUserExists(request);
        createEmptyGroup(request);
        addUsersToGroup(request);
    }

    void deleteUserFromGroup(String groupName, String username) {
        AdminRemoveUserFromGroupRequest awsRequest = cognitoRequestFactory.adminRemoveUserFromGroupRequest(groupName, username);
        awsCognitoIdentityProvider.adminRemoveUserFromGroup(awsRequest);
    }

    private void createEmptyGroup(CreateGroupRequest request) {
        com.amazonaws.services.cognitoidp.model.CreateGroupRequest awsRequest =
                cognitoRequestFactory.createGroupRequest(request.groupName, request.description);
        try {
            awsCognitoIdentityProvider.createGroup(awsRequest);
        } catch (GroupExistsException e) {
            throw new BusinessException(e.getErrorMessage());
        }
    }

    private void addUsersToGroup(CreateGroupRequest request) {
        Set<String> usernames = Optional.ofNullable(request.usernames)
                                      .orElse(Collections.emptySet());
        for (String username : usernames) {
            AdminAddUserToGroupRequest awsRequest = cognitoRequestFactory.adminAddUserToGroupRequest(request.groupName, username);
            awsCognitoIdentityProvider.adminAddUserToGroup(awsRequest);
        }
    }

    private void validateUserExists(CreateGroupRequest request) {
        Set<String> usernames = Optional.ofNullable(request.usernames)
                                        .orElse(Collections.emptySet());
        for (String username : usernames) {
            AdminGetUserRequest awsRequest = cognitoRequestFactory.adminGetUserRequest(username);
            try {
                awsCognitoIdentityProvider.adminGetUser(awsRequest);
            } catch (UserNotFoundException e) {
                throw new BusinessException("Username " + username + " doesn't exist.");
            }
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
