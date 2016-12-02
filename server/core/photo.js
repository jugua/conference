'use strict';

const fs = require('fs');
const path = require('path');


const User = require('./../model/User');

function uploadImage(req, res) {
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
      res.status(401).send({ error: 'no-current-user' });
      return;
    }

    const file = req.files[0];

    // Logic for handling missing file, wrong mimetype, no buffer, etc.

    if (file.size > 2097152) {
      res.status(413).send({ error: 'maxSize' });
      return;
    }
    if (file.size === 0) {
      res.status(413).send({ error: 'minSize' });
      return;
    }
    if (!/jp(e)?g|gif|png$/.test(file.mimetype)) {
      res.status(415).send({ error: 'pattern' });
      return;
    }

    if (current.photo) {
      fs.unlink(path.join(__dirname, `/../../dist/${current.photo}`), (err) => {
        console.log(err);
        if (err) {
          res.status(403).send({ error: 'delete' });
          return;
        }
        console.log('successfully deleted');
      });
    }

    const pathFile = path.join(__dirname, '/../../dist/assets/img/');
    const buffer = file.buffer;
    const random = Math.random().toString(36).substr(2, 5);
    const fileName = current._id;
    const stream = fs.createWriteStream(pathFile + fileName + random);

    stream.write(buffer);
    stream.on('error', (err) => {
      console.log(err);
      console.log('Could not write file to memory.');

      res.status(400).send({
        error: 'save'
      });
    });

    stream.on('finish', () => {
      console.log('File saved successfully.');

      current.photo = `assets/img/${fileName}${random}`;

      current.save((err) => {
        if (err) {
          res.send({ error: 'save' });
          return;
        }

        const data = {
          answer: current.photo
        };
        res.send(data);
      });
    });

    stream.end();
    console.log('Stream ended.');
  });

}

// deleting user photo

function deleteImage(req, res) {
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
      res.status(401).send({ error: 'no-current-user' });
      return;
    }

    if (current.photo) {
      fs.unlink(path.join(__dirname, `/../../dist/${current.photo}`), (err) => {
        console.log(err);
        if (err) {
          res.status(403).send({ error: 'delete' });
          return;
        }
        console.log('successfully deleted');
      });

      current.photo = '';

      current.save((err) => {
        if (err) {
          res.send({ error: 'delete' });
          return;
        }

        res.send();
      });
    }
  });
}

module.exports = {
  uploadImage,
  deleteImage
};