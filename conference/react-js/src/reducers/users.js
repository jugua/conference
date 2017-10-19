import actions from '../constants/actions-types';
import propComparator from '../utils/sortData';

const { LOAD_USER_DATA, SORT_ALL_TALKS } = actions;

const users = (allUser = [], action) => {
  const { type, payload } = action;
  if (type === LOAD_USER_DATA) {
    return [
      ...allUser,
      ...payload,
    ];
  } else if (type === SORT_ALL_TALKS) {
    const { direction, talks, field } = payload;
    return [...talks.sort(propComparator(field, direction))];
  }
  return allUser;
};

export default users;
