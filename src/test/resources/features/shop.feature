@RegressionTest
@ui @api @loggedIn @pageStart #@db
Feature: feature to test shop functionality

  Background:
    Given User is logged in

  Scenario Outline: Verify that "<product>" is listed
    Then Product "<product>" is listed
    Examples:
      | product                           |
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |

  Scenario Outline: Verify that products can be sorted by <name> from "<type>"
    When User clicks to sort products by "<name>" "<type>"
    Then Products sorted from "<name>" "<type>"
    Examples:
      | name  | type |
      | Name  | az   |
      | Name  | za   |
      | Price | lohi |
      | Price | hilo |


  Scenario Outline: Verify that <product> details can be viewed

    When User selects "<product>"
    Then "<product>" page is opened
    Then "<product>" description and price is previewed
    #Then Product details in database

    Examples:
      | product                           |
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |