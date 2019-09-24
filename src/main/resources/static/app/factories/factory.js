APP.factory("factory", function ($http, $q) {
    return {
        get: function (url) {
            return $http({
                url: '/' + url,
                method: 'GET'
            }).then(
                (success) => {
                    return success.data;
                },
                // el error no lo podemos controlar,  ya que nose sabe dcomo se va a retornar 
                (error) => {
                    return $q.reject(error)
                }
            )
        },
        getBinarioFile: function (url) {
            return $http({
                url: '/' + url,
                method: 'GET',
                headers: {
                    'Content-Type': 'application/pdf'
                }
                , responseType: 'arraybuffer'
            }).then(
                (success) => {
                    return success.data;
                },
                // el error no lo podemos controlar,  ya que nose sabe dcomo se va a retornar
                (error) => {
                    console.log("E: ",error)
                    return $q.reject(error)
                }
            )
        },
        getById: function (url, data) {
            return $http({
                url: '/' + url+"?id="+data,
                method: 'GET',
                // headers: {
                //     'Content-Type': 'application/json'
                // },
                // params: {
                //     "id": data
                // }
            }).then(
                (success) => {
                    return success.data;
                },
                (error) => {
                    return $q.reject(error)
                }
            )
        },
        getBusqueda: function (url, filtro, dato) {
            return $http({
                url: '/' + url + "?ciudadanoJSON=" + dato + "&filtro=" + filtro,
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(
                (success) => {
                    return success.data;
                },
                // el error no lo podemos controlar,  ya que nose sabe dcomo se va a retornar 
                (error) => {
                    return $q.reject(error)
                }
            )
        },
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
        },
        postFile: function (url, data) {
            return $http({
                url: '/' + url,
                method: 'POST',
                dataType: 'text',
                processData: false,
                contentType: false,
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
        },
        postSubir: function (url, data) {
            return $http({
                url: '/' + url,
                method: 'POST',
                data: data,
                dataType: 'text'
                // headers: {
                //     // 'Content-Type': undefined
                //     "Content-type": "application/x-www-form-urlencoded",

                // }
            }).then(
                (success) => {
                    return success.data;
                },
                // el error no lo podemos controlar,  ya que nose sabe dcomo se va a retornar 
                (error) => {
                    return $q.reject(error)
                }
            )
        },
        put: function (url, data) {
            return $http({
                url: '/' + url,
                method: 'PUT',
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
        },
        delete: function (url, data) {
            return $http({
                url: '/' + url,
                method: 'DELETE',
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