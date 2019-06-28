const URL = 'http://localhost:8907';
const STUBBY_BODY = {
	request: {
		url: '/',
		method: 'GET',
		query: {}
	},
	response: {
		status: 200,
		headers: {
			'Content-Type': 'application/json'
		},
		body: '{}'
	}
};

const MOCK = 'createdMock';
const FEATURE = 'featureMock';

const getCreatedMocks = function() {
	const createdMocks = Cypress.env(MOCK);
	if (!createdMocks) {
		setCreatedMocks([]);
		return [];
	} else {
		return createdMocks;
	}
};

const setCreatedMocks = function(createdMocksParam) {
	Cypress.env(MOCK, createdMocksParam);
};

const getCreatedFeatureMocks = function() {
	const createdMocks = Cypress.env(FEATURE);
	if (!createdMocks) {
		setCreatedFeatureMocks([]);
		return [];
	} else {
		return createdMocks;
	}
};

const setCreatedFeatureMocks = function(createdMocksParam) {
	Cypress.env(FEATURE, createdMocksParam);
};

const addStubby = function(url, query, body, status, method) {
	STUBBY_BODY.request.url = url;
	STUBBY_BODY.request.query = query;
	STUBBY_BODY.response.body = body;
	STUBBY_BODY.response.status = status;
	STUBBY_BODY.request.method = method;

	deleteMocks(url);
	return cy.request('POST', URL, JSON.stringify(STUBBY_BODY)).then(response => {
		expect(response.status).to.equal(201);
		const id = response.headers['location'].match(/.*\/(\d+)/)[1];
		const mocks = getCreatedMocks();
		mocks.push(id);
		setCreatedMocks(mocks);
		return;
	});
};

const clearMocks = function() {
	getCreatedMocks().forEach(id => {
		cy.request('DELETE', `${URL}/${id}`).then(response => {
			expect(response.status).to.equal(204);
			return;
		});
	});
	setCreatedMocks([]);
};

const addFeature = function(url, featurePath, body) {
	STUBBY_BODY.request.url = url + featurePath;
	STUBBY_BODY.request.query = undefined;
	STUBBY_BODY.response.body = body;
	STUBBY_BODY.response.status = 200;
	STUBBY_BODY.request.method = 'GET';

	deleteMocks(url + featurePath);
	return cy.request('POST', URL, JSON.stringify(STUBBY_BODY)).then(response => {
		expect(response.status).to.equal(201);
		const id = response.headers['location'].match(/.*\/(\d+)/)[1];
		const mocks = getCreatedFeatureMocks();
		mocks.push({ id: id, featurePath: featurePath });
		setCreatedFeatureMocks(mocks);
		return;
	});
};

const deleteMocks = url => {
	return cy.request('GET', URL).then(response => {
		expect(response.status).to.equal(200);
		cy.log(response.body);
		response.body.forEach(mock => {
			if (mock.request.url === url) {
				cy.request('DELETE', `${URL}/${mock.id}`).then(response => {
					deleteMockFromLocalEnv(mock.id);
					expect(response.status).to.equal(204);
				});
			}
		});
	});
};

const deleteMockFromLocalEnv = idToDelete => {
	const filtered = getCreatedMocks().filter(id => {
		return id != idToDelete;
	});
	setCreatedMocks(filtered);
};

module.exports = {
	addStubby: addStubby,
	clearMocks: clearMocks,
	addFeature: addFeature
};
