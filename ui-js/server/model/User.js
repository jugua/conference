'use strict';

const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const crypto = require('crypto');

const UserSchema = new Schema({
  mail: {
    type: String,
    unique: true,
    required: true
  },
  hashedPassword: {
    type: String,
    required: true
  },
  salt: {
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
  bio: {
    type: String
  },
  job: {
    type: String
  },
  company: {
    type: String
  },
  past: {
    type: String
  },
  photo: {
    type: String
  },
  linkedin: {
    type: String
  },
  twitter: {
    type: String
  },
  facebook: {
    type: String
  },
  blog: {
    type: String
  },
  info: {
    type: String
  },
  roles: {
    type: Array
  },
  hash: {
    type: Array
  },
  talks: [{
    title: {
      type: String
    },
    description: {
      type: String
    },
    topic: {
      type: String
    },
    lang: {
      type: String
    },
    level: {
      type: String
    },
    addon: {
      type: String
    },
    status: {
      type: String
    },
    date: {
      type: Number
    },

  }]
});

UserSchema.methods.encryptPassword = function(password) {
  return crypto.createHmac('sha256', this.salt).update(password).digest('hex');
};

UserSchema.virtual('password')
  .set(function(password) {
    this._plainPassword = password;
    this.salt = `${Math.random()}`;
    this.hashedPassword = this.encryptPassword(password);
  })
  .get(function() { return this._plainPassword; });


UserSchema.methods.checkPassword = function(password) {
  return this.encryptPassword(password) === this.hashedPassword;
};

module.exports = mongoose.model('User', UserSchema);