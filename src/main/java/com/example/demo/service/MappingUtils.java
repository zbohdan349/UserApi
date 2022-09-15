package com.example.demo.service;

import com.example.demo.exception.BadUserCreation;
import com.example.demo.exception.EmailException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

@Component
public class MappingUtils {

    @Value("${availableage}")
    private  int availableAge;

    @Autowired
    private UserRepository userRepository;
    public<T,F> F map( T from, F to)  {
        try {
            Field[] m1=from.getClass().getDeclaredFields();
            for (int i = 0; i < m1.length; i++) {


                String field = firstToUpperCase(m1[i].getName());
                String nameOfGetMethod ="get"+ field;
                String nameOfSetMethod ="set"+ field;
                Method getMethod = from.getClass().getMethod(nameOfGetMethod);
                Method setMethod = to.getClass().getMethod(nameOfSetMethod,getMethod.getReturnType());

                switch (field){
                    case "Birthday":
                        LocalDate localDate = (LocalDate) getMethod.invoke(from);
                            if(localDate!= null){
                                if(localDate.isBefore(LocalDate.now().minusYears(availableAge)))
                                    setMethod.invoke(to,localDate);
                                else throw new BadUserCreation("The user is not old enough to register");
                            }
                        break;
                    case "Email":
                        String email = (String) getMethod.invoke(from);
                            if(email!= null){
                                if(!userRepository.isUserExist(email)){
                                    setMethod.invoke(to,email);
                                }
                                else throw new EmailException("User with email: " + email + " exist");
                            }
                        break;
                    default:
                        if(getMethod.invoke(from)!=null)setMethod.invoke(to,getMethod.invoke(from));
                        break;
                }

            }
            return to;
        }catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException exception ){
            exception.getStackTrace();
        }
        return to;
    }
    private  String firstToUpperCase(String s){
        return  s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
