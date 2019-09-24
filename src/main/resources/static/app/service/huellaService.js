APP.service('huellaService', function ($q, factory) {
    const SELF = this;
    const PATH = "huella";
    const pathHuella = "huellas"

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

    SELF.getById = (idCiudadano) => {
            return $q((success, error) => {
                factory.getById(pathHuella, idCiudadano).then(
                    (resolve) => {
                        success(resolve)
                    },
                    (reject) => {
                        error(reject)
                    })
            })
        }

    SELF.post = (huella) => {
        return $q((success, error) => {
            factory.post(PATH, huella).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }



})