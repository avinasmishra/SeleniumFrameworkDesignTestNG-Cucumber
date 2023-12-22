Feature: ErrorValidation on Login Page

  @LoginError
  Scenario Outline: Verify the product added successfully and submit the order
    Given I landed into application
    And enter valid username <email> and password <password>
    Then verify the error message "Incorrect email or password." display

    Examples:
      | email          | password  |
      | ram1@gmail.com | Ram@gmail |