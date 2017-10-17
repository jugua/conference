import axios from 'axios';

import { talk } from '../constants/backend-url';

const getTalks = () => axios.get(talk);

export default getTalks;
