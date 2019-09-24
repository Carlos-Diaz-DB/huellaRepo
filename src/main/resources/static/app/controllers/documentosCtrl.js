APP.controller("DocumentoControlador", function ($scope, documentService, $window) {

    function _getUserMedia() {
        return (navigator.getUserMedia || (navigator.mozGetUserMedia || navigator.mediaDevices.getUserMedia) || navigator.webkitGetUserMedia || navigator.msGetUserMedia).apply(navigator, arguments);
    }

    $scope.opcionIdentificador = "Identificador";
    const video = document.getElementById('video');
    const canvas = document.getElementById('canvas');
    const errorMsgElement = document.querySelector('span#errorMsg');
    let stream;
    $scope.datoCiudadano = null;
    $scope.registroPdf = {};
    $scope.cargando = false;
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000
    });

    const constraints = {
        audio: false,
        video: {
            facingMode: "user"
        }
    };

    $scope.descargarDocumento = function (filename) {
        documentService.getFile(filename).then((data) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(file);
            link.download = filename;
            link.click();
        }, (reject) => {
            console.log("ERROR: ", reject);
        })
    };

    $scope.verDocumento = function (filename) {
        documentService.getFile(filename).then((data) => {
            let file = new Blob([data], {type: 'application/pdf'});
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = function () {
                let base64data = reader.result;
                let win = window.open();
                win.document.write('<iframe src="' + base64data + '" frameborder="0" style="border:0; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%;" allowfullscreen></iframe>');
            }
        }, (reject) => {
            console.log("ERROR: ", reject);
        })
    };

    $scope.borrarDocumento = function (pdf) {
        Swal.fire({
            title: 'Eliminar?',
            text: "Esta accion no se puede revertir!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Eliminar!'
        }).then((result) => {
            if (result.value) {
                documentService.put(pdf).then((data) => {
                    if (data) {
                        $scope.submitDocumento(true, $scope.opcionIdentificador === "Identificador" ? $scope.usuario.idCiudadano : $scope.usuario.correoElectronico);
                        Toast.fire({
                            type: 'success',
                            title: '¡Borrado correctamente!'
                        })
                    }
                }, (reject) => {
                    console.log("reject", reject);
                });
            }
        })
    };

    $scope.buscarCiudadano = function (datoCiudadano) {
        $scope.busquedaPor = $scope.opcionIdentificador !== "Identificador";
        documentService.getBusquedaC($scope.busquedaPor, datoCiudadano).then((data) => {
            if (data !== "") {
                if (data) {
                    $scope.usuario = data;
                    documentService.getDocsById($scope.usuario.idCiudadano).then((data) => {
                        if (data) {
                            $scope.documentos = data;
                        }
                    }, (reject) => {
                        console.log("getDocsById: ", reject.data.message);
                    });
                }
            } else {
                $scope.verListaArchivos = null;
                $scope.dato = null;
                $scope.verAgragarArchivo = null;
                $scope.datoCiudadano = null;
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: 'Error',
                    text: 'Ciudadano NO encontrado',
                    showConfirmButton: true
                })
            }
            $scope.aux = data;
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
    
    $scope.guardarDocumento = function () {
    	$scope.cargando = true;
        let pdf = new jsPDF();
        let f = new Date();
        let fecha = f.getDate() + "-" + f.getMonth() + "-" + f.getFullYear();
        let imgData = canvas.toDataURL("image/JPEG").replace("image/JPEG", "image/octet-stream");
        pdf.setFontSize(14);
        pdf.text(35, 25, 'Nombre: ' + $scope.usuario.nombre + " " + $scope.usuario.apellidoPaterno + " " + $scope.usuario.apellidoMaterno);
        pdf.text(35, 30, 'Correo electronico: ' + $scope.usuario.correoElectronico);
        pdf.addImage(imgData, 'JPEG', 5, 40, 200, 200);
        pdf.setFontSize(20);
        let file = pdf.output('datauristring');
        $scope.nombrePDF = "pdf_" + $scope.usuario.nombre + "-" + $scope.usuario.apellidoPaterno + "-" + $scope.usuario.apellidoMaterno + "-" + f.getFullYear() + f.getMonth() + f.getDay() + "_" + f.getHours() + f.getMinutes() + f.getSeconds();
        file = file + "base64," + $scope.nombrePDF;// Esto no se hace :/

        documentService.postFile(file, $scope.nombrePDF).then((data) => {
            if (data) {
                $scope.registroPdf.nombre = $scope.nombrePDF + ".pdf";
                $scope.registroPdf.path = $scope.nombrePDF;
                $scope.registroPdf.status = '1';
                $scope.registroPdf.ciudadano = $scope.usuario;
                documentService.post($scope.registroPdf).then((data) => {
                    if (data) {
                        $scope.detenerCamara();
                        $('#exampleModal').modal('hide');
                        $scope.cargando = false;
                        Swal.fire({
                            position: 'center',
                            type: 'success',
                            title: 'Archivo registrado',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        $scope.submitDocumento(true, $scope.opcionIdentificador === "Identificador" ? $scope.usuario.idCiudadano : $scope.usuario.correoElectronico);
                    }
                }, (reject) => {
                    console.log("ERROR: al registrar documento", reject);
                })
            }
        }, (reject) => {
            console.log("ERROR: File no guardado", reject);
        })
    }

    $scope.editDocumento = function (pdf) {
        $scope.pdfEdit = pdf;
    }

    $scope.actualizarDocumento = function () {
        $scope.pdfEdit.accion = "editar";
        documentService.put($scope.pdfEdit).then((data) => {
            if (data) {
                $scope.submitDocumento(true, $scope.opcionIdentificador === "Identificador" ? $scope.usuario.idCiudadano : $scope.usuario.correoElectronico);
                $('#modalEdit').modal('hide');
                Toast.fire({
                    type: 'success',
                    title: '¡actualizado correctamente!'
                })
            }
        }, (reject) => {
            console.log("reject", reject);
        });
    };

    $scope.submitDocumento = function (valid, dato) {
        if (valid) {
            if ($scope.opcionIdentificador === "Identificador") {
                $scope.verListaArchivos = {};
                $scope.busquedaPor = false;
                $scope.buscarCiudadano(dato);
            } else {
                $scope.verListaArchivos = {};
                $scope.busquedaPor = true;
                $scope.buscarCiudadano(dato);
            }
        }
    };

    $scope.buscarOtro = function () {
        $scope.verListaArchivos = null;
        $scope.dato = null;
        $scope.verAgragarArchivo = null;
        $scope.datoCiudadano = null;
        if(stream){        	
        	$scope.detenerCamara();
        }
    };

    $scope.agregarArchivo = function () {
        $scope.verAgragarArchivo = {};
        $scope.iniciarCamara();
    };

    $scope.iniciarCamara = async function () {
         
        try {
            stream = await navigator.mediaDevices.getUserMedia(constraints);
            $scope.handleSuccess(stream);
        } catch (e) {
            console.log("Error: ", e.toString());
            errorMsgElement.innerHTML = `navigator.getUserMedia error:${e.toString()}`;
        }
    };

    $scope.handleSuccess = function (stream) {
        window.stream = stream;
        video.srcObject = stream;
    };

    $scope.canvas = function () {
        let context = canvas.getContext('2d');
        context.drawImage(video, 0, 0, 640, 480);
        let link = document.getElementById('link');
        link.setAttribute('href', canvas.toDataURL("image/png").replace("image/png", "image/octet-stream"));
    };

    $scope.detenerCamara = function () {
        const track = stream.getTracks()[0];
        track.stop();
        $scope.verAgragarArchivo = null;
    };


});