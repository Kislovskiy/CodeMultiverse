# React

Very minimalistic React app.

```sh
document.createElement
```

# Installation
`npm` is your best friend to get dependencies.

```sh
$ npm --version
11.12.1
$ npm init
$ npm install --save react
$ npm install --save react-dom
$ npm install --save-dev @babel/cli
$ npm install --save-dev @babel/plugin-transform-react-jsx
```

You'll also need to define custom `"build": "babel --plugins @babel/plugin-transform-react-jsx MyApp.jsx > MyApp.js"` command in `package.json`.
