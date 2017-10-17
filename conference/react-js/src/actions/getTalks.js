import axios from 'axios';
import { talks } from '../constants/backend-url';

const getTalks = () => axios.get(talks);

export default getTalks;
