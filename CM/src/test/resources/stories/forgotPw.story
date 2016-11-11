Meta:

Narrative:
As a user
I would to have an ability to reset my password
Once a user clicks on the Forgot Password link the new pop up will appears saying
"Send me a new password to my email address +user's email address+ " with the Cancel and Continue button bellow

Scenario: forgtot pasword pop up
Given the unsigned user accesses the conference management home page
When user clicks the login button
And clicks the forgot password link
Then new pop up will appears saying Send me a new password to my email address:
And Cancel button appears
And Continue button appears