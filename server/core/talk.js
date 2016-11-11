'use strict';

let User = require('./../model/User');

function addTalk(req, res) {
  console.log(req.body);
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
    if (err)
      res.status(403).send(err);

    if (!current) {
      res.status(401).send({});
      return;
    }

    let answer = current.talks;
    res.send(answer);
  });
}
module.exports = {
  addTalk,
  getTalks
}