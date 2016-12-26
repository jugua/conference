
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
|27         |27        |tesAuto@auto.tttt|SvfvYTsaeaegbeeejjrjrjejgjejvjejejr|SvfvYTsaeaegbeeejjrjrjejgjejvjejejr|no text msg        |no text msg         |passFieldMax