@API-GetStores @API-Latam-Columbia @API-Latam-Brazil
Feature: Send request for GetStores Details API

  @API-GetStoreDetails-01
  Scenario Outline: Verify GetStores Details API
    Given send get request to latam "GetStoreDetails" for "<testcase>"
    Then verify "GetStoreDetails"  api response for "<testcase>"
    Examples:
      | testcase        |
      | Valid-Content   |
      | InValid-Content |


  @API-Content-02
  Scenario Outline: Verify Content Details API
    Given send get request to latam "Content" for "<testcase>"
    Then  verify "Content"  api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Product-03
  Scenario Outline: Verify Product Details API
    Given send get request to latam "Product" for "<testcase>"
    Then  verify "Product" details  api response for "<testcase>"
    Examples:
      | testcase               |
      | Valid-Content          |
      | Blank-storeId          |
      | Blank-okta-accesstoken |

  @API-orderHistory-04
  Scenario Outline: Verify OrderHistory Details API
    Given send get request to latam "OrderHistory" for "<testcase>"
    Then  verify "OrderHistory"  api details response for "<testcase>"
    Examples:
      | testcase                 |
      | Valid-okta-accesstoken   |
      | InValid-orderType        |
      | InValid-storeId          |
      | Blank-okta-accesstoken   |
      | InValid-okta-accesstoken |

  @API-RewardsList-05
  Scenario Outline: Verify Reward List API
    Given send get request to latam "RewardList" for "<testcase>"
    Then  verify "RewardList" rewards api response for "<testcase>"
    Examples:
      | testcase                 |
      | Valid-Content            |
      | Blank-okta-accesstoken   |
      | InValid-okta-accesstoken |

  @API-Translation-06
  Scenario Outline: Verify Translation API
    Given send get request to latam "Translation" for "<testcase>"
    Then  verify "Translation" Translation api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Promotion-07
  Scenario Outline: Verify Promotion Details API
    Given send get request to latam "Promotion" for "<testcase>"
    Then  verify "Promotion"  api details response for "<testcase>"
    Examples:
      | testcase                 |
      | Valid-Content            |
      | Blank-storeId            |
      | Blank-SiteId             |
      | Blank-okta-accesstoken   |
      | InValid-okta-accesstoken |

  @API-Holidays-08
  Scenario Outline: Verify Holidays API
    Given send get request to latam "Holidays" for "<testcase>"
    Then  verify "Holidays"  api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-SalesTarget-09
  Scenario Outline: Verify SalesTarget API
    Given send get request to latam "SalesTarget" for "<testcase>"
    Then  verify "SalesTarget" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
#      | Blank-storeId |
      | Blank-okta-accesstoken |

  @API-Faq-10
  Scenario Outline: Verify Faq API
    Given send get request to latam "Faq" for "<testcase>"
    Then  verify "Faq" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-ActivityList-11
  Scenario Outline: Verify ActivityList API
    Given send get request to latam "ActivityList" for "<testcase>"
    Then  verify "ActivityList" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-BalancePoints-12
  Scenario Outline: Verify BalancePoints API
    Given send get request to latam "BalancePoints" for "<testcase>"
    Then  verify "BalancePoints" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-VendorList-13
  Scenario Outline: Verify VendorList API
    Given send get request to latam "VendorList" for "<testcase>"
    Then  verify "VendorList" vendor list api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-DeliveryEstimate-14
  Scenario Outline: Verify  Delivery Estimate  API
    Given send get request to latam "DeliveryEstimate" for "<testcase>"
    Then verify latam "DeliveryEstimate" api response output fields for "<testcase>"
    Examples:
      | testcase               |
      | Valid-okta-accesstoken |
      | Blank-okta-accesstoken |

#  @API-OrderCreate-16
#  Scenario Outline: Verify Order create API
#    Given send get request to latam "DeliveryEstimate" for "<testcase>"
#    When post  latam "OrderCreate" request for "<testcase>"
#    Then  verify "OrderCreate"  api details response for "<testcase>"
#    Examples:
#      | testcase               |
#      | Valid-okta-accesstoken |
#      | InValid-storeId        |
#      | Blank-okta-accesstoken |
#      | InValid-okta-accesstoken |
#      | OrderCreate-Rewards    |
