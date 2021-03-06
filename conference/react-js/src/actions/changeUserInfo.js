import axios from 'axios';
import { userInfo } from '../constants/backend-url';
import { expiredSession, technicalError } from '../constants/errors';
import actionTypes from '../constants/actions-types';

const editUser = update => ({
  type: actionTypes.EDIT_USER,
  payload: update,
});

const errorHandler = ({ response: { status } }) => {
  if (status === 401) {
    return expiredSession;
  }
  return technicalError;
};

const success = (dispatch, updatedUser) => {
  dispatch(editUser(updatedUser));
  return { message: 'Your info updated' };
};

const userToDefaultShape = (user, keys) => {
  const res = {};
  keys.forEach((key) => {
    res[key] = user[key];
  });
  return res;
};

const changeUserInfo = (updatedUser, userKeys) => dispatch => (
  axios.post(userInfo, userToDefaultShape(updatedUser, userKeys))
    .then(() => success(dispatch, updatedUser))
    .catch(res => ({ error: errorHandler(res) }))
);

export default changeUserInfo;
