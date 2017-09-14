
Narrative: User logout
As an  user
I want to be able to logout from my account

Scenario: User successfully logs out from the Account
Meta:
@regression @smoke

Given the user logged as:
|email            |password|
|tester@tester.com|tester  |
Given user logs out
Then user is redirected to Home page
And there is "Your Account" button in page header