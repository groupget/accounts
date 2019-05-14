package com.agh.groupget.accounts.infrastructure;

import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class CognitoRequestFactory {

    private final String userPoolId;

    CognitoRequestFactory(@Value("${security.cognito.userPoolId}") String userPoolId) {
        this.userPoolId = userPoolId;
    }

    public ListUsersRequest listUsersRequest() {
        ListUsersRequest listUsersRequest = new ListUsersRequest();
        listUsersRequest.setUserPoolId(userPoolId);
        return listUsersRequest;
    }

    public ListGroupsRequest listGroupsRequest() {
        ListGroupsRequest listGroupsRequest = new ListGroupsRequest();
        listGroupsRequest.setUserPoolId(userPoolId);
        return listGroupsRequest;
    }

    public AdminDeleteUserRequest adminDeleteUserRequest() {
        AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest();
        adminDeleteUserRequest.setUserPoolId(userPoolId);
        return adminDeleteUserRequest;
    }

    public AdminGetUserRequest adminGetUserRequest(String username) {
        AdminGetUserRequest request = new AdminGetUserRequest();
        request.setUsername(username);
        request.setUserPoolId(userPoolId);
        return request;
    }

    public AdminListGroupsForUserRequest adminListGroupsForUserRequest(String username) {
        AdminListGroupsForUserRequest request = new AdminListGroupsForUserRequest();
        request.setUserPoolId(userPoolId);
        request.setUsername(username);
        return request;
    }

    public CreateGroupRequest createGroupRequest(String groupName, String description) {
        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setUserPoolId(userPoolId);
        createGroupRequest.setGroupName(groupName);
        createGroupRequest.setDescription(description);
        return createGroupRequest;
    }

    public AdminAddUserToGroupRequest adminAddUserToGroupRequest(String groupName, String username) {
        AdminAddUserToGroupRequest request = new AdminAddUserToGroupRequest();
        request.setUserPoolId(userPoolId);
        request.setGroupName(groupName);
        request.setUsername(username);
        return request;
    }

    public AdminRemoveUserFromGroupRequest adminRemoveUserFromGroupRequest(String groupName, String username) {
        AdminRemoveUserFromGroupRequest request = new AdminRemoveUserFromGroupRequest();
        request.setGroupName(groupName);
        request.setUsername(username);
        request.setUserPoolId(userPoolId);
        return request;
    }

    public GetGroupRequest getGroupRequest(String groupName) {
        GetGroupRequest request = new GetGroupRequest();
        request.setGroupName(groupName);
        request.setUserPoolId(userPoolId);
        return request;
    }

    public ListUsersInGroupRequest listUsersInGroupRequest(String groupName) {
        ListUsersInGroupRequest request = new ListUsersInGroupRequest();
        request.setGroupName(groupName);
        request.setUserPoolId(userPoolId);
        return request;
    }
}
