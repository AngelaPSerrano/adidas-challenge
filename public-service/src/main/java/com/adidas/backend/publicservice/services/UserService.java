package com.adidas.backend.publicservice.services;

import com.adidas.backend.publicservice.models.UserBean;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {


    static final String emailRegex = "^(.+)@(\\S+)$";

    /**
     * Checks if name is not null and email is a valid value
     * @param user
     * @return boolean
     */
    public boolean check(UserBean user) {
             return user.getName() != null && validatorEmail(user.getEmail());
    }

    /**
     * Checks if email is a valid value using a regex match
     * @param email
     * @return boolean
     */
    public static boolean validatorEmail(String email) {
        return Pattern.compile(emailRegex)
                .matcher(email)
                .matches();
    }
}
