
Narrative: User login
  In order to gain access to all features of the site
  As an anonymous user
  I want to be able to login to my account

- Being signed in as a speaker
I have the following dropdown options available under *'First Name' Account* :
Manage My Account page, My Info page and My Talks
- Being signed in as an organizer
I have the following dropdown options available under *'First Name' Account* :
Manage My Account page and Talks



Scenario: User unsuccessfully logs in to the site with invalid credentials
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email           |password|
|invalid@site.com|invalid |
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

Scenario: User uses sql injections in login form
Meta:
@regression
Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email  |password  |
|<email>|<password>|
When user clicks SignIn button on login form
Then user still in login form
And login error message is displayed: "We can not find an account with that email address"

Examples:
|<email>  |<password>|
|'-'      |'-'       |
|' '      |' '       |
|'&'      |'&'       |
|'^'      |'^'       |
|'*'      |'*'       |
|' or ''-'|' or ''-' |
|' or '' '|' or '' ' |
|' or ''&'|' or ''&' |
|' or ''^'|' or ''^' |
|' or ''*'|' or ''*' |
|"-"      |"-"       |
|" "      |" "       |
|"&"      |"&"       |
|"^"      |"^"       |
|"*"      |"*"       |
|" or ""-"|" or ""-" |


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


Scenario: Being signed in as a speaker manage My Account page, My Info page and My Talks
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks SignIn button on login form
Then "Your Account" replaced by:
|name            |
|Master's Account|
And there are My_account, My_Info and  My Talks links in the given order
|btnName |link       |
|My Info |/#/my-info |
|My Talks|/#/my-talks|
|Settings|/#/account |
|Sign Out|/#/        |
Then user logs out



Scenario: Being signed in as an organizer manage My Account page, My Info page and My Talks
Meta:
@regression @smoke @ignore

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email                  |password |
|organizer@organizer.com|organizer|
When user clicks SignIn button on login form
Then "Your Account" replaced by "Organizer's Account"
Then there are My account and  My Talks links in the given order
|btnName |link      |
|Talks   |/#/talks  |
|Settings|/#/account|
|Sign Out|/#/       |
Then user logs out


Scenario: Being signed in as an admin manage My Account page, My Info page and My Talks
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email          |password |
|admin@gmail.com|java1love|
When user clicks SignIn button on login form
Then "Your Account" replaced by "I'm super's Account"
Then there are My account and  My Talks links in the given order
|btnName     |link           |
|Talks       |/#/talks       |
|Settings    |/#/account     |
|Manage Users|/#/manage-users|
|Sign Out    |/#/            |
Then user logs out