Scenario: User fill some fields with incorrect length values
Given user logged as speaker:
|email                    |password     |
|testNewUser2@testUser.com|testuser12345|
When user clicks My Info option in account menu
And user fill valid info in fields:
|shortBio  |jobTitle  |company  |pastConferences  |linkedIn  |twitter  |facebook  |blog  |additionalInfo  |
|<shortBio>|<jobTitle>|<company>|<pastConferences>|<linkedIn>|<twitter>|<facebook>|<blog>|<additionalInfo>|
Then user can`t enter/type more than the maximum allowed characters:
|shortBio  |jobTitle  |company  |pastConferences  |linkedIn  |twitter  |facebook  |blog  |additionalInfo  |
|<shortBio>|<jobTitle>|<company>|<pastConferences>|<linkedIn>|<twitter>|<facebook>|<blog>|<additionalInfo>|
And user logout

Examples:
|<shortBio>        |<jobTitle>       |<company>         |<pastConferences> |<linkedIn>|<twitter>|<facebook>         |<blog>        |<additionalInfo>   |
|stringsNumbers2001|job title        |info about company|                  |          |         |                   |              |                   |
|some info         |stringsNumbers257|info about company|info about conf   |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info         |job title        |info about company|stringsNumbers1001|srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info         |job title        |info about company|info about conf   |srgwg     |facebook |facebook.com/fwfwgg|some blog info|stringsNumbers1001 |
|stringsNumbers3000|job title        |info about company|                  |          |         |                   |              |                   |
|some info         |stringsNumbers400|info about company|info about conf   |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info         |job title        |info about company|stringsNumbers1200|srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info         |job title        |info about company|info about conf   |srgwg     |facebook |facebook.com/fwfwgg|some blog info|stringsNumbers1600 |