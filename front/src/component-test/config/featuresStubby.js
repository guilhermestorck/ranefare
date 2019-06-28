// Configure features toggles
const stubby = require('./stubby');

const FEATURE_URL = '/features';

var featuresType = {
	ADYEN_CARD_ENCRYPTED: 'adyen-card-encrypted'
};

const createFeature = function (feature, enable) {
	const response = { enable: enable };
	const path = `/${feature}`;
	stubby.addFeature(FEATURE_URL, path, response);
};

module.exports = {
	createFeature: createFeature,
	type: featuresType
};
