
Narrative: User create account
  In order to gain access to all features of the site
  As an anonymous user
  I want to be able to create account

Scenario: User successfully create new account
Meta:
@regression @smoke

Given user is on the sign up page
When user  fill in the following fields: First Name, Last Name, E-mail, Password, Confirm Password:
|firstName  |lastName  |email  |password  |confirmPassword  |
|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
And click submit button
Then new user is registered
And notification link was sent on email

Examples:
|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
|1          |1         |1,1,2  |6         |6                |
|56         |56        |36,20,4|30        |30               |
|27         |34        |4,13,6 |16        |16               |


Scenario: User unsuccessfully create new account with empty fields
Given user is on the sign up page
When user  leaves some of the following fields empty: First Name, Last Name, E-mail, Password, Confirm Password:
|firstName  |lastName  |email  |password  |confirmPassword  |expectedText  |position  |
|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|<expectedText>|<position>|
And click submit button
Then <expectedText> is highlighted in all the empty fields:
|expectedText  |position  |
|<expectedText>|<position>|
And new user is not registered

Examples:
|<firstName>|<lastName>|<email>|<password>         |<confirmPassword>  |<expectedText>                                                               |<position>   |
|           |          |       |                   |                   |All fields are mandatory. Please make sure all required fields are filled out|allFields    |
|           |1         |1,1,2  |testAutoPassNegpass|testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|nameField    |
|56         |          |36,20,4|testAutoPassNegpass|testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|lastNameField|
|27         |34        |       |testAutoPassNegpass|testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|emailField   |
|27         |34        |4,13,6 |                   |testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|passField    |
|1          |1         |1,1,2  |testAutoPassNegpass|                   |All fields are mandatory. Please make sure all required fields are filled out|confPassField|



Scenario: User unsuccessfully create new account with wrong input
Given user is on the sign up page
When user  types wrong values in some of the following fields: First Name, Last Name, E-mail, Password, Confirm Password:
|firstName  |lastName  |email  |password  |confirmPassword  |
|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
And click submit button
Then text msg is highlighted in all the incorrect fields:
|firstExpectedText  |secondExpectedText  |position  |
|<firstExpectedText>|<secondExpectedText>|<position>|
And new user is not registered

Examples:
|<firstName>|<lastName>|<email>              |<password>           |<confirmPassword>    |<firstExpectedText>                                                 |<secondExpectedText>                                                |<position>   |
|27         |27        |testAuto@auto.       |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |testAuto@auto.t      |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |testAuto@auto.ttttttt|someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |testAuto@.ttt        |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |@auto.ttt            |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |.@auto.ttt           |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |testAuto.@auto.ttt   |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |testAuto@auto.123    |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |test..Auto@auto.ttt  |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|27         |27        |some@valid.mail      |5space               |5space               |Please use at least one non-space character in your password        |Your password must be at least 6 characters long. Please try another|passFields   |
|27         |27        |some@valid.mail      |6space               |6space               |Please use at least one non-space character in your password        |no text msg                                                         |passField2   |
|27         |27        |some@valid.mail      |4                    |4                    |Your password must be at least 6 characters long. Please try another|no text msg                                                         |passField    |
|27         |27        |some@valid.mail      |testAutoPass         |testAutoDifferentPass|Passwords do not match                                              |no text msg                                                         |confPassField|
|27         |27        |some@valid.mail      |testAutoDifferentPass|testAutoPass         |Passwords do not match                                              |no text msg                                                         |confPassField|
|27         |27        |some@valid.mail      |testAutoDifferentPass|testAutoPass         |Passwords do not match                                              |no text msg                                                         |confPassField|


Scenario: User unsuccessfully create new account with wrong length
Given user is on the sign up page
When user  types wrong values in some of the following fields: First Name, Last Name, E-mail, Password, Confirm Password:
|firstName  |lastName  |email  |password  |confirmPassword  |
|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
Then user cant enter/type more than the maximum allowed characters:
|firstExpectedText  |secondExpectedText  |position  |
|<firstExpectedText>|<secondExpectedText>|<position>|

Examples:
|<firstName>|<lastName>|<email>          |<password>                         |<confirmPassword>                  |<firstExpectedText>|<secondExpectedText>|<position>   |
|57         |27        |tesAuto@auto.tttt|someAutoTestPass                   |someAutoTestPass                   |no text msg        |no text msg         |nameField    |
|93         |27        |tesAuto@auto.tttt|someAutoTestPass                   |someAutoTestPass                   |no text msg        |no text msg         |nameField    |
|27         |57        |tesAuto@auto.tttt|someAutoTestPass                   |someAutoTestPass                   |no text msg        |no text msg         |lastNameField|
|27         |87        |tesAuto@auto.tttt|someAutoTestPass                   |someAutoTestPass                   |no text msg        |no text msg         |lastNameField|
|27         |27        |tesAuto@auto.tttt|SvfvYTsaeaegbeeejjrjrjejgjejvjejejr|SvfvYTsaeaegbeeejjrjrjejgjejvjejejr|no text msg        |no text msg         |passFieldMax |