package tn.besoftilys.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.besoftilys.messages.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
