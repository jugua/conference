Scenario: Fill valid data in all field not saving it
Given user logged as speaker accessing 'My Talks' page:
|email             |password|
|mytalks@tester.com|tester  |
When user clicks on 'Submit New Talk' button
And user fills data in 'Title','Description' and 'Additional Info':
|title  |description  |additionalInfo  |
|<title>|<description>|<additionalInfo>|
And choose Topic, Type, Language, Level dropdown menu:
|topic  |type  |language  |level  |
|<topic>|<type>|<language>|<level>|
And clicks 'Exit' button
Then info msg is shown saying 'Are you sure you want to leave the window?',
 'Click 'Yes' to leave and all your changes will lost.',
  'Click 'No' to return and Submit/Update your changes'
And click 'Yes' button

Examples:
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
|10     |10           |10              |1      |1     |1         |1      |