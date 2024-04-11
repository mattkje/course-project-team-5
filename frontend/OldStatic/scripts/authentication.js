window.onload = function () {
    login();
};

function login() {
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();

        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;

        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password),
        })
            .then(function(response) {
                if (response.ok) {
                    return response.json();
                } else {
                    // If the login failed, display an error message
                    document.getElementById('error').style.display = 'block';
                    throw new Error('Login failed');
                }
            })
            .then(function(data) {
                // Store the token and username in the local storage
                localStorage.setItem('token', data.token);
                localStorage.setItem('username', data.username);

                // If the login was successful, redirect the user to the desired page
                window.location.href = '/';
            })
            .catch(function(error) {
                console.error('Error:', error);
            });
    });
}

