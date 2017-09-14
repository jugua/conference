import userPhotoComponent from './user-photo.component';
import userPhotoService from './user-photo.service';

export default (app) => {
  app.component('userPhoto', userPhotoComponent)
     .service('userPhotoService', userPhotoService);
};
