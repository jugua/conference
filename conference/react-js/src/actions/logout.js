import axios from 'axios';

import actionTypes from '../constants/actions-types';
import { logout as logoutUrl } from '../constants/backend-url';
import defaultUser from '../constants/default-user';

const logoutAction = () => ({
  type: actionTypes.SET_USER,
  payload: defaultUser,
});

const logout = () => axios.post(
  logoutUrl,
  {
    headers: {
      'Cache-Control': 'no-cache, no-store',
      Pragma: 'no-cache',
    },
  },
).then(logoutAction);

export default logout;
