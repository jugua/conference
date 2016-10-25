'use strict'


let User = require('./../model/User');

function uploadImage(req, res) {
  console.log('fdffffffff');
  console.log(req);
  console.log(req.files);
}

module.exports = uploadImage;