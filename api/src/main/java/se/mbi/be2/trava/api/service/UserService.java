package se.mbi.be2.trava.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.mbi.be2.trava.api.model.UserEntity;
import se.mbi.be2.trava.api.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Slå upp användaren från databasen
        UserEntity userFromDb = userRepository.findByName(username);

        if (userFromDb != null) {
            List<GrantedAuthority> grants = Collections.emptyList();
            return new User(userFromDb.getName(), userFromDb.getPassword(), grants);
        }

        throw new UsernameNotFoundException(username);
    }

    public void createUser(String username, String cleartextPassword) {
        String hashedPassword = passwordEncoder.encode(cleartextPassword);
        userRepository.save(new UserEntity(username, hashedPassword));
    }

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
