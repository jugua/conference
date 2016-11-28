import template from './user-photo.html';
import controller from './user-photo.controller';

const userPhotoComponent = {
  bindings: {
    user: '='
  },
  template,
  controller
};

export default userPhotoComponent;
