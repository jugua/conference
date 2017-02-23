import template from './conference-card.html';
import controller from './conference-card.controller';

const component = {
  bindings: {
    showCounters: '<',    // boolean
    showSubmit: '<',      // boolean
    data: '<',            // object
  },
  template,
  controller,
};

export default component;