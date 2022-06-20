export function downAppImage(){
    return request({
        url: 'http://localhost:8080/room/qrcode',
        method:'get',
        responseType:"blob"
    })
}