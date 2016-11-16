/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var _components = __webpack_require__(1);
	
	var _components2 = _interopRequireDefault(_components);
	
	var _services = __webpack_require__(37);
	
	var _services2 = _interopRequireDefault(_services);
	
	var _app = __webpack_require__(48);
	
	var _app2 = _interopRequireDefault(_app);
	
	__webpack_require__(49);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	/* global angular */
	//  Angular & Router ES6 Imports
	//  import angular from 'angular';
	//  import angularUIRouter from 'angular-ui-router';
	var app = angular.module('app', ['ui.router', 'ngResource', 'ngFileUpload']).config(function ($httpProvider) {
	  $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	});
	
	// Components Entrypoint
	
	
	// Single Style Entry Point
	
	//  import commonComponents from './common/components.js';
	(0, _components2.default)(app);
	
	//  Common Components Entrypoint
	//  commonComponents(app);
	
	//  App Services Entrypoint
	(0, _services2.default)(app);
	
	// Router Configuration
	// Components must be declared first since
	// Routes reference controllers that will be bound to route templates.
	(0, _app2.default)(app);

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _header = __webpack_require__(2);
	
	var _header2 = _interopRequireDefault(_header);
	
	var _home = __webpack_require__(5);
	
	var _home2 = _interopRequireDefault(_home);
	
	var _signIn = __webpack_require__(9);
	
	var _signIn2 = _interopRequireDefault(_signIn);
	
	var _accountPage = __webpack_require__(13);
	
	var _accountPage2 = _interopRequireDefault(_accountPage);
	
	var _myInfo = __webpack_require__(17);
	
	var _myInfo2 = _interopRequireDefault(_myInfo);
	
	var _tabs = __webpack_require__(21);
	
	var _tabs2 = _interopRequireDefault(_tabs);
	
	var _signUp = __webpack_require__(25);
	
	var _signUp2 = _interopRequireDefault(_signUp);
	
	var _myTalks = __webpack_require__(29);
	
	var _myTalks2 = _interopRequireDefault(_myTalks);
	
	var _forgotPassword = __webpack_require__(33);
	
	var _forgotPassword2 = _interopRequireDefault(_forgotPassword);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  (function includeAllModulesGlobalFn(modulesArray, application) {
	            modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
	                moduleFn(application);
	            });
	        })([_header2.default, _home2.default, _signIn2.default, _accountPage2.default, _myInfo2.default, _tabs2.default, _myTalks2.default, _signUp2.default, _forgotPassword2.default], app);
	}; /* global include_all_modules */

/***/ },
/* 2 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _header = __webpack_require__(3);
	
	var _header2 = _interopRequireDefault(_header);
	
	var _header3 = __webpack_require__(4);
	
	var _header4 = _interopRequireDefault(_header3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider, $urlRouterProvider) {
	    $urlRouterProvider.otherwise('/');
	    $stateProvider.state('header', {
	      url: '',
	      resolve: {
	        user: function getCurrent(Current) {
	          Current.getInfo();
	          return Current.current;
	        }
	      },
	      abstract: true,
	      template: _header4.default,
	      controller: _header2.default,
	      controllerAs: 'ctrl'
	    });
	  });
	};

/***/ },
/* 3 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	/* global angular */
	
	var HeaderController = function () {
	  function HeaderController(user, Menus, Current, $state, $scope, $document) {
	    var _this = this;
	
	    _classCallCheck(this, HeaderController);
	
	    this.role = user ? user.roles : '';
	    this.name = user ? user.fname + '\'s' : 'Your';
	    this.menu = Menus.getMenu(this.role);
	    this.menuHidden = false;
	    $scope.$on('signInEvent', function () {
	      $state.go('header.home', {}, { reload: true });
	    });
	    $scope.$on('closeDropdown', function () {
	      _this.menuHidden = false;
	    });
	
	    var off = $scope.$on('$viewContentLoaded', function () {
	      $document.on('click', function (e) {
	        var target = angular.element(e.target);
	
	        var parents = target;
	        var dropDownArea = false;
	
	        if (!_this.menuHidden) {
	          return;
	        }
	        // to not use jQuery for one method parents() not supported in JQLight
	        // so this loop finds all parents
	        while (parents.parent().length) {
	          if (parents.hasClass('js-dropdown')) {
	            dropDownArea = true;
	            break;
	          }
	          parents = parents.parent();
	        }
	
	        if (!dropDownArea) {
	          _this.menuHidden = false;
	          $scope.$apply();
	        }
	      });
	
	      off();
	    });
	
	    this.Current = Current;
	    this.$state = $state;
	  }
	
	  _createClass(HeaderController, [{
	    key: 'logout',
	    value: function logout() {
	      var _this2 = this;
	
	      this.Current.logout().then(function () {
	        // success callback
	        _this2.$state.go('header.home', {}, { reload: true });
	      });
	    }
	  }]);
	
	  return HeaderController;
	}();
	
	exports.default = HeaderController;

