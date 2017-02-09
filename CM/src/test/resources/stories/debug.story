<<<<<<< HEAD
Scenario: once a user hovers attachment icon he can see a hint
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks on 'Submit New Talk' button
And user hovers attachment icon over
Then hint "You can add an attachment of your talk here and insert a link to it in the Additional Info field.
           Allowed formats are docx, pdf, pptx, ppt, odp and maximum size is 300 Mb" is displayed

Scenario:
=======
Scenario: Fill valid data in all field

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
And clicks 'Submit' button
Then talk appears in grid and have 'New' status
And email was sent to users email :
|email             |
|mytalks@tester.com|
And with subject 'Your talk's status has been updated'
And body contains 'Thank you for your submitted talk. It has been successfully registered in our system. We will review it and send an additional info soon.'

Examples:
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
|10     |10           |10              |1      |1     |1         |1      |
|15     |12           |12              |2      |2     |2         |2      |
|15     |12           |12              |2      |2     |2         |2      |
>>>>>>> 96907699db0949cef74e0f89d400002b20cd6276
