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