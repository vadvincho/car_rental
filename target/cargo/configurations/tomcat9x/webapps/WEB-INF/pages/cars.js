var xhr = new XMLHttpRequest()
xhr.open(
    'GET',
    'http://localhost:8081/cars',
    true
)
xhr.send()

xhr.onreadystatechange = function() {
    if (xhr.readyState != 4) {
        return
    }

    if (xhr.status === 200) {
        console.log('result', xhr.responseText)
    } else {
        console.log('err', xhr.responseText)
    }
}