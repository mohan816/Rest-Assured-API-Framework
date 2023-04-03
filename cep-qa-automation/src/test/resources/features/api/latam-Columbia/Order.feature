@API-GetStores @API-Latam-Columbia
Feature: Send request for Order Create Details API

  @API-OrderCreate-15
  Scenario Outline: Verify Order create API
    Given send get request to latam "OrderHistory" for "<testcase>" and store "totalItems"
    Given send get request to latam "DeliveryEstimate" for "<testcase>"
    When post  latam "OrderCreate" request for "<testcase>"
    Then  verify "OrderCreate"  api details response for "<testcase>"
    Given send get request to latam "OrderHistory" for "<testcase>" and verify "totalItems"
    Examples:
      | testcase               |
      | Valid-okta-accesstoken |
