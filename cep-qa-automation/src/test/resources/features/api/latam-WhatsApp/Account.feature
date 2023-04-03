@API-Latam-WhatsApp
Feature: Send request for Account Details API

  @API-Latam-WhatsApp-AccessToken-01
  Scenario Outline: Verify  AccessToken  API
    Given post  latam "Okta" request for "<testcase>"
    Given send get request to latam "Okta" for "<testcase>"
    Examples:
      | testcase     |
      | Valid-UserId |

  @API-Latam-WhatsApp-GetAccountDetails-01
  Scenario Outline: Verify Get Account Details API
    Given send get request to latam "GetAccountDetails" for "<testcase>"
    Then verify latam "GetAccountDetails" details api response for "<testcase>"
    Examples:
      | testcase          |
      | Blank-AccessToken |
      | Valid-AccessToken |

