'use strict';
let User = require('./../model/User');
const userfields = ['mail', 'fname', 'lname','roles'];

function getCurrent(req, res) {
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

    let answer = {};
    userfields.forEach((field) => {
      if (current[field]){
        answer[field] = current[field];
      }
    })
    res.json(answer);
  });
}
module.exports = getCurrent;