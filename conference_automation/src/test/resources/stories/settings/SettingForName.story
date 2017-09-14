Meta:

Narrative:
As a user
I would like to have an ability to update my account info: first name, last name
Test cases 3350 - 3358

Scenario: When user clicks on the Edit link next to Name the row is expanded and
has following elements: 'First Name', 'Last Name' fields 'Save' and 'Cancel' buttons
Meta:
@regression
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
And clicks name save button
Then Empty field is highlighted in red and  message saying 'Please enter a name' is shown

Scenario: When user leaves all fields empty
Meta:
@regression
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And leaves 'First name' field empty
And leaves 'Last name' field empty
And clicks name save button
Then empty fields are highlighted in red and message saying 'Please enter a name' is shown

Scenario: When users put valid data for 'Last Name' field on 'Account Settings' page
Meta:
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And puts data in Last name:
|validData  |
|<validData>|
And clicks name save button
Then user click on the Edit link next to Name
And the new last name is changed:
|validData  |
|<validData>|

Examples:
|<validData>                                             |
|A                                                       |
|0oDNrUrLUmUaKdOrQW5H5x3YIb2jFv6x9qXCD84nT4RCdDzftzPaDa9 |
|0oDNrUrLUmUaKdOrQW5H5x3YIb2jFv6x9qXCD84nT4RCdDzftzPaDa57|
|gjtsmdm                                                 |

Scenario: When users put valid data for 'First Name' field on 'Account Settings' page
Meta:
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And puts data in First name:
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
|0oDNrUrLUmUaKdOrQW5H5x3YIb2jFv6x9qXCD84nT4RCdDzftzPaD55 |
|0oDNrUrLUmUaKdOrQW5H5x3YIb2jFv6x9qXCD84nT4RCdDzftzPaDa56|
|gjtsmdm                                                 |

Scenario: When users put invalid data for 'Last Name' field on 'Account Settings' page
Meta:
Given user on the settings page logged as speaker:
|email                              |password   |
|testUserSettingsStory@testUser.test|testuserpwd|
When user click on the Edit link next to Name
And puts data in Last name:
|validData  |
|<validData>|
Then user can`t enter/type more than the maximum allowed characters in last name field:
|validData  |
|<validData>|

Examples:
|<validData>                                              |
|yG6XL7qeWy2DWOqcEkefUem2BKWtVqPCXlFd48yM7ItzgqlMSTicTJHQn|


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
|yG6XL7qeWy2DWOqcEkefUem2BKWtVqPCXlFd48yM7ItzgqlMSTicTJHQn|


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
|ValidData  |