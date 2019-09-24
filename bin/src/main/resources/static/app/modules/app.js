const app = angular.module("poc", ["ngRoute"]);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/Documentos", {
            templateUrl: "vistas/documento.html"
        })
        .when("/Fotografia", {
            templateUrl: "vistas/fotografia.html"
        })
        .when("/Huella", {
            templateUrl: "vistas/huellaDigital.html"
        })
        .otherwise({
            redirectTo: "/"
        });
});