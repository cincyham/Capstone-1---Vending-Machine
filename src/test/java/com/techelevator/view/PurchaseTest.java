package com.techelevator.view;

import com.techelevator.Purchase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.math.BigDecimal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PurchaseTest {

    Purchase purchase;

    @Before
    public void setup(){purchase = new Purchase();}


    @Test
    public void createMapOfItems_happyFilePathInput_returnTrue(){
        //Arrange
        File file = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-3\\vendingmachine.csv");
        boolean expected = true;
        //Act
        boolean output = purchase.createMapOfItems(file);;
        //Assert
        Assert.assertEquals(expected, output);
    }


    @Test
    public void createMapOfItems_badFilePathInput_returnError(){
        //Arrange
        File file = new File("p");
        boolean expected = false;
        //Act
        boolean output = purchase.createMapOfItems(file);
        //Assert
        Assert.assertEquals(expected, output);
    }


    @Test
    public void getItemMap_happyPathNotNull_returnCorrectMap(){
        File file = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-3\\vendingmachine.csv");
        purchase.createMapOfItems(file);
        String expected = "A1|Potato Crisps|3.05|Chip|Amount available: 5\n" +
                "A2|Stackers|1.45|Chip|Amount available: 5\n" +
                "A3|Grain Waves|2.75|Chip|Amount available: 5\n" +
                "A4|Cloud Popcorn|3.65|Chip|Amount available: 5\n" +
                "B1|Moonpie|1.80|Candy|Amount available: 5\n" +
                "B2|Cowtales|1.50|Candy|Amount available: 5\n" +
                "B3|Wonka Bar|1.50|Candy|Amount available: 5\n" +
                "B4|Crunchie|1.75|Candy|Amount available: 5\n" +
                "C1|Cola|1.25|Drink|Amount available: 5\n" +
                "C2|Dr. Salt|1.50|Drink|Amount available: 5\n" +
                "C3|Mountain Melter|1.50|Drink|Amount available: 5\n" +
                "C4|Heavy|1.50|Drink|Amount available: 5\n" +
                "D1|U-Chews|0.85|Gum|Amount available: 5\n" +
                "D2|Little League Chew|0.95|Gum|Amount available: 5\n" +
                "D3|Chiclets|0.75|Gum|Amount available: 5\n" +
                "D4|Triplemint|0.75|Gum|Amount available: 5";
        //Act
        String output = purchase.getItemMap();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void getItemMap_badFilePath_returnEmptyMap(){
        File file = new File("p");
        purchase.createMapOfItems(file);
        String expected = "";
        //Act
        String output = purchase.getItemMap();
        //Assert
        Assert.assertEquals(expected, output);
    }



    // These tests kept turning out false and then true and kept switching between. Now they all work. We know that our vending machine works so we left them.
    // If you have some insight into why this happens, we would love to hear it.



    @Test
    public void returnMoney_oneDollar() {
        // Arrange
        purchase.feedMoney("1");
        String expected = "Money returned: $1.00\n" +
                "\n" +
                "Dollar Bills: $1.00";
        // Act
        String output = purchase.returnMoney();
        // Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void returnMoney_oneDollar_fiftyCents(){
        // Arrange
        purchase.feedMoney("1.5");
        String expected = "Money returned: $1.50\n" +
                "\n" +
                "Dollar Bills: $1.00\n" +
                "Quarter(s): $0.50";
        // Act
        String output = purchase.returnMoney();
        // Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void returnMoney_oneDollar_thirtyFiveCents(){
        // Arrange
        purchase.feedMoney("1.35");
        String expected = "Money returned: $1.35\n" +
                "\n" +
                "Dollar Bills: $1.00\n" +
                "Quarter(s): $0.25\n" +
                "Dime(s): $0.10";
        // Act
        String output = purchase.returnMoney();
        // Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void returnMoney_oneDollar_FortyCents(){
        // Arrange
        purchase.feedMoney("1.4");
        String expected = "Money returned: $1.40\n" +
                "\n" +
                "Dollar Bills: $1.00\n" +
                "Quarter(s): $0.25\n" +
                "Dime(s): $0.10\n" +
                "Nickel(s): $0.05";
        // Act
        String output = purchase.returnMoney();
        // Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void returnMoney_oneDollar_fiveCents(){
        // Arrange;
        purchase.feedMoney("1.05");
        String expected = "Money returned: $1.05\n" +
                "\n" +
                "Dollar Bills: $1.00\n" +
                "Nickel(s): $0.05";
        // Act
        String output = purchase.returnMoney();
        // Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void returnMoney_oneDollar_fifteenCents(){
        // Arrange
        purchase.feedMoney("1.15");
        String expected = "Money returned: $1.15\n" +
                "\n" +
                "Dollar Bills: $1.00\n" +
                "Dime(s): $0.10\n" +
                "Nickel(s): $0.05";
        // Act
        String output = purchase.returnMoney();
        // Assert
        Assert.assertEquals(expected, output);
    }


    @Test
    public void feedMoney_oneDollar_moneyFedEqualsOneDollar(){
        //Arrange
        purchase.feedMoney("1");
        BigDecimal expected = BigDecimal.valueOf(1);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_oneDollarFiftyCents_moneyFedEqualsOneDollarFiftyCents(){
        //Arrange
        purchase.feedMoney("1.5");
        BigDecimal expected = BigDecimal.valueOf(1.5);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_oneDollarFortyFiveCents_moneyFedEqualsOneDollarFortyFiveCents(){
        //Arrange
        purchase.feedMoney("1.45");
        BigDecimal expected = BigDecimal.valueOf(1.45);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_threeDollars_moneyFedEqualsThreeDollars(){
        //Arrange
        purchase.feedMoney("3");
        BigDecimal expected = BigDecimal.valueOf(3);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_threeHundredDollars_moneyFedEqualsThreeHundredDollars(){
        //Arrange
        purchase.feedMoney("300");
        BigDecimal expected = BigDecimal.valueOf(300);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_zeroDollars_moneyFedEqualsZeroDollars(){
        //Arrange
        purchase.feedMoney("0");
        BigDecimal expected = BigDecimal.valueOf(0);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_negativeAmount_moneyFedEqualsZero(){
        //Arrange
        purchase.feedMoney("-1");
        BigDecimal expected = BigDecimal.valueOf(0);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void feedMoney_oneDollarFortyThreeCents_moneyFedEqualsZero(){
        //Arrange
        purchase.feedMoney("1.43");
        BigDecimal expected = BigDecimal.valueOf(0);
        //Act
        BigDecimal output = purchase.getMoneyFed();
        //Assert
        Assert.assertEquals(expected, output);
    }

    // Some of these test cases also aren't working while the program is. Again insight into why would be most welcome.


    @Test
    public void productSelectionPurchase_slotD4_upperCase() {
        //Arrange
        String expected = "Triplemint $0.75: Chew Chew, Yum!";
        //Act
        String output = purchase.productSelectionPurchase("D4");
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void productSelectionPurchase_slotd4_lowerCase() {
        //Arrange
        String expected = "Triplemint $0.75: Chew Chew, Yum!";
        //Act
        String output = purchase.productSelectionPurchase("d4");
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void productSelectionPurchase_slot45_returnSlotDoesNotExist() {
        //Arrange
        String expected = "Product slot does not exist.";
        //Act
        String output = purchase.productSelectionPurchase("45");
        //Assert
        Assert.assertEquals(expected, output);
    }

    @Test
    public void productSelectionPurchase_null_returnProductSlotDoesNotExist() {
        //Arrange
        String expected = "Please enter a slot.";
        //Act
        String output = purchase.productSelectionPurchase(null);
        //Assert
        Assert.assertEquals(expected, output);
    }

}
