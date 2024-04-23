import {deleteCookie, getCookie, setCookie} from "@/js/tools";
import {sendApiRequest} from "@/js/requests";
import {redirectTo} from "@/js/navigation";

function getAuthenticatedUser() {
    let user = null;
    const username = getCookie("current_username");
    const commaSeparatedRoles = getCookie("current_user_roles");
    if(username && commaSeparatedRoles) {
        const roles = commaSeparatedRoles.split(",");
        user = {
            "username": username,
            "roles": roles
        }
    }
    return user;
}

export function isOfRoleUser(role) {
    const user = getAuthenticatedUser();
    return user && user.roles.includes(role);
}


function sendAuthenticationRequest(username, password, successCallback, errorCallback) {
    const postData = {
        "username": username,
        "password": password
    };
    sendApiRequest(
        "POST", "/users/login",
        function (jwtResponse) {
            setCookie("jwt", jwtResponse.jwt, 30);
            setCookie("refresh_token", jwtResponse.refreshToken);
            const userData = parseJwtUser(jwtResponse.jwt);
            if (userData) {
                setCookie("current_username", userData.username, 30);
                setCookie("current_user_roles", userData.roles.join(","), 30);
            }
            successCallback();
        },
        postData,
        function (responseText) {
            errorCallback(responseText);
        }
    );
}

function parseJwtUser(jwtString) {
    let user = null;
    const jwtObject = parseJwt(jwtString);
    if (jwtObject) {
        user = {
            "username": jwtObject.sub,
            "roles": jwtObject.roles.map(r => r.authority)
        }
    }
    return user;
}

function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

export function doLogout() {
    console.log("Logging out...");
    deleteAuthorizationCookies();
    redirectTo("/");
}

function deleteAuthorizationCookies() {
    deleteCookie("jwt");
    deleteCookie("current_username");
    deleteCookie("current_user_roles");
    deleteCookie("refresh_token");
}

export {getAuthenticatedUser};
export {sendAuthenticationRequest};
export {parseJwt};