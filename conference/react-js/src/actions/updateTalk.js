import axios from 'axios';
import { talk as talkUrl } from '../constants/backend-url';

const updateTalk = talk => axios.patch(`${talkUrl}/${talk.id}`, talk);

export default updateTalk;
