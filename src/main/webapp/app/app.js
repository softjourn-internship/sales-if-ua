'use strict';

/* App Module */

var phonecatApp = angular.module('phonecatApp', [
  'ngRoute',
  'ngResource',
  'angularModalService',
  // Our app/modules our app/modules
  'home',
  'admin',
  'goods'

]);

phonecatApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    // admin code
    when('/admin/users/:userId/:role',{
          templateUrl: 'app/modules/admin/views/adm-user-page.html'
        }).
        when('/admin/shops/:userId/:role',{
          templateUrl: 'app/modules/admin/views/adm-shops-page.html'
        }).
        when('/admin/statistics/:userId/:role',{
          templateUrl: 'app/modules/admin/views/adm-stat-page.html'
        }).
        when('/user/bucket/:userId/:role',{
          templateUrl: 'app/modules/admin/views/user-bucket-page.html'
        }).
        when('/user/purchases/:userId/:role',{
          templateUrl: 'app/modules/admin/views/user-all-purchases-page.html'
        }).
        when('/user/profile/edit/:userId/:role',{
          templateUrl: 'app/modules/admin/views/edit-user-page.html'
        }).
        when('/shop/orders/:userId/:role',{
          templateUrl: 'app/modules/admin/views/shop-orders-page.html'
        }).
        when('/shop/products/:userId/:role',{
          templateUrl: 'app/modules/admin/views/shop-products-page.html'
        }).
         when('/shop/profile/edit/:userId/:role',{
          templateUrl: 'app/modules/admin/views/shop-admin-edit-profile.html'
        }).
        when('/shop/statistics/:userId/:role',{
            templateUrl: 'app/modules/admin/views/shop-stat-page.html'
        }).
        when('/shop/payment/:userId/:role',{
            templateUrl: 'app/modules/admin/views/shop-payment-page.html'
        }).
        when('/home/service',{
            templateUrl: 'app/modules/admin/views/services-page.html'
        }).

      // Our roots
      when('/home', {
        templateUrl: 'app/modules/home/views/main.html'
      }).
      //when('/home/:categoryId', {
      //  templateUrl: 'app/modules/home/views/chosen.html'
      //}).
      //when('/home/chosen',{
      // templateUrl: 'app/modules/home/views/chshop.html'
      //}).
      when('/stuff', {
        templateUrl: 'app/modules/home/views/stuff.html'
      }).
      when('/stuf/:categoryId/:subcategoryId', {
        templateUrl: 'app/modules/home/views/stuf.html'
      }).
      when('/stuff/:goodId', {
        templateUrl: 'app/modules/goods/views/characteristic-of-product.html'
      }).
      when('/ordering',{
        templateUrl: 'app/modules/goods/views/ordering.html'
      }).
      when('/admin', {
        templateUrl: 'app/modules/admin/views/main.html'
      }).
      when('/character', {
        templateUrl: 'app/modules/goods/views/characteristic-of-product.html'
      }).
      otherwise({
        redirectTo: '/home'
      });
  }]);
