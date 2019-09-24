APP.controller('fotografiaCtrl', function ($scope, $location, fotografiaService) {
        const tieneSoporteUserMedia = () =>
            !!(navigator.getUserMedia || (navigator.mozGetUserMedia || navigator.mediaDevices.getUserMedia) || navigator.webkitGetUserMedia || navigator.msGetUserMedia)
        const _getUserMedia = (...arguments) =>
            (navigator.getUserMedia || (navigator.mozGetUserMedia || navigator.mediaDevices.getUserMedia) || navigator.webkitGetUserMedia || navigator.msGetUserMedia).apply(navigator, arguments);

        // Declaramos elementos del DOM
        const $video = document.querySelector("#video"),
            $canvas = document.querySelector("#canvas"),
            // $estado = document.querySelector("#estado"),
            $boton = document.querySelector("#boton"),
            $listaDeDispositivos = document.querySelector("#listaDeDispositivos");

        let stream;
        $scope.subirFotografia = { }

        $scope.submitBusqueda = function (valid, correo) {
            if (valid) {
                $scope.verTabla = {};
                console.log("Correo introducido: ", correo);

                fotografiaService.getBusquedaC(true, correo).then((data) => {
                    if (data) {
                        // console.log("data: ",data)
                        $scope.usuario = data;
                        console.warn('obj', $scope.usuario)
                        fotografiaService.getById($scope.usuario.idCiudadano).then((data) => {
                            if (data) {
                                // console.log("data Docs: ",data)
                                $scope.fotos = data;
                            }
                        }, (reject) => {
                            console.log("getDocsById: ", reject.data.message);
                        })

                    }
                }, (reject) => {
                    Swal.fire({
                        position: 'center',
                        type: 'error',
                        title: 'Error',
                        text: 'Error: ' + reject.data.message,
                        showConfirmButton: true
                    })
                });
            }
        }

        $scope.activarWebcam = () => {
            $scope.verFoto = {};
            iniciar();
        }

        $scope.cancelarFoto = () => {
            let track = stream.getTracks()[0];
            track.stop();
            $scope.verFoto = null;
        }

        $scope.fileUpload = (e) => {
            const FORM_SUBIR = document.getElementById('canvas');
            let img = JSON.stringify(FORM_SUBIR.toDataURL());
            let soloBase64 = img.split(',')[1];
            let today = new Date();
            let date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
            let time = today.getHours() + "_" + today.getMinutes() + "_" + today.getSeconds();
            let dateTime = date + '--' + time;
            let nombreEImgBase = $scope.usuario.idCiudadano + '--' + $scope.usuario.nombre + '--' + dateTime + ',' + soloBase64

            fotografiaService.postSubirFoto(nombreEImgBase).then((data) => {
                if (data) {
                    // $scope.subirFotografia.fechaActualizacion = '2010-12-03T11:30'
                    // $scope.subirFotografia.fechaCreacion = '2010-12-03T11:30'
                    $scope.subirFotografia.nombre = nombreEImgBase.split(',')[0] + ".png";
                    $scope.subirFotografia.path = $scope.subirFotografia.nombre;
                    $scope.subirFotografia.status = '1';
                    $scope.subirFotografia.ciudadano = $scope.usuario;
                    // console.log("TCL: $scope.fileUpload -> nombre", $scope.subirFotografia)
                    fotografiaService.post($scope.subirFotografia).then((dataa) => {
                    // console.log("TCL: $scope.fileUpload -> dataa", dataa)
                        
                        if (dataa) {
                            // console.log("TCL: $scope.fileUpload -> subirFotografia")
                            $scope.cancelarFoto();
                            $scope.obtenerFotos()

                            $('#exampleModalCenter').modal('hide');
                            Swal.fire({
                                position: 'center',
                                type: 'success',
                                title: 'Fotografia registrada',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            // fotografiaService.getById($scope.usuario.idCiudadano)
                        }
                    }, (reject) => {
                        console.log("ERROR: al registrar foto", reject);
                    })
                }
            }, (reject) => {
                console.log("ERROR: foto no guardado", reject);
            })




        }

        const limpiarSelect = () => {
            for (let x = $listaDeDispositivos.options.length - 1; x >= 0; x--)
                $listaDeDispositivos.remove(x);
        };
        const obtenerDispositivos = () => navigator
            .mediaDevices
            .enumerateDevices();

        // La función que es llamada después de que ya se dieron los permisos
        // Lo que hace es llenar el select con los dispositivos obtenidos
        const llenarSelectConDispositivosDisponibles = () => {

            limpiarSelect();
            obtenerDispositivos()
                .then(dispositivos => {
                    const dispositivosDeVideo = [];
                    dispositivos.forEach(dispositivo => {
                        const tipo = dispositivo.kind;
                        if (tipo === "videoinput") {
                            dispositivosDeVideo.push(dispositivo);
                        }
                    });

                    // Vemos si encontramos algún dispositivo, y en caso de que si, entonces llamamos a la función
                    if (dispositivosDeVideo.length > 0) {
                        // Llenar el select
                        dispositivosDeVideo.forEach(dispositivo => {
                            const option = document.createElement('option');
                            option.value = dispositivo.deviceId;
                            option.text = dispositivo.label;
                            $listaDeDispositivos.appendChild(option);
                        });
                    }
                });
        }

        const iniciar = () => {
            if (!tieneSoporteUserMedia()) {
                alert("Lo siento. Tu navegador no soporta esta característica");
                // $estado.innerHTML = "Parece que tu navegador no soporta esta característica. Intenta actualizarlo.";
                return;
            }
            //Aquí guardaremos el stream globalmente
            // let stream;


            // Comenzamos pidiendo los dispositivos
            obtenerDispositivos()
                .then(dispositivos => {
                    // Vamos a filtrarlos y guardar aquí los de vídeo
                    const dispositivosDeVideo = [];

                    // Recorrer y filtrar
                    dispositivos.forEach(function (dispositivo) {
                        const tipo = dispositivo.kind;
                        if (tipo === "videoinput") {
                            dispositivosDeVideo.push(dispositivo);
                        }
                    });

                    // Vemos si encontramos algún dispositivo, y en caso de que si, entonces llamamos a la función
                    // y le pasamos el id de dispositivo
                    if (dispositivosDeVideo.length > 0) {
                        // Mostrar stream con el ID del primer dispositivo, luego el usuario puede cambiar
                        mostrarStream(dispositivosDeVideo[0].deviceId);
                    }
                });



            const mostrarStream = idDeDispositivo => {
                _getUserMedia({
                        video: {
                            // Justo aquí indicamos cuál dispositivo usar
                            deviceId: idDeDispositivo,
                        }
                    },
                    (streamObtenido) => {
                        // Aquí ya tenemos permisos, ahora sí llenamos el select,
                        // pues si no, no nos daría el nombre de los dispositivos
                        llenarSelectConDispositivosDisponibles();

                        // Escuchar cuando seleccionen otra opción y entonces llamar a esta función
                        $listaDeDispositivos.onchange = () => {
                            // Detener el stream
                            if (stream) {
                                stream.getTracks().forEach(function (track) {
                                    track.stop();
                                });
                            }
                            // Mostrar el nuevo stream con el dispositivo seleccionado
                            mostrarStream($listaDeDispositivos.value);
                        }

                        // Simple asignación
                        stream = streamObtenido;

                        // Mandamos el stream de la cámara al elemento de vídeo
                        $video.srcObject = stream;
                        $video.play();

                        //Escuchar el click del botón para tomar la foto
                        //Escuchar el click del botón para tomar la foto
                        $boton.addEventListener("click", function () {

                            //Pausar reproducción
                            $video.pause();

                            //Obtener contexto del canvas y dibujar sobre él
                            let contexto = $canvas.getContext("2d");
                            $canvas.width = $video.videoWidth;
                            $canvas.height = $video.videoHeight;
                            contexto.drawImage($video, 0, 0, $canvas.width, $canvas.height);

                            // let foto = $canvas.toDataURL(); //Esta es la foto, en base 64
                            // // $estado.innerHTML = "Enviando foto. Por favor, espera...";
                            // fetch("./guardar_foto.php", {
                            //         method: "POST",
                            //         body: encodeURIComponent(foto),
                            //         headers: {
                            //             "Content-type": "application/x-www-form-urlencoded",
                            //         }
                            //     })
                            //     .then(resultado => {
                            //         // A los datos los decodificamos como texto plano
                            //         return resultado.text()
                            //     })
                            //     .then(nombreDeLaFoto => {
                            //         // nombreDeLaFoto trae el nombre de la imagen que le dio PHP
                            //         console.log("La foto fue enviada correctamente");
                            //         // $estado.innerHTML = `Foto guardada con éxito. Puedes verla <a target='_blank' href='./${nombreDeLaFoto}'> aquí</a>`;
                            //     })

                            //Reanudar reproducción
                            $video.play();
                        });
                    }, (error) => {
                        console.log("Permiso denegado o error: ", error);
                        // $estado.innerHTML = "No se puede acceder a la cámara, o no diste permiso.";
                    });
            }
        }

        $scope.obtenerFotos = function () {
            if (1) {
    
                fotografiaService.getById($scope.usuario.idCiudadano).then(
                    (data) => {
                        console.log("Ctrl: ", data);
                        $scope.fotos = data;
                    },
                    (reject) => {
                        console.log("Ctrl: fallo get", reject); 
                    });
            }
        }

        $scope.editar = function (foto) {
            // $scope.foto.fechaActualizacion = null
            delete foto['fechaActualizacion']
            delete foto['fechaCreacion']
            // console.log("TCL: $scope.editar -> foto", foto)
            // delete foto.fechaCreacion


            fotografiaService.put(foto).then(
                (data) => {
                    $scope.obtenerFotos()
                    // console.log("Ctrl: ", data);
                    // document.getElementById("myForm").reset();
                    // $scope.obtenerTareas()
                    // $scope.task = null
                },
                (reject) => {
                    console.log("Ctrl: ", reject);
                });
        }

        $scope.borrar = function (foto) {
            // $scope.taskEliminar = task
            delete foto['fechaActualizacion']
            delete foto.fechaCreacion
    
            fotografiaService.delete(foto).then(
                (data) => {
                    console.log("Ctrl: delete ->", data);
                    $scope.obtenerFotos()
                },
                (reject) => {
                    console.log("Ctrl: ", reject.data.message.toString());
                });
        }
    }
)