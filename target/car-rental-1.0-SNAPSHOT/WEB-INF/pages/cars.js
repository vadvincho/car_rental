
fetch('http://localhost:8081/cars')
    .then(
        response => {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }

            // Examine the text in the response
            response.json().then(function(data) {
                console.log(data);
            });
        })
    .catch(err => console.log('Fetch Error :-S', err));


// function sendRequest(method, url, body = null) {
//     return new Promise((resolve, reject) => {
//             var xhr = new XMLHttpRequest()
//             xhr.open(method, url, true)
//             xhr.responseType = 'json'
//         xhr.setRequestHeader('Content-Type','application/json')
//             xhr.onload = () => {
//                 if (xhr.status >= 400) {
//                     reject(xhr.response)
//                 } else {
//                     resolve(xhr.response)
//                 }
//             }
//             xhr.onerror = () => {
//                 reject(xhr.response)
//             }
//             xhr.send(JSON.stringify(body))
//         }
//     )
// }

// sendRequest('GET', 'http://localhost:8081/cars')
//     .then(data => console.log(data))
//     .catch(err => console.log(err))

// xhr.onreadystatechange = function() {
//     if (xhr.readyState != 4) {
//         return
//     }
//
//     if (xhr.status === 200) {
//         console.log('result', xhr.responseText)
//     } else {
//         console.log('err', xhr.responseText)
//     }
// }

