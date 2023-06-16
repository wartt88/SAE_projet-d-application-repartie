console.log('Hello World');

fetch("").then(function(response) {
    return response.json();
}).then(function(data) {
    console.log(data);
}).catch(function(err) {
    console.log(err);
}