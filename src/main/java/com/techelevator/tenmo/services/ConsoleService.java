package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }


    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }


    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }


    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("6: Login to a different account");
        System.out.println("0: Exit");
        System.out.println();
    }


    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }


    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }


    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }


    //prompt for transfer constructs transfer object and ensures you are not sending to yourself.
    public Transfer promptForTransfer(Account account, String type, String status) {
        Transfer transfer = new Transfer();
        transfer.setTransferType(type);
        transfer.setTransferStatus(status);
        transfer.setAccountFrom(account.getAccountId());
        transfer.setAccountTo(promptForInt("Please enter the Account ID of the RECEIVER: "));
        while(transfer.getAccountTo() == transfer.getAccountFrom()){
            System.out.println("You CAN NOT send TEBucks yo yourself");
            transfer.setAccountTo(promptForInt("Please enter the Account ID of the RECEIVER: "));
        }
        transfer.setAmount(promptForBigDecimal("Please enter the amount (XX.XX) of TEBucks to send: $"));
        return transfer;
    }

    //on the view pending transactions prompts user to proceed or ext before accepting or rejecting transaction.
    public int promptForProceedOrMainMenu() {
        return promptForInt("Press [1] - To Proceed to next  |  Press [2] - Return to Main: ");
    }





    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }


    //prints a tranfer object by passing in a transfer object.
    public void printTransfers(Transfer transfer) {
        if (transfer == null) {
            System.out.println("No past transfers to print");
        } else {
            System.out.println(transfer.getTransferId() + "           From: " + transfer.getAccountFrom() + "         $-" + transfer.getAmount());
            System.out.println(transfer.getTransferId() + "             To: " + transfer.getAccountTo() + "         $+" + transfer.getAmount());
        }
        System.out.println("--------------------------------------------");
    }


    //prints user by passing in a user object
    public void printUser(User user) {
        System.out.println("Username: " + user.getUsername());
        System.out.println("User ID: " + user.getId());
        System.out.println("-------------------------");
    }


    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }




    //prints a transfer's details by passing in a transfer
    public void printViewTransferDetails(Transfer transfer){
        System.out.println("--------------------------------------------");
        System.out.println("           Transfer Details                   ");
        System.out.println("--------------------------------------------");
        System.out.println("Id: " + transfer.getTransferId());
        System.out.println("From: " + transfer.getAccountFrom());
        System.out.println("To: " +transfer.getAccountTo());
        System.out.println("Type: " +transfer.getTransferType());
        System.out.println("Status: " +transfer.getTransferStatus());
        System.out.println("Amount: $" +transfer.getAmount());

    }
}
