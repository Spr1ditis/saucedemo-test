#Comments

@RegressionTest
@ui @api #@db
Feature: feature to test login functionality

#First login test
  Scenario Outline: Check login is successful with valid credentials
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks log in button
    Then User is navigated to home page
    Then API should return successful login response
    #Then User <username> should exist in database

    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
      | problem_user  | secret_sauce |

  Scenario Outline: Check locked out user status appears
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks log in button
    Then User is informed that account has been locked out
    #Then User <username> should exist in database and has locked status

    Examples:
      | username        | password     |
      | locked_out_user | secret_sauce |


  Scenario Outline: Check login fails, when user tries to login with invalid password
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks log in button
    Then User is informed that login fails with message: "<message>"

    Examples:
      | username      | password      | message                                                                   |
      | standard_user | secret_sauce2 | Epic sadface: Username and password do not match any user in this service |
      | problem_user  | secret        | Epic sadface: Username and password do not match any user in this service |

  Scenario Outline: Check that login fails when credential fields are empty
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks log in button
    Then User is informed that login fails with message: "<message>"

    Examples:
      | username     | password      | message                            |
      |              | secret_sauce2 | Epic sadface: Username is required |
      | problem_user |               | Epic sadface: Password is required |
      |              |               | Epic sadface: Username is required |

  Scenario Outline: Logout prevents User to go back to see home page
    Given User is on login page
    When User enters "<username>" and "<password>"
    And User clicks log in button
    Then User is navigated to home page
    And User clicks menu and logout
    Then User is in login page
    And User clicks back on browser
    Then User is in login page
    #Then User <username> should exist in database and has locked status

    Examples:
      | username        | password     |
      | standard_user | secret_sauce |


