'use strict';

const User = require('./../model/User');

const auth = function auth(req, res) {
  if (req.body.mail) {
    User.findOne({
      mail: req.body.mail.toLowerCase()
    },
      (err, user) => {
        if (!user) {
          res.status(401).json({ error: 'login_auth_err' });
          return;
        }

        if (!req.body.password) {
          res.status(401).json({ error: 'password_auth_err' });
          return;
        }

        if (!user.checkPassword(req.body.password)) {
          res.status(401).json({ error: 'password_auth_err' });
          return;
        }

        const rand = Math.floor(Math.random() * 90000) + 10000;
        const token = user._id + rand; // fake token
        user.hash.push(token);
        user.save();
        res.json({ token });
      });
  } else {
    res.status(401).json({ error: 'no_info_auth_err' });
  }
};


module.exports = auth;