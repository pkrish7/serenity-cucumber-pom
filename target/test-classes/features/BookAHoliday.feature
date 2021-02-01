Feature: Book a holiday trip

  Scenario: User should be able to book a holiday trip via mailtravel website
    Given the user is on the mailtravel website home page
    When he or she searches for the holiday place "India"
    Then he or she should be able to see holiday details
    And should be able to book a holiday trip on the first available date successfully on giving all required information
    And should be able to sign up to newsletter
