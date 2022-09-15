package com.example.demo.model;

import com.example.demo.exception.BadUserCreation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.regex.Pattern;


public class User {
    @NonNull
    private Integer id;
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @Nullable
    private String address;
    @Nullable
    private String phone;
    @NonNull
    private LocalDate birthday;

    public User() {
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);

        if(pattern.matcher(email).matches()){
            this.email = email;
        }
        else throw new BadUserCreation("Email: " + email + " is not valid");

    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @NonNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(@NonNull LocalDate birthday) {
        LocalDate now= LocalDate.now();
        if(now.isAfter(birthday)){
            this.birthday = birthday;
        }
        else throw new BadUserCreation("Date: " + birthday + " is not correct");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email)|| id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
