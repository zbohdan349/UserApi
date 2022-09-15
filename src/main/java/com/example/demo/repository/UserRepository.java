package com.example.demo.repository;

import com.example.demo.exception.DateException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepository{
    @Autowired
    private Set<User> storage;

    public void save(User user){
        storage.add(user);
    }
    public User getUserById(Integer id){
        return storage.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(()->{throw new UserNotFoundException("User with id: "+ id +" does not exist");
                });
    }

    public void deleteUserById(Integer id){
        storage.remove(getUserById(id));
    }

    public void removeUser(User user){
        storage.remove(user);
    }

    public void update(User user){
        storage.add(user);
    }

    public boolean isUserExist(String email){
        return storage.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public Set<User> getUsersByBirthDateRange(LocalDate from, LocalDate to){
        if(from.isBefore(to))
            return storage.stream()
                .filter(user ->
                        user.getBirthday().isAfter(from.minusDays(1)))
                .filter(user ->
                        user.getBirthday().isBefore(to.plusDays(1)))
                .collect(Collectors.toSet());
        else throw new DateException("start date is greater than end date");
    }

    public Set<User> getStorage(){
        return storage;
    }

}