/***/ },
/* 4 */
/***/ function(module, exports) {

	module.exports = "<header class=\"header\">\n        <div class=\"header__title\">conference management</div>\n        <div class=\"menu-container\">\n            <button class=\"menu-container__button js-dropdown\" ng-click=\"ctrl.menuHidden=!ctrl.menuHidden\">{{ctrl.name}} Account</button>\n            <div class=\"menu-container__content\" ng-show=\"ctrl.menuHidden\">\n              <sign-in ng-if=\"!ctrl.menu\"></sign-in>\n              <div  ng-if=\"ctrl.menu\" class=\"dropdown\">\n                <div class=\"menu-arrow\"></div>\n                <ul class=\"menu-list\">\n                  <li class=\"menu-list__item\"\n                      ng-repeat=\"items in ctrl.menu\">\n                    <a href=\"#\" class=\"menu-list__title\"\n                       ui-sref=\"header.{{items.link}}\"\n                       ui-sref-active=\"is-active\">\n                      {{items.name}}</a>\n                  </li>\n                  <li class=\"menu-list__item menu-list__item_sign-out\">\n                      <a href=\"#\" class=\"menu-list__title\"\n                         ng-click=\"ctrl.logout()\">Sign Out</a>\n                  </li>\n                </ul>\n              </div>\n            </div>\n        </div>\n</header>\n\n<div ui-view></div>\n";

/***/ },
/* 5 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _home = __webpack_require__(6);
	
	var _home2 = _interopRequireDefault(_home);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.home', {
	      url: '/',
	      template: '<home></home>'
	    });
	  }).component('home', _home2.default);
	};

/***/ },
/* 6 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _home = __webpack_require__(7);
	
	var _home2 = _interopRequireDefault(_home);
	
	var _home3 = __webpack_require__(8);
	
	var _home4 = _interopRequireDefault(_home3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var homeComponent = {
	  template: _home2.default,
	  controller: _home4.default,
	  controllerAs: 'homeCtrl'
	};
	
	exports.default = homeComponent;

/***/ },
/* 7 */
/***/ function(module, exports) {

	module.exports = "<div class=\"Container home\">\n  <div class=\"home-layout\">\n    <ui-view></ui-view>\n  </div>\n</div>\n";

/***/ },
/* 8 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var HomeController = function HomeController() {
	  _classCallCheck(this, HomeController);
	
	  this.name = 'home';
	};
	
	exports.default = HomeController;

/***/ },
/* 9 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _signIn = __webpack_require__(10);
	
	var _signIn2 = _interopRequireDefault(_signIn);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.component('signIn', _signIn2.default);
	};

/***/ },
/* 10 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _signIn = __webpack_require__(11);
	
	var _signIn2 = _interopRequireDefault(_signIn);
	
	var _signIn3 = __webpack_require__(12);
	
	var _signIn4 = _interopRequireDefault(_signIn3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var signInComponent = {
	  bindings: {},
	  template: _signIn2.default,
	  controller: _signIn4.default,
	  controllerAs: 'signInCtrl'
	};
	
	exports.default = signInComponent;

/***/ },
/* 11 */
/***/ function(module, exports) {

	module.exports = "<div class=\"sign-in-wrapper  js-dropdown\">\n  <form class=\"sign-in\" name=\"signInCtrl.userForm\" novalidate ng-submit=\"signInCtrl.login()\">\n    <h2 class=\"form-title sign-in__title\">sign in</h2>\n    <label for=\"sign-in-email\" class=\"sign-in__label form-label\">email:</label>\n    <input type=\"email\"\n           name=\"mail\"\n           id=\"sign-in-email\"\n           class=\"field sing-in__field\"\n           ng-model=\"signInCtrl.user.mail\"\n           ng-pattern=\"/^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$/i\"\n           required\n           tabindex=\"1\">\n    <span class=\"field-error\" ng-if=\"signInCtrl.userForm.mail.$error.login_auth_err\">We can not find an account with that email address</span>\n    <div class=\"sign-in__password-cont\">\n      <label for=\"sign-in-password\" class=\"form-label\">password:</label>\n      <a href=\"#\" ui-sref=\"header.home.forgotPassword\" class=\"sign-in__forgot\" tabindex=\"3\">forgot password?</a>\n    </div>\n    <input type=\"password\"\n           name=\"password\"\n           class=\"field\"\n           id=\"sign-in-password\"\n           ng-model=\"signInCtrl.user.password\"\n           tabindex=\"2\">\n    <span class=\"field-error\" ng-if=\"signInCtrl.userForm.password.$error.password_auth_err\">Your password is incorrect</span>\n\n    <input type=\"submit\"\n           value=\"Sign In\"\n           class=\"btn sign-in__submit\">\n  </form>\n  \n  <div class=\"sign-in__separator\"></div>\n\n  <a ui-sref=\"header.sign-up\" class=\"btn sign-in__create\">create new account</a>\n</div>";

/***/ },
/* 12 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var SignInController = function () {
	  function SignInController(SignIn, $scope) {
	    _classCallCheck(this, SignInController);
	
	    this.user = {};
	    this.userForm = {};
	    this.service = SignIn;
	    this.scope = $scope;
	  }
	
	  _createClass(SignInController, [{
	    key: 'login',
	    value: function login() {
	      var _this = this;
	
	      this.userForm.password.$setValidity('password_auth_err', true);
	      this.userForm.mail.$setValidity('login_auth_err', true);
	
	      if (this.userForm.mail.$error.required || this.userForm.mail.$error.pattern) {
	        this.userForm.mail.$setValidity('login_auth_err', false);
	      }
	
	      if (this.userForm.$valid) {
	        this.service.login(this.user).then(function () {
	          _this.successSignIn();
	        }, function (error) {
	          _this.showError(error.data.error);
	        });
	      }
	    }
	  }, {
	    key: 'showError',
	    value: function showError(error) {
	      if (error === 'password_auth_err') {
	        this.userForm.password.$setValidity(error, false);
	      }
	
	      if (error === 'login_auth_err') {
	        this.userForm.mail.$setValidity(error, false);
	      }
	    }
	  }, {
	    key: 'successSignIn',
	    value: function successSignIn() {
	      this.user = {};
	      this.userForm.$setPristine();
	      this.service.callTheEvent();
	    }
	  }, {
	    key: 'emitCloseDropdown',
	    value: function emitCloseDropdown() {
	      this.scope.$emit('closeDropdown');
	    }
	  }]);
	
	  return SignInController;
	}();
	// no_info_auth_err
	
	
	exports.default = SignInController;

/***/ },
/* 13 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _accountPage = __webpack_require__(14);
	
	var _accountPage2 = _interopRequireDefault(_accountPage);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.account', {
	      url: '/account',
	      template: '<account-page></account-page>'
	    });
	  }).component('accountPage', _accountPage2.default);
	};

/***/ },
/* 14 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _accountPage = __webpack_require__(15);
	
	var _accountPage2 = _interopRequireDefault(_accountPage);
	
	var _accountPage3 = __webpack_require__(16);
	
	var _accountPage4 = _interopRequireDefault(_accountPage3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var accountPageComponent = {
	  bindings: {},
	  template: _accountPage2.default,
	  controller: _accountPage4.default,
	  controllerAs: 'accountPageCtrl'
	};
	
	exports.default = accountPageComponent;

/***/ },
/* 15 */
/***/ function(module, exports) {

	module.exports = "<div>Account page</div>";

/***/ },
/* 16 */
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var AccountPageController = function AccountPageController() {
	  _classCallCheck(this, AccountPageController);
	};
	
	exports.default = AccountPageController;

