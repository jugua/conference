Scenario: When users put valid data for 'First Name' and ' Last Name' fields
on 'Account Settings' page and peress the Cacel button
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And puts data in First name:
|validData  |
|<validData>|
And puts data in Last name:
|validData  |
|<validData>|
And clicks Name's field Cancel button
Then user click on the Edit link next to Name
And Changes are not saved and user can see his/her old name in the Name row:
|validData  |
|<validData>|

Examples:
|<validData>|
|ValidData  |                    |