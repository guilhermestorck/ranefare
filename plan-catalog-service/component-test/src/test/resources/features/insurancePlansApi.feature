Feature: Insurance Plan API

  Background:
    Given that the database is empty

  Scenario: Create an insurance plan using the API
    When the "create insurance plan" API is called with:
      | method | POST                                 |
      | body   | create-simple-insurance-plan-request |
    Then the "create insurance plan" API response has:
      | status | 201                                   |
      | body   | create-simple-insurance-plan-response |
    And the "insurance_plans" table contains 1 records
    And the "insurance_plans" table contains the following rows:
      | name          | cost_rate |
      | Plano Simples | 0.05      |