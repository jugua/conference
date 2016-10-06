import usersService from './users.service';

export default app => {
  app.factory('users', usersService);



}
