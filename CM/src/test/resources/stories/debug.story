
Scenario: Being signed in as an admin manage My Account page, My Info page and My Talks
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email          |password |
|admin@gmail.com|java1love|
When user clicks SignIn button on login form
Then "Your Account" replaced by "Admin's Account"
Then there are My account and  My Talks links in the given order
|btnName     |link           |
|Talks       |/#/talks       |
|Settings    |/#/account     |
|Manage Users|/#/manage-users|
|Sign Out    |/#/            |
Then user logs out