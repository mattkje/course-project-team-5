import {getAuthenticatedUser} from "@/js/authentication";

//biggie biggie biggie, cant you see... never hard code these ip's in
const BASE_URL = 'https://10.22.165.115';

function redirectTo(frontendUrl) {
    window.location = BASE_URL + frontendUrl;
}

function checkLoginStatus() {
    const user = getAuthenticatedUser();
    return !!user;
}



export { redirectTo };
export { checkLoginStatus };