export default class UserPhotoController {
  constructor(userPhotoService, Constants) {
    'ngInject';

    this.userPhotoService = userPhotoService;

    this.defaultImage = Constants.ava;
    this.ava = null;
    this.file = null;
    this.uploadForm = {};

    this.uploadPreview = false;
    this.deletePreview = false;
    this.deleteMessage = false;
    this.animation = false;

    this.currentPhotoStatus = this.getCurrentPhotoStatus();
  }

  toggleSlide() {
    this.showLoad = !this.showLoad;
  }

  downloadByDrag() {
    this.uploadForm.file.$setValidity('pattern', true);
    this.uploadForm.file.$setValidity('minSize', true);
    this.uploadForm.file.$setValidity('maxSize', true);
    this.togglePreview();
  }

  downloadBySelect() {
    this.uploadForm.dragfile.$setValidity('pattern', true);
    this.uploadForm.dragfile.$setValidity('minSize', true);
    this.uploadForm.dragfile.$setValidity('maxSize', true);
    this.togglePreview();
  }

  togglePreview() {
    this.uploadForm.$setValidity('save', true);
    if (this.uploadForm.$valid) {
      this.uploadPreview = !this.uploadPreview;
    }
  }

  toggleAnimation() {
    this.animation = !this.animation;
  }

  successUpload({ data }) {
    this.ava = this.file;
    this.toggleSlide();
    this.togglePreview();
    this.toggleAnimation();
    this.user.photo = data.result;
    this.currentPhotoStatus = this.getCurrentPhotoStatus();
  }

  errorUpload({ data }) {
    this.togglePreview();
    this.toggleAnimation();
    this.file = null;
    this.uploadForm.file.$setValidity(data.error, false);
  }

  uploadAva() {
    this.toggleAnimation();
    this.userPhotoService.uploadPhoto(this.file)
      .then(
        (result) => {
          this.successUpload(result);
        }
      )
      .catch(
        (error) => {
          this.errorUpload(error);
        });
  }

  getCurrentPhotoStatus() {
    if (this.user.photo) {
      return { button: 'Update Photo', title: 'Update Your Photo' };
    }
    return { button: 'Upload Photo', title: 'Upload new photo' };
  }

  // deleting photo
  toggleDeletePreview() {
    this.deletePreview = !this.deletePreview;
  }

  askToDeletePhoto() {
    if (this.ava || this.user.photo) {
      this.toggleDeletePreview();
    }
  }

  deletePhoto() {
    this.toggleAnimation();
    this.userPhotoService.deleteUserPhoto()
        .then(
            () => {
              this.successDelete();
            }
        )
        .catch(
            () => {
              this.errorDelete();
            });
  }

  successDelete() {
    this.ava = null;
    this.toggleDeletePreview();
    this.toggleAnimation();
    this.user.photo = '';
    this.currentPhotoStatus = this.getCurrentPhotoStatus();
  }

  errorDelete() {
    this.toggleAnimation();
    this.deleteMessage = !this.deleteMessage;
  }
}