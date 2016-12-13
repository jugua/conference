Scenario: When users put valid data for 'First Name' field on 'Account Settings' page
Meta:
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And puts valid data in First name:
|validData  |
|<validData>|
And clicks name save button
Then user click on the Edit link next to Name
And the new first name is changed:
|validData  |
|<validData>|
And menu Title is replaced by new name:
|validData  |
|<validData>|

Examples:
|<validData>                                             |
|A                                                       |
|0oDNrUrLUmUaKdOrQW5H5x3YIb2jFv6x9qXCD84nT4RCdDzftzPaDa9 |
|0oDNrUrLUmUaKdOrQW5H5x3YIb2jFv6x9qXCD84nT4RCdDzftzPaDa57|
|gjtsmdm                                                 |