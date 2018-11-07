package kz.kartel.dutyscheduler.components.user.service;

import kz.kartel.dutyscheduler.components.user.model.User;
import kz.kartel.dutyscheduler.components.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.findById(userId);
    }
}
