var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
  mail: {
    type: String,
    unique: true,
    required: true
  },
  password: {
    type: String,
    required: true
  },
  fname: {
    type: String,
    required: true
  },
  lname: {
    type: String,
    required: true
  },
  roles:{
    type: Array
  },
  hash: {
    type: Array
  }
});

module.exports = mongoose.model('User', UserSchema);