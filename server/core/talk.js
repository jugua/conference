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
    res.send();
  });
}

module.exports = {
  addTalk
}