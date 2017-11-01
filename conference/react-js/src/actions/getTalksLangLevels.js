import axios from 'axios';
import { langLevels } from '../constants/backend-url';

const getTalksLangLevels = () => axios.get(langLevels);

export default getTalksLangLevels;
