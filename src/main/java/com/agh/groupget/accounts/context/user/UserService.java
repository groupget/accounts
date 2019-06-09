package com.agh.groupget.accounts.context.user;

import com.agh.groupget.accounts.context.user.dto.UserInvitationsDto;
import com.agh.groupget.accounts.domain.Invitation;
import com.agh.groupget.accounts.domain.InvitationRepository;
import com.agh.groupget.accounts.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
final class UserService {

    private final InvitationRepository invitationRepository;

    UserService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    UserInvitationsDto userInvitations(String username) {
        Set<Invitation> invitations = invitationRepository.findByUsername(username)
                                                          .orElse(Collections.emptySet());
        Set<String> groupNames = invitations.stream()
                                            .map(Invitation::getGroupName)
                                            .collect(Collectors.toSet());

        UserInvitationsDto userInvitationsDto = new UserInvitationsDto();
        userInvitationsDto.username = username;
        userInvitationsDto.groupNames = groupNames;
        return userInvitationsDto;
    }

    void deleteInvitation(String groupName, String username) {
        Invitation invitation = invitationRepository.findByUsernameAndGroupName(username, groupName)
                                                    .orElseThrow(() ->
                                                            new ResourceNotFoundException("Invivation to group " + groupName + " doesn't exist."));
        invitationRepository.delete(invitation);
    }
}
