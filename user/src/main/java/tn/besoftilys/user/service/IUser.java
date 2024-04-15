package tn.besoftilys.user.service;

import org.springframework.http.ResponseEntity;
import tn.besoftilys.user.payload.request.LoginRequest;
import tn.besoftilys.user.payload.request.SignupRequest;

public interface IUser {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
}
