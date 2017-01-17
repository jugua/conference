Scenario: scenario description
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
|status|title  |
|New   |<title>|
And clicks reject button after filling comment:
|comment  |
|<comment>|
Then reject status is shown:
|status  |title  |
|Rejected|<title>|


Examples:
|<title>  |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestTitle|10           |10              |1      |1     |1         |1      |comment  |