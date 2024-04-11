const BASE_URL = 'http://localhost:8081';

function redirectTo(frontendUrl) {
    window.location = BASE_URL + frontendUrl;
}

export { redirectTo };