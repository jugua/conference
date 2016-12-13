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

Scenario: user type wrong email in New email field
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Email
And type incorrect values in New email field:
|wrongEmail  |
|<wrongEmail>|
And clicks email save button
Then user see a warning message saying 'Please enter a valid email address'

Examples:
|<wrongEmail>         |
|testAuto@auto.       |
|testAuto@auto.t      |
|testAuto@auto.ttttttt|
|testAuto@.ttt        |
|@auto.ttt            |
|testAuto@auto.123    |
