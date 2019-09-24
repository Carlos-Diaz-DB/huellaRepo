APP.controller('correoCtrl', function ($scope, correoService, ciudadanoService) {
    $scope.correo = {};
    $scope.obtenerCiudadano = function () {
        ciudadanoService.get().then((data) => {
            $scope.Ciudadanos = data;
            //console.log($scope.Ciudadanos);
        }, (reject) => {
            console.log("Ctrl: ", reject);
        });
    }
    $scope.enviarCorreoForm = function () {
        $scope.correo.ciudadano = $scope.ciudadano;
        $scope.correo.veces = $scope.veces;
        //console.log("correo: ", $scope.correo);
        //console.log("veces: ", $scope.correoNumero);
        correoService.post($scope.correo).then((respuesta) => {
            //console.log("Respuesta: ", respuesta);
            $scope.correoNumero = null;
            Swal.fire(
                '¡OK!',
                '¡Se envio correctamente!',
                'success'
            )
            $scope.correoNumero = null;
            //$scope.obtenerTarea();
        }, (reject) => {
            console.log("Ctrl: ", reject);
        })
    }
    const initController = function () {
        $scope.obtenerCiudadano();
    }
    angular.element(document).ready(function () {
        initController();
    })
})
