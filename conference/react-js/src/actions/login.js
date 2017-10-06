import axios from 'axios';
import { loginUrl } from '../constants/backend-url';

const utf8ToB64 = str => window.btoa(unescape(encodeURIComponent(str)));

const login = ({ password, email }) => {
  const headers = {
    authorization: `Basic ${utf8ToB64(`${email}:${password}`)}`,
  };

  return axios.post(loginUrl, null, { headers });
};

export default login;
