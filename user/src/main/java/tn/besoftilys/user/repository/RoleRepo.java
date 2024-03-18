package tn.besoftilys.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.besoftilys.user.entity.Enum.ERole;
import tn.besoftilys.user.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role>findByName(ERole eRole);
}
