Scenario: User input invalid email into 'Forgot password?' form
Meta:
@regression

Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying 'Please enter your email:'
And user fiels email textbox with invalid: <email>
And clicks on Continue button
And message apears saying Please enter a valid email address

Examples:
|email           |
|example.com     |
|user@example    |
|t@i.u           |
|tester@tester.22|