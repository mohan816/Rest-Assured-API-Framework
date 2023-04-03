@API-AU-Loyalty
Feature: Send request for Content API

  @API-AuRewards-ContactUS-01
  Scenario Outline: Verify Contact us API
    Given post "ContactUS" request for "<testcase>"
    Then verify "ContactUS" api response output fields for "<testcase>"
    Examples:
      | testcase                   |
      | Valid-Type-Subject-Message |
      | Valid-Type-Invalid-mailID  |
      | Valid-Type-Blank-Phone     |

  @API-AuRewards-Content-02
  Scenario Outline: Verify Content Details API
    Given send get request to "Content" for "<testcase>"
    Then verify "Content" details api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-AuRewards-Faq-03
  Scenario Outline: Verify FAQ Details API
    Given send get request to "FAQ" for "<testcase>"
    Then verify "FAQ" faq details api response for "<testcase>"
    Examples:
      | testcase  |
      | Valid-FAQ |


  @API-AuRewards-GetRewards-04
  Scenario Outline: Verify GetRewards API
    Given send get request to "GetRewards" for "<testcase>"
    Then verify "GetRewards" Rewards details api response for "<testcase>"
    Examples:
      | testcase                 |
      | Valid-Content            |
      | Blank-okta-accesstoken   |
      | InValid-okta-accesstoken |

  @API-AuRewards-GetClaimedRewards-05
  Scenario Outline: Verify GetClaimedRewards API
    Given send get request to "GetClaimedRewards" for "<testcase>"
    Then verify "GetClaimedRewards" Rewards details api response for "<testcase>"
    Examples:
      | testcase                 |
      | Valid-Content            |
      | Blank-okta-accesstoken   |
      | InValid-okta-accesstoken |

  @API-AuRewards-GetMyActivity-06
  Scenario Outline: Verify GetMyActivity API
    Given send get request to "GetMyActivity" for "<testcase>"
    Then verify "GetMyActivity" Activity details api response for "<testcase>"
    Examples:
      | testcase                 |
      | Valid-Content            |
      | Blank-okta-accesstoken   |
      | InValid-okta-accesstoken |
