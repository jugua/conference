Scenario: Email vlaidation for new Pw
Given user on the settings page logged as speaker:
|email            |password|
|testerPw@test.com|tester  |
And changes his password:
|currentPw|newPw |confirmNewPw|
|tester   |tester|tester      |
Then an email is send to users email adress:
|email            |
|testerPw@test.com|
And subject's name is 'Password assistance'
And body contains:'Hi <name>', 'Per your request, we have successfully changed your password.', 'The Conference Management Team.'


Examples:
|name  |
|Tester|