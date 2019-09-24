const APP = angular.module("poc", ["ngRoute"]);

APP.config(function ($routeProvider) {
    $routeProvider
        .when("/Documentos", {
            templateUrl: "vistas/documento.html",
            controller: "DocumentoControlador"
        })
        .when("/Fotografia", {
            templateUrl: "vistas/fotografia.html"
        })
        .when("/Huella", {
            templateUrl: "vistas/huellaDigital.html"
        })
        .when("/Correo", {
            templateUrl: "vistas/correo.html"
        })
        .when("/Ciudadano", {
            templateUrl: "vistas/ciudadano.html"
        })
        .otherwise({
            redirectTo: "/"
        });
});