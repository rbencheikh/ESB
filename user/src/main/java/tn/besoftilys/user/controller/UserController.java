package tn.besoftilys.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.besoftilys.user.payload.request.LoginRequest;
import tn.besoftilys.user.payload.request.SignupRequest;
import tn.besoftilys.user.service.IUser;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    IUser iUser;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return iUser.authenticateUser(loginRequest);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){
        return iUser.registerUser(signUpRequest);
    }
}
