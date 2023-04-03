@API-AU-Loyalty
Feature: Send request for registration API

  @API-AuRewards-Registration-01
  Scenario Outline: Verify Registration API
    Given post "Registration" request for "<testcase>"
    Then verify "Registration" api response output fields for "<testcase>"
    Examples:
      | testcase               |
      | Blank-UserId           |
      | Blank-UserId-Firstname |
      | Existing-UserId        |

  @API-AuRewards-ResendEmail-02
  Scenario Outline: Verify Resend Email API
    Given post "ResendEmail" request for "<testcase>"
    Then verify "ResendEmail" api response output fields for "<testcase>"
    Examples:
      | testcase     |
      | Valid-UserId |

  @API-AuRewards-ForgotPassword-03
  Scenario Outline: Verify Forgot Password API
    Given post "ForgotPassword" request for "<testcase>"
    Then verify "ForgotPassword" api response output fields for "<testcase>"
    Examples:
      | testcase       |
      | Valid-UserName |
      | Blank-UserName |

  @API-AuRewards-VerifyRecovery-04
  Scenario Outline: Verify Recovery Password API
    Given post "ForgotPassword" request for "Valid-UserName"
    When post "VerifyRecovery" request for "<testcase>"
    Then verify "VerifyRecovery" api response output fields for "<testcase>"
    Examples:
      | testcase                  |
      | Valid-UserName-Token-Code |
      | Valid-Token-BlankPassword |

  @API-AuRewards-ResetPassword-05
  Scenario Outline: Verify Reset Password API
    Given post "ForgotPassword" request for "Valid-UserName"
    When post "VerifyRecovery" request for "Valid-UserName-Token-Code"
    When post "ResetPassword" request for "<testcase>"
    Then verify "ResetPassword" api response output fields for "<testcase>"
    Examples:
      | testcase                  |
      | Valid-Token-Password      |
      | Valid-Token-BlankPassword |

  @API-AuRewards-Registration-06
  Scenario Outline: Verify Change Password API
    Given post "ChangePassword" request for "<testcase>"
    Then verify "ChangePassword" api response output fields for "<testcase>"
    Examples:
      | testcase                          |
      | Valid-Userid-Oldpwd-Newpwd        |
      | Valid-Userid-InvalidOldpwd-Newpwd |


  @API-AuRewards-ClaimReward-07
  Scenario Outline: Verify ClaimReward API
    Given send get request to "GetRewards" for "Valid-Content"
    Then post "ClaimReward" request for "<testcase>"
    Then verify "ClaimReward" response output fields for "<testcase>"
    Examples:
      | testcase                  |
      | Valid-Content |
      | Blank-okta-accesstoken |
      | InValid-okta-accesstoken |
      | inValid-RewardID        |
      | Blank-RewardID           |

  @API-AuRewards-BurnReward-08
  Scenario Outline: Verify BurnReward API
    Given send get request to "GetRewards" for "Valid-Content"
    Then post "ClaimReward" request for "Valid-Content"
    Then post "BurnReward" request for "<testcase>"
    Then verify "BurnReward" response output fields for "<testcase>"
    Examples:
      | testcase                  |
      | Valid-Content             |
      | Blank-okta-accesstoken    |
      | InValid-okta-accesstoken  |
      | Blank-couponCode          |
      | inValid-RewardID         |
      | Blank-RewardID           |

#  @API-AuRewards-OktaAccessToken-02
#  Scenario Outline: Verify Okta Accesstoken  API
#    Given post "Okta" request for "<testcase>"
#    Then send get request to "Okta" for "<testcase>"
#    Examples:
#      | testcase     |
#      | Valid-UserId |