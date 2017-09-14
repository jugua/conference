const User = require('./../model/User');

function editEmail(req, res) {
  if (!req.headers.token) {
    res.status(401).send({ error: 'no-token' });
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

    User.findOne({  // check if email already exists
      mail: req.body.mail.toLowerCase()
    },
    (err2, user) => {
      if (user) {
        res.status(409).send({ error: 'email_already_exists' });
        return;
      }
      User.findOneAndUpdate({ hash: req.headers.token }, req.body, (err3) => {
        if (err3) {
          res.status(403).send(err3);
          return;
        }
        res.send('success');
      });
    });
  });
}

module.exports = editEmail;