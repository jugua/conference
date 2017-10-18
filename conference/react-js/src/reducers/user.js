import actionTypes from '../constants/actions-types';

const defaultUser = {
  id: -1,
  roles: [],
  fname: '',
};

const user = (state = defaultUser, { type, payload }) => {
  const { SET_USER, EDIT_USER } = actionTypes;

  switch (type) {
  case SET_USER:
    return payload;
  case EDIT_USER:
    return {
      ...state,
      ...payload,
    };
  default: return state;
  }
};

export default user;
