Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description
Given user logged as speaker accessing 'My Talks' page:
|email                  |password|
|mytalksEmpty@tester.com|tester  |
And creates new Talk:
|title  |description  |additionalInfo  |topic  |type  |language  |level  |
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|



Examples:
|<title>|<description>|<additionalInfo>|<topic>|<type>|<language>|<level>|
|10     |10           |10              |1      |1     |1         |1      |
