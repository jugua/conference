import roleFilter from './role.filter';

export default (app) => {
  app.filter('roleFilter', roleFilter);
};