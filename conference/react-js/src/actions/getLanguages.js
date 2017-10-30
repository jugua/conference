import axios from 'axios';
import { languages } from '../constants/backend-url';

const getLanguages = () => axios.get(languages);

export default getLanguages;
