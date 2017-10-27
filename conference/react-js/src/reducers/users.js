import actions from '../constants/actions-types';
import propComparator from '../utils/sortData';

const { SORT_ALL_TALKS, LOAD_USER_DATA } = actions;

const users = (allUser = [], action) => {
  const { type, payload } = action;
  if (type === LOAD_USER_DATA) {
    return payload;
  } else if (type === SORT_ALL_TALKS) {
    const { direction, talks, field } = payload;
    return talks.sort(propComparator(field, direction));
  }
  return allUser;
};

export default users;
