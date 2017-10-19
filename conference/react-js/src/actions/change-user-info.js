import axios from 'axios';
import { userInfo } from '../constants/backend-url';
import { expiredSession, technicalError } from '../constants/errors';

const errorHandler = ({ response: { status } }) => {
  if (status === 401) {
    return expiredSession;
  }
  return technicalError;
};

const changeUserInfo = updatedUser => (
  axios.post(userInfo, updatedUser)
    .then(() => ({ message: 'Your info updated' }))
    .catch(res => ({ error: errorHandler(res) }))
);

export default changeUserInfo;
