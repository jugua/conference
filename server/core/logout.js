'use strict';
let User = require('./../model/User');

function logout(req, res) {

  if ( && req.headers.token) {
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

            let token = user._id + 1234; //fake token
            user.hash.push(token);
            user.save();
            res.json({"token": token});
          });
      });

  } else {
    res.status(404).json ({"error": "session_not_found"});
  }
}


module.exports = auth;