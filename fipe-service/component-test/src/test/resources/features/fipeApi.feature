@CleanStubby
Feature: Fipe API

  Scenario: Create an coverage item using the API
    When the "create insurance coverage item" API is called with:
      | method | POST                               |
      | body   | create-quake-coverage-item-request |
    Then the "create insurance coverage item" API response has:
      | status | 201                                 |
      | body   | create-quake-coverage-item-response |
    And the "insurance_coverage_items" table contains 1 rows
    And the "insurance_coverage_items" table contains the following rows:
      | name  | description                            |
      | quake | Protecao contra terremotos e maremotos |
