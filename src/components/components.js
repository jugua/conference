import homeComponent from './home/home';
import loginComponent from './login/login';
import listComponent from './list/list';

export default app => {
  INCLUDE_ALL_MODULES([homeComponent, loginComponent, listComponent], app);
};
