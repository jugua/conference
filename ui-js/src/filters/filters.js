import roleFilter from './role-filter/role';

export default (app) => {
  include_all_modules([
    roleFilter
  ], app);
};