import axios from 'axios';
import { changePasswordUrl } from '../constants/backend-url';
import { wrongPassword,
  expiredSession,
  technicalError } from '../constants/errors';

const errorHandler = ({ response: { status, data: { error } } }) => {
  switch (status) {
  case 401:
    return expiredSession;

  case 400:
    if (error === 'wrong_password') {
      return wrongPassword;
    }
    return technicalError;
  default:
    return technicalError;
  }
};

const changeEmail = data => axios.post(changePasswordUrl, data)
  .then(() => ({ message: 'Your password changed!' }))
  .catch(res => ({ error: errorHandler(res) }));

export default changeEmail;
