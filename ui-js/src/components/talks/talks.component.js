import template from './talks.html';
import controller from './talks.controller';


const talksComponent = {
  bindings: {
    reviewTalkId: '<',
  },
  template,
  controller,
};

export default talksComponent;

