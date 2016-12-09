Scenario: Being signed in as a speaker manage My Account page, My Info page and My Talks
Meta:
@regression @smoke

Given the unsigned user accesses home page
And user clicks 'Your Account' menu option
And user filled in login form:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks SignIn button on login form
Then "Your Account" replaced by "Speaker's Account"
And there are My_account, My_Info and  My Talks links in the given order
|btnName   |link       |
|My Info   |/#/my-info |
|My Talks  |/#/my-talks|
|Settings  |/#/account |
|Sign Out  |/#/        |
When user logs out