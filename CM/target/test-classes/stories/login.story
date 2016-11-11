Meta:

Narrative: User login
  In order to gain access to all features of the site
  As an anonymous customer
  I want to be able to login to my account
Meta:

@regression @smoke
Scenario: User successfully logs in to the site
Given user is on Home page
When user clicks 'Your Account' button in page header
Then account menu is unfolding

Given user filled in login form:
| email              | password|
| speaker@speaker.com| speaker |
When user clicks SignIn button on login form
Then 'Your Account' replaced by 'Speaker's Account'
And there is 'Sign Out' button in account menu

When user logs out
Then user is redirected to Home page
And there is 'Your Account' button in page header


Meta:

@regression @smoke
Scenario: User unsuccessfully logs in to the site with invalid credentials
Meta:

Given user is on Home page
When user clicks 'Your Account' button in page header
Then account menu is unfolding

Given user filled in login form:
| email              | password|
| speaker@speaker.com| wrong   |
When user clicks SignIn button on login form
Then user still in login form
And password field is highlighted
And wrong password message is Your password is incorrect

