package team08.oop.lms.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import team08.oop.lms.model.Role;
import team08.oop.lms.model.User;
import team08.oop.lms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), 
            user.getPassword(), 
            mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        Collection< ? extends GrantedAuthority> mapRoles = roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

        return mapRoles;
    }
}
