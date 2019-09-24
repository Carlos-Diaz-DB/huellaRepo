APP.service('documentService', function ($q, factory) {
    const SELF = this;
    const PATH = "documentos";
    const PATHPDF = "documentoPDF";
    const PATH_DOS = "documentosUser";
    const PATH2 = "busqueda"; //Para buscar un ciudadano
    const DOCS = "files";

    SELF.get = () => {
        return $q((success, error) => {
            factory.get(PATH).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.getFile = (filename) => {
        return $q((success, error) => {
            factory.getBinarioFile(DOCS+'/'+filename).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.getBusquedaC = (filtro, dato) => {
        return $q((success, error) => {
            factory.getBusqueda(PATH2, filtro, dato).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.getDocsById = (idUser) => {
        return $q((success, error) => {
            factory.getById(PATH_DOS, idUser).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.post = (pdf) => {
        return $q((success, error) => {
            factory.post(PATH, pdf).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.postFile = (pdf) => {
        return $q((success, error) => {
            factory.postFile(PATHPDF, pdf).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.put = (pdf) => {
        return $q((success, error) => {
            factory.put(PATH, pdf).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    };

    SELF.delete = (pdf) => {
        return $q((success, error) => {
            factory.delete(PATH, pdf).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
});