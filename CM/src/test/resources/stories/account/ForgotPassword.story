Narrative:
As a user
I would to have an ability to reset my password
Once a user clicks on the Forgot Password link the new pop up will appears saying
"Send me a new password to my email address +user's email address+ " with the Cancel and Continue button bellow

Scenario: Then user click 'Forgot pasword' link pop up element are present
Meta:
@regression @smoke

Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying 'Please enter your email:'
And Cancel button appears
And Continue button appears


Scenario: User leaves empty 'E-mail' field in 'Forgot Password' form
Meta:
@regression

Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying 'Please enter your email:'
And clicks on Continue button
And message is shown sayin Please enter your registered email
And email field is highlighted

Scenario: User input valid data into 'Forgot password?' form

Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying 'Please enter your email:'
And user fiels email textbox with valid: <email>
And clicks on Continue button
And message apears saying:'We just emailed you a link. Please check your email and click the secure link.'
And 'Close' button is shown

Examples:
|email              |
|tester@tester.com  |
|speaker@speaker.com|


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


Scenario: User input not registrated email
Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying 'Please enter your email:'
And user fiels email textbox with invalid: <email>
And clicks on Continue button
And message appears saying for not existing email 'We can not find an account with that email address'

Examples:
|email          |
|example@qwe.com|



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
|email            |
|tester@tester.com|