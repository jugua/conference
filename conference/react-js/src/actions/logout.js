import axios from 'axios';

import actionTypes from '../constants/actions-types';
import { logout as logoutUrl } from '../constants/backend-url';

const logoutAction = {
  type: actionTypes.USER_LOGOUT,
};

const logout = dispatch => axios.post(logoutUrl)
  .then(() => dispatch(logoutAction))
  .catch((error) => { throw error; });

export default logout;
