import axios from 'axios';
import { topics } from '../constants/backend-url';

const getTopics = () => axios.get(topics);

export default getTopics;
