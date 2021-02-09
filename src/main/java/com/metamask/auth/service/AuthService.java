package com.metamask.auth.service;

import com.metamask.auth.exception.ServiceException;
import com.metamask.auth.model.EndUser;
import com.metamask.auth.model.dto.LoginRequest;
import com.metamask.auth.model.dto.PubUserResp;
import com.metamask.auth.model.dto.SignUpRequest;
import com.metamask.auth.model.dto.AuthTokenResponse;
import com.metamask.auth.repository.EndUserRepo;
import com.metamask.auth.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class AuthService {

    private static Random random;
    private static final String MESSAGE = "I am signing my one time nonce ";

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Autowired private EndUserRepo endUserRepo;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public PubUserResp signup(SignUpRequest signUpRequest) {
        EndUser endUser = new EndUser();
        endUser.setPublicAddress(signUpRequest.getPublicAddress());
        endUser.setNonce(random.nextLong());
        endUserRepo.save(endUser);

        PubUserResp resp = new PubUserResp();
        resp.setPublicAddress(endUser.getPublicAddress());
        resp.setNonce(endUser.getNonce());
        return resp;
    }

    @Transactional
    public AuthTokenResponse login(LoginRequest request) {
        EndUser endUser = endUserRepo.findByPublicAddressIgnoreCase(request.getPublicAddress());

        if (endUser == null) {
            throw new ServiceException("Error");
        }

       // byte[] signByte = Numeric.hexStringToByteArray(request.getSigniture());
        try {
//            boolean isValid = verifyPrefixedMessage(
//                    Hash.sha3((MESSAGE + endUser.getNonce()).getBytes()), signByte,
//                    Numeric.cleanHexPrefix(request.getPublicAddress().toLowerCase())
//            );
           // if (isValid) {
                String token = jwtTokenProvider.createToken(endUser.getId());
                AuthTokenResponse response = new AuthTokenResponse();
                response.setToken(token);
                endUser.setNonce(random.nextLong());
                endUserRepo.save(endUser);
                return response;
          //  } else {
          //      throw new ServiceException("");
         //   }
        } catch (Exception e) {
            throw new ServiceException("Auth Error");
        }
    }


//    private static boolean verifyPrefixedMessage(byte[] data, byte[] sig, String pubKeyAddress) throws SignatureException {
//        byte[] r = Arrays.copyOfRange(sig, 0, 32);
//        byte[] s = Arrays.copyOfRange(sig, 32, 64);
//        byte[] v = Arrays.copyOfRange(sig, 64, sig.length);
//        Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
//        BigInteger recoveredPubKey = Sign.signedPrefixedMessageToKey(data, signatureData);
//        System.out.println(Keys.getAddress(recoveredPubKey));
//        System.out.println(pubKeyAddress);
//        return pubKeyAddress.equals(Keys.getAddress(recoveredPubKey));
//    }

}
