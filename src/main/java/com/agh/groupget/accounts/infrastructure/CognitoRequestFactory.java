package com.agh.groupget.accounts.infrastructure;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class CognitoRequestFactory {

    private final String userPoolId;
    private final AWSCredentialsProvider credentialsProvider;

    CognitoRequestFactory(@Value("${security.cognito.userPoolId}") String userPoolId) {
        credentialsProvider = new EnvironmentVariableCredentialsProvider();
        this.userPoolId = userPoolId;
    }

    public ListUsersRequest listUsersRequest() {
        ListUsersRequest listUsersRequest = new ListUsersRequest();
        listUsersRequest.setUserPoolId(userPoolId);
        listUsersRequest.setRequestCredentialsProvider(credentialsProvider);
        return listUsersRequest;
    }

    public ListGroupsRequest listGroupsRequest() {
        ListGroupsRequest listGroupsRequest = new ListGroupsRequest();
        listGroupsRequest.setUserPoolId(userPoolId);
        listGroupsRequest.setRequestCredentialsProvider(credentialsProvider);
        return listGroupsRequest;
    }

    public AdminDeleteUserRequest adminDeleteUserRequest() {
        AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest();
        adminDeleteUserRequest.setUserPoolId(userPoolId);
        adminDeleteUserRequest.setRequestCredentialsProvider(credentialsProvider);
        return adminDeleteUserRequest;
    }

    public AdminGetUserRequest adminGetUserRequest(String username) {
        AdminGetUserRequest request = new AdminGetUserRequest();
        request.setUsername(username);
        request.setUserPoolId(userPoolId);
        request.setRequestCredentialsProvider(credentialsProvider);
        return request;
    }

    public AdminListGroupsForUserRequest adminListGroupsForUserRequest(String username) {
        AdminListGroupsForUserRequest request = new AdminListGroupsForUserRequest();
        request.setUserPoolId(userPoolId);
        request.setUsername(username);
        request.setRequestCredentialsProvider(credentialsProvider);
        return request;
    }

    public CreateGroupRequest createGroupRequest(String groupName, String description) {
        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setUserPoolId(userPoolId);
        createGroupRequest.setGroupName(groupName);
        createGroupRequest.setDescription(description);
        createGroupRequest.setRequestCredentialsProvider(credentialsProvider);
        return createGroupRequest;
    }

    public AdminAddUserToGroupRequest adminAddUserToGroupRequest(String groupName, String username) {
        AdminAddUserToGroupRequest request = new AdminAddUserToGroupRequest();
        request.setUserPoolId(userPoolId);
        request.setGroupName(groupName);
        request.setUsername(username);
        request.setRequestCredentialsProvider(credentialsProvider);
        return request;
    }

    public AdminRemoveUserFromGroupRequest adminRemoveUserFromGroupRequest(String groupName, String username) {
        AdminRemoveUserFromGroupRequest request = new AdminRemoveUserFromGroupRequest();
        request.setGroupName(groupName);
        request.setUsername(username);
        request.setUserPoolId(userPoolId);
        request.setRequestCredentialsProvider(credentialsProvider);
        return request;
    }

    public GetGroupRequest getGroupRequest(String groupName) {
        GetGroupRequest request = new GetGroupRequest();
        request.setGroupName(groupName);
        request.setUserPoolId(userPoolId);
        request.setRequestCredentialsProvider(credentialsProvider);
        return request;
    }

    public ListUsersInGroupRequest listUsersInGroupRequest(String groupName) {
        ListUsersInGroupRequest request = new ListUsersInGroupRequest();
        request.setGroupName(groupName);
        request.setUserPoolId(userPoolId);
        request.setRequestCredentialsProvider(credentialsProvider);
        return request;
    }
}
