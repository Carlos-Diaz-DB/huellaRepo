APP.factory("correoFactory", function ($http, $q) {
    return {
        post: function (url, data) {
            return $http({
                url: '/' + url,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: data
            }).then(
                (success) => {
                    return success.data;
                },
                // el error no lo podemos controlar,  ya que nose sabe dcomo se va a retornar 
                (error) => {
                    return $q.reject(error)
                }
            )
        }
    }
})