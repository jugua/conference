module.exports = {
    "plugins": [
        "react",
        "jsx-a11y",
        "import",
        "jest"
    ],
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
        "react/prop-types": 0


    }
};