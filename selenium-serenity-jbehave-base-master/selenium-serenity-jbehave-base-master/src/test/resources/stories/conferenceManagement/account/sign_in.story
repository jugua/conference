Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

As an unsigned user I would like to have an ability to sign it to my account by clicking on
Your Account menu option and entering my email and password (see makeup.com.ua as an example)
Once an unsigned user signed in successfully s/he will see her/his first name on the menu option,
i.e. Andrew's Account

I will know that the User Story is done when...
I can successfully sign it to the system under my valid login/password
I am not allowed to sign in to the system using invalid login or password.
In case an invalid/unregistered email address is entered I get a warning message "We can not find an account with that email address".
In case an incorrect password is entered I get the message saying "Your password is incorrect"


Scenario: login as valid user
Given the unsigned user accesses the conference management home page
When when he login as valid user
Then user signed in successfully

Scenario: login as invalid user
Given the unsigned user accesses the conference management home page
When when he login as invalid user
Then user is unsigned


