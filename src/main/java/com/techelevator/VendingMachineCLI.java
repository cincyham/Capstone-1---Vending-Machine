package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;

public class VendingMachineCLI {


	// Properties

	// Main menu options 1-3 created in the array

	private static final String[] MAIN_MENU_OPTIONS = {"Display Vending Machine Items", "Purchase", "Exit"};
	private BigDecimal moneyFed;
	private BigDecimal totalMoneyMade;
	private int numOfTimesLogged = 0;
	private Menu menu;
	private Map<String, Item> itemMap = new TreeMap<>();


	// Got idea of Tree Map from https://www.baeldung.com/java-hashmap-sort to help sort itemMap

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		File file = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-3\\vendingmachine.csv");
		cli.createMapOfItems(file);
		cli.run();

	}


	// Constructor


	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.moneyFed = new BigDecimal(0.00);
		this.totalMoneyMade = new BigDecimal(0.00);
	}


	//Start of the application


	public void run() {

		boolean userChoice = true;
		while (userChoice) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTIONS[0])) {
				printItemList();
			} else if (choice.equals(MAIN_MENU_OPTIONS[1])) {
				purchase();
			} else {
				userChoice = false;
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
					itemMap.put(array[0], new Item(array[1], new BigDecimal(array[2]), array[3], 5));

				}
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}


	// Print the list of all Products


	public void printItemList() {
		for (Map.Entry<String, Item> item : itemMap.entrySet()) {
			System.out.println(item.getKey() + item.getValue().toString());
		}
	}


	// Start of the Second Main Option


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
				this.moneyFed = moneyFed.ZERO;
			}
		}
	}


	// Selecting the Product to buy


	public void selectProduct() {

		// Getting user Input and their product choice

		System.out.println();
		printItemList();

		Scanner userInput = new Scanner(System.in);
		System.out.println();

		System.out.print("Please enter a Product slot to purchase >>> ");
		String productSlot = userInput.nextLine();
		System.out.println();


		productSelectionPurchase(productSlot);
	}


		// Feeding money into the machine


		public void feedMoney() {

		//TODO add check for whole number

			System.out.println();
			Scanner userInput = new Scanner(System.in);
			System.out.print("Please feed Money (Only whole dollars) >>> ");
			BigDecimal userNumber = new BigDecimal(Double.parseDouble(userInput.nextLine()));
			moneyFed = moneyFed.add(userNumber);
			logChanges(userNumber, "FEED MONEY");
		}


		//  Purchasing selected product

		private void productSelectionPurchase (String productSlot){
			if (productSlot != null && itemMap.containsKey(productSlot)) {

				//Getting Key Value pair Object as a variable

				Item item = itemMap.get(productSlot);

				//Getting Variables for the checks

				boolean getQuantity = item.getQuantity() > 0;
				boolean getPriceToMoneyFed = item.getPrice().compareTo(moneyFed) <= 0;

				if (getQuantity && getPriceToMoneyFed) {

					// Money actions associated with purchase

					moneyFed = moneyFed.subtract(item.getPrice());
					totalMoneyMade = totalMoneyMade.add(item.getPrice());

					// Purchase item action

					item.purchaseItem();

					// Log

					logChanges(item.getPrice(), (item.getName() + " " + productSlot));
					System.out.println();

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
				writer.printf("%s %s: $%s $%s\n", date, typeOfChange, money, newMoneyFed);
			} else {
				writer.printf("%s %s: $%s $%s\n", date, typeOfChange, money, moneyFed);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	}
