import actionTypes from '../constants/actions-types';

const initialState = { id: 5,
  roles: [''],
  mail: '',
  fname: 'Admin',
  lname: 'Admin',
  bio: '',
  job: '',
  company: '',
  past: null,
  photo: null,
  linkedin: null,
  twitter: null,
  facebook: null,
  blog: null,
  info: null };

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
