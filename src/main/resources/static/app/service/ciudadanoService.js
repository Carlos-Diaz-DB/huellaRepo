APP.service('ciudadanoService', function ($q, factory) {
    const SELF = this;
    // nombre del restController
    const PATH = 'Ciudadano';
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
    SELF.post = (ciudadano) => {
        return $q((success, error) => {
            factory.post(PATH, ciudadano).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
    SELF.put = (ciudadano) => {
        return $q((success, error) => {
            factory.put(PATH, ciudadano).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
    SELF.delete = (ciudadano) => {
        return $q((success, error) => {
            factory.delete(PATH, ciudadano).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
})