package com.example.CENProj.service;

import com.example.CENProj.model.Dto.UserCreatedDto;
import com.example.CENProj.model.Dto.UserDto;
import com.example.CENProj.model.User;
import com.example.CENProj.model.enums.UserType;
import com.example.CENProj.repository.UserRepository;
import com.example.CENProj.service.interfaces.SecurityAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements SecurityAdminService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    public boolean delete(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) return false;
        this.userRepository.delete(user.get());
        return true;
    }

    public UserCreatedDto createUser(String email, String password, String firstName, String lastName, UserType userType) {
        UserCreatedDto userCreatedDto = new UserCreatedDto();
        User userWithExistingEmail = this.userRepository.findByEmail(email);
        if(userWithExistingEmail != null) {
            userCreatedDto.setErrorMsg("Email already exists");
            return userCreatedDto;
        }
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder().userType(userType).email(email).publicationDate(new Date(System.currentTimeMillis()))
                .firstName(firstName).lastName(lastName).password(pw_hash).build();
        userCreatedDto.setUser(this.userRepository.save(user));
        return userCreatedDto;
    }

    public User updateUser(int id, String email, String firstName, String lastName, UserType userType) {
        Optional<User> userById = this.userRepository.findById(id);
        User userAlreadyWithEmail = this.userRepository.findByEmail(email);
        if(userById.isEmpty()) {
            return null;
        }
        if(userAlreadyWithEmail != null && userAlreadyWithEmail.getId() != id ) {
            return userById.get();
        }
        User user = userById.get();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(userType);
        this.userRepository.save(user);
        return user;
    }

    @Override
    public void provideSystemFeedback(String feedback) {

    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public void updateUser(String userId, String newData) {

    }

    @Override
    public void logSecurityEvent(String eventDetails) {

    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
