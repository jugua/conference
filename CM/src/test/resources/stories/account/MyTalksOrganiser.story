
Scenario: Organiser reject new talk
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks new created Talk:
|status|
|New   |
And clicks reject button after filling comment:
|comment  |
|<comment>|
Then reject status is shown:
|status  |
|Rejected|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |


Scenario: Organiser reject talk which was in progress
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks new created Talk:
|status|
|New   |
And clicks in progress button after filling comment:
|comment  |
|<comment>|
When organiser clicks new created Talk:
|status     |
|In Progress|
And clicks reject button after filling comment:
|comment  |
|<comment>|
Then reject status is shown:
|status  |
|Rejected|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |


Scenario: Organiser reject new talk without comment
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks new created Talk:
|status|
|New   |
And clicks reject button without filling comment
Then message asking me to specify rejection reason:
|message  |
|Please specify a rejection reason|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |


Scenario: Organiser put in progress new talk
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks new created Talk:
|status|
|New   |
And clicks in progress button after filling comment:
|comment  |
|<comment>|
Then in progress status is shown:
|status     |
|In Progress|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |


Scenario: Organiser approve new talk
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks new created Talk:
|status|
|New   |
And clicks approve button after filling comment:
|comment  |
|<comment>|
Then approve status is shown:
|status  |
|Approved|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |
|TestMyTalks|10           |10              |1      |1     |1         |1      |         |


Scenario: Organiser approve  talk which was in progress
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks new created Talk:
|status|
|New   |
And clicks in progress button after filling comment:
|comment  |
|<comment>|
When organiser clicks new created Talk:
|status     |
|In Progress|
And clicks approve button after filling comment:
|comment  |
|<comment>|
Then approve status is shown:
|status  |
|Approved|

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |
|TestMyTalks|10           |10              |1      |1     |1         |1      |         |
