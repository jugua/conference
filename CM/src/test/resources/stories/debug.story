
Scenario: When users put invalid data for 'First Name' field on 'Account Settings' page
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And puts data in First name:
|validData  |
|<validData>|
Then user can`t enter/type more than the maximum allowed characters in first name field:
|validData  |
|<validData>|


Examples:
|<validData>                                              |
|yG6XL7qeWy2DWOqcEkefUem2BKWtVqPCXlFd48yM7ItzgqlMSTicTJHQn|                         |