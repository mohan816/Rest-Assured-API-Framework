@API-Latam-WhatsApp
Feature: Send request for GetStores Details API

  @API-Latam-WhatsApp-GetStoreDetails-01
  Scenario Outline: Verify GetStores Details API
    Given send get request to latam "GetStoreDetails" for "<testcase>"
    Then verify "GetStoreDetails"  api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | InValid-Content |


  @API-Latam-WhatsApp-Content-02
  Scenario Outline: Verify Content Details API
    Given send get request to latam "Content" for "<testcase>"
    Then  verify "Content"  api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Latam-WhatsApp-Product-03
  Scenario Outline: Verify Product Details API
    Given send get request to latam "Product" for "<testcase>"
    Then  verify "Product" details  api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | Blank-storeId |
      | Blank-okta-accesstoken |

  @API-Latam-WhatsApp-orderHistory-04
  Scenario Outline: Verify OrderHistory Details API
    Given send get request to latam "OrderHistory" for "<testcase>"
    Then  verify "OrderHistory"  api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | InValid-orderType |
      | InValid-storeId |
      | Blank-okta-accesstoken |
      | InValid-okta-accesstoken |

  @API-Latam-WhatsApp-RewardsList-05
  Scenario Outline: Verify Reward List API
    Given send get request to latam "RewardList" for "<testcase>"
    Then  verify "RewardList" rewards api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | Blank-okta-accesstoken |
      | InValid-okta-accesstoken |

  @API-Latam-WhatsApp-Translation-06
  Scenario Outline: Verify Translation API
    Given send get request to latam "Translation" for "<testcase>"
    Then  verify "Translation" Translation api response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Latam-WhatsApp-Promotion-07
  Scenario Outline: Verify Promotion Details API
    Given send get request to latam "Promotion" for "<testcase>"
    Then  verify "Promotion"  api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | Blank-storeId |
      | Blank-SiteId |
      | Blank-okta-accesstoken |
      | InValid-okta-accesstoken |

  @API-Latam-WhatsApp-Holidays-08
  Scenario Outline: Verify Holidays API
    Given send get request to latam "Holidays" for "<testcase>"
    Then  verify "Holidays"  api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Latam-WhatsApp-SalesTarget-09
  Scenario Outline: Verify SalesTarget API
    Given send get request to latam "SalesTarget" for "<testcase>"
    Then  verify "SalesTarget" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |
      | Blank-storeId |
#      | Blank-okta-accesstoken |

  @API-Latam-WhatsApp-Faq-10
  Scenario Outline: Verify Faq API
    Given send get request to latam "Faq" for "<testcase>"
    Then  verify "Faq" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Latam-WhatsApp-ActivityList-11
  Scenario Outline: Verify ActivityList API
    Given send get request to latam "ActivityList" for "<testcase>"
    Then  verify "ActivityList" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Latam-WhatsApp-BalancePoints-12
  Scenario Outline: Verify BalancePoints API
    Given send get request to latam "BalancePoints" for "<testcase>"
    Then  verify "BalancePoints" sales target api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

  @API-Latam-WhatsApp-VendorList-13
  Scenario Outline: Verify VendorList API
    Given send get request to latam "VendorList" for "<testcase>"
    Then  verify "VendorList" vendor list api details response for "<testcase>"
    Examples:
      | testcase      |
      | Valid-Content |

