Scenario: Input invalid data into a 'Current password' field
Meta:
@regression
Given user on the settings page logged as speaker:
|email            |password|
|testerPw@test.com|tester  |
When user click on the Edit link next to Password
And enter current password in 'Current password' field:
|currentPw  |
|<currentPw>|
And enter new password in 'New password' field and confirms it:
|newPw  |confirmNewPw|
|tester1|tester1     |
And clicks password's Save button
Then messages apears saying 'You entered an invalid current password'


Examples:
|<currentPw>                    |
|tester1234tester1234tester1234t|
 