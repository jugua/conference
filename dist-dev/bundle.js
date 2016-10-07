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
	
	var _services = __webpack_require__(6);
	
	var _services2 = _interopRequireDefault(_services);
	
	var _app = __webpack_require__(9);
	
	var _app2 = _interopRequireDefault(_app);
	
	__webpack_require__(10);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	/*
	if (ENVIRONMENT === 'test') {
	  console.log('ENV:', ENVIRONMENT);
	  require('angular-mocks/angular-mocks');
	}*/
	// Angular & Router ES6 Imports
	//import angular from 'angular';
	//import angularUIRouter from 'angular-ui-router';
	var d2 = 3;
	
	// Single Style Entry Point
	
	//import commonComponents from './common/components.js';
	
	
	var app = angular.module('app', ['ui.router']);
	
	// Components Entrypoint
	(0, _components2.default)(app);
	
	// Common Components Entrypoint
	//commonComponents(app);
	
	// App Services Entrypoint
	(0, _services2.default)(app);
	
	// Router Configuration
	// Components must be declared first since
	// Routes reference controllers that will be bound to route templates.
	// appConfiguration(app);

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _home = __webpack_require__(2);
	
	var _home2 = _interopRequireDefault(_home);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  (function includeAllModulesGlobalFn(modulesArray, application) {
	                modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
	                    moduleFn(application);
	                });
	            })([_home2.default], app);
	};

/***/ },
/* 2 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _home = __webpack_require__(3);
	
	var _home2 = _interopRequireDefault(_home);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.config(function ($stateProvider, $urlRouterProvider) {
	    $urlRouterProvider.otherwise('/');
	
	    $stateProvider.state('home', {
	      url: '/',
	      template: '<home></home>' //Essentially Treats the Home Directive as the Route View.
	    });
	  }).directive('home', _home2.default);
	};

/***/ },
/* 3 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _home = __webpack_require__(4);
	
	var _home2 = _interopRequireDefault(_home);
	
	var _home3 = __webpack_require__(5);
	
	var _home4 = _interopRequireDefault(_home3);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	// This is the Directive Definition Object function seen in a traditional Angular setup.
	// In this example it is abstracted as a shell and used in the home.js.
	var homeComponent = function homeComponent() {
	  return {
	    restrict: 'EA',
	    scope: {},
	    template: _home2.default,
	    controller: _home4.default,
	    controllerAs: 'homeCtrl',
	    bindToController: true
	  };
	};
	
	exports.default = homeComponent;

/***/ },
/* 4 */
/***/ function(module, exports) {

	module.exports = "<navbar></navbar>\r\n<header>\r\n  <hero></hero>\r\n</header>\r\n<div class=\"Container home\">\r\n  <div>\r\n    <h1>Found in {{ homeCtrl.name }}.html</h1>\r\n  </div>\r\n</div>\r\n";

/***/ },
/* 5 */
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
/* 6 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _users = __webpack_require__(7);
	
	var _users2 = _interopRequireDefault(_users);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  (function includeAllModulesGlobalFn(modulesArray, application) {
	                modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
	                    moduleFn(application);
	                });
	            })([_users2.default], app);
	};

/***/ },
/* 7 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	var _users = __webpack_require__(8);
	
	var _users2 = _interopRequireDefault(_users);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = function (app) {
	  app.factory('users', _users2.default);
	};

/***/ },
/* 8 */
/***/ function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	var usersService = function usersServiceFn() {
	  return {
	    name: 'users'
	  };
	};
	
	exports.default = usersService;

/***/ },
/* 9 */
/***/ function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	
	exports.default = function (app) {
	  app.config([configFn]);
	
	  function configFn() {}
	};

/***/ },
/* 10 */
/***/ function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }
/******/ ]);
//# sourceMappingURL=bundle.js.map