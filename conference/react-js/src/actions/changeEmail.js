import axios from 'axios';
import { changeEmailUrl } from '../constants/backend-url';

const changeEmail = mail => axios.post(changeEmailUrl, { mail });

export default changeEmail;
