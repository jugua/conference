'use strict';

const express = require('express');
const app = express();
const path = require('path');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const User = require('./model/User');
const auth = require('./core/auth');
const registration = require('./core/registration');
const getCurrent = require('./core/current');

module.exports = (PORT) => {

  if (!PORT) {
    PORT = process.env.PORT || 9000;
  }

  let router = express.Router();

  mongoose.connect('mongodb://mey:computers@ds015574.mlab.com:15574/mey_test');

  app.use(express.static(path.join(__dirname, './../dist')));
  app.use("/", express.static(path.join(__dirname, './../dist')));
  app.use(bodyParser.urlencoded({extended: true}));
  app.use(bodyParser.json());

  router.use((req, res, next) => {
    console.log('Something is happening.');
    next();
  });


// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api

  router.route('/login')
    .post(auth);

//EXAMPLE REST FOR  testing adding users NOW NOT USED------------------------------------
  router.route('/users')
    .post(registration)
    .get((req, res) => {
      User.find((err, current) => {
        if (err)
          res.send(err);

        res.json(current);
      });
    });

// current  get user
  router.route('/users/current')
    .get(getCurrent);

  // get user by id

  router.route('/users/:user_id')

    // get the user  with that id )
    .get(function (req, res) {
      User.findById(req.params.user_id, (err, current) => {
        if (err)
          res.send(err);
        res.json(current);
      });
    });


  app.use('/api', router);

  app.get("/*", (req, res) => {
    res.redirect("/");
  });


// START THE SERVER
// =============================================================================
  app.listen(PORT);
  console.log('Magic happens on port ' + PORT);
}