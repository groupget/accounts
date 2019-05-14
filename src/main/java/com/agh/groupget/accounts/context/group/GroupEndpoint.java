package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.domain.UserBasicInfo;
import com.agh.groupget.accounts.domain.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/groups")
final class GroupEndpoint {

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

    @GetMapping("/{groupName}")
    ResponseEntity<GroupDetailsDto> groupDetails(@PathVariable String groupName) {
        GroupDetailsDto groupDetailsDto = groupService.groupDetails(groupName);
        return ResponseEntity.ok(groupDetailsDto);
    }

    @PostMapping
    HttpStatus createGroup(@RequestBody @Valid CreateGroupRequest request) {
        groupService.createGroup(request);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{groupName}/users/{username}")
    HttpStatus deleteUserFromGroup(@PathVariable String groupName, @PathVariable String username) {
        if (!userBasicInfo.isRemovalUsersFromGroupPermitted(groupName)) {
            throw new ForbiddenException();
        }

        groupService.deleteUserFromGroup(groupName, username);
        return HttpStatus.OK;
    }
}
