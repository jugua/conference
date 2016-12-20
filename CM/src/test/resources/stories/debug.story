
Scenario: Sended mail verification
Meta:
@regression

Given the unsigned user accesses the conference management home page
When uses forgot password function
And user fiels email textbox with valid: <email>
Then clicks on Continue button
And message apears saying:'We just emailed you a link. Please check your email and click the secure link.'
And an email is send to users email adress:
|email            |
|tester@tester.com|
And subject's name is 'Password assistance'
And notification link was sent on email for Forgot Password
And fills new password 'tester'
And "Your Account" replaced by "Tester's Account"


Examples:
|email              |
|tester@tester.com  |
