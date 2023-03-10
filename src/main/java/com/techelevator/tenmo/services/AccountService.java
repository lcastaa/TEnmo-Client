package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import io.cucumber.core.internal.gherkin.Token;
import io.cucumber.java.bs.A;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class AccountService {

    public static final String API_URL = "http://localhost:8080/account/";
    private final RestTemplate RESTTEMPLATE = new RestTemplate();

    private final AuthenticatedUser authenticatedUser;

    public AccountService(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }




    //gets account balance by feeding in a USERID
    public Account getAccountBalance(int id){
        Account account = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Void> entity = new HttpEntity<Void>(headers);
        ResponseEntity<Account> response = RESTTEMPLATE.exchange(API_URL+"user/"+id ,HttpMethod.GET ,entity, Account.class);
        account = response.getBody();
        return account;
    }



    //gets all accounts
    public Account[] getAllAccounts(){
        Account[] account = {};
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Void> entity = new HttpEntity<Void>(headers);
        ResponseEntity<Account[]> response = RESTTEMPLATE.exchange(API_URL+"users" ,HttpMethod.GET ,entity, Account[].class);
        account = response.getBody();
        return account;
    }


    //gets a single account by feeding in a USERID
    public Account getAccount(int id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Void> entity = new HttpEntity<Void>(headers);
        ResponseEntity<Account> response = RESTTEMPLATE.exchange(API_URL+"user/" + id ,HttpMethod.GET ,entity, Account.class);
        return response.getBody();
    }



}
