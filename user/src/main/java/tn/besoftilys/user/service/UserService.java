package tn.besoftilys.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.besoftilys.user.entity.User;
import tn.besoftilys.user.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class UserService implements IUser{

    private final UserRepo userRepo;
    @Override
    public void add(User user) {
        userRepo.save(user);
    }
}
