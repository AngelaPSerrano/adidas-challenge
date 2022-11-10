package com.adidas.backend.prioritysaleservice.controllers;

import com.adidas.backend.prioritysaleservice.models.UserBean;
import com.adidas.backend.prioritysaleservice.services.PrioritySaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;


@RestController
public class PrioritySaleController {

    @Autowired
    PrioritySaleService service;


    /**
     * Receives and processes received data from Public Service component
     * @param name
     * @param email
     * @return ResponseEntity with result
     */
    @PostMapping(path = "/saved")
    public ResponseEntity receiveData(@Value("name") final String name,
                                      @Value("email") final String email) {

            if(service.checkEmail(email)){
                HttpEntity<UserBean> request = new HttpEntity<UserBean>(new UserBean(name,email));
                try {
                    new RestTemplate().postForObject("http://localhost:8080/adiclub", request, UserBean.class);
                    return new ResponseEntity("All good", HttpStatus.CREATED);
                } catch (HttpClientErrorException e) {
                    e.printStackTrace();
                    return new ResponseEntity("BAD", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                service.addToNoAdiclubQueue(new UserBean(name, email));
            }
            return new ResponseEntity(null, HttpStatus.OK);
    }

    /**
     * Checks with adiclub-service data about the client
     * @param name
     * @param email
     * @param points
     * @param registrationDate
     * @return ResponseEntity with result
     */
    @GetMapping(path = "/checkuser")
    public ResponseEntity checkUser(@Value("points") final Integer points,
                                    @Value("name") final String name,
                                    @Value("email") final String email,
                                    @Value("registrationDate") final Instant registrationDate){

            service.addToAdiclubQueue(new UserBean(name, email, points,registrationDate));

        return new ResponseEntity(null, HttpStatus.OK);

    }

    /**
     * Sends clients information to email sender component
     * @return ResponseEntity with result
     */
    @PutMapping(path = "/select")
    public ResponseEntity selectClients() {
        List<UserBean> choosen = service.selectClients();
        for (int i = 0; i < choosen.size(); i++) {
            HttpEntity<UserBean> request = new HttpEntity<UserBean>(choosen.get(i));
            try {
                new RestTemplate().postForObject("http://localhost:8080/email", request, UserBean.class);
                return new ResponseEntity("All good", HttpStatus.CREATED);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
                return new ResponseEntity("BAD", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;


    }


}
