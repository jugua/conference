export default class UserPhotoController {
  constructor(userPhotoService) {
    this.userPhotoService = userPhotoService;

    this.uploadPreview = false;
    this.deletePreview = false;
    this.deleteMessage = false;
    this.defaultImage = 'assets/img/default_ava.jpg';
    this.ava = null;
    this.file = null;
    this.uploadForm = {};

    this.currentPhotoStatus = this.getCurrentPhotoStatus();
    this.animation = false;
  }

  toggleSlide() {
    this.showLoad = !this.showLoad;
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
    this.user.photo = data.answer;
    this.currentPhotoStatus = this.getCurrentPhotoStatus();
  }

  errorUpload({ data }) {
    this.togglePreview();
    this.toggleAnimation();
    this.file = null;
    this.uploadForm.$setValidity(data.error, false);
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