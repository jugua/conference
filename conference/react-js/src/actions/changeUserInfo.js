import axios from 'axios';
import { userInfo } from '../constants/backend-url';
import { expiredSession, technicalError } from '../constants/errors';
import actionTypes from '../constants/actions-types';

const editUser = update => ({
  type: actionTypes.SET_USER,
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

const changeUserInfo = dispatch => updatedUser => (
  axios.post(userInfo, updatedUser)
    .then(() => success(dispatch, updatedUser))
    .catch(res => ({ error: errorHandler(res) }))
);

export default changeUserInfo;
