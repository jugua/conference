Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

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
|<newEmail>                          |
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
|<newEmail>                          |
|testSettingsNewSecondEmail@test.test|