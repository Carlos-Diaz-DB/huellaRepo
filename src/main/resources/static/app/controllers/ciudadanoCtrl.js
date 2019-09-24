APP.controller('ciudadanoCtrl', function ($scope, ciudadanoService) {
    $scope.ciudadano = null;
    $scope.verSeccion = function () {
        $scope.ciudadano = {};
    }
    $scope.cancelar = function () {
        $scope.ciudadano = null;
    }
    $scope.obtenerCiudadano = function () {
        ciudadanoService.get().then((data) => {
            $scope.listaCiudadano = data;
        }, (reject) => {
            console.log("Ctrl: ", reject);
        });
    }
    $scope.submitForm = function (esValido) {
        if (esValido) {
            if ($scope.ciudadano.idCiudadano) {
                //aqui se actualiza la tarea
                $scope.editarCiudadano();

            } else {
                //aqui se registra
                $scope.enviarCiudadano();
            }
        } else {
            Swal.fire(
                '¡error!',
                '¡Faltan Datos!',
                'error'
            )
        }
    }
    $scope.enviarCiudadano = function () {
        ciudadanoService.post($scope.ciudadano).then((respuesta) => {
            if (respuesta === true) {
                $scope.ciudadano = {};
                Swal.fire(
                    '¡OK!',
                    '¡Se registro correctamente!',
                    'success'
                )
                $scope.ciudadano = null;
                $scope.obtenerCiudadano();
            } else {
                console.log("ERROR");
            }
        }, (reject) => {
            console.log("Ctrl: ", reject);
        });

    }
    $scope.iniciarEdicion = function (ciudadano) {
        $scope.ciudadano = ciudadano;
        //        $scope.verSeccion('Editar');
    }
    $scope.editarCiudadano = function () {
        ciudadanoService.put($scope.ciudadano).then((respuesta) => {
            if (respuesta === true) {
                Swal.fire(
                    '¡OK!',
                    '¡Se edito correctamente!',
                    'success'
                )
                $scope.ciudadano = null;
                $scope.obtenerCiudadano();
            } else {
                console.log("ERROR");
            }
        }, (reject) => {
            console.log("Ctrl: ", reject);
        });

    }
    $scope.borrarCiudadano = function (ciudadano) {
        Swal.fire({
            title: '¿Estas seguro?',
            text: "¡Esta accion es permante!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si, Eliminar!'
        }).then((result) => {
            if (result.value) {
                ciudadanoService.delete(ciudadano).then((respuesta) => {
                    if (respuesta === true) {
                        Swal.fire(
                            'Eliminado!',
                            'El ciudadano se borro correctamente .',
                            'success'
                        )
                        $scope.ciudadano = null;
                        $scope.obtenerCiudadano();
                    } else {
                        console.log("ERROR");
                    }
                }, (reject) => {
                    console.log("Ctrl: ", reject);
                });
            }
        })
    }
    const initController = function () {
        $scope.obtenerCiudadano();
    }
    angular.element(document).ready(function () {
        initController();
    })
})