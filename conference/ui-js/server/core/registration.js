'use strict';

const User = require('./../model/User');

function registration(req, res) {
  const userfields = ['mail', 'password', 'fname', 'lname'];

  const isEmptyFields = userfields.some((field) => {
    if (!req.body[field]) {
      return true;
    }
  });

  if (isEmptyFields) {
    res.status(400).send({ message: 'empty_fields' });
    return;
  }

  User.findOne({
    mail: req.body.mail.toLowerCase()
  },
    (err, user) => {
      if (user) {
        res.status(409).json({ error: 'email_already_exists' });
      } else {
        const user = new User();// create a new instance of the User

        userfields.forEach((field) => {
          if (field === 'mail') {
            req.body[field] = req.body[field].toLowerCase();
          }
          user[field] = req.body[field];
        });

        user.roles.push('s');

        user.save((err) => {
          if (err) {
            res.send({ error: 'database' });
          } else {
            res.send({ status: 'success' });
          }
        });
      }
    });
}

module.exports = registration;