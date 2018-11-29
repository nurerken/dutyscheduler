package kz.kartel.dutyscheduler.components.user.service;

import kz.kartel.dutyscheduler.components.user.model.User;
import kz.kartel.dutyscheduler.components.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return (userRepository.getUserById(userId));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Long saveUser(User user){
        return userRepository.save(user).getId();
    }
}
