import actionTypes from '../constants/actions-types';
import propComparator from '../utils/sortData';

const { SET_TALKS, SORT_USER_TALKS } = actionTypes;

const userTalks = (state = [], { type, payload }) => {
  if (type === SET_TALKS) {
    return payload;
  } else if (type === SORT_USER_TALKS) {
    const { direction, talks, field } = payload;
    return [...talks.sort(propComparator(field, direction))];
  }
  return state;
};

export default userTalks;
