package com.agh.groupget.accounts.context.user;

import com.agh.groupget.accounts.domain.UserBasicInfo;
import com.agh.groupget.accounts.infrastructure.CognitoRequestFactory;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class UserService {

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
    private final UserBasicInfo userBasicInfo;

    private final ListGroupsRequest listGroupsRequest;
    private final ListUsersRequest listUsersRequest;
    private final AdminDeleteUserRequest adminDeleteUserRequest;
    private final AdminGetUserRequest adminGetUserRequest;

    UserService(AWSCognitoIdentityProvider awsCognitoIdentityProvider, UserBasicInfo userBasicInfo,
                CognitoRequestFactory cognitoRequestFactory) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        this.userBasicInfo = userBasicInfo;
        listGroupsRequest = cognitoRequestFactory.listGroupsRequest();
        listUsersRequest = cognitoRequestFactory.listUsersRequest();
        adminDeleteUserRequest = cognitoRequestFactory.adminDeleteUserRequest();
        adminGetUserRequest = cognitoRequestFactory.adminGetUserRequest("");
    }

    Set<String> getAllUsernames() {
        return awsCognitoIdentityProvider.listUsers(listUsersRequest)
                .getUsers()
                .stream()
                .map(UserType::getUsername)
                .collect(Collectors.toSet());
    }

    Set<String> getAllGroupNames() {
        return awsCognitoIdentityProvider.listGroups(listGroupsRequest)
                .getGroups()
                .stream()
                .map(GroupType::getGroupName)
                .collect(Collectors.toSet());
    }

//    void deleteUser(String username) {
//        if (!userBasicInfo.group().equals("Admins")) {
//            throw new ForbiddenException();
//        }
//        adminDeleteUserRequest.setUsername(username);
//        try {
//            awsCognitoIdentityProvider.adminDeleteUser(adminDeleteUserRequest);
//        } catch (UserNotFoundException | ResourceNotFoundException e) {
//            throw new ResourceNotFoundException(username + "not found");
//        }
//        userPublisher.publishDeletion(username);
//    }
}
