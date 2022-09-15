package com.example.demo.service;

import com.example.demo.model.UserUpdateReq;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.BadUserCreation;
import com.example.demo.model.User;
import com.example.demo.model.UserCreateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class UserService{
    @Value("${counterid}")
    private  int counterId;
    @Value("${availableage}")
    private  int availableAge;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public boolean save(UserCreateReq req){
        if(!userRepository.isUserExist(req.getEmail())){
            User user = new User();
            user.setEmail(req.getEmail());
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setPhone(req.getPhone());
            user.setAddress(req.getAddress());
            user.setBirthday(checkAge(req.getBirthday()));
            user.setId(counterId++);
            userRepository.save(user);
            return true;
        }
        else throw new BadUserCreation("This user exist");
    }
    public void update(UserUpdateReq req, Integer id){
        User user = userRepository.getUserById(id);
        userRepository.removeUser(user);
        user =mappingUtils.map(req,user);
        userRepository.update(user);
    }

    public Set<User> getAllUsers(){
        return  userRepository.getStorage();
    }

    public Set<User> getUsersByBirthDateRange(LocalDate from, LocalDate to){
        return userRepository.getUsersByBirthDateRange(from,to);
    }

    public User getUserById(Integer id){
        return userRepository.getUserById(id);
    }

    public void deleteUserById(Integer id){
        userRepository.deleteUserById(id);
    }

    private LocalDate checkAge(LocalDate localDate){
        if(localDate.isBefore(LocalDate.now().minusYears(availableAge))){
            return localDate;
        }
        throw new BadUserCreation("The user is not old enough to register");
    }
}
