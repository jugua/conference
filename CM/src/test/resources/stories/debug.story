
Scenario: When user click on the Edit link next to Email Current Email and New Email fields are visible
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Email
Then Current Email and New Email fields are visible

Scenario: user type invalid email in New email field
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Email
And type email in New email field:
|newEmail  |
|<newEmail>|
And clicks email save button
Then user see a warning message saying 'Please enter a valid email address'

Examples:
|<newEmail>           |
|testAuto@auto.       |
|testAuto@auto.t      |
|testAuto@auto.ttttttt|
|testAuto@.ttt        |
|@auto.ttt            |
|testAuto@auto.123    |


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
|<newEmail>                    |
|testSettingsNewSecondEmail@test.test|


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
|<newEmail>                    |
|testSettingsNewSecondEmail@test.test|

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

