const callSolicitationPage = {
    goToHome: () => {
        cy.visit('/home');
    },

    clickInButton: () => {
        cy.get('#btn-solicitation').click();
    }, 
    checkActualRoute: route => {
        cy.location('pathname').should('eq', route);
    }
};
module.exports = callSolicitationPage;