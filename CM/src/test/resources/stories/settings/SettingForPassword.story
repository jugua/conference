Meta:

Narrative:
As a user
I would like to have an ability to change my password
Test cases for story 3044 and 3273

Scenario: Input valid data into a 'New password' field
Meta:
@regression
Given user on the settings page logged as speaker:
|email            |password|
|testerPw@test.com|tester  |
When user click on the Edit link next to Password
And enter current password in 'Current password' field:
|currentPw|
|tester   |
And enter new password in 'New password' field and confirms it:
|newPw  |confirmNewPw|
|tester1|tester1     |
And clicks password's Save button
Then user is able to login with new password:
|email            |password|
|testerPw@test.com|tester1 |
And changes his password:
|currentPw|newPw |confirmNewPw|
|tester1  |tester|tester      |

Scenario: Email vlaidation for new Pw
Given user on the settings page logged as speaker:
|email            |password|
|testerPw@test.com|tester  |
And changes his password:
|currentPw|newPw |confirmNewPw|
|tester1  |tester|tester      |