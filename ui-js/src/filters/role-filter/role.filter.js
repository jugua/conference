export default () => {
  function roleFilter(input) {
    if (input.indexOf('a') !== -1) {
      return 'Admin';
    }

    if (input.indexOf('s') !== -1) {
      return 'Speaker';
    }

    if (input.indexOf('o') !== -1) {
      return 'Organiser';
    }
  }

  return roleFilter;
};