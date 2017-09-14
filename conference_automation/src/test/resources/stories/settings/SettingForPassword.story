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


Scenario: Email vlaidation for new password
Given user on the settings page logged as speaker:
|email            |password|
|testerPw@test.com|tester  |
And changes his password:
|currentPw|newPw |confirmNewPw|
|tester   |tester|tester      |
Then an email is send to users email adress:
|email            |
|testerPw@test.com|
And subject's name is 'Your password has been changed'
And body contains:'Hi <name>',
'Per your request, we have successfully changed your password.',
'The Conference Management Team.'


Examples:
|name  |
|Tester|

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
Then messages apears saying 'You entered an invalid current password.'


Examples:
|<currentPw>                    |
|tester1234tester1234tester1234t|
|confPassword6space             |
|1                              |
|tester1234tester1234tester1234 |

Scenario: Input less then 6 symbols into a 'New password' and 'Confirm new password' field
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
|newPw  |confirmNewPw  |
|<newPw>|<confirmNewPw>|
And clicks password's Save button
Then messages apears saying 'Your password must be at least 6 characters long. Please try another.'
Examples:
|<newPw>|<confirmNewPw>|
|1      |1             |

Scenario: Input 6 space symbols into a 'New password' and 'Confirm new password' field
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
|newPw  |confirmNewPw  |
|<newPw>|<confirmNewPw>|
And clicks password's Save button
Then messages apears saying 'Please use at least one non-space character in your password.'
Examples:
|<newPw>           |<confirmNewPw>    |
|confPassword6space|confPassword6space|

Scenario: Input not matching data into a 'New password' and 'Confirm new password' field
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
|newPw  |confirmNewPw  |
|<newPw>|<confirmNewPw>|
And clicks password's Save button
Then messages apears saying 'Passwords do not match.'
Examples:
|<newPw>|<confirmNewPw>|
|123123 |321311        |

Scenario: Leave empty fields in Change password fields
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
|newPw  |confirmNewPw  |
|<newPw>|<confirmNewPw>|
And clicks password's Save button
Then messages apears saying 'All fields are mandatory. Please make sure all required fields are filled out.'
Examples:
|<currentPw>|<newPw>  |<confirmNewPw>|
|           |123123123|123123123123  |
|123123     |         |123123123123  |
|12312312313|12312312 |              |
 