'use strict';

const express = require('express');

const app = express();
const path = require('path');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const User = require('./model/User');
const auth = require('./core/auth');
const registration = require('./core/registration');
const current = require('./core/current');
const forgotPassword = require('./core/forgot-password');
const photo = require('./core/photo');
const logout = require('./core/logout');
const talk = require('./core/talk');
const editPassword = require('./core/edit-password');
const editEmail = require('./core/edit-email');
const multer = require('multer');

const upload = multer();


module.exports = (PORT) => {
  if (!PORT) {
    PORT = process.env.PORT || 9000;
  }

  const router = new express.Router();

 // mongoose.connect('mongodb://mey:computers@ds015574.mlab.com:15574/mey_test');
 // mongoose.connect('mongodb://conference:management@ds151127.mlab.com:51127/managment');
  mongoose.connect('mongodb://conference:management@ds163667.mlab.com:63667/crypto');

  app.use(express.static(path.join(__dirname, './../dist')));
  app.use('/', express.static(path.join(__dirname, './../dist')));
  app.use(bodyParser.urlencoded({ limit: '5mb', extended: true }));
  app.use(bodyParser.json({ limit: '5mb' }));

  router.use((req, res, next) => {
    console.log('Something is happening.');
    next();
  });


// REGISTER OUR ROUTES -------------------------------
// all of our routes will be prefixed with /api
  router.route('/talk')
    .post(talk.addTalk)
    .get(talk.getTalks);

  router.route('/login')
    .post(auth);

  router.route('/forgot-password')
    .post(forgotPassword);

  router.route('/logout')
    .post(logout);

// EXAMPLE REST FOR  testing adding users NOW NOT USED------------------------------------
  router.route('/user')
    .post(registration)
    .get((req, res) => {
      User.find((err, current) => {
        if (err) {
          res.send(err);
        }
        res.json(current);
      });
    });

// current  get user
  router.route('/myinfo')
    .get(current.get)
    .post(current.update);

  router.route('/user/current/password')
    .post(editPassword);

  router.route('/user/current/email')
    .post(editEmail);

  router.route('/myinfo/photo')
    .post(upload.any(), photo.uploadImage)
    .delete(photo.deleteImage); // deleting photo

  // get user by id

  router.route('/user/:user_id')

    // get the user  with that id )
    .get((req, res) => {
      User.findById(req.params.user_id, (err, current) => {
        if (err) {
          res.send(err);
        }
        res.json(current);
      });
    });


  app.use('/api', router);

  app.get('/*', (req, res) => {
    res.redirect('/');
  });


// START THE SERVER
// =============================================================================
  app.listen(PORT);
  console.log(`Server run on ${PORT} port`);
};