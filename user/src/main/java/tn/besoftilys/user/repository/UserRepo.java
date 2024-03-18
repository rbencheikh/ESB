package tn.besoftilys.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.besoftilys.user.entity.User;

import java.util.Optional;

@Repository
public interface  UserRepo extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
