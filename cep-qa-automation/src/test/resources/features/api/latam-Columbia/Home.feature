@API-AU-Latam-Home @API-Latam-Columbia
Feature: Send request for Content API

  @API-Latam-Home-01
  Scenario Outline: Verify Home  API
    Given send get request to latam "Home" for "<testcase>"
    Then verify "Home" page details api response for "<testcase>"
    Examples:
      | testcase   |
      | Valid-Home |