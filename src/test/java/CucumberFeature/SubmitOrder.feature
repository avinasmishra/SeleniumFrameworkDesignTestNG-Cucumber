Feature: Purchase the product and submit Order

  Background:
    Given I landed into application

  Scenario Outline: Verify the product added successfully and submit the order
    Given enter valid username <email> and password <password>
    When I add product <productName> to cart
    And I checkout product <productName> and submit order
    Then verify the message "THANKYOU FOR THE ORDER." display

    Examples:
      | email          | password   | productName |
      | ram1@gmail.com | Ram@gmail1 | I PHONE     |