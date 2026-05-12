#Comments

@RegressionTest
@ui @api @pageStart #@db
Feature: Feature to test login functionality

#First login test
  Scenario Outline: Check login is successful with valid credentials
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks Submit button
    Then User is logged in their contact list page
    Then API should return successful login response
    #Then User <username> should exist in database

    Examples:
      | username      | password     |
      | test@fake.com | myPassword |
      | testy@email.com  | 123$%^A |

  Scenario Outline: User is informed when incorrect username or password is entered
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks Submit button
    Then Warning informs user that username or password is incorrect
    #Then User <username> should exist in database

    Examples:
      | username      | password     |
      | test@fake.com | myPassword1 |
      | testy2@email.com  | 123$%^A |
    |      b             |     a    |

    @deleteUser
  Scenario Outline: New user can be created
    Given User is on login page
    When User clicks on Sign up button
    And Enters "<firstName>", "<lastName>", "<email>", "<password>" and clicks submit
    Then User is logged in their contact list page
    #Then User <username> should exist in database

    Examples:
      | firstName      | lastName     | email | password |
      | John | Smith | email11@epasts.com       | password1 |
      | Jane | Doe | email22@epasts.com       | password2 |
      | Janis | Berzins | email33@epasts.com       | password3 |

      @deleteUser
  Scenario Outline: New user can not be created with taken email
    Given User with "<firstName>", "<lastName>", "<email>", "<password>" has been added via API
    And User is on login page
    When User clicks on Sign up button
    And Enters "<firstName>", "<lastName>", "<email>", "<password>" and clicks submit
    Then User is informed that email address is already in use
    #Then User <username> should exist in database

    Examples:
      | firstName      | lastName     | email | password |
      | John | Smith | email11@epasts.com       | password1 |
      | Jane | Doe | email22@epasts.com       | password2 |
      | Janis | Berzins | email33@epasts.com       | password3 |