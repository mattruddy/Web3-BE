package com.metamask.auth.repository;

import com.metamask.auth.model.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndUserRepo extends JpaRepository<EndUser, Long> {
    EndUser findByPublicAddressIgnoreCase(String publicAddress);
}
