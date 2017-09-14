'use strict';

const User = require('./../model/User');

function addTalk(req, res) {
  User.findOneAndUpdate({ hash: req.headers.token }, { $push: { talks: req.body } },
    (err, current) => {
      if (err) {
        res.status(403).send(err);
        return;
      }

      if (!current) {
        res.status(401).send({ error: 'no-current-user' });
        return;
      }
      console.log('sucsess saved');
      res.send();
    });
}

function getTalks(req, res) {
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
      res.status(401).send({});
      return;
    }

    if (current.roles.indexOf('s') !== -1) {
      res.send(current.talks);
    } else {
      User.find({}, 'talks fname lname').lean().exec((err, dbtalks) => {
        const talks = [];
        if (err) {
          res.status(500).send(err);
          return;
        }

        dbtalks.forEach((user) => {
          const name = `${user.lname} ${user.fname}`;
          if (!user.talks) {
            return;
          }
          user.talks.forEach((dbtalk) => {
            let talk = {};
            talk = Object.assign(dbtalk, { name });
            talks.push(talk);
          });
        });

        res.send(talks);
      });
    }
  });
}
module.exports = {
  addTalk,
  getTalks
};