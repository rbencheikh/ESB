package tn.besoftilys.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.besoftilys.user.entity.User;
import tn.besoftilys.user.service.IUser;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUser iUser;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void save (@RequestBody User user) {
        iUser.add(user);
    }
}
