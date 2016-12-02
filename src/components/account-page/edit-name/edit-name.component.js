import template from './edit-name.html';
import controller from './edit-name.controller';

const editNameComponent = {
  bindings: {
    changename: '=',
    user: '='
  },
  template,
  controller,
};

export default editNameComponent;
