@BE
Feature: User login
  In order to gain access to all features of the site
  As an anonymous customer
  I want to be able to login to my account

  Background:
    Given user has opened login page

  @smoke @regression
  Scenario: User successfully logs in to the site
    Given user filled in login form
      | email                             | password  |
      | Bradamate_Jodion@be.ipxl.epam.com | Password1 |
    When user clicks submit button on login form
    Then browser is redirected to My Account page
    And account icon contains name Bradamate
    When user logs out
    Then browser is on Home page

  @regression
  Scenario: User enters invalid email address
    Given user filled in login form
      | email             | password  |
      | invalid@email.com | Password1 |
    When user clicks submit button on login form
    Then login error message is displayed: "Your username or password was incorrect."

  @regression
  Scenario: User logs in with empty credentials
    Given user filled in login form
      | email | password |
      |       |          |
    When user clicks submit button on login form
    Then email address field is highlighted
    And password field is highlighted

  Scenario Outline: User logs in with login that does not comply with validation rules
    Given user filled in login form
      | email   | password   |
      | <email> | <password> |
    When user clicks submit button on login form
    Then email address field is highlighted

    Examples:
      | email                                    | password  |
      | example.com                              | Password1 |
      | user@example                             | Password1 |
      | t@i.ua                                   | Password1 |
      | morethanthirtyfivecharacters@example.com | Password1 |

  Scenario Outline: User logs in with password that does not comply with validation rules
    Given user filled in login form
      | email   | password   |
      | <email> | <password> |
    When user clicks submit button on login form
    Then email address field is highlighted

    Examples:
      | email                             | password     |
      | Bradamate_Jodion@be.ipxl.epam.com | Lesstn8      |
      | Bradamate_Jodion@be.ipxl.epam.com | nocapletter1 |
      | Bradamate_Jodion@be.ipxl.epam.com | nonumber     |