/***/ },
/* 17 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _myInfo = __webpack_require__(18);
	
	var _myInfo2 = _interopRequireDefault(_myInfo);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.tabs.myInfo', {
	      url: '/my-info',
	      template: '<my-info user="ctrl.currentUser"></my-info>',
	      resolve: {
	        currentUser: function currentUser(Current) {
	          return Current.current;
	        }
	      },
	      controller: function controller(currentUser, $scope) {
	        if (!currentUser || currentUser.roles.indexOf('s') === -1) {
	          $scope.$emit('signInEvent');
	        }
	        this.currentUser = currentUser;
	      },
	      controllerAs: 'ctrl'
	    });
	  }).component('myInfo', _myInfo2.default);
	};

/***/ },
/* 18 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _myInfo = __webpack_require__(19);
	
	var _myInfo2 = _interopRequireDefault(_myInfo);
	
	var _myInfo3 = __webpack_require__(20);
	
	var _myInfo4 = _interopRequireDefault(_myInfo3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var myInfoComponent = {
	  bindings: {
	    user: '='
	  },
	  template: _myInfo2.default,
	  controller: _myInfo4.default
	};
	
	exports.default = myInfoComponent;

/***/ },
/* 19 */
/***/ function(module, exports) {

	module.exports = "<form class=\"my-info\" name=\"$ctrl.userInfoForm\" novalidate ng-submit=\"$ctrl.submit()\">\n  <div class=\"my-info__ava\" ngf-background=\"$ctrl.ava || $ctrl.user.photo || $ctrl.defaultImage\">\n    <input type=\"button\" class=\"my-info__remove\">\n    <div class=\"slide\">\n      <input type=\"button\" class=\"slide__button\" value=\"{{ $ctrl.currentPhotoStatus.button }}\" ng-click=\"$ctrl.toggleSlide()\">\n    </div>\n  </div>\n  <label for=\"my-info-bio\" class=\"form-label form-label_required my-info__label my-info__label_bio\">Short Bio</label>\n  <textarea name=\"bio\" id=\"my-info-bio\" class=\"my-info__bio\" maxlength=\"2000\"\n              ng-model=\"$ctrl.user.bio\"\n              ng-maxlength=\"2000\"\n              ng-required=\"true\">{{$ctrl.user.bio}}</textarea>\n  <label for=\"my-info-job\" class=\"form-label form-label_required my-info__label\">Job</label>\n  <input type=\"text\" name=\"job\" id=\"my-info-job\"\n         class=\"field my-info__field my-info__field_job\"\n         maxlength=\"256\"\n         ng-model=\"$ctrl.user.job\"\n         ng-required=\"true\">\n  <label for=\"my-info-linkedin-past-conferences\" class=\"form-label form-label_required my-info__label\">Past Conferences</label>\n  <input type=\"text\" name=\"past\" id=\"my-info-linkedin-past-conferences\"\n         class=\"field my-info__field my-info__field_past\"\n         maxlength=\"1000\"\n         ng-model=\"$ctrl.user.past\"\n         ng-required=\"true\">\n  <label for=\"my-info-linkedin\" class=\"form-label my-info__label\">LinkedIn</label>\n  <input type=\"text\" name=\"linkedin\" id=\"my-info-linkedin\"\n         class=\"field my-info__field\"\n         ng-model=\"$ctrl.user.linkedin\">\n  <label for=\"my-info-twitter\" class=\"form-label my-info__label\">twitter</label>\n  <input type=\"text\" name=\"twitter\" id=\"my-info-twitter\"\n         class=\"field my-info__field\"\n         ng-model=\"$ctrl.user.twitter\">\n  <label for=\"my-info-facebook\" class=\"form-label my-info__label\">facebook</label>\n  <input type=\"text\" name=\"facebook\" id=\"my-info-facebook\"\n          class=\"field my-info__field\"\n         ng-model=\"$ctrl.user.facebook\">\n  <label for=\"my-info-blog\" class=\"form-label my-info__label\">blog</label>\n  <input type=\"text\" name=\"blog\" id=\"my-info-blog\"\n         class=\"field my-info__field\"\n         ng-model=\"$ctrl.user.blog\">\n  <label for=\"my-info-additional-info\" class=\"form-label my-info__label\">Additional Info</label>\n  <textarea name=\"info\" id=\"my-info-additional-info\" rows=\"5\" class=\"my-info__info\"\n            ng-model=\"$ctrl.user.info\"\n            maxlength=\"1000\">{{$ctrl.user.info}}</textarea>\n\n  <input type=\"submit\" value=\"save\" class=\"btn my-info__button \">\n\n</form>\n\n<div class=\"pop-up-wrapper\" ng-if=\"$ctrl.showLoad\">\n  <div class=\"pop-up\" ng-show=\"!$ctrl.uploadPreview\">\n    <h3 class=\"pop-up__title\">{{$ctrl.currentPhotoStatus.title}}</h3>\n    <button class=\"pop-up__close\" ng-click=\"$ctrl.toggleSlide()\"></button>\n    <form novalidate\n          name=\"$ctrl.uploadForm\"\n          ngf-drop=\"$ctrl.togglePreview()\"\n          ng-model=\"$ctrl.file\"\n          ngf-max-size=\"2MB\"\n          ngf-min-size=\"1\"\n          ngf-pattern=\"image/jpeg,image/png,image/gif\"\n          required\n          class=\"upload-form\">\n        <p class=\"pop-up__notification pop-up__notification_light\">\n          It is much easier to identify you if you have a photo.\n        </p>\n        <p class=\"pop-up__notification pop-up__notification_light\">\n          You can upload an image in JPG, PNG or GIF format. The maximum allowed size for uploads is 2 Mb\n        </p>\n\n        <label class=\"file-upload\">\n          <span class=\"btn file-upload__button\">Choose</span>\n          <input type=\"file\"\n                 class=\"file-upload__uploading\"\n                 ng-model=\"$ctrl.file\"\n                 required\n                 ngf-select=\"$ctrl.togglePreview()\"\n                 ngf-max-size=\"2MB\"\n                 ngf-min-size=\"1\"\n                 ngf-pattern=\"image/jpeg,image/png,image/gif\"\n                 accept=\"image/jpeg,image/png,image/gif\"\n          >\n        </label>\n        <p ng-if=\"$ctrl.uploadForm.$error.maxSize\" class=\"field-error field-error_center\">\n          You exceeded maximum allowed size for uploaded photo (2Mb)\n        </p>\n      <p ng-if=\"$ctrl.uploadForm.$error.minSize\" class=\"field-error field-error_center\">\n        You try to upload an empty file\n      </p>\n        <p ng-if=\"$ctrl.uploadForm.$error.pattern\" class=\"field-error field-error_center\">\n          We could not understand the contents of your file. Make sure it is a jpg, gif or png formatted image.\n        </p>\n        <p ng-if=\"$ctrl.uploadForm.$error.save\" class=\"field-error field-error_center\">\n          Sorry we can`t save Your photo try do change it some later.\n        </p>\n    </form>\n  </div>\n\n  <div class=\"pop-up\" ng-show=\"$ctrl.uploadPreview\">\n    <h3 class=\"pop-up__title\">Photo preview</h3>\n\n    <p class=\"pop-up__notification\">\n      It is how a selected photo will look on the My Info page.\n    </p>\n\n    <div class=\"preview-wrapper\">\n      <div class=\"preview\" ngf-background=\"$ctrl.file\"></div>\n      <div class=\"load-animation\" ng-show=\"$ctrl.animation\">\n        <div class=\"squaresWaveG squaresWaveG_1\"></div>\n        <div class=\"squaresWaveG squaresWaveG_2\"></div>\n        <div class=\"squaresWaveG squaresWaveG_3\"></div>\n        <div class=\"squaresWaveG squaresWaveG_4\"></div>\n        <div class=\"squaresWaveG squaresWaveG_5\"></div>\n      </div>\n    </div>\n\n    <p class=\"pop-up__notification pop-up__notification_light\">\n      By clicking on the Save button your changes will be saved and you will see your photo on the My Info page.\n    </p>\n    <p class=\"pop-up__notification pop-up__notification_light\">\n      By clicking on the Cancel button you will be redirected to the Upload new photo window.\n    </p>\n\n    <div class=\"pop-up-button-wrapper\">\n      <input type=\"button\" value=\"Save\" class=\"btn\"\n             ng-click=\"$ctrl.uploadAva()\">\n      <input type=\"button\" value=\"Cancel\" class=\"btn btn_cancel\"\n             ng-click=\"$ctrl.togglePreview()\">\n    </div>\n    <button class=\"pop-up__close\" ng-click=\"$ctrl.togglePreview()\"></button>\n  </div>\n</div>\n\n<div class=\"pop-up-wrapper\" ng-show=\"$ctrl.isShowMessage\">\n  <div class=\"pop-up\">\n    <h3 class=\"pop-up__title\">{{$ctrl.message.title}}</h3>\n    <p class=\"pop-up__notification\">\n      {{$ctrl.message.p}}\n    </p>\n\n    <div class=\"pop-up-button-wrapper\">\n      <input type=\"button\" value=\"yes\" class=\"btn\"\n             ng-if=\"$ctrl.message.showBtns\"\n             ng-click=\"$ctrl.saveChangesBeforeOut()\">\n      <input type=\"button\" value=\"No\" class=\"btn btn_cancel\"\n             ng-if=\"$ctrl.message.showBtns\"\n             ng-click=\"$ctrl.cancelChanges()\">\n    </div>\n    <button class=\"pop-up__close\" ng-click=\"$ctrl.hideMessage()\"></button>\n  </div>\n</div>";

/***/ },
/* 20 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var MyInfoController = function () {
	  function MyInfoController(Current, $scope, $state) {
	    var _this = this;
	
	    _classCallCheck(this, MyInfoController);
	
	    this.state = $state;
	    this.currentUserService = Current;
	
	    this.uploadPreview = false;
	    this.defaultImage = 'assets/img/default_ava.jpg';
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
	
	    this.event = $scope.$on('$stateChangeStart', function (e, toState) {
	      if (_this.userInfoForm.$pristine || _this.userInfoForm.$submitted && _this.userInfoForm.$valid && _this.userInfoForm.$pristine) {
	        return;
	      }
	      e.preventDefault();
	      _this.nextState = toState;
	      _this.showMessage('goAwayMessage');
	    });
	  }
	
	  _createClass(MyInfoController, [{
	    key: 'submit',
	    value: function submit() {
	      if (this.userInfoForm.$invalid) {
	        this.showMessage('errorMessage');
	      } else {
	        this.currentUserService.updateInfo(this.user);
	        this.showMessage('sucessMessage');
	        this.userInfoForm.$setPristine();
	      }
	    }
	  }, {
	    key: 'showMessage',
	    value: function showMessage(messageType) {
	      this.message = this[messageType];
	      this.isShowMessage = true;
	    }
	  }, {
	    key: 'hideMessage',
	    value: function hideMessage() {
	      this.isShowMessage = false;
	    }
	  }, {
	    key: 'toggleSlide',
	    value: function toggleSlide() {
	      this.showLoad = !this.showLoad;
	    }
	  }, {
	    key: 'togglePreview',
	    value: function togglePreview() {
	      this.uploadForm.$setValidity('save', true);
	      if (this.uploadForm.$valid) {
	        this.uploadPreview = !this.uploadPreview;
	      }
	    }
	  }, {
	    key: 'toggleAnimation',
	    value: function toggleAnimation() {
	      this.animation = !this.animation;
	    }
	  }, {
	    key: 'saveChangesBeforeOut',
	    value: function saveChangesBeforeOut() {
	      this.submit();
	      this.userInfoForm.$setSubmitted();
	      if (this.userInfoForm.$valid) {
	        this.event();
	        this.state.go(this.nextState.name);
	      }
	    }
	  }, {
	    key: 'cancelChanges',
	    value: function cancelChanges() {
	      this.event();
	      this.state.reload();
	      this.state.go(this.nextState.name);
	    }
	  }, {
	    key: 'successUpload',
	    value: function successUpload(res) {
	      this.ava = this.file;
	      this.toggleSlide();
	      this.togglePreview();
	      this.toggleAnimation();
	      this.user.photo = res.data.answer;
	      this.currentPhotoStatus = this.getCurrentPhotoStatus();
	    }
	  }, {
	    key: 'errorUpload',
	    value: function errorUpload(error) {
	      this.togglePreview();
	      this.toggleAnimation();
	      this.file = {};
	      this.uploadForm.$setValidity(error.data.error, false);
	    }
	  }, {
	    key: 'uploadAva',
	    value: function uploadAva() {
	      var _this2 = this;
	
	      this.toggleAnimation();
	      this.currentUserService.uploadPhoto(this.file).then(function (result) {
	        _this2.successUpload(result);
	      }).catch(function (error) {
	        _this2.errorUpload(error);
	      });
	    }
	  }, {
	    key: 'getCurrentPhotoStatus',
	    value: function getCurrentPhotoStatus() {
	      if (this.user.photo) {
	        return { button: 'Update Photo', title: 'Update Your Photo' };
	      }
	      return { button: 'Upload Photo', title: 'Upload new photo' };
	    }
	  }]);
	
	  return MyInfoController;
	}();
	
	exports.default = MyInfoController;

/***/ },
/* 21 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _tabs = __webpack_require__(22);
	
	var _tabs2 = _interopRequireDefault(_tabs);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.tabs', {
	      template: '<tabs></tabs>'
	    });
	  }).component('tabs', _tabs2.default);
	};

/***/ },
/* 22 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _tabs = __webpack_require__(23);
	
	var _tabs2 = _interopRequireDefault(_tabs);
	
	var _tabs3 = __webpack_require__(24);
	
	var _tabs4 = _interopRequireDefault(_tabs3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var tabsComponent = {
	  bindings: {},
	  template: _tabs2.default,
	  controller: _tabs4.default,
	  controllerAs: 'tabsCtrl'
	};
	
	exports.default = tabsComponent;

/***/ },
/* 23 */
/***/ function(module, exports) {

	module.exports = "<div class=\"tabs-layout\">\n  <div class=\"tabs-wrapper\">\n    <ul class=\"tabs-list\">\n      <li class=\"tabs-list__item\">\n        <a ui-sref=\"header.tabs.myInfo\" ui-sref-active=\"tabs-list__anchor_active\" class=\"tabs-list__anchor\">\n          my info\n        </a>\n      </li>\n      <li class=\"tabs-list__item\">\n        <a ui-sref=\"header.tabs.myTalks\" ui-sref-active=\"tabs-list__anchor_active\" class=\"tabs-list__anchor\">\n          my talks\n        </a>\n      </li>\n    </ul>\n    <div class=\"tabs-container\">\n      <ui-view></ui-view>\n    </div>\n  </div>\n</div>";

/***/ },
/* 24 */
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var TabsController = function TabsController() {
	  _classCallCheck(this, TabsController);
	};
	
	exports.default = TabsController;

