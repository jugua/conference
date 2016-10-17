'use strict';

var express = require('express');
var app = express();
var path = require('path');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var User = require('./model/User');
var auth = require('./core/auth');

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
    .post((req, res) => {
      console.log('got post request');
      console.log('req body:');
      console.log(req.body);
      if (req.body.mail && req.body.password && req.body.fname && req.body.lname) {
        console.log('checking if email is already registered');
        User.findOne({
            mail: req.body.mail
          },
          (err, user) => {
            if (user) {   // email already registered
              console.log('email found');
              res.status(409).json({error: 'email_already_exists'});
              return;
            } else {
              console.log('email not found, registering');
              var user = new User();      // create a new instance of the User
              user.mail = req.body.mail;
              user.password = req.body.password;
              user.save((err) => {
                if (err) {
                  res.send(err);
                } else {
                  res.send({message: 'user_created'});
                }
              });
            }
          });
      } else {
        console.log('required data not passed');
      }
    })

// current  get user
  router.route('/users/current')
    .get((req, res)=> {

      if (!req.headers.token) {
        res.status(401).send({});
        return;
      }
      User.findOne({hash: req.headers.token}, (err, current) => {
        if (err)
          res.status(403).send(err);

        if (!current) {
          res.status(401).send({});
          return;
        }

        res.json(current);
      });

    });

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