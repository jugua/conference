import axios from 'axios';
import { changeEmailUrl } from '../constants/backend-url';
import { expiredSession,
  emailAlreadyExists,
  technicalError } from '../constants/errors';

const errorHandler = ({ response: { status } }) => {
  switch (status) {
  case 401:
    return expiredSession;
  case 409:
    return emailAlreadyExists;
  default:
    return technicalError;
  }
};

const changeEmail = mail => axios.post(changeEmailUrl, { mail })
  .then(() => ({ message: 'Please, check your email!' }))
  .catch(res => ({ error: errorHandler(res) }));

export default changeEmail;