/***/ },
/* 25 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _signUp = __webpack_require__(26);
	
	var _signUp2 = _interopRequireDefault(_signUp);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.sign-up', {
	      url: '/sign-up',
	      template: '<sign-up></sign-up>'
	    });
	  }).component('signUp', _signUp2.default);
	};

/***/ },
/* 26 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _signUp = __webpack_require__(27);
	
	var _signUp2 = _interopRequireDefault(_signUp);
	
	var _signUp3 = __webpack_require__(28);
	
	var _signUp4 = _interopRequireDefault(_signUp3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var signUpComponent = {
	  bindings: {},
	  template: _signUp2.default,
	  controller: _signUp4.default,
	  controllerAs: 'signUpCtrl'
	};
	
	exports.default = signUpComponent;

/***/ },
/* 27 */
/***/ function(module, exports) {

	module.exports = "<div class=\"sign-up-wrapper\">\n  <form class=\"sign-up\" name=\"signUpCtrl.userForm\" novalidate ng-submit=\"signUpCtrl.signUp()\">\n    <h2 class=\"form-title sign-up__title\">create new account</h2>\n\n    <label for=\"name\" class=\"form-label form-label_required\">first name:</label>\n    <input type=\"text\" id=\"name\" name=\"fname\"\n           class=\"field sign-up__field\"\n           required\n           maxlength=\"56\"\n           ng-model=\"signUpCtrl.user.fname\">\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.fname.$error.required\">\n                All fields are mandatory. Please make sure all required fields are filled out</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.fname.$error.maxlength\">\n                Your First Name should not be longer than 56 characters. Please try another</span>\n\n    <label for=\"surname\" class=\"form-label form-label_required\">last name:</label>\n    <input type=\"text\" id=\"surname\" name=\"lname\"\n           class=\"field sign-up__field\"\n           required\n           maxlength=\"56\"\n           ng-model=\"signUpCtrl.user.lname\">\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.lname.$error.required\">\n                All fields are mandatory. Please make sure all required fields are filled out</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.fname.$error.maxlength\">\n                Your Last Name should not be longer than 56 characters. Please try another</span>\n\n    <label for=\"mail\" class=\"form-label form-label_required\">Email:</label>\n    <input type=\"email\" id=\"mail\" name=\"mail\"\n           class=\"field sign-up__field\"\n           required\n           ng-model=\"signUpCtrl.user.mail\"\n           ng-pattern=\"/^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$/i\"\n           ng-change=\"signUpCtrl.resetEmailAlreadyExists()\">\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.mail.$error.required\">\n                All fields are mandatory. Please make sure all required fields are filled out</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.mail.$error.pattern\">\n                Please enter a valid email address</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.mail.$error.email_already_exists\">\n                There is an existing account associated with {{signUpCtrl.user.mail}}</span>\n\n    <label for=\"password\" class=\"form-label form-label_required\">password:</label>\n    <input type=\"password\" id=\"password\" name=\"password\"\n           class=\"field sign-up__field\"\n           required\n           minlength=\"6\"\n           maxlength=\"30\"\n           ng-pattern=\"/\\S+\\s*/\"\n           ng-model=\"signUpCtrl.user.password\"\n           ng-change=\"signUpCtrl.setPasswordsMatch(true)\">\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.password.$error.required\">\n                All fields are mandatory. Please make sure all required fields are filled out</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.password.$error.pattern\">\n                Please use at least one non-space character in your password</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.password.$error.minlength\">\n                Your password must be at least 6 characters long. Please try another</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.password.$error.maxlength\">\n                Your password should not be longer than 30 characters. Please try another</span>\n\n    <label for=\"confirm\" class=\"form-label form-label_required\">confirm password:</label>\n    <input type=\"password\" id=\"confirm\" name=\"confirm\"\n           class=\"field sign-up__field\"\n           required\n           minlength=\"6\"\n           maxlength=\"30\"\n           ng-model=\"signUpCtrl.user.confirm\"\n           ng-change=\"signUpCtrl.setPasswordsMatch(true)\">\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.confirm.$error.required\">\n                All fields are mandatory. Please make sure all required fields are filled out</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.confirm.$error.minlength\">\n                Your password must be at least 6 characters long. Please try another</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.$submitted && signUpCtrl.userForm.confirm.$error.maxlength\">\n                Your password should not be longer than 30 characters. Please try another</span>\n    <span class=\"field-error\"\n          ng-show=\"signUpCtrl.userForm.confirm.$error.passwords_match\">\n                Passwords do not match</span>\n\n    <input type=\"submit\" class=\"sign-up__button btn\" value=\"submit\">\n  </form>\n</div>\n\n<div class=\"pop-up-wrapper\" ng-show=\"signUpCtrl.showPopup\">\n  <div class=\"pop-up\">\n    <h3 class=\"pop-up__title\">You've successfully registered.</h3>\n    <p class=\"pop-up__notification\">\n      <span class=\"pop-up__notification-user\">{{signUpCtrl.user.fname}}</span>, go to\n      <span class=\"pop-up__notification-user\">{{signUpCtrl.user.mail}}</span> to complete the sign-up process</p>\n    <button class=\"btn pop-up__button\" ui-sref=\"header.home\">OK</button>\n  </div>\n</div>\n\n";

/***/ },
/* 28 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var SignUpController = function () {
	  function SignUpController(SignUp) {
	    _classCallCheck(this, SignUpController);
	
	    this.user = {};
	    this.userForm = {};
	    this.service = SignUp;
	    this.showPopup = false;
	  }
	
	  _createClass(SignUpController, [{
	    key: 'signUp',
	    value: function signUp() {
	      var _this = this;
	
	      if (this.userForm.$valid) {
	        if (this.user.password !== this.user.confirm) {
	          this.setPasswordsMatch(false);
	          return;
	        }
	
	        this.service.signUp(this.user, function () {
	          // success callback
	          _this.userForm.$setPristine();
	          _this.showPopup = true;
	        }, function (error) {
	          // error callback
	          if (error.data.error === 'email_already_exists') {
	            _this.userForm.mail.$setValidity('email_already_exists', false);
	          }
	        });
	      }
	    }
	  }, {
	    key: 'setPasswordsMatch',
	    value: function setPasswordsMatch(bool) {
	      this.userForm.password.$setValidity('passwords_match', bool);
	      this.userForm.confirm.$setValidity('passwords_match', bool);
	    }
	  }, {
	    key: 'resetEmailAlreadyExists',
	    value: function resetEmailAlreadyExists() {
	      this.userForm.mail.$setValidity('email_already_exists', true);
	    }
	  }]);
	
	  return SignUpController;
	}();
	
	exports.default = SignUpController;

/***/ },
/* 29 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _myTalks = __webpack_require__(30);
	
	var _myTalks2 = _interopRequireDefault(_myTalks);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.tabs.myTalks', {
	      url: '/my-talks',
	      template: '<my-talks></my-talks>'
	    });
	  }).component('myTalks', _myTalks2.default);
	};

/***/ },
/* 30 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _myTalks = __webpack_require__(31);
	
	var _myTalks2 = _interopRequireDefault(_myTalks);
	
	var _myTalks3 = __webpack_require__(32);
	
	var _myTalks4 = _interopRequireDefault(_myTalks3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var myTalksComponent = {
	  bindings: {},
	  template: _myTalks2.default,
	  controller: _myTalks4.default,
	  controllerAs: 'myTalksCtrl'
	};
	
	exports.default = myTalksComponent;

/***/ },
/* 31 */
/***/ function(module, exports) {

	module.exports = "<! *** !>\n<h1>Hello! hahaha</h1>";

/***/ },
/* 32 */
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var MyTalksController = function MyTalksController() {
	  _classCallCheck(this, MyTalksController);
	};
	
	exports.default = MyTalksController;

