const iubi = new WebSocket('ws://127.0.0.1:2015');
var huella64 = null;
const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000
                });
    iubi.onerror = function (error) {
            Ext.Msg.info({
                ui      : 'warning',
                title   : 'UI',
                html    : 'El servicio del Lector U Are U 4500 Digital Persona esta inactivo',
                iconCls : '#Error' });
        };

        function loadDevice() {
             local,products
             emit('checkin');
             emit('connectserver', { type: String('products') });
              emit('register', { user: '12345678', finger: '1' });
            }

            iubi.onmessage = function (evt) {
        var data;
        eval(evt.data);
        if(data.type == "connect"){
             Toast.fire({
                       type: 'success',
                        title: '¡Se ha conectado el lector de huella!'
                        })
        }else if(data.type == "disconnect"){
                Toast.fire({
                        type: 'error',
                        title: '¡El lector de huella se ha desconectado!'
                        })
        }
        console.log(data.payload[0].data);
        huella64 = data.payload[0].data;


       }
