/* global angular */
//  Angular & Router ES6 Imports
//  import angular from 'angular';
//  import angularUIRouter from 'angular-ui-router';
import appComponents from './components/components';
//  import commonComponents from './common/components.js';
import appServices from './services/services';
import appConfiguration from './app.config';

// Single Style Entry Point
import './main.sass';

const app = angular.module('app', ['ui.router', 'ngResource', 'ngFileUpload']);

// Components Entrypoint
appComponents(app);

//  Common Components Entrypoint
//  commonComponents(app);

//  App Services Entrypoint
appServices(app);


// Router Configuration
// Components must be declared first since
// Routes reference controllers that will be bound to route templates.
appConfiguration(app);
