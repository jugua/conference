'use strict';

let User = require('./../model/User');

function forgotPassword(req, res) {
  if (!req.body.mail) {
    res.status(400).send({error: 'email_is_empty'});
    return;
  }

  User.findOne({
    mail: req.body.mail.toLowerCase()
  },
    (err, user) => {
      if (user) {
        res.status(200).json({ result: 'new_password_sent' });
      } else {
        res.status(404).send({ error: 'email_not_found' });
      }
    });
}

module.exports = forgotPassword;