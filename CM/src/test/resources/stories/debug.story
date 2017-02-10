Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: Admin accesses admin page
Given user logged as admin:
|email          |password |
|admin@gmail.com|java1love|
When admin accesses the Admin page
Then user can see all created user in the table

Scenario: admin successfully adds new user

Given admin accesses Add New User popup:
|email          |password |
|admin@gmail.com|java1love|
When admin fills data in in the following fields: Role, First Name, Last Name, E-mail, Password, Confirm Password:
|role  |firstName  |lastName  |email  |password  |confirmPassword  |
|<role>|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
Then new user appears in the user table


Examples:
|<role>|<firstName>|<lastName>|<email>|<password>|<confirmPassword>|
|1     |1          |1         |1,1,2  |6         |6                |
|1     |56         |56        |36,20,4|30        |30               |
|1     |27         |34        |4,13,6 |16        |16               |

