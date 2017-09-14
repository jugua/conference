Scenario: user attach correct files
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks on 'Submit New Talk' button
And user fills data in 'Title','Description' and 'Additional Info':
|title  |description  |additionalInfo  |
|<title>|<description>|<additionalInfo>|
And choose Topic, Type, Language, Level dropdown menu:
|topic  |type  |language  |level  |
|<topic>|<type>|<language>|<level>|
And clicks on the pencil icon and choose file:
|filePath  |
|<filePath>|
And clicks 'Submit' button
Then file is attached


Examples:
|<filePath>                                                             |<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
|C:\Users\Lev_Serba\Desktop\positiveAttachmentTest\positiveDocFile.docx |10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\positiveAttachmentTest\positivePdfFile.pdf  |10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\positiveAttachmentTest\positivePptxFile.pptx|10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\positiveAttachmentTest\positivePptFile.ppt  |10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\positiveAttachmentTest\positiveOdpFile.odp  |10     |10           |10              |1      |1     |1         |1      |


Scenario: user attach files with incorrect size
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks on 'Submit New Talk' button
And user fills data in 'Title','Description' and 'Additional Info':
|title  |description  |additionalInfo  |
|<title>|<description>|<additionalInfo>|
And choose Topic, Type, Language, Level dropdown menu:
|topic  |type  |language  |level  |
|<topic>|<type>|<language>|<level>|
And clicks on the pencil icon and choose file:
|filePath  |
|<filePath>|
And clicks 'Submit' button
Then max size error message is displayed: "You exceeded the maximum allowed size of 300 MB. Please make another choice."

Examples:
|<filePath>                                                      |<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
|C:\Users\Lev_Serba\Desktop\negativeAttachmentTest\docFile0.docx |10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\negativeAttachmentTest\pptxFile0.pptx|10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\negativeAttachmentTest\pptFile0.ppt  |10     |10           |10              |1      |1     |1         |1      |
|C:\Users\Lev_Serba\Desktop\negativeAttachmentTest\odpFile0.ppt  |10     |10           |10              |1      |1     |1         |1      |


Scenario: user attach files with incorrect format
Given user logged as speaker accessing 'My Talks' page:
|email              |password|
|speaker@speaker.com|speaker |
When user clicks on 'Submit New Talk' button
And user fills data in 'Title','Description' and 'Additional Info':
|title  |description  |additionalInfo  |
|<title>|<description>|<additionalInfo>|
And choose Topic, Type, Language, Level dropdown menu:
|topic  |type  |language  |level  |
|<topic>|<type>|<language>|<level>|
And clicks on the pencil icon and choose file:
|filePath  |
|<filePath>|
And clicks 'Submit' button
Then format error message is displayed: "We could not understand the contents of your file. Make sure it is docx, pdf, pptx, ppt or odp."

Examples:
|<filePath>                                                              |<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
|C:\Users\Lev_Serba\Desktop\negativeAttachmentTest\icorrectFileFormat.jpg|10     |10           |10              |1      |1     |1         |1      |