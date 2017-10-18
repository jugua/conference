import axios from 'axios';
import { userInfo } from '../constants/backend-url';

const getUserInfo = () => axios.get(userInfo);

export default getUserInfo;
