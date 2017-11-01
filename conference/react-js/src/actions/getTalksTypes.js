import axios from 'axios';
import { types } from '../constants/backend-url';

const getTalksTypes = () => axios.get(types);

export default getTalksTypes;
