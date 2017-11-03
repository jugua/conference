import axios from 'axios';
import {
  langLevels,
  languages,
  topics,
  types,
} from '../constants/backend-url';

const getData = url => axios.get(url).then(({ data }) => data);

const getTalkOptions = () => Promise.all([
  getData(topics),
  getData(types),
  getData(languages),
  getData(langLevels),
]);

export default getTalkOptions;
