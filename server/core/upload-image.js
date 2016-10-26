'use strict'

const fs = require('fs');

let User = require('./../model/User');
const userfields = ['photo'];

function uploadImage(req, res) {
  if (!req.headers.token) {
    res.status(401).send({});
    return;
  }

  User.findOneAndUpdate({hash: req.headers.token}, req.body, (err, current) => {
    if (err) {
      res.status(403).send(err);
      return;
    }

    if (!current) {
      res.status(401).send({error: 'no-current-user'});
      return;
    }

    let file = req.files[0],
      path = './images/';
    console.log(file);

    // Logic for handling missing file, wrong mimetype, no buffer, etc.

    if (file.size > 2097152){
      res.status(413).send({error: 'maxSize'})
      return
    }

    if (!/(jp(e)?g)?(gif)?(png)?/.test(file.mimetype)){
      res.status(415).send({error: 'pattern'})
      return
    }

    let buffer = file.buffer,
      fileName = file.originalname;
    let stream = fs.createWriteStream(path + fileName);
    stream.write(buffer);

    stream.on('error', function(err) {
      console.log('Could not write file to memory.');
      res.status(400).send({
        message: 'Problem saving the file. Please try again.'
      });
    });

    stream.on('finish', function() {
      console.log('File saved successfully.');
      current['photo'] = path + fileName;
      var data = {
        message: 'File saved successfully.'
      };
      res.json(data);
    });
    stream.end();
    console.log('Stream ended.');
  });


}

module.exports = uploadImage;