package com.adidas.backend.prioritysaleservice.services;

import com.adidas.backend.prioritysaleservice.models.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PrioritySaleService {


    private List<UserBean> noAdiclubQueue = new ArrayList();
    private List<UserBean> adiclubQueue = new ArrayList();

    static final String emailRegex = "^(.+)@adiclub.com$";

    /**
     * Adds to the queue the new client in priority order within adiclub users
     * @param user
     */

    public void addToAdiclubQueue(UserBean user){
        UserBean userQueued;
        for (int i = 0; i < adiclubQueue.size(); i++) {
            userQueued = adiclubQueue.get(i);
            if(user.getPoints() > userQueued.getPoints()){
                adiclubQueue.add(i, user);
                break;
            }else if (user.getPoints() == userQueued.getPoints()){
                if(user.getRegistrationDate().isBefore(userQueued.getRegistrationDate())){
                    adiclubQueue.add(i, user);
                    break;
                }else{
                    adiclubQueue.add(i+1, user);
                    break;
                }
            }
        }
        if(!adiclubQueue.contains(user)){
            adiclubQueue.add(user);
        }

    }

    /**
     * Adds to the queue the new client with no adiclub users
     * @param user
     */

    public void addToNoAdiclubQueue(UserBean user){
        noAdiclubQueue.add(user);
    }

    /**
     * Selects clients to send email
     * @param
     * @return List
     */
    public List<UserBean> selectClients(){
        List<UserBean> choosen = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if(!adiclubQueue.isEmpty()) {
                choosen.add(adiclubQueue.get(0));
                adiclubQueue.remove(0);
            }else{
                choosen.add(noAdiclubQueue.get(0));
                noAdiclubQueue.remove(0);
            }
        }
        return choosen;
    }

    /**
     * Checks if email is a from adiclub using a regex match
     * @param email
     * @return boolean
     */
    public static boolean checkEmail(String email) {
        return Pattern.compile(emailRegex)
                .matcher(email)
                .matches();
    }


}
