package team08.oop.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team08.oop.lms.model.PasswordResetToken;
import team08.oop.lms.model.User;

import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer>{
    
    PasswordResetToken findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

}
