package com.example.CENProj.service;

import com.example.CENProj.model.Dto.UserCreatedDto;
import com.example.CENProj.model.PasswordResetToken;
import com.example.CENProj.model.User;
import com.example.CENProj.model.enums.UserType;
import com.example.CENProj.repository.PasswordResetTokenRepository;
import com.example.CENProj.repository.UserRepository;
import com.example.CENProj.service.interfaces.SecurityAdminService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void forgotPassword(String userId, String newData) {

    }


    @Override
    public void logSecurityEvent(String eventDetails) {

    }

    @Override
    public User getUserByEmail(String email) {

        return this.userRepository.findByEmail(email);
    }

    @Override
    public User forgotPassword(String email) {
        return null;
    }



    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public void initiateForgotPassword(String email) {
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOpt.isEmpty()) return;

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiration(LocalDateTime.now().plusMinutes(15));
        passwordResetTokenRepository.save(resetToken);

        String resetUrl = "http://localhost:8080/user/reset-password?token=" + token;
        EmailService.send(email, "Password Reset Link", "Click to reset: " + resetUrl);
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetTokenOpt = passwordResetTokenRepository.findByToken(token);
        if (resetTokenOpt.isEmpty()) return false;

        PasswordResetToken resetToken = resetTokenOpt.get();
        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) return false;

        User user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
        return true;
    }

    public boolean changePassword(User user, String password){
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(pw_hash);
        this.userRepository.save(user);

        return true;
    }

    @PostConstruct
    public void initMockUser() {
        if (userRepository.findByEmail("client@test.com") == null) {
            User user = User.builder()
                    .email("client@test.com")
                    .firstName("Peter")
                    .lastName("Clarke")
                    .userType(UserType.CLIENT)
                    .password(new BCryptPasswordEncoder().encode("test123"))
                    .build();
            userRepository.save(user);
        }
    }

}
