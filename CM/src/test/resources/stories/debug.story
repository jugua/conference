Scenario: Submit new Talk with empty fields
Given user logged as speaker accessing 'My Talks' page:
|email             |password|
|mytalks@tester.com|tester  |
When user clicks on 'Submit New Talk' button
And clicks 'Submit' button
Then pop-up window 'Please fill in all mandatory fields.' is shown
And all fields are highlighted in red