import axios from 'axios';
import { types } from '../constants/backend-url';

const getTypes = () => axios.get(types);

export default getTypes;
