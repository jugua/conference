import actionTypes from '../constants/actions-types';

function user(state = {}, action) {
  const { type, payload } = action;
  switch (type) {
  case actionTypes.SET_USER:
    return payload;
  default:
    return state;
  }
}

export default user;
