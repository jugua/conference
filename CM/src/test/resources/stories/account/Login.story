
Narrative: User login
  In order to gain access to all features of the site
  As an anonymous user
  I want to be able to login to my account


Scenario: User successfully logs in to the site
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email            |password|
|tester@tester.com|tester  |
When user clicks SignIn button on login form
Then "Your Account" replaced by "Tester's Account"
And there is 'Sign Out' button in account menu

When user logs out



Scenario: User unsuccessfully logs in to the site with invalid credentials
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email              |password|
|invalid@site.com   |invalid |

When user clicks SignIn button on login form
Then user still in login form
And login field is highlighted
And login error message is displayed: "We can not find an account with that email address"


Scenario: User logs in with empty credentials
Meta:
@regression
Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email|password|
|     |        |
When user clicks SignIn button on login form
Then user still in login form
And login field is highlighted
And login error message is displayed: "We can not find an account with that email address"


Scenario: User logs in with login that does not comply with validation rules
Meta:
@regression

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email  |password  |
|<email>|<password>|
When user clicks SignIn button on login form
Then user still in login form
And login field is highlighted
And login error message is displayed: "We can not find an account with that email address"

Examples:
|<email>         |<password>|
|example.com     |tester    |
|user@example    |tester    |
|t@i.u           |tester    |
|tester@tester.22|tester    |



Scenario: User logs in with password that does not comply with validation rules
Meta:
@regression

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email  |password  |
|<email>|<password>|
When user clicks SignIn button on login form
Then user still in login form
And password field is highlighted
And password error message is displayed: "Your password is incorrect"

Examples:
|<email>            |<password>|
|tester@tester.com  |          |
|tester@tester.com  |teste     |
|speaker@speaker.com|tester    |