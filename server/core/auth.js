'use strict';
let User = require('./../model/User');

let auth = function auth(req, res) {

  if (req.body.password && req.body.mail) {
    User.findOne ({
        mail: req.body.mail.toLowerCase()
      },
      (err, user) => {

        if (!user) {
          res.status(401).json ({"error": "login_auth_err"});
          return;
        }

        User.findOne ({
            mail: req.body.mail.toLowerCase(),
            password: req.body.password
          },
          (err, user) => {

            if (!user) {
              res.status(401).json ({"error": "password_auth_err"});
              return;
            }

            let rand = Math.floor(Math.random() * 90000) + 10000;
            let token = user._id + rand; // fake token
            user.hash.push(token);
            user.save();
            res.json({"token": token});
          });
      });


  } else {
    res.status(401).json ({"error": "no_info_auth_err"});
  }
}


module.exports = auth;