import {getAuthenticatedUser} from "@/js/authentication";

const BASE_URL = 'http://localhost:8081';

function redirectTo(frontendUrl) {
    window.location = BASE_URL + frontendUrl;
}

function checkLoginStatus() {
    const user = getAuthenticatedUser();
    return !!user;
}



export { redirectTo };
export { checkLoginStatus };