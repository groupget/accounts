package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.domain.exception.BusinessException;
import com.agh.groupget.accounts.infrastructure.CognitoRequestFactory;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.stereotype.Service;

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

    //todo: n+1...
    void createGroup(CreateGroupRequest request) {
        validateUsersExist(request);
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
        for (String username : request.usernames) {
            AdminAddUserToGroupRequest awsRequest = cognitoRequestFactory.adminAddUserToGroupRequest(request.groupName, username);
            awsCognitoIdentityProvider.adminAddUserToGroup(awsRequest);
        }
    }

    private void validateUsersExist(CreateGroupRequest request) {
        for (String username : request.usernames) {
            AdminGetUserRequest awsRequest = cognitoRequestFactory.adminGetUserRequest(username);
            try {
                awsCognitoIdentityProvider.adminGetUser(awsRequest);
            } catch (UserNotFoundException e) {
                throw new BusinessException("Username " + username + " doesn't exist.");
            }
        }
    }
}
