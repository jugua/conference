import axios from 'axios';

import actionTypes from '../constants/actions-types';
import { logout as logoutUrl } from '../constants/backend-url';
import defaultUser from '../constants/default-user';

const logoutAction = {
  type: actionTypes.SET_USER,
  payload: defaultUser,
};

const logout = dispatch => axios.post(logoutUrl)
  .then(() => dispatch(logoutAction))
  .catch(() => console.error('Logout failed'));

export default logout;
