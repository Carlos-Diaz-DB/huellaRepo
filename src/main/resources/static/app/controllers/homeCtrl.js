APP.controller("HomeControlador", function ($scope) {

    $scope.home = null;

    $scope.verVista = function () {
        $scope.home = {};
    }

    $scope.verHome = function () {
        $scope.home = null;
    }
});