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

      var user = new User();      // create a new instance of the User
      user.mail = req.body.mail;
      user.password = req.body.password;
      user.roles.push(req.body.role);
      user.save(function (err) {
        if (err)
          res.send(err);

        res.send({message: 'user created'});
      });

    })
    .get((req, res) => {
      User.find((err, current) => {
        if (err)
          res.send(err);

        res.json(current);
      });
    });


// current  get user
  router.route('/users/current')
    .get((req,res)=>{
        res.json({"role":["s"],"name":"Ivan"});
        console.log(req.headers.token);
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

  app.get("/*", (req, res, next) => {
    res.redirect("/");
  });


// START THE SERVER
// =============================================================================
  app.listen(PORT);
  console.log('Magic happens on port ' + PORT);
}