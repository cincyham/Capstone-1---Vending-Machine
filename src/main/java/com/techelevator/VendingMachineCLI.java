package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;

public class VendingMachineCLI {


	// Properties

	// Main menu options 1-3 created in the array

	private static final String[] MAIN_MENU_OPTIONS = {"Display Vending Machine Items", "Purchase", "Exit"};
	private Menu menu;
	private static Purchase purchase = new Purchase();



	// Got idea of Tree Map from https://www.baeldung.com/java-hashmap-sort to help sort itemMap

	public static void main(String[] args) {

		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		File file = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-3\\vendingmachine.csv");
		purchase.createMapOfItems(file);
		cli.run();

	}


	// Constructor


	public VendingMachineCLI(Menu menu) {this.menu = menu;}


	//Start of the application

	public void run() {

		boolean userChoice = true;

		while (userChoice) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTIONS[0])) {
				System.out.println();
				System.out.println(purchase.getItemMap());
			} else if (choice.equals(MAIN_MENU_OPTIONS[1])) {
				purchase.purchaseOptions();
			} else {
				System.out.println();
				System.out.println("Thanks for visiting our vending machine!");
				userChoice = false;
			}
		}
	}
}
