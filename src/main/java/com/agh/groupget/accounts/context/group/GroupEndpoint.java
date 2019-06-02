package com.agh.groupget.accounts.context.group;

import com.agh.groupget.accounts.context.group.dto.CreateGroupRequest;
import com.agh.groupget.accounts.context.group.dto.GroupDetailsDto;
import com.agh.groupget.accounts.context.group.dto.InviteUserToGroupRequest;
import com.agh.groupget.accounts.domain.UserBasicInfo;
import com.agh.groupget.accounts.domain.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/{groupName}")
    ResponseEntity<GroupDetailsDto> groupDetails(@PathVariable String groupName) {
        GroupDetailsDto groupDetailsDto = groupService.groupDetails(groupName);
        return ResponseEntity.ok(groupDetailsDto);
    }

    @PostMapping("/{groupName}/invitations")
    HttpStatus inviteUserToGroup(@PathVariable String groupName, @RequestBody @Valid InviteUserToGroupRequest request) {
        if (!userBasicInfo.isUserInGroup(groupName)) {
            throw new ForbiddenException();
        }

        groupService.inviteUserToGroup(groupName, request);
        return HttpStatus.OK;
    }

    @PostMapping("/{groupName}/users")
    HttpStatus addUserToGroup(@PathVariable String groupName) {
        groupService.addUserToGroup(groupName, userBasicInfo.username());
        return HttpStatus.OK;
    }

    @PostMapping
    HttpStatus createGroup(@RequestBody @Valid CreateGroupRequest request) {
        groupService.createGroup(request);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{groupName}/users/{username}")
    HttpStatus deleteUserFromGroup(@PathVariable String groupName, @PathVariable String username) {
        if (!userBasicInfo.isUserInGroup(groupName)) {
            throw new ForbiddenException();
        }

        groupService.deleteUserFromGroup(groupName, username);
        return HttpStatus.OK;
    }
}
