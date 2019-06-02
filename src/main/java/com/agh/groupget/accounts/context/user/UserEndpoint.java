package com.agh.groupget.accounts.context.user;

import com.agh.groupget.accounts.context.user.dto.UserInvitationsDto;
import com.agh.groupget.accounts.domain.UserBasicInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/users")
final class UserEndpoint {

    private final UserBasicInfo userBasicInfo;
    private final UserService userService;

    UserEndpoint(UserBasicInfo userBasicInfo, UserService userService) {
        this.userBasicInfo = userBasicInfo;
        this.userService = userService;
    }

    @GetMapping("/invitations")
    ResponseEntity<UserInvitationsDto> userInvitations() {
        UserInvitationsDto userInvitationsDto = userService.userInvitations(userBasicInfo.username());
        return ResponseEntity.ok(userInvitationsDto);
    }
}
