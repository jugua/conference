import axios from 'axios';
import baseUrl from '../constants/backend-url';

const utf8ToB64 = str => window.btoa(unescape(encodeURIComponent(str)));

const login = ({ password, email }) => axios.post(
  `${baseUrl}/api/login`,
  null,
  {
    headers: {
      authorization: `Basic ${utf8ToB64(`${email}:${password}`)}`,
    },
  },
);

export default login;
