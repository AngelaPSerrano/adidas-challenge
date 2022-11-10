package com.adidas.backend.publicservice.controllers;

import com.adidas.backend.publicservice.models.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adidas.backend.publicservice.services.UserService;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Sends the information provided by the user to PrioritySale Component
     * @param email
     * @param name
     * @return ResponseEntity with result
     */
    @PostMapping(path = "/add")
    public ResponseEntity receiveData(String name, String email) {
        String result = name + " : " + email;
        UserBean user = new UserBean(name, email);
        if(userService.check(user)){
            HttpEntity<UserBean> request = new HttpEntity<>(new UserBean(name, email));
            try {
                new RestTemplate().postForObject("http://localhost:8080/saved", request, UserBean.class);
                return new ResponseEntity(result + "All good", HttpStatus.CREATED);
            }catch(HttpClientErrorException e){
                e.printStackTrace();
                return new ResponseEntity(result + "BAD", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity(result + "BAD", HttpStatus.BAD_REQUEST);
        }
    }
}
