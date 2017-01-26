Scenario: Organiser view speaker's info
Given user logged as speaker accessing 'My Talks' page:
|email            |password|
|tester@tester.com|tester  |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |


Scenario: user type valid email in New email field
Given user on the settings page logged as speaker:
|email                            |password   |
|testSettingsChangeEmail@test.test|testuserpwd|
When user click on the Edit link next to Email
And type email in New email field:
|newEmail  |
|<newEmail>|
And clicks email save button
Then notification change email link was sent on email
And email is changed

Examples:
|<newEmail>                          |
|testSettingsSecondEmail@test.test|


Scenario: user type valid email in New email field but cancel changes
Given user on the settings page logged as speaker:
|email                            |password   |
|testSettingsChangeEmail@test.test|testuserpwd|
When user click on the Edit link next to Email
And type email in New email field:
|newEmail  |
|<newEmail>|
And clicks email cancel button
And user click on the Edit link next to Email
Then email didnt change
And user can log in with old credentials

Examples:
|<newEmail>                          |
|testSettingsNewSecondEmail@test.test|