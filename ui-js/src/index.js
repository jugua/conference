/* global angular */
//  Angular & Router ES6 Imports
//  import angular from 'angular';
//  import angularUIRouter from 'angular-ui-router';
import appComponents from './components/components';
//  import commonComponents from './common/components.js';
import appServices from './services/services';
import appFilters from './filters/filters';
import appConfiguration from './app.config';

// Single Style Entry Point
import './main.sass';

const app = angular.module('app', ['ui.router', 'ngResource', 'ngFileUpload', 'ngMaterial', 'ngAnimate']);

// Components Entrypoint
appComponents(app);

//  Common Components Entrypoint
//  commonComponents(app);

//  App Services Entrypoint
appServices(app);


// App Filters Entrypoint
appFilters(app);


// Router Configuration
// Components must be declared first since
// Routes reference controllers that will be bound to route templates.
appConfiguration(app);
