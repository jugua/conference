export default () => {
  function roleFilter(input) {
    if (input.indexOf('ROLE_ADMIN') !== -1) {
      return 'Admin';
    }

    if (input.indexOf('ROLE_SPEAKER') !== -1) {
      return 'Speaker';
    }

    if (input.indexOf('ROLE_ORGANISER') !== -1) {
      return 'Organiser';
    }

    return false;   // consistent return
  }

  return roleFilter;
};