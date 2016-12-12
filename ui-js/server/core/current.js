'use strict';

const User = require('./../model/User');
const userfields = ['mail', 'fname', 'lname', 'roles', 'bio', 'job', 'company', 'past', 'photo', 'linkedin', 'twitter', 'facebook', 'blog', 'info'];

function get(req, res) {
  if (!req.headers.token) {
    res.status(401).send({});
    return;
  }
  User.findOne({ hash: req.headers.token }, (err, current) => {
    if (err) {
      res.status(403).send(err);
    }

    if (!current) {
      res.status(401).send({});
      return;
    }

    const answer = {};
    userfields.forEach((field) => {
      if (current[field]) {
        answer[field] = current[field];
      }
    });
    res.json(answer);
  });
}

function update(req, res) {
  User.findOneAndUpdate({ hash: req.headers.token }, req.body, (err, current) => {
    if (err) {
      res.status(403).send(err);
      return;
    }

    if (!current) {
      res.status(401).send({ error: 'no-current-user' });
      return;
    }
    res.send();
  });
}

module.exports = {
  get,
  update
};