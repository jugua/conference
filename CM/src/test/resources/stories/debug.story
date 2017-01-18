Scenario: All fields except Organizer's Comments box are read-only
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
Then all fields except Organizer's Comments box are read-only

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |