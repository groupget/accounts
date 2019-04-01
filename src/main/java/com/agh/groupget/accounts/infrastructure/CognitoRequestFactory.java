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

    public AdminGetUserRequest adminGetUserRequest() {
        AdminGetUserRequest adminGetUserRequest = new AdminGetUserRequest();
        adminGetUserRequest.setUserPoolId(userPoolId);
        return adminGetUserRequest;
    }

    public AdminListGroupsForUserRequest adminListGroupsForUserRequest() {
        AdminListGroupsForUserRequest adminListGroupsForUserRequest = new AdminListGroupsForUserRequest();
        adminListGroupsForUserRequest.setUserPoolId(userPoolId);
        return adminListGroupsForUserRequest;
    }
}