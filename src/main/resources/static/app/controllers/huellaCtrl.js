APP.controller("huellaCtrl", function ($scope, huellaService) {

   $scope.hand = {};
    $scope.ciudadano = {};

    $scope.agregarHuella = function (nombre_huella) {
    $scope.hand.huella = huella64;
        $scope.hand.idHuella = null;
   if (nombre_huella){
    console.log($scope.hand);
            huellaService.post($scope.hand).then((respuesta) => {
                if (respuesta === true) {
                  $scope.hand = {};
                    Swal.fire(
                        '¡OK!',
                        '¡Se registro correctamente!',
                        'success'
                    )
                    $scope.hand = null;
                } else {
                    console.log("ERROR");
                }
            }, (reject) => {
                console.log("Ctrl: ", reject);
            })
        } else {
            Swal.fire(
                '¡error!',
                '¡Indique el dedo!',
                'error'
            )
        }
    }


    $scope.obtenerHuella = function () {
        huellaService.getById($scope.ciudadano.id).then((data) => {
            $scope.listarHuellas = data;
        }, (reject) => {
            console.log("Ctrl: ", reject);
        });
    }

});