/***/ },
/* 33 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _forgotPassword = __webpack_require__(34);
	
	var _forgotPassword2 = _interopRequireDefault(_forgotPassword);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider) {
	    $stateProvider.state('header.home.forgotPassword', {
	      template: '<forgot-password></forgot-password>'
	    });
	  }).component('forgotPassword', _forgotPassword2.default);
	};

/***/ },
/* 34 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _forgotPassword = __webpack_require__(35);
	
	var _forgotPassword2 = _interopRequireDefault(_forgotPassword);
	
	var _forgotPassword3 = __webpack_require__(36);
	
	var _forgotPassword4 = _interopRequireDefault(_forgotPassword3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	var forgotPasswordComponent = {
	  bindings: {},
	  template: _forgotPassword2.default,
	  controller: _forgotPassword4.default,
	  controllerAs: 'forgotPasswordCtrl'
	};
	
	exports.default = forgotPasswordComponent;

/***/ },
/* 35 */
/***/ function(module, exports) {

	module.exports = "<div class=\"pop-up-wrapper\">\n  <div class=\"pop-up\" ng-if=\"!forgotPasswordCtrl.forgotten\">\n    <h3 class=\"pop-up__title\">Forgot password?</h3>\n    <form novalidate\n          name=\"forgotPasswordCtrl.userForm\"\n          ng-submit=\"forgotPasswordCtrl.restore()\">\n      <p class=\"pop-up__notification\">Send me a new password to my email address:</p>\n      <input type=\"email\"\n             class=\"field pop-up__input\"\n             name=\"mail\"\n             required\n             ng-model=\"forgotPasswordCtrl.user.mail\"\n             ng-pattern=\"/^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$/i\"\n             ng-change=\"forgotPasswordCtrl.resetEmailNotFound()\">\n\n      <span class=\"field-error error-title_pop-up\"\n         ng-show=\"forgotPasswordCtrl.userForm.$submitted && forgotPasswordCtrl.userForm.mail.$error.required\">\n        Email is required</span>\n      <span class=\"field-error error-title_pop-up\"\n         ng-show=\"forgotPasswordCtrl.userForm.$submitted && forgotPasswordCtrl.userForm.mail.$error.pattern\">\n        Please enter a valid email address</span>\n      <span class=\"field-error error-title_pop-up\"\n         ng-show=\"forgotPasswordCtrl.userForm.mail.$error.email_not_found\">\n        We can not find an account with that email address</span>\n\n      <div class=\"pop-up-button-wrapper\">\n        <input type=\"submit\"\n               value=\"Continue\"\n               class=\"btn pop-up__button\">\n\n        <input class=\"btn pop-up__button btn_cancel\"\n               ui-sref=\"header.home\"\n               type=\"button\"\n               value=\"cancel\">\n      </div>\n    </form>\n    <button class=\"pop-up__close\" ui-sref=\"header.home\"></button>\n  </div>\n\n  <div class=\"pop-up\" ng-if=\"forgotPasswordCtrl.forgotten\">\n    <h3 class=\"pop-up__title\">Password forgotten</h3>\n    <p class=\"pop-up__notification\">\n      We sent a new password to your email address\n      <span class=\"pop-up__notification-user\">\n       {{ forgotPasswordCtrl.user.mail}}\n      </span>\n    </p>\n\n    <button class=\"btn pop-up__button\" ui-sref=\"header.home\">Close</button>\n  </div>\n</div>";

/***/ },
/* 36 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var ForgotPasswordController = function () {
	  function ForgotPasswordController(ForgotPassword) {
	    _classCallCheck(this, ForgotPasswordController);
	
	    this.user = {};
	    this.userForm = {};
	    this.service = ForgotPassword;
	    this.forgotten = false;
	  }
	
	  _createClass(ForgotPasswordController, [{
	    key: 'restore',
	    value: function restore() {
	      var _this = this;
	
	      if (this.userForm.$valid) {
	        this.service.restore(this.user, function () {
	          // success callback
	          _this.forgotten = true;
	        }, function (error) {
	          // error callback
	          if (error.data.error === 'email_not_found') {
	            _this.userForm.mail.$setValidity('email_not_found', false);
	          }
	        });
	      }
	    }
	  }, {
	    key: 'resetEmailNotFound',
	    value: function resetEmailNotFound() {
	      this.userForm.mail.$setValidity('email_not_found', true);
	    }
	  }]);
	
	  return ForgotPasswordController;
	}();
	
	exports.default = ForgotPasswordController;

/***/ },
/* 37 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _current = __webpack_require__(38);
	
	var _current2 = _interopRequireDefault(_current);
	
	var _menus = __webpack_require__(40);
	
	var _menus2 = _interopRequireDefault(_menus);
	
	var _signIn = __webpack_require__(42);
	
	var _signIn2 = _interopRequireDefault(_signIn);
	
	var _signUp = __webpack_require__(44);
	
	var _signUp2 = _interopRequireDefault(_signUp);
	
	var _forgotPassword = __webpack_require__(46);
	
	var _forgotPassword2 = _interopRequireDefault(_forgotPassword);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  (function includeAllModulesGlobalFn(modulesArray, application) {
	            modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
	                moduleFn(application);
	            });
	        })([_current2.default, _menus2.default, _signIn2.default, _signUp2.default, _forgotPassword2.default], app);
	}; /* global include_all_modules */

