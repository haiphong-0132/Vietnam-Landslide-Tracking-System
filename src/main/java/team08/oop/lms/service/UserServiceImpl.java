package team08.oop.lms.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import team08.oop.lms.model.Role;
import team08.oop.lms.model.User;
import team08.oop.lms.DTO.UserDTO;
import team08.oop.lms.model.PasswordResetToken;
import team08.oop.lms.repository.RoleRepository;
import team08.oop.lms.repository.TokenRepository;
import team08.oop.lms.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private RoleRepository roleRepository;	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender JavaMailSender;

	@Autowired
	TokenRepository tokenRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User save(UserDTO userDTO) {
		Role userRole = roleRepository.findByName("ROLE_USER");

		if (userRole == null){
			userRole = new Role("ROLE_USER");
			roleRepository.save(userRole);
		}

		User user = new User(userDTO.getFirstName(),
				userDTO.getLastName(), userDTO.getEmail(),
				passwordEncoder.encode(userDTO.getPassword()),
				userDTO.getAddress(), userDTO.getPhoneNumber(),
				Arrays.asList(userRole), userDTO.isReceiveAlerts());

		return userRepository.save(user);
	}

	@Override
    public List<User> getAllUsers() {

		return userRepository.findAll();
    }

	public String sendEmail(User user){
		try {
			String resetLink = generateResetToken(user);

			SimpleMailMessage msg = new SimpleMailMessage();

			msg.setTo(user.getEmail());
			msg.setSubject("Reset your password");
			msg.setText("Xin chào " + user.getFirstName() + user.getLastName() + " \n\n" +
					"Vui lòng nhấp vào liên kết này để Đặt lại mật khẩu của bạn:" + resetLink
					+ ". \n\n"
					+ "Trân trọng, \n" + "Đội ngũ Admin \n" + "Vietnam Landslide Tracking System");

			JavaMailSender.send(msg);

			return "success";
		}
		catch (Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Transactional
	public String generateResetToken(User user){
		Optional<PasswordResetToken> existingToken = tokenRepository.findByUser(user);

		if (existingToken.isPresent()){
			PasswordResetToken token = existingToken.get();
			token.setToken(UUID.randomUUID().toString());
			token.setExpiryDateTime(LocalDateTime.now().plusMinutes(30));
			tokenRepository.save(token);

			return "http://localhost:8080/resetPassword/" + token.getToken();
		}

		// Nếu token chưa tồn tại, tạo mới
		PasswordResetToken newToken = new PasswordResetToken();
		newToken.setToken(UUID.randomUUID().toString());
		newToken.setExpiryDateTime(LocalDateTime.now().plusMinutes(30));
		newToken.setUser(user);
		tokenRepository.save(newToken);

		return "http://localhost:8080/resetPassword/" + newToken.getToken();
	}
	
	public boolean hasExipred(LocalDateTime expiryDateTime){
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		return expiryDateTime.isAfter(currentDateTime);
	}

	public void updateUser(User user){
		userRepository.save(user);
	}

	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}

	public List<User> getUsersWithAlerts(){
		return userRepository.findByReceiveAlertsTrue();
	}

}