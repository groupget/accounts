package com.agh.groupget.accounts.context.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/users")
class UserEndpoint {

    private final UserService userService;

    UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/usernames")
    ResponseEntity<Set<String>> allUsernames() {
        Set<String> allUsernames = userService.getAllUsernames();
        return ResponseEntity.ok(allUsernames);
    }

//    @GetMapping("/user")
//    ResponseEntity<UserBasicInfo> user() {}

//    @DeleteMapping("/{username}")
//    HttpStatus deleteUser(@PathVariable String username) {
//        userService.deleteUser(username);
//        return HttpStatus.OK;
//    }
}