/***/ },
/* 38 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _current = __webpack_require__(39);
	
	var _current2 = _interopRequireDefault(_current);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.factory('Current', _current2.default);
	};

/***/ },
/* 39 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	/* global angular */
	/* global FormData */
	
	function Current($resource, $window, $q, $rootScope, $http) {
	  function getToken() {
	    var info = $window.localStorage.userInfo;
	    var token = void 0;
	
	    if (info) {
	      info = JSON.parse(info);
	      token = info.token;
	    } else {
	      token = '';
	    }
	
	    return token;
	  }
	
	  var users = $resource('/api/users/current', {}, {
	    getCurrentUser: {
	      method: 'GET',
	      headers: {
	        token: getToken,
	        'Cache-Control': 'no-cache, no-store',
	        Pragma: 'no-cache'
	      }
	    },
	    updateCurrentUser: {
	      method: 'POST',
	      headers: {
	        token: getToken,
	        'Cache-Control': 'no-cache, no-store',
	        Pragma: 'no-cache'
	      }
	    }
	  });
	
	  function getInfo() {
	    var current = $q.defer();
	    users.getCurrentUser({}, function (data) {
	      current.resolve(data);
	    }, function () {
	      current.resolve(null);
	    });
	
	    this.current = current.promise;
	  }
	
	  function updateInfo(userInfo) {
	    users.updateCurrentUser(userInfo, function () {}, function () {
	      $rootScope.$broadcast('signInEvent');
	    });
	  }
	
	  function uploadPhoto(file) {
	    var formData = new FormData();
	    formData.append('file', file);
	    return $http.post('api/users/current/photo', formData, {
	      transformRequest: angular.identity,
	      headers: {
	        token: getToken,
	        'Cache-Control': 'no-cache, no-store',
	        Pragma: 'no-cache',
	        'Content-Type': undefined
	      }
	    });
	  }
	
	  function logout() {
	    return $http.get('/api/users/current/logout', {
	      headers: {
	        token: getToken,
	        'Cache-Control': 'no-cache, no-store',
	        Pragma: 'no-cache'
	      }
	    });
	  }
	
	  return {
	    getInfo: getInfo,
	    updateInfo: updateInfo,
	    uploadPhoto: uploadPhoto,
	    logout: logout
	  };
	}
	
	exports.default = Current;

