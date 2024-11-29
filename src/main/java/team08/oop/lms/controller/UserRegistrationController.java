package team08.oop.lms.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import team08.oop.lms.DTO.UserDTO;
import team08.oop.lms.model.PasswordResetToken;
import team08.oop.lms.repository.TokenRepository;
import team08.oop.lms.model.User;
import team08.oop.lms.repository.UserRepository;
import team08.oop.lms.service.UserService;

@Controller
public class UserRegistrationController {

	private UserService userService;
	private UserRepository userRepository;
	private TokenRepository tokenRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserRegistrationController(UserService userService, UserRepository userRepository, TokenRepository tokenRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
	}
	
	@ModelAttribute("user")
    public UserDTO userDTO(){
		return new UserDTO();
	}
	
	@GetMapping("/registration")
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") UserDTO userDTO) {
		userService.save(userDTO);
		return "redirect:/registration?success";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}
	
	@PostMapping("/forgotPassword")
	public String forgotPasswordProcess(@ModelAttribute UserDTO userDTO) {
		String output = "";
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			output = userService.sendEmail(user);
		}
		if (output.equals("success")){
			return "redirect:/forgotPassword?success";
		}
		return "redirect:/login?error";
	}

	@GetMapping("/resetPassword/{token}")
	public String resetPasswordForm(@PathVariable String token, Model model) {
		PasswordResetToken reset = tokenRepository.findByToken(token);
		if (reset != null && userService.hasExipred(reset.getExpiryDateTime())) {
			model.addAttribute("email", reset.getUser().getEmail());
			return "resetPassword";
		}
		return "redirect:/forgotPassword?error";
	}
	
	@PostMapping("/resetPassword")
	public String passwordResetProcess(@ModelAttribute UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userRepository.save(user);
		}
		return "redirect:/login";
	}
		
}