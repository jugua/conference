import axios from 'axios';
import { changePasswordUrl } from '../constants/backend-url';

const changeEmail = data => axios.post(changePasswordUrl, data);

export default changeEmail;
