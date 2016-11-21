
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

Examples:
|<firstName>|<lastName>|<email>                |<password>|<confirmPassword>|
|firstName1 |lastName1 |Rand1@Rand1.tt         |password6 |password6        |
|firstName56|lastName56|testRand36@Rand20.tttt |password30|password30       |
|firstName27|lastName34|testRand4@Rand13.tttttt|password16|password16       |


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
|<firstName>|<lastName>|<email>                |<password>         |<confirmPassword>  |<expectedText>                                                               |<position>   |
|           |          |                       |                   |                   |All fields are mandatory. Please make sure all required fields are filled out|allFields    |
|           |lastName1 |Rand1@Rand1.tt         |testAutoPassNegpass|testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|nameField    |
|firstName56|          |testRand36@Rand20.tttt |testAutoPassNegpass|testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|lastNameField|
|firstName27|lastName34|                       |testAutoPassNegpass|testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|emailField   |
|firstName27|lastName34|testRand4@Rand13.tttttt|                   |testAutoPassNegpass|All fields are mandatory. Please make sure all required fields are filled out|passField    |
|firstName1 |lastName1 |Rand1@Rand1.tt         |testAutoPassNegpass|                   |All fields are mandatory. Please make sure all required fields are filled out|confPassField|



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
|firstName27|lastName27|testAuto@auto.       |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|firstName27|lastName27|testAuto@auto.t      |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|firstName27|lastName27|testAuto@auto.ttttttt|someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|firstName27|lastName27|testAuto@.ttt        |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|firstName27|lastName27|@auto.ttt            |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|firstName27|lastName27|testAuto@auto.123    |someAutoTestPass     |someAutoTestPass     |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|firstName27|lastName27|testRand4@Rand13.tt  |password5space       |confPassword5space   |Please use at least one non-space character in your password        |Your password must be at least 6 characters long. Please try another|passFields   |
|firstName27|lastName27|testRand4@Rand13.tt  |password6space       |confPassword6space   |Please use at least one non-space character in your password        |no text msg                                                         |passField2   |
|firstName27|lastName27|testRand4@Rand13.tt  |12345                |12345                |Your password must be at least 6 characters long. Please try another|no text msg                                                         |passField    |
|firstName27|lastName27|testRand4@Rand13.tt  |testAutoPass         |testAutoDifferentPass|Passwords do not match                                              |no text msg                                                         |confPassField|
|firstName27|lastName27|testRand4@Rand13.tt  |testAutoDifferentPass|testAutoPass         |Passwords do not match                                              |no text msg                                                         |confPassField|


Scenario: User unsuccessfully create new account with wrong length
Given user is on the sign up page
When user  types wrong values in some of the following fields: First Name, Last Name, E-mail, Password, Confirm Password:
|firstName  |lastName  |email  |password  |confirmPassword  |
|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
Then user cant enter/type more than the maximum allowed characters:
|firstExpectedText  |secondExpectedText  |position  |
|<firstExpectedText>|<secondExpectedText>|<position>|

Examples:
|<firstName>|<lastName>|<email>          |<password>                        |<confirmPassword>                 |<firstExpectedText>|<secondExpectedText>|<position>   |
|firstName57|lastName27|tesAuto@auto.tttt|someAutoTestPass                  |someAutoTestPass                  |no text msg        |no text msg         |nameField    |
|firstName93|lastName27|tesAuto@auto.tttt|someAutoTestPass                  |someAutoTestPass                  |no text msg        |no text msg         |nameField    |
|firstName27|lastName57|tesAuto@auto.tttt|someAutoTestPass                  |someAutoTestPass                  |no text msg        |no text msg         |lastNameField|
|firstName27|lastName87|tesAuto@auto.tttt|someAutoTestPass                  |someAutoTestPass                  |no text msg        |no text msg         |lastNameField|
|firstName27|lastName27|tesAuto@auto.tttt|Svfv67aeaegbeeejjrjrjejgjejvjejejr|Svfv67aeaegbeeejjrjrjejgjejvjejejr|no text msg        |no text msg         |passFieldMax |