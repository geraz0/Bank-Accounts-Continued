import java.util.ArrayList;
import java.util.Scanner;

public class BankAccount {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    private String accountHolder;
    private double balance;
    private int accountNumber;

    // Constructor for user input
    public BankAccount() {
        System.out.print("Enter the account holder's name: ");
        this.accountHolder = scanner.nextLine();
        System.out.print("Enter the initial balance: ");
        this.balance = scanner.nextDouble();
        scanner.nextLine();  // Handle nextDouble leftover newline
        System.out.print("Enter the account number: ");
        this.accountNumber = scanner.nextInt();
        scanner.nextLine();  // Handle nextInt leftover newline
        System.out.println(this.accountHolder + "'s account #" + this.accountNumber + " opened with $" + this.balance);
    }

    // Constructor with parameters
    public BankAccount(String name, double initialBalance, int accNumber) {
        this.accountHolder = name;
        this.balance = initialBalance;
        this.accountNumber = accNumber;
        System.out.println(this.accountHolder + "'s account #" + this.accountNumber + " opened with $" + initialBalance);
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Deposited $" + amount + " into " + this.accountHolder + "'s account. Total balance is now $" + this.balance);
    }

    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Withdrew $" + amount + " from " + this.accountHolder + "'s account. Remaining balance is $" + this.balance);
        } else {
            System.out.println("Failed to withdraw $" + amount + " due to insufficient funds in " + this.accountHolder + "'s account.");
        }
    }

    public void displayBalance() {
        System.out.println(this.accountHolder + "'s current balance: $" + String.format("%.2f", this.balance));
    }

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Hello world! Welcome to the Bank of Matt!");
            if (accounts.isEmpty()) {
                System.out.println("No accounts found. Please create a new account.");
                BankAccount newAccount = new BankAccount();
                accounts.add(newAccount);
                continue;
            }

            System.out.println("Enter account number or -1 to create a new account:");
            int accountNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (accountNumber == -1) {
                BankAccount newAccount = new BankAccount();
                accounts.add(newAccount);
                continue;
            }

            BankAccount currentAccount = findAccount(accountNumber);
            if (currentAccount == null) {
                System.out.println("Account doesn't exist.");
                continue;
            }

            System.out.println("Hello " + currentAccount.accountHolder + "!");
            while (true) {
                System.out.println("Welcome to the Main Menu, what would you like to do today?");
                System.out.println("1. To check account balance");
                System.out.println("2. To make a withdrawal");
                System.out.println("3. To make a deposit");
                System.out.println("4. To make a transfer to another account");
                System.out.println("0. To exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        currentAccount.displayBalance();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawalAmount = scanner.nextDouble();
                        currentAccount.withdraw(withdrawalAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        currentAccount.deposit(depositAmount);
                        break;
                    case 4:
                        transfer(currentAccount);
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
                if (choice == 0) {
                    break;
                }
            }
        }
    }

    private static BankAccount findAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.accountNumber == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private static void transfer(BankAccount fromAccount) {
        System.out.print("Please enter the account number to transfer to: ");
        int toAccountNumber = scanner.nextInt();
        BankAccount toAccount = findAccount(toAccountNumber);
        if (toAccount == null) {
            System.out.println("Account doesn't exist.");
            return;
        }
        System.out.print("Please enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        if (fromAccount.balance >= amount) {
            fromAccount.balance -= amount;
            toAccount.balance += amount;
            System.out.println("Transferred $" + amount + " from " + fromAccount.accountHolder + " to " + toAccount.accountHolder);
        } else {
            System.out.println("Insufficient funds to transfer.");
        }
    }
}
