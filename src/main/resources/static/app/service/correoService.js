APP.service('correoService', function ($q, correoFactory) {
    const SELF = this;
    const PATH = 'Correo';

    SELF.post = (correo) => {
        return $q((success, error) => {
            correoFactory.post(PATH, correo).then(
                (resolve) => {
                    success(resolve)
                },
                (reject) => {
                    error(reject)
                })
        })
    }
})