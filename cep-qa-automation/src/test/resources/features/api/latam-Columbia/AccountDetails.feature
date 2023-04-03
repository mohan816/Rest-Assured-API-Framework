@API-AccountDetails @API-Latam-Columbia @API-Latam-Brazil
Feature: Send request for Account Details API

  @API-AccessToken-01
  Scenario Outline: Verify  AccessToken  API
    Given post  latam "Okta" request for "<testcase>"
    Given send get request to latam "Okta" for "<testcase>"
    Examples:
      | testcase     |
      | Valid-UserId |

  @API-GetAccountDetails-02
  Scenario Outline: Verify Get Account Details API
    Given send get request to latam "GetAccountDetails" for "<testcase>"
    Then verify latam "GetAccountDetails" details api response for "<testcase>"
    Examples:
      | testcase          |
      | Blank-AccessToken |
      | Valid-AccessToken |

  @API-UpdateAccountDetails-03
  Scenario Outline: Verify Update Account Details API
    Given send put request to latam "UpdateAccountDetails" for "<testcase>"
    Then verify latam "UpdateAccountDetails" details api response for "<testcase>"
    Examples:
      | testcase                      |
      | Update-FirstName-AlphaNumeric |
      | Update-LastName-AlphaNumeric  |

  @API-GetFavorite-04
  Scenario Outline: Verify Get Favorite Products API
    Given send get request to latam "Favorite" for "<testcase>"
    Then  verify "Favorite" details  api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | Blank-storeId |
      | Blank-okta-accesstoken |

  @API-SuggestedSKU-05
  Scenario Outline: Verify Get Suggested SKU API
    Given send get request to latam "SuggestedSKU" for "<testcase>"
    Then  verify "SuggestedSKU" details  api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | Blank-okta-accesstoken |
