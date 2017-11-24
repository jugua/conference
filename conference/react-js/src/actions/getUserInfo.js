import axios from 'axios';

import actionTypes from '../constants/actions-types';
import { myInfo } from '../constants/backend-url';

const setUser = user => ({
  type: actionTypes.EDIT_USER,
  payload: user,
});

const setUserKeys = user => ({
  type: actionTypes.SET_USER_KEYS,
  payload: Object.keys(user),
});

const success = (dispatch, user) => {
  dispatch(setUser(user));
  dispatch(setUserKeys(user));
};

const getUserInfo = dispatch => (
  axios.get(myInfo)
    .then(res => success(dispatch, res.data))
    .catch(err => err)
);

export default getUserInfo;