/***/ },
/* 40 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _menus = __webpack_require__(41);
	
	var _menus2 = _interopRequireDefault(_menus);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.factory('Menus', _menus2.default);
	};

/***/ },
/* 41 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	var Menus = function Menus() {
	  var menu = {
	    s: [{
	      link: 'account',
	      name: 'My Account'
	    }, {
	      link: 'tabs.myInfo',
	      name: 'My Info'
	    }, {
	      link: 'tabs.myTalks',
	      name: 'My Talks'
	    }],
	    o: [{
	      link: 'oaccount',
	      name: 'My Account'
	    }, {
	      link: 'otalks',
	      name: 'Talks'
	    }]
	  };
	
	  return {
	    getMenu: function getMenu(role) {
	      var menuRole = void 0;
	
	      if (!role || role.length === 0) {
	        return false;
	      }
	
	      if (role.indexOf('s') !== -1) {
	        menuRole = 's';
	      }
	
	      if (role.indexOf('o') !== -1) {
	        menuRole = 'o';
	      }
	
	      return menu[menuRole];
	    }
	  };
	};
	
	exports.default = Menus;

/***/ },
/* 42 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _signIn = __webpack_require__(43);
	
	var _signIn2 = _interopRequireDefault(_signIn);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.factory('SignIn', _signIn2.default);
	};

/***/ },
/* 43 */
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	function SignIn($http, $q, $window, $rootScope) {
	  var userInfo = void 0;
	
	  function login(user) {
	    var deferred = $q.defer();
	
	    var headers = user ? { authorization: "Basic " + btoa(user.mail + ":" + user.password)
	    } : {};
	    console.log(headers);
	    $http.post('/api/login/', user, { headers: headers }).then(function (result) {
	      userInfo = {
	        token: result.data.token
	      };
	
	      $window.localStorage.setItem('userInfo', JSON.stringify(userInfo));
	      deferred.resolve(userInfo);
	    }, function (error) {
	      deferred.reject(error);
	    });
	
	    return deferred.promise;
	  }
	
	  function callTheEvent() {
	    $rootScope.$broadcast('signInEvent');
	  }
	
	  return {
	    login: login,
	    callTheEvent: callTheEvent
	  };
	}
	
	exports.default = SignIn;

