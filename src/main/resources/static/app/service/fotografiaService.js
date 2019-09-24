APP.service('fotografiaService', function ($q, factory) {
    const SELF = this;
    // nombre del restController
    const PATH = 'foto';
    const PATH_FOTOS = 'fotoCiudadano';
    const PATH_CIUDADANO = 'busqueda';
    // const PATH_SUBIR = 'api/upload'
    const PATH_SUBIR = 'subirFoto'
    // BUSQUEDA CIUDADANO
    SELF.getBusquedaC = (filtro, dato) => {
        return $q((success, error) => {
            factory.getBusqueda(PATH_CIUDADANO, filtro, dato).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }

    // FOTOGRAFIAS DE CADA CIUDADANO
    SELF.getById = (id) => {
        return $q((success, error) => {
            factory.getById(PATH_FOTOS, id).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
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
    }
    // SUBIR FOTOGRAFIA DESDE MODAL
    SELF.postSubirFoto = (foto) => {
        return $q((success, error) => {
            factory.postSubir(PATH_SUBIR, foto).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
    // recibe url y data
    SELF.post = (usuario) => {
        return $q((success, error) => {
            factory.post(PATH, usuario).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
    SELF.put = (usuario) => {
        return $q((success, error) => {
            factory.put(PATH, usuario).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                }
            )
        })
    }
    SELF.delete = (usuario) => {
        return $q((success, error) => {
            factory.delete(PATH, usuario).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                }
            )
        })
    }
    // realizar conttrolador y terminar el REST
})