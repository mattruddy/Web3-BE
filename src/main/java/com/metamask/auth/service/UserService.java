package com.metamask.auth.service;

import com.metamask.auth.exception.ServiceException;
import com.metamask.auth.model.EndUser;
import com.metamask.auth.model.dto.PubUserResp;
import com.metamask.auth.repository.EndUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {

    @Autowired private EndUserRepo endUserRepo;

    @Transactional(readOnly = true)
    public PubUserResp getUserNonce(String publicAddress) {
        EndUser endUser = endUserRepo.findByPublicAddressIgnoreCase(publicAddress);
        if (endUser == null) {
            throw new ServiceException("Address doesnt exist");
        }

        PubUserResp resp = new PubUserResp();
        resp.setNonce(endUser.getNonce());
        resp.setPublicAddress(endUser.getPublicAddress());

        return resp;
    }
}
