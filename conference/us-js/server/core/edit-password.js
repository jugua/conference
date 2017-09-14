'use strict';

const User = require('./../model/User');

function editPassword(req, res) {
  if (!req.headers.token) {
    res.status(401).send({});
    return;
  }

  User.findOne({ hash: req.headers.token }, (err, current) => {
    if (err) {
      res.status(403).send(err);
      return;
    }

    if (!current) {
      res.status(401).send({ error: 'no-current-user' });
      return;
    }

    const error = {
      error: null,
      fields: []
    };

    const userInfo = req.body || {};

    if (!userInfo.currentPassword || !userInfo.newPassword || !userInfo.confirmNewPassword) {
      error.error = 'required';
      if (!userInfo.currentPassword) {
        error.fields.push('currentPassword');
      }
      if (!userInfo.newPassword) {
        error.fields.push('newPassword');
      }
      if (!userInfo.confirmNewPassword) {
        error.fields.push('confirmNewPassword');
      }
      console.log(error);
      res.status(400).send(error);
      return;
    }

    if (userInfo.newPassword.length < 6) {
      error.error = 'minlength';
      error.fields.push('newPassword');

      console.log(error);
      res.status(400).send(error);
      return;
    }

    if (userInfo.newPassword.length > 30) {
      error.error = 'maxlength';
      error.fields.push('newPassword');

      console.log(error);
      res.status(400).send(error);
      return;
    }

    if (!/\S+\s*/.test(userInfo.newPassword)) {
      error.error = 'pattern';
      error.fields.push('newPassword');

      console.log(error);
      res.status(400).send(error);
      return;
    }

    if (userInfo.newPassword !== userInfo.confirmNewPassword) {
      error.error = 'password_match';
      error.fields.push('newPassword');
      error.fields.push('confirmNewPassword');
      res.status(400).send(error);
      return;
    }


    if (!current.checkPassword(userInfo.currentPassword)) {
      error.error = 'wrong_password';
      error.fields.push('currentPassword');
      res.status(400).send(error);
      return;
    }

    current.password = userInfo.newPassword;
    current.save((error) => {
      if (error) {
        res.status(500).send({ error: 'save' });
        return;
      }
      console.log('password changed');
      res.send('OK');
    });
  });
}


module.exports = editPassword;