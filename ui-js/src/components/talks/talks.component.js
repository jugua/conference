import template from './talks.html';
import controller from './talks.controller';


const component = {
  bindings: {
    reviewTalkId: '<',
  },
  template,
  controller,
};

export default component;
