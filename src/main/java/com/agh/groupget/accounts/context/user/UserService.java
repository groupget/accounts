package com.agh.groupget.accounts.context.user;

import com.agh.groupget.accounts.context.user.dto.UserInvitationsDto;
import com.agh.groupget.accounts.domain.InvitationRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
final class UserService {

    private final InvitationRepository invitationRepository;

    UserService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    UserInvitationsDto userInvitations(String username) {
        Set<String> userInvitations = invitationRepository.findUserInvitations(username);
        UserInvitationsDto userInvitationsDto = new UserInvitationsDto();
        userInvitationsDto.username = username;
        userInvitationsDto.groupNames = userInvitations;
        return userInvitationsDto;
    }
}
