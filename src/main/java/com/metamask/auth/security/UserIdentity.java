package com.metamask.auth.security;

import com.metamask.auth.model.EndUser;
import com.metamask.auth.exception.ServiceException;
import com.metamask.auth.repository.EndUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserIdentity {

    private EndUser endUser;
    @Autowired
    private EndUserRepo endUserRepo;

    @Transactional(readOnly = true)
    public void create(long userId) {
        EndUser user = endUserRepo.getOne(userId);
        if (user == null) {
            throw new ServiceException("Error");
        }
        this.endUser = user;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = endUser;
    }
}
