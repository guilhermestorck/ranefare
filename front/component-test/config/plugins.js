// ***********************************************************
// This example plugins/index.js can be used to load plugins
//
const cucumber = require('cypress-cucumber-preprocessor').default;

module.exports = on => {
	on('file:preprocessor', cucumber());
};
