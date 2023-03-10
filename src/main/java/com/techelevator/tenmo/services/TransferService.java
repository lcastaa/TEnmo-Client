package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/transfer/";
    private static final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authenticatedUser;

    public TransferService(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
    }

    //method to GET all transfers from the server's endpoint http://localhost:8080/transfer/sent/{accountID}
    public Transfer[] getTransferById(Account account){
        Transfer[] transfers = null;
        try {
           ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL+"/sent/"+account.getAccountId(), HttpMethod.GET, makeAuthEntity(), Transfer[].class);
           transfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }


    //method to POST a transfer to the server's endpoint http://localhost:8080/transfer/send
    public void createSendTransfer(Transfer transfer) {
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        Transfer sendingTransfer = null;
        try {
            sendingTransfer = restTemplate.postForObject(API_BASE_URL + "/send", entity, Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }


    //method to GET a transfer object from the server's endpoint http://localhost:8080/transfer/details/{id}
    public Transfer viewTransfer(int id){
        Transfer transfer = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL+"/details/"+id, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfer;

    }


    //method to PUT an updated transfer to the server's endpoint http://localhost:8080/transfer/update
    public void updateRequest(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer,headers);
        try {
            restTemplate.put(API_BASE_URL + "/update", entity);

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }


    }

    //makes the httpEntity with authenticated token
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }

    //makes the httpEntity with an authenticated token plus a transfer object
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(transfer,headers);
    }




}
