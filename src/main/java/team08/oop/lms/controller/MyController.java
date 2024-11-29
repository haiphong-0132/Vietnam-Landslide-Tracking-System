package team08.oop.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import team08.oop.lms.model.FinalResult;
import team08.oop.lms.model.User;
import team08.oop.lms.service.FinalResultService;
import team08.oop.lms.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyController {

    @Autowired
    private FinalResultService f;

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @PostMapping("/submit-cities")
    @ResponseBody
    public FinalResult getFinalResult(
            @RequestParam(name = "cities", required = false) List<String> cities,
            @RequestParam(name = "construction_types", required = false) List<String> constructionTypes) {
        if (cities == null)
            cities = new ArrayList<>();
        if (constructionTypes == null)
            constructionTypes = new ArrayList<>();

        List<String> types = new ArrayList<>();

        for (int i = 0; i < constructionTypes.size(); i++) {
            try {
                String[] temp = constructionTypes.get(i).split("\\s+");
                types.add(temp[0]);
                constructionTypes.set(i, temp[1]);
            } catch (Exception e) {

            }
        }

        FinalResult finalResult = f.generateFinalResult(cities, types, constructionTypes);
        return finalResult;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateUser(@RequestParam boolean receiveAlerts, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User currentUser = userService.findByEmail(email);

            System.out.println("Received receiveAlerts: " + receiveAlerts);

            currentUser.setReceiveAlerts(receiveAlerts); 
            userService.updateUser(currentUser);

            return ResponseEntity.ok("Cập nhật thành công!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần đăng nhập để thực hiện thao tác này.");
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/getNotificationStatus")
    @ResponseBody
    public Map<String, Boolean> getNotificationStatus(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email);
        return Collections.singletonMap("receiveAlerts", currentUser.isReceiveAlerts());
    }

    @GetMapping("/user-info")
    @ResponseBody
    public Map<String, String> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        Map<String, String> userInfo = new HashMap<>();
        if (user != null) {
            userInfo.put("name", user.getFirstName() + " " + user.getLastName());
            userInfo.put("email", user.getEmail());
            userInfo.put("address", user.getAddress());
        }

        return userInfo;
    }
}
