import actionTypes from '../constants/actions-types';

const propComparator = (field, direction) => {
  if (direction === actionTypes.ASC) {
    return (a, b) => {
      if (a[field] > b[field]) {
        return 1;
      } else if (a[field] < b[field]) {
        return -1;
      }
      return 0;
    };
  }
  return (a, b) => {
    if (a[field] < b[field]) {
      return 1;
    } else if (a[field] > b[field]) {
      return -1;
    }
    return 0;
  };
};

export default propComparator;
