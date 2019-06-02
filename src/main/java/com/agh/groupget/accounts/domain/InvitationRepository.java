package com.agh.groupget.accounts.domain;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@EnableScan
public interface InvitationRepository extends CrudRepository<Invitation, String> {

    Optional<Set<Invitation>> findByUsername(String username);

    Optional<Invitation> findByUsernameAndGroupName(String username, String groupName);
}
