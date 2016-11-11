import template from './newtalk.html';
import controller from './newtalk.controller';

const newtalkComponent = {
  bindings : {
      talks: '='
  },
  template,
  controller
};

export default newtalkComponent;
