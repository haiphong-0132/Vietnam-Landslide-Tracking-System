package team08.oop.lms.service;

import java.util.List;
import java.time.LocalDateTime;

import team08.oop.lms.DTO.UserDTO;
import team08.oop.lms.model.User;



public interface UserService {
    User save(UserDTO userDTO);

    List<User> getAllUsers();

    String sendEmail(User user);

    boolean hasExipred(LocalDateTime expiryDateTime);

    void updateUser(User user);

    User findByEmail(String email);

    List<User> getUsersWithAlerts();
}