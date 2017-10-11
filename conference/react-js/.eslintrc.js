module.exports = {
    "plugins": [
        "react",
        "jsx-a11y",
        "import",
        "jest"
    ],

    "parser": "babel-eslint",
    "parserOptions": {
        "ecmaVersion": 6,
        "sourceType": "module",
        "ecmaFeatures": {
            "jsx": true,
            "modules": true,
            "experimentalObjectRestSpread": true,

        }
    },
    "extends": ["eslint-config-airbnb"],
    "env": {
        "browser": true,
        "node": true,
        "jest": true
    },
    "rules": {
        "semi": ["error", "always"],
        "quotes": ["error", "single"],
        "indent": ["error", 2],
        "eol-last": ["error", "always"],
        "max-len": [1, 80, 2],
        "react/no-deprecated": "error",
        "no-multiple-empty-lines": [2, {"max": 1}]
    }
};
