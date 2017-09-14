
Meta:
Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: My Info page is loaded successfully
Given user logged as speaker:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks My Info option in account menu
Then my info page is opened with active My Info tab
And user logout

Scenario: User can switch to MyTalks tab and  back
Given user logged as speaker:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks My Info option in account menu
And switch to MyTalks
Then MyTalks tab is active
When user switch back to MyInfo tab
Then MyInfo tab is active
And user logout

Scenario: My Info page fields are empty by default
Given user logged as speaker:
|email                        |password     |
|testNewUserEmpty@testUser.com|testuser12345|
When user clicks My Info option in account menu
Then all the following fields are empty Short Bio, Job Title, Company, Past Conferences, Linked In, Twitter, Facebook, Blog, Additional info
And user logout

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
|<shortBio>|<jobTitle>|<company>         |<pastConferences>|<linkedIn>|<twitter>|<facebook>         |<blog>        |<additionalInfo>    |
|some info |job title |info about company|                 |          |         |                   |              |                    |
|some info |job title |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|

Scenario: User fill valid info but forget press Save button
Given user logged as speaker:
|email                   |password     |
|testNewUser@testUser.com|testuser12345|
When user clicks My Info option in account menu
And user fill valid info in fields:
|shortBio  |jobTitle  |company  |pastConferences  |linkedIn  |twitter  |facebook  |blog  |additionalInfo  |
|<shortBio>|<jobTitle>|<company>|<pastConferences>|<linkedIn>|<twitter>|<facebook>|<blog>|<additionalInfo>|
And user go away from my info tab without clicking save button
Then attention pop up is shown
And user logout

Examples:
|<shortBio>|<jobTitle>|<company>         |<pastConferences>|<linkedIn>|<twitter>|<facebook>         |<blog>        |<additionalInfo>    |
|some info |job title |info about company|                 |          |         |                   |              |                    |
|some info |job title |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|



Scenario: User forget to fill some of the mandatory fields
Given user logged as speaker:
|email                    |password     |
|testNewUser2@testUser.com|testuser12345|
When user clicks My Info option in account menu
And user fill valid info in fields:
|shortBio  |jobTitle  |company  |pastConferences  |linkedIn  |twitter  |facebook  |blog  |additionalInfo  |
|<shortBio>|<jobTitle>|<company>|<pastConferences>|<linkedIn>|<twitter>|<facebook>|<blog>|<additionalInfo>|
Then error popup is shown
And user logout

Examples:
|<shortBio>|<jobTitle>|<company>         |<pastConferences>|<linkedIn>|<twitter>|<facebook>         |<blog>        |<additionalInfo>    |
|some info |          |info about company|                 |          |         |                   |              |                    |
|some info |job title |                  |info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|          |          |                  |info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|          |job title |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|


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
|<shortBio>|<jobTitle>|<company>         |<pastConferences>|<linkedIn>|<twitter>|<facebook>         |<blog>        |<additionalInfo>    |
|2001      |job title |info about company|                 |          |         |                   |              |                    |
|some info |257       |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info |job title |info about company|1001             |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info |job title |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|1001                |
|3000      |job title |info about company|                 |          |         |                   |              |                    |
|some info |400       |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info |job title |info about company|1200             |srgwg     |facebook |facebook.com/fwfwgg|some blog info|some additional info|
|some info |job title |info about company|info about conf  |srgwg     |facebook |facebook.com/fwfwgg|some blog info|1600                |