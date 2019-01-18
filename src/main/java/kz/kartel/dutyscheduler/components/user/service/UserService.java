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

    public Integer getLastUserId(){
        return (Integer)userRepository.getLastIUserId();
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email.trim().toLowerCase());
    }

    public Long saveUser(User user){
        return userRepository.save(user).getId();
    }

    public void saveUser(String firstName, String lastName, String email, String title, String phone, String department, String address){
        userRepository.saveUser(getLastUserId() + 1, firstName, lastName, email, title, phone, department, address);
    }

    public void updateUser(String firstName, String lastName, String title, String phone,String department, String address, String email){
        userRepository.updateUser(firstName, lastName, title, phone, department, address, email);
    }
}
