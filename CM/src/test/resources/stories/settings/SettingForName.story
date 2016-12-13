Meta:

Narrative:
As a user I would like to have an ability to update my account info: first name, last name


Scenario: When user clicks on the Edit link next to Name the row is expanded and
has following elements: 'First Name', 'Last Name' fields 'Save' and 'Cancel' buttons
Meta:
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
Then 'Name' field has following elements: 'First Name', 'Last Name' fields 'Save' and 'Cancel' buttons

Scenario: When user leaves 'Last name' field empty
Meta:
@regression
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And leaves 'Last name' field empty
And clicks Save button
Then Empty field is highlighted in red and  message saying 'Please enter a name' is shown

