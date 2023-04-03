@API-AU-Loyalty
Feature: Send request for Account Details API

  @API-AuRewards-GetAccountDetails-01
  Scenario Outline: Verify Get Account Details API
    Given send get request to "GetAccountDetails" for "<testcase>"
    Then verify loyalty "GetAccountDetails"  details api response for "<testcase>"
    Examples:
      | testcase          |
      | Blank-AccessToken |
      | Valid-AccessToken |

  @API-AuRewards-UpdateAccountDetails-02
  Scenario Outline: Verify Update Account Details API
    Given send put request to "UpdateAccountDetails" for "<testcase>"
    Then verify loyalty "UpdateAccountDetails"  details api response for "<testcase>"
    Examples:
      | testcase                      |
      | Update-FirstName-AlphaNumeric |
      | Update-LastName-AlphaNumeric  |