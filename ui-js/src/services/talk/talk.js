import Talks from './talk.service';
import TalkFile from './talk-file/talk-file.service';

export default (app) => {
  app.service('Talks', Talks)
     .service('TalkFile', TalkFile);
};
