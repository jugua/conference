Scenario: User input not registrated email
Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying 'Please enter your email:'
And user fiels email textbox with invalid: <email>
And clicks on Continue button
And message apears saying We can not find an account with that email address

Examples:
|email          |
|example@qwe.com|