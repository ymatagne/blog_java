'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('my blog', function() {

  beforeEach(function() {
    browser().navigateTo('../../webapp/index.html');
  });


  describe('index du blog', function() {

    beforeEach(function() {
      browser().navigateTo('#/');
    });


    it('nous verifions la precense de la liste des articles', function() {
      expect(element('[ng-view] h2').text()).
        toMatch(/Liste des articles/);
    });

  });

});
