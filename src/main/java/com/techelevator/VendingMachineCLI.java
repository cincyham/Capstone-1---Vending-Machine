package com.techelevator;

import com.techelevator.view.Menu;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
	private BigDecimal moneyFed;
	private BigDecimal totalMoneyMade;


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

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.moneyFed = new BigDecimal(0.00);
		this.totalMoneyMade = new BigDecimal(0.00);
	}

	public void run() {

		boolean userChoice = true;
		while (userChoice) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				printItemList();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchase();
			} else {
				userChoice = false;
			}

		}

	}

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

	public void printItemList() {
		for (Map.Entry<String, Item> item : itemMap.entrySet()) {

			System.out.println(item.getKey() + item.getValue().toString());
		}
	}

	public void purchase() {

		System.out.println();
		final String[] PURCHASE_OPTIONS = {"Feed Money", "Select Product", "Finish Transaction"};

		Menu purchaseMenu = new Menu(System.in, System.out);

		boolean userChoice = true;
		while (userChoice) {

			System.out.println("Current Money Provided: $" + moneyFed);
			String choice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_OPTIONS);

			if (choice.equals(PURCHASE_OPTIONS[0])) {
				feedMoney();
			} else if (choice.equals(PURCHASE_OPTIONS[1])) {
				System.out.println();
				selectProduct();
			} else {
				userChoice = false;
			}
		}
	}

	public void feedMoney(){
		Scanner userInput = new Scanner(System.in);
		System.out.print("Please feed Money (Only whole dollars) >>> ");
		BigDecimal userNumber = new BigDecimal(Double.parseDouble(userInput.nextLine()));
		moneyFed = moneyFed.add(userNumber);
	}

	public void selectProduct(){

		printItemList();
		Scanner userInput = new Scanner(System.in);
		System.out.println();
		System.out.print("Please enter a Product slot to purchase >>> ");
		String productSlot = userInput.nextLine();
		System.out.println();
		if (productSlot != null) {

		if (itemMap.containsKey(productSlot)) {

			Item item = itemMap.get(productSlot);
				if (item.getQuantity() > 0) {

					if (item.getPrice().compareTo(moneyFed) <= 0) {

						moneyFed = moneyFed.subtract(item.getPrice());
						totalMoneyMade = totalMoneyMade.add(item.getPrice());
						item.purchaseItem();
						System.out.println();
					} else {

						System.out.println("Error. Not enough Money.");
					}
				} else {

					System.out.println("Product not available. Not enough stock.");
				}
			} else {

				System.out.println("Product Slot does not exist.");
			}
		} else {
			System.out.println("Error. Something went wrong.");
		}
	}

	public void logChanges(BigDecimal money, String typeOfChange) {
		File file = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-3\\src\\main\\resources\\log.txt");
		int i = 0;
		if (file.exists()) {
			if(i == 0) {
				try (PrintWriter writer = new PrintWriter(file)) {

				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
