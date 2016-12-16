Scenario: User successfully save valid info
Given user logged as speaker:
|email                   |password     |
|testNewUser@testUser.com|testuser12345|
When user clicks My Info option in account menu
And user fill valid info in fields:
|shortBio  |jobTitle  |company  |pastConferences  |linkedIn  |twitter  |facebook  |blog  |additionalInfo  |
|<shortBio>|<jobTitle>|<company>|<pastConferences>|<linkedIn>|<twitter>|<facebook>|<blog>|<additionalInfo>|
Then successfully saved popup is shown
And information saved successfully:
|shortBio  |jobTitle  |company  |pastConferences  |linkedIn  |twitter  |facebook  |blog  |additionalInfo  |
|<shortBio>|<jobTitle>|<company>|<pastConferences>|<linkedIn>|<twitter>|<facebook>|<blog>|<additionalInfo>|

Examples:
|<shortBio>|<jobTitle>|<company>         |<pastConferences>|<linkedIn>|<twitter>|<facebook>         |<blog>        |<additionalInfo>   |
|some info |job title |info about company|                 |          |         |                   |              |                   |
|some info |job title |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
