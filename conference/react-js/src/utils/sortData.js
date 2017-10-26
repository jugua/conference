import actionTypes from '../constants/actions-types';

const propComparator = (field, direction) => {
  if (direction === actionTypes.ASC) {
    return (a, b) =>
      a[field] > b[field];
  }
  return (a, b) =>
    a[field] < b[field];
};

export default propComparator;
