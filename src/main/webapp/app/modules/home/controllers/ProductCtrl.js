home.controller('ProductCtrl', ['$scope', '$http', '$location', 'CategoryService', '$routeParams', function($scope, $http, $location, CategoryService, $routeParams){
	$scope.goods=[];
	$scope.hi="HI";
	$scope.obj={};
	// $scope.products
	console.log($routeParams.categoryId, $routeParams.subcategoryId);
	$http.get('/Practice/category/'+$routeParams.categoryId+'/subcategory/'+$routeParams.subcategoryId).then(function(response){
		$scope.obj = response.data;
		console.log($scope.obj);
	});
	console.log(CategoryService.categories);
	// Fetching data from server
	// $http.get('http://localhost:8080/Practice/goods/search?name=Sony').then(function(response){
	// 	$scope.goods = response.data;
	// });

	// $http.get('http://');

	// $scope.inputData = $location.search().name;

	// $http.get('http://localhost:8080/Practice/goods/search?name='+$location.search().name).then(function(response){
	// 	$scope.goods = response.data;
	// });
}]);