/***/ },
/* 44 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _signUp = __webpack_require__(45);
	
	var _signUp2 = _interopRequireDefault(_signUp);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.factory('SignUp', _signUp2.default);
	};

/***/ },
/* 45 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	function SignUp($resource) {
	  var resource = $resource('/api/users/:id', { id: '@_id' }, {
	    update: {
	      method: 'PUT' // this method issues a PUT request
	    }
	  });
	
	  function signUp(user, successCallback, errorCallback) {
	    // POST
	    resource.save(user, function (result) {
	      successCallback(result);
	    }, function (error) {
	      errorCallback(error);
	    });
	  }
	
	  return {
	    signUp: signUp
	  };
	}
	
	exports.default = SignUp;

/***/ },
/* 46 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _forgotPassword = __webpack_require__(47);
	
	var _forgotPassword2 = _interopRequireDefault(_forgotPassword);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.factory('ForgotPassword', _forgotPassword2.default);
	};

/***/ },
/* 47 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	function ForgotPassword($resource) {
	  var resource = $resource('/api/forgot-password');
	
	  function restore(user, successCallback, errorCallback) {
	    resource.save(user, function (result) {
	      successCallback(result);
	    }, function (error) {
	      errorCallback(error);
	    });
	  }
	
	  return {
	    restore: restore
	  };
	}
	
	exports.default = ForgotPassword;

/***/ },
/* 48 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	exports.default = function (app) {
	  app.run(function ($rootScope) {
	    $rootScope.$on('$stateChangeSuccess', function () {
	      $rootScope.$broadcast('closeDropdown');
	    });
	  });
	};

/***/ },
/* 49 */
/***/ function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }
/******/ ]);
//# sourceMappingURL=bundle.js.map