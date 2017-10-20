import actionTypes from '../constants/actions-types';

const { SET_TALKS, ASC, SORT_DATA } = actionTypes;

const userTalks = (state = [], { type, payload }) => {
  if (type === SET_TALKS) {
    return payload;
  } else if (type === SORT_DATA) {
    const { direction, talks, field } = payload;
    const sortDirection = (a, b) => {
      if (direction === ASC) {
        return a[field] > b[field];
      }
      return a[field] < b[field];
    };
    return talks.sort(sortDirection);
  }
  return state;
};

export default userTalks;
