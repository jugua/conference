'use strict'

const fs = require('fs');
const path = require('path');


let User = require('./../model/User');

function uploadImage(req, res) {
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
      res.status(401).send({error: 'no-current-user'});
      return;
    }

    let file = req.files[0];

    // Logic for handling missing file, wrong mimetype, no buffer, etc.

    if (file.size > 2097152){
      res.status(413).send({error: 'maxSize'});
      return;
    }
    if (file.size === 0){
      res.status(413).send({error: 'minSize'});
      return;
    }
    if (!/jp(e)?g|gif|png$/.test(file.mimetype)){
      res.status(415).send({error: 'pattern'});
      return
    }

    if(current.photo) {

      fs.unlink(path.join(__dirname, '/../../dist/' + current.photo), (err)=>{
        console.log(err);
        if (err) {
          res.status(403).send({error: 'delete'});
          return
        }
        console.log('successfully deleted')
      });
    }

    let pathFile = path.join(__dirname, '/../../dist/assets/img/'),
        buffer = file.buffer,
        random = Math.random().toString(36).substr(2, 5),
        fileName = current._id,
        stream = fs.createWriteStream(pathFile + fileName + random);

    stream.write(buffer);
    stream.on('error', function(err) {
      console.log(err);
      console.log('Could not write file to memory.');

      res.status(400).send({
        error: 'save'
      });
    });

    stream.on('finish', function() {
      console.log('File saved successfully.');

      current['photo'] = 'assets/img/' + fileName + random;

      current.save((err) => {
        if (err) {
          res.send({error:"save"});
          return;
        }

        let data = {
          answer: current['photo']
        };
        res.send(data);

      });
    });

    stream.end();
    console.log('Stream ended.');
  });


}

module.exports = uploadImage;