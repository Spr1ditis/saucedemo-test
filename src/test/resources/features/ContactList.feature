#Comments

@RegressionTest
@ui @api @pageStart #@db
Feature: Feature to test login functionality

##First login test
#  Scenario Outline: Check login is successful with valid credentials
#    Given User is on login page
#    When User enters "<username>" and "<password>"
#    And User clicks Submit button
#    Then User is navigated to home page
#    Then API should return successful login response
#    #Then User <username> should exist in database
#
#    Examples:
#      | username      | password     |
#      | test@fake.com | myPassword |
#      | testy@email.com  | 123$%^A |
#
#  Scenario Outline: User is informed when incorrect username or password is entered
#    Given User is on login page
#    When User enters "<username>" and "<password>"
#    And User clicks Submit button
#    Then Warning informs user that username or password is incorrect
#    #Then User <username> should exist in database
#
#    Examples:
#      | username      | password     |
#      | test@fake.com | myPassword1 |
#      | testy2@email.com  | 123$%^A |
#    |      b             |     a    |
#
