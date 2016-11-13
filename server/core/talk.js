'use strict';

let User = require('./../model/User');

function addTalk(req, res) {

  User.findOneAndUpdate({hash: req.headers.token}, {$push: {"talks": req.body}}, (err, current) => {
    if (err) {
      res.status(403).send(err);
      return;
    }

    if (!current) {
      res.status(401).send({error: 'no-current-user'});
      return;
    }
    console.log('sucsess saved')
    res.send();
  });
}

function getTalks(req, res) {

  if (!req.headers.token) {
    res.status(401).send({});
    return;
  }

  User.findOne({hash: req.headers.token}, (err, current) => {
    if (err) {
      res.status(403).send(err);
      return;
    }

    if (!current) {
      res.status(401).send({});
      return;
    }
    if (current.roles.indexOf('s') != -1) {
      res.send(current.talks);
    } else {
      User.find({},'talks', (err, res) => {
        if (err) {

          res.status(500).send(err);
          return;
        }
        console.log(res);
        res.send(res);
      })
    }


  });
}
module.exports = {
  addTalk,
  getTalks
}