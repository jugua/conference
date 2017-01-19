Scenario: Speaker didnt filled My info majority fields

Given user logged as speaker accessing 'My Talks' page:
|email                  |password|
|mytalksEmpty@tester.com|tester  |
When user clicks on 'Submit New Talk' button
Then pop up is shown with text 'Please fill out all required info on the My Info page before submitting a talk.'
And clicks OK button