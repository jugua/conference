Scenario: Sended mail verification
Given the unsigned user accesses the conference management home page
When uses forgot password function
And user fiels email textbox with valid: <email>
Then clicks on Continue button
And message apears saying:'We sent a new password to your email address <email>'
And an email is send to users email adress:
|email            |
|tester@tester.com|
And subject's name is 'Password assistance'


Examples:
|email              |
|tester@tester.com  |