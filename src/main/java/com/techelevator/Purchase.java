package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class Purchase {

    private BigDecimal moneyFed;
    private BigDecimal totalMoneyMade;
    private int numOfTimesLogged = 0;
    private Map<String, Item> itemMap = new TreeMap<>();

    public BigDecimal getMoneyFed() {
        return moneyFed;
    }

    public BigDecimal getTotalMoneyMade() {
        return totalMoneyMade;
    }

    //constructor
    public Purchase(){
        this.moneyFed = new BigDecimal(0.00);
        this.totalMoneyMade = new BigDecimal(0.00);
    }


    //second option of main menu

    public void purchaseOptions() {

        //Menu

        final String[] PURCHASE_OPTIONS = {"Feed Money", "Select Product", "Finish Transaction"};
		Menu purchaseMenu = new Menu(System.in, System.out);


        // Getting the user choice

        boolean userChoice = true;
        while (userChoice) {

            System.out.println();
            System.out.println("Current Money Provided: $" + moneyFed);
            String choice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_OPTIONS);

            if (choice.equals(PURCHASE_OPTIONS[0])) {
                System.out.println();
                Scanner userInput = new Scanner(System.in);
                System.out.print("Please feed Money >>> ");
                String userNumber = userInput.nextLine().trim();
                if(!userNumber.isEmpty()) {
                    feedMoney(userNumber);
                }
            } else if (choice.equals(PURCHASE_OPTIONS[1])) {
                System.out.println(selectProduct());
            } else {
                System.out.println();
                userChoice = false;
                logChanges(this.moneyFed, "GIVE CHANGE");
                System.out.println(returnMoney());

            }
        }
    }


    // Creating a map of the item slots and their object values


    public boolean createMapOfItems(File file) {

        if (file.exists()) {

            try (Scanner useFile = new Scanner(file)) {

                while (useFile.hasNextLine()) {

                    String line = useFile.nextLine();
                    String[] array = line.split("\\|");


                    if (array[3].equals("Chip")) {

                        itemMap.put(array[0].toLowerCase(), new Chip(array[0], array[1], new BigDecimal(array[2]), array[3], 5));

                    } else if (array[3].equals("Candy")) {

                        itemMap.put(array[0].toLowerCase(), new Candy(array[0], array[1], new BigDecimal(array[2]), array[3], 5));

                    } else if (array[3].equals("Drink")) {

                        itemMap.put(array[0].toLowerCase(), new Drink(array[0], array[1], new BigDecimal(array[2]), array[3], 5));

                    } else if (array[3].equals("Gum")) {

                        itemMap.put(array[0].toLowerCase(), new Gum(array[0], array[1], new BigDecimal(array[2]), array[3], 5));
                    } else {
                        System.out.println(array[0] + " Type not found.");
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Something went wrong with the file");
        }
        if (!itemMap.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    // Get the list of all Products

    public String getItemMap() {

        String returnString = "";
        for(Map.Entry<String, Item> item : itemMap.entrySet()){
            returnString += item.getValue().toString() + "\n";
        }
        return returnString.trim();
    }


    //Returns change in least amount of coins


    public String returnMoney() {

        String returnString = "";

        BigDecimal wholeNumber = moneyFed.setScale(0, RoundingMode.FLOOR);
        returnString += String.format("Money returned: $%.2f\n", this.moneyFed);
        returnString += "\n";

        if (wholeNumber.signum() > 0){
            returnString += String.format("Dollar Bills: $%.2f\n", wholeNumber);
        }

        BigDecimal decimalNumber = (moneyFed.subtract(wholeNumber));

        if (!decimalNumber.equals(0)){

            final BigDecimal QUARTER = new BigDecimal("0.25");
            final BigDecimal DIME = new BigDecimal("0.10");
            final BigDecimal NICKLE = new BigDecimal("0.05");

//			 .signum() method from StackOverflow

            for (int i = 0; i < 3; i++) {

                BigDecimal coin;
                String coinName;

                if (i == 0 && decimalNumber.divide(QUARTER,0, RoundingMode.FLOOR).signum() > 0){

                    coinName = "Quarter";
                    coin = new BigDecimal("0.25");

                }
                else if (i == 1 && decimalNumber.divide(DIME,0, RoundingMode.FLOOR).signum() > 0){

                    coinName = "Dime";
                    coin = new BigDecimal("0.10");

                }
                else if(i == 2 && decimalNumber.divide(NICKLE,0, RoundingMode.FLOOR).signum() > 0) {

                    coinName = "Nickel";
                    coin = new BigDecimal("0.05");

                }
                else {continue;}

                BigDecimal numOfCoins = decimalNumber.divide(coin,0, RoundingMode.FLOOR);
                BigDecimal coinValue = numOfCoins.multiply(coin);


                returnString += String.format("%s(s): $%.2f\n", coinName,coinValue);
                decimalNumber = decimalNumber.subtract(coinValue);

            }
        }
        this.moneyFed = moneyFed.ZERO;
        return returnString.trim();
    }


    // Selecting the Product to buy


    public String selectProduct() {

        // Getting user Input and their product choice

        System.out.println();
        System.out.println(getItemMap());

        Scanner userInput = new Scanner(System.in);
        System.out.println();

        System.out.print("Please enter a Product slot to purchase >>> ");
        String productSlot = userInput.nextLine();
        System.out.println();


        return productSelectionPurchase(productSlot);
    }


    //First option of second menu: Feeding money into the machine


    public void feedMoney(String userInput) {

        try {

            BigDecimal userNumber = new BigDecimal(userInput);
            if (userNumber.signum() > 0 && userNumber.remainder(new BigDecimal("0.05")).compareTo(BigDecimal.ZERO) == 0) {
                moneyFed = moneyFed.add(userNumber);
                logChanges(userNumber, "FEED MONEY");
            } else {
                System.out.println("Error. Amount must be greater than zero and a multiple of 5 cents.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number");
        }

    }


    //  Purchasing selected product

    public String productSelectionPurchase(String productSlot){

        String returnString = "";

        if (productSlot != null && productSlot.length() > 0) {

            String lowerCaseProductSlot = productSlot.trim().substring(0,1).toLowerCase() + productSlot.trim().substring(1);
            if(itemMap.containsKey(lowerCaseProductSlot)) {
                //Getting Key Value pair Object as a variable

                Item item = itemMap.get(lowerCaseProductSlot);

                //Getting Variables for the checks

                boolean getQuantity = item.getQuantity() > 0;
                boolean getPriceToMoneyFed = item.getPrice().compareTo(moneyFed) <= 0;

                if (getQuantity && getPriceToMoneyFed) {

                    // Money actions associated with purchase

                    moneyFed = moneyFed.subtract(item.getPrice());
                    totalMoneyMade = totalMoneyMade.add(item.getPrice());

                    // Return item message and minus quantity of item

                    item.minusQuantity();
                    returnString += String.format("%s $%s: %s", item.getName(), item.getPrice(), item.getMessage());

                    // Log

                    logChanges(item.getPrice(), (item.getName() + " " + item.getItemSlot()));
                    return returnString;
                } else {

                    String quantityErrorOutput = "Product not available. Not enough stock.";
                    String moneyErrorOutput = "Error. Not enough Money.";
                    return (!getQuantity ? quantityErrorOutput : moneyErrorOutput);
                }
            } else {
                return "Product slot does not exist.";
            }
        } else {
            return "Please enter a slot.";
        }
    }


    // Logging changes


    private void logChanges(BigDecimal money, String typeOfChange) {

        // Creating the File Object

        File file = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-3\\src\\main\\resources\\log.txt");

        // Creating the Date Formatter

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = formatter.format(new Date());

        boolean append = numOfTimesLogged != 0;
        if (!append) {numOfTimesLogged++;}

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, append))) {

            if (typeOfChange.equals("GIVE CHANGE")) {

                BigDecimal newMoneyFed = new BigDecimal(0);
                writer.printf("%s %s: $%.2f $%.2f\n", date, typeOfChange, money, newMoneyFed);
            } else /*if (typeOfChange.equals("FEED MONEY"))*/{
                writer.printf("%s %s: $%.2f $%.2f\n", date, typeOfChange, money, moneyFed);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }






}
