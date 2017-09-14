import template from './new-conference-popup.html';
import controller from './new-conference-popup.controller';

const component = {
  template,
  controller,
  bindings: {
    onSubmit: '&',
    onClose: '&'
  },
};

export default component;

