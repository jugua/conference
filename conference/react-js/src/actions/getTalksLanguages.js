import axios from 'axios';
import { languages } from '../constants/backend-url';

const getTalksLanguages = () => axios.get(languages);

export default getTalksLanguages;
