import actionTypes from '../constants/actions-types';

const initialState = {
  id: -1,
  roles: [],
  mail: '',
  fname: '',
  lname: '',
  bio: '',
  job: '',
  company: '',
  past: '',
  photo: '',
  linkedin: '',
  twitter: '',
  facebook: '',
  blog: '',
  info: '',
};

function user(state = initialState, action) {
  const { type, payload } = action;
  switch (type) {
  case actionTypes.SET_USER:
    return payload;
  default:
    return state;
  }
}

export default user;
