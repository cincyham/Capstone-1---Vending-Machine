package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Purchase {

    private BigDecimal moneyFed;
    private BigDecimal totalMoneyMade;
    private int numOfTimesLogged = 0;
    private Map<String, Item> itemMap = new TreeMap<>();


    //constructor
    public Purchase(){
        this.moneyFed = new BigDecimal(0.00);
        this.totalMoneyMade = new BigDecimal(0.00);
    }


    //second option of main menu

    public void purchase() {

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
                feedMoney();
            } else if (choice.equals(PURCHASE_OPTIONS[1])) {
                selectProduct();
            } else {
                userChoice = false;
                logChanges(this.moneyFed, "GIVE CHANGE");

                //takes away the decimals

                returnMoney();

            }
        }
    }


    // Creating a map of the item slots and their object values


    public void createMapOfItems(File file) {

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
                        System.out.println("Type not found.");
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    // Get the list of all Products

    public void printItemMap() {
        for(Map.Entry<String, Item> item : itemMap.entrySet()){
            System.out.println(item.getValue().toString());
        }
    }


    //Returns change in least amount of coins


    private void returnMoney() {

        BigDecimal wholeNumber = moneyFed.setScale(0, RoundingMode.FLOOR);
        System.out.println();
        System.out.printf("Money returned: $%s\n", this.moneyFed);
        System.out.println();

        if (wholeNumber.signum() > 0){
            System.out.printf("Dollar Bills: $%s\n", wholeNumber);
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

                if (i == 0 && decimalNumber.divide(QUARTER).signum() > 0){

                    coinName = "Quarter";
                    coin = new BigDecimal("0.25");

                }
                else if (i == 1 && decimalNumber.divide(DIME).signum() > 0){

                    coinName = "Dime";
                    coin = new BigDecimal("0.10");

                }
                else if(i == 2 && decimalNumber.divide(NICKLE).signum() > 0) {

                    coinName = "Nickel";
                    coin = new BigDecimal("0.05");

                }
                else {continue;}


                //TODO clean this up!!
                BigDecimal[] array = new BigDecimal[3];
                array[0] = coin;
                array[1] = decimalNumber.divide(coin,0, RoundingMode.FLOOR);
                array[2] = array[1].multiply(coin);


                System.out.printf("%s(s): $%s\n", coinName,array[2]);
                decimalNumber = decimalNumber.subtract(array[2]);

            }
        }
        this.moneyFed = moneyFed.ZERO;
    }


    // Selecting the Product to buy


    public void selectProduct() {

        // Getting user Input and their product choice

        System.out.println();
        printItemMap();

        Scanner userInput = new Scanner(System.in);
        System.out.println();

        System.out.print("Please enter a Product slot to purchase >>> ");
        String productSlot = userInput.nextLine();
        System.out.println();


        productSelectionPurchase(productSlot);
    }


    //First option of second menu: Feeding money into the machine


    public void feedMoney() {

        System.out.println();
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please feed Money >>> ");

        BigDecimal userNumber = new BigDecimal(Double.parseDouble(userInput.nextLine()));
        if (userNumber.signum() > 0){
            moneyFed = moneyFed.add(userNumber);
            logChanges(userNumber, "FEED MONEY");
        } else {
            System.out.println();
            System.out.println("Error. Amount must be greater than zero.");
        }

    }


    //  Purchasing selected product

    private void productSelectionPurchase (String productSlot){

        if (productSlot != null && itemMap.containsKey(productSlot.toLowerCase())) {

            //Getting Key Value pair Object as a variable

            Item item = itemMap.get(productSlot);

            //Getting Variables for the checks

            boolean getQuantity = item.getQuantity() > 0;
            boolean getPriceToMoneyFed = item.getPrice().compareTo(moneyFed) <= 0;

            if (getQuantity && getPriceToMoneyFed) {

                // Money actions associated with purchase

                moneyFed = moneyFed.subtract(item.getPrice());
                totalMoneyMade = totalMoneyMade.add(item.getPrice());

                // Return item message and minus quantity of item

                item.minusQuantity();
                System.out.printf("%s $%s: %s\n",item.getName(), item.getPrice(), item.getMessage());

                // Log

                logChanges(item.getPrice(), (item.getName() + " " + item.getItemSlot()));

            } else {

                String quantityErrorOutput = "Product not available. Not enough stock.";
                String moneyErrorOutput = "Error. Not enough Money.";
                System.out.println(!getQuantity ? quantityErrorOutput : moneyErrorOutput);
            }
        } else {

            System.out.println("Product Slot does not exist.");
        }
    }


    // Logging changes


    public void logChanges(BigDecimal money, String typeOfChange) {

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
            } else {
                writer.printf("%s %s: $%.2f $%.2f\n", date, typeOfChange, money, moneyFed);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }






}