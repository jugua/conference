export default class MyInfoController {
  constructor(Current, $scope, $state) {
    this.state = $state;
    this.currentUserService = Current;


    this.uploadPreview = false;
    this.defaultImage = 'assets/img/ava.jpg';
    this.ava;
    this.file = {};
    this.uploadForm = {};

    this.currentPhotoStatus = this.getCurrentPhotoStatus();
    this.animation = false;

    this.errorMessage = {
      title: 'Error',
      p: 'Please fill in all mandatory fields'
    };
    this.sucessMessage = {
      title: 'Saved',
      p: 'Changes saved successfully'
    };
    this.goAwayMessage = {
      title: 'Attention',
      p: 'Would you like to save changes?',
      showBtns: true
    };
    this.userInfoForm = {};

    this.event = $scope.$on('$stateChangeStart', (e, toState) => {
      if (this.userInfoForm.$pristine ||
         (this.userInfoForm.$submitted
         && this.userInfoForm.$valid
         && this.userInfoForm.$pristine)) {
        return;
      }
      e.preventDefault();
      this.nextState = toState;
      this.showMessage('goAwayMessage');
    });
  }

  submit() {
    if (this.userInfoForm.$invalid) {
      this.showMessage('errorMessage');
    } else {
      this.currentUserService.updateInfo(this.user);
      this.showMessage('sucessMessage');
      this.userInfoForm.$setPristine();
    }
  }

  showMessage(messageType) {
    this.message = this[messageType];
    this.isShowMessage = true;
  }

  hideMessage() {
    this.isShowMessage = false;
  }

  toggleSlide() {
    this.showLoad = !this.showLoad;
  }

  togglePreview() {
    if (this.uploadForm.$valid) {
      this.uploadPreview = !this.uploadPreview;
    }
  }

  toggleAnimation() {
    this.animation = !this.animation;
  }

  saveChangesBeforeOut() {
    this.submit();
    this.userInfoForm.$setSubmitted();
    if (this.userInfoForm.$valid) {
      this.event();
      this.state.go(this.nextState.name);
    }
  }

  cancelChanges() {
    this.event();
    this.state.reload();
    this.state.go(this.nextState.name);
  }

  successUpload() {
    this.ava = this.file;
    this.toggleSlide();
    this.togglePreview();
    this.toggleAnimation();
    this.currentUserService.getInfo();
    this.currentPhotoStatus = this.getCurrentPhotoStatus();
  }

  errorUpload(error) {
    this.togglePreview();
    this.toggleAnimation();
    this.file = {};
    this.uploadForm.$setValidity(error.data.error, false);
  }

  uploadAva() {
    this.toggleAnimation();
    this.currentUserService.uploadPhoto(this.file)
      .then(
        () => {
          this.successUpload();
        }
      )
      .catch(
        (error) => {
          this.errorUpload(error);
        });
  }

  getCurrentPhotoStatus() {
    this.currentUserService.getPhotoStatus().then((result) => {
      this.currentPhotoStatus = result;
    });
  }
}

