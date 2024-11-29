package team08.oop.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team08.oop.lms.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);

    User findById(long userId);

    List<User> findByReceiveAlertsTrue();
}
