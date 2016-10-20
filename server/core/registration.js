let User = require('./../model/User');

function registration(req, res) {
  let token;
  let userfileds = ['mail', 'password', 'fname', 'lname'];

  const isEmptyFields = userfileds.some(function(field) {
    if (!req.body[field]) {
      return true;
    }
  });

  if (isEmptyFields) {
    res.status(400).send({message: 'empty_fields'});
    return;
  }

  User.findOne({
      mail: req.body.mail
    },
    (err, user) => {
      if (user) {
        res.status(409).json({error: 'email_already_exists'});
      } else {
        let user = new User();// create a new instance of the User
        token = user._id + 123;
        userfileds.forEach((field) => {
          user[field] = req.body[field];
        })

        user.roles.push('s');

        user.hash.push(token);
        user.save((err) => {
          if (err) {
            res.send({error:"database"});
          } else {
            res.send({token});
          }
        });
      }
    });
}

module.exports = registration;