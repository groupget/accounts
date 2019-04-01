package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.domain.UserBasicInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/groups")
class GroupEndpoint {

    private final UserBasicInfo userBasicInfo;
    private final GroupService groupService;

    GroupEndpoint(UserBasicInfo userBasicInfo, GroupService groupService) {
        this.userBasicInfo = userBasicInfo;
        this.groupService = groupService;
    }

    @GetMapping("/userGroups")
    ResponseEntity<Set<String>> userGroups() {
        String username = userBasicInfo.username();
        Set<String> userGroups = groupService.userGroups(username);
        return ResponseEntity.ok(userGroups);
    }
}
