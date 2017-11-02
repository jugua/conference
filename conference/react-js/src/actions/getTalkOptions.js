import axios from 'axios';
import {
  langLevels,
  languages,
  topics,
  types,
} from '../constants/backend-url';

const getTalksLangLevels = () => axios.get(langLevels);
const getTalksLanguages = () => axios.get(languages);
const getTalksTopics = () => axios.get(topics);
const getTalksTypes = () => axios.get(types);

const getTalkOptions = () => Promise.all([
  getTalksTopics(),
  getTalksTypes(),
  getTalksLanguages(),
  getTalksLangLevels(),
]);

export default getTalkOptions;
