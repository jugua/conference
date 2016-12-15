Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal


Scenario: user type valid email in New email field
Given user on the settings page logged as speaker:
|email                            |password   |
|testSettingsChangeEmail@test.test|testuserpwd|
When user click on the Edit link next to Email
And type email in New email field:
|newEmail  |
|<newEmail>|
And clicks email save button
And user click on the Edit link next to Email
Then email has been changed
And user can log in with it

Examples:
|<newEmail>                    |
|testSettingsNewEmail@test.test|


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
|testSettingsNewEmail@test.test|
