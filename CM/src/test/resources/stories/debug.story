Scenario: Organiser view speaker's info
Given user logged as speaker accessing 'My Talks' page:
|email            |password|
|tester@tester.com|tester  |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
And user logged as organiser 'Talks' page:
|email              |password |
|organiser@gmail.com|organiser|
When organiser clicks speaker's name
Then speakers info is shown with read-only fields

Examples:
|<title>    |<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|<comment>|
|TestMyTalks|10           |10              |1      |1     |1         |1      |comment  |