
Scenario: Admin unsuccessfully create new account with wrong input
Given admin accesses Add New User popup:
|email          |password |
|admin@gmail.com|java1love|
When admin fills data in in the following fields: Role, First Name, Last Name, E-mail, Password, Confirm Password:
|role  |firstName  |lastName  |email  |password  |confirmPassword  |
|<role>|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
Then text msg is highlighted in all incorrect fields:
|firstExpectedText  |secondExpectedText  |position  |
|<firstExpectedText>|<secondExpectedText>|<position>|
And new user is not registered

Examples:
|<role>|<firstName>|<lastName>|<email>              |<password>           |<confirmPassword>|<firstExpectedText>                                                 |<secondExpectedText>                                                |<position>   |
|1     |27         |27        |testAuto@auto.       |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |testAuto@auto.t      |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |testAuto@auto.ttttttt|someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |testAuto@.ttt        |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |@auto.ttt            |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |.@auto.ttt           |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |testAuto.@auto.ttt   |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |testAuto@auto.123    |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |test..Auto@auto.ttt  |someAutoTestPass     |someAutoTestPass |Please enter a valid email address                                  |no text msg                                                         |emailField   |
|1     |27         |27        |some@valid.mail      |5space               |5space           |Please use at least one non-space character in your password        |Your password must be at least 6 characters long. Please try another|passFields   |
|1     |27         |27        |some@valid.mail      |6space               |6space           |Please use at least one non-space character in your password        |no text msg                                                         |passField2   |
|1     |27         |27        |some@valid.mail      |4                    |4                |Your password must be at least 6 characters long. Please try another|no text msg                                                         |passField    |
|1     |27         |27        |3,3,3                |testAutoPass         |asdasd           |Passwords do not match                                              |no text msg                                                         |confPassField|
|1     |27         |27        |3,3,3                |testAutoDifferentPass|testAutoPass     |Passwords do not match                                              |no text msg                                                         |confPassField|
|1     |27         |27        |3,3,3                |testAutoDifferentPass|testAutoPass     |Passwords do not match                                              |no text msg                                                         |confPassField|
