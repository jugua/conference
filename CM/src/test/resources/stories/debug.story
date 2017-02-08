Scenario: once a user hovers attachment icon he can see a hint
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks on 'Submit New Talk' button
And user hovers attachment icon over
Then hint "You can add an attachment of your talk here and insert a link to it in the Additional Info field.
           Allowed formats are docx, pdf, pptx, ppt, odp and maximum size is 300 Mb" is displayed

Scenario: