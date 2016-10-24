'use strict';

const fileUpload = require('express-fileupload');

let User = require('./../model/User');

function uploadImage(req, res) {
  if (!req.headers.token) {
    res.status(401).send({});
    return;
  }

  User.findOne({hash: req.headers.token}, (err, current) => {
    let sampleFile;

    if (!req.files) {
      res.send('No files were uploaded.');
      return;
    }

    sampleFile = req.files.sampleFile;
    sampleFile.mv('/img/' + req.headers.token.jpg, function(err) {
      if (err) {
        res.status(500).send(err);
      }
      else {
        res.send('File uploaded!');
      }
    });

  });
}

module.exports = uploadImage;