import axios from 'axios';
import { changeNameUrl } from '../constants/backend-url';

const changeEmail = user => axios.post(changeNameUrl, user);

export default changeEmail;
