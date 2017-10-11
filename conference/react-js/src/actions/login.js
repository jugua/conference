import axios from 'axios';
import { loginUrl } from '../constants/backend-url';

const login = ({ password, email }) => {
  const headers = {
    authorization: `Basic ${btoa(`${email}:${password}`)}`,
  };

  return axios.post(loginUrl, null, { headers });
};

export default login;
