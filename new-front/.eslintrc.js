module.exports = {
  parser: "@typescript-eslint/parser",
  plugins: ["@typescript-eslint"],
  extends: [
    "react-app",
    "airbnb",
    "plugin:@typescript-eslint/eslint-recommended",
    "plugin:react/recommended"

  ],
  rules: {
    "semi": ["error", "never"],
    "max-len": ["error", { "code": 120 }],
    "import/no-extraneous-dependencies": ["error", { "devDependencies": true }],
    "quotes": ["error", "single", { "avoidEscape": true }],
    "jsx-quotes": ["error", "prefer-single"],
    "linebreak-style": 0,
    "import/prefer-default-export": 0,
    "import/no-unresolved": "off",
    "import/extensions": [1, "never"],
    "comma-dangle": ["error", "never"],
    "no-param-reassign": 0,
    "react/jsx-filename-extension": ["error", { "extensions": [".js", ".jsx", ".ts", ".tsx"] }],
    "react/prop-types": 0,
    "react/jsx-closing-bracket-location": 0,
    "react/jsx-one-expression-per-line": 0,
    "react/jsx-closing-tag-location": 0,
    "object-curly-newline": 0,
    "react/jsx-max-props-per-line": 0,
    "react/jsx-first-prop-new-line": 0,
    "no-unused-expressions": ["error", { "allowTernary": true }],
    "no-use-before-define": 0,
    "dot-notation": ["error", { "allowPattern": "^[A-Z]+(_[A-Z]+)+$" }],
    "react/display-name": [0]
  }
}
