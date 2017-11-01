import axios from 'axios';
import { topics } from '../constants/backend-url';

const getTalksTopics = () => axios.get(topics);

export default getTalksTopics;
