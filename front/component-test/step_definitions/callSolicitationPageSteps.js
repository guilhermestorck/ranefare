import callSolicitationPage from '../page_objects/callSolicitationPage.page'

Given(/^I open the home page$/, () => {
    callSolicitationPage.goToHome();
});

When(/^I click on new solicitation button$/, () => {
    callSolicitationPage.clickInButton();
});
Then(/^I expect to be in (.*)$/, (route) => {
    callSolicitationPage.checkActualRoute(route)
});