package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.infrastructure.CognitoRequestFactory;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminListGroupsForUserRequest;
import com.amazonaws.services.cognitoidp.model.GroupType;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class GroupService {

    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
    private final AdminListGroupsForUserRequest adminListGroupsForUserRequest;

    GroupService(AWSCognitoIdentityProvider awsCognitoIdentityProvider, CognitoRequestFactory   cognitoRequestFactory) {
        this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
        adminListGroupsForUserRequest = cognitoRequestFactory.adminListGroupsForUserRequest();
    }

    Set<String> userGroups(String username) {
        adminListGroupsForUserRequest.setUsername(username);
        return awsCognitoIdentityProvider.adminListGroupsForUser(adminListGroupsForUserRequest)
                .getGroups()
                .stream()
                .map(GroupType::getGroupName)
                .collect(Collectors.toSet());
    }
}
