/* global include_all_modules */
import headerComponent from './header/header';
import homeComponent from './home/home';
import signInComponent from './sign-in/sign-in';
import accountPageComponent from './account-page/account-page';
<<<<<<< HEAD
import myInfoComponent from './my-info/my-info';
import tabsComponent from './tabs/tabs';
=======
import signUpComponent from './sign-up/sign-up';
>>>>>>> fa15b40b06e8ec2934b825de13963bcf6788c395

export default (app) => {
  include_all_modules([
    headerComponent,
    homeComponent,
    signInComponent,
    accountPageComponent,
<<<<<<< HEAD
    myInfoComponent,
    tabsComponent
  ], app);
}
=======
    signUpComponent
  ], app);
};
>>>>>>> fa15b40b06e8ec2934b825de13963bcf6788c395
