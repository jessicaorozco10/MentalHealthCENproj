package com.example.CENProj.service;

import com.example.CENProj.model.User;
import com.example.CENProj.repository.UserRepository;
import com.example.CENProj.service.interfaces.SecurityAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements SecurityAdminService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
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
