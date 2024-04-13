import {getCookie, setCookie} from "@/js/tools";
import {sendApiRequest} from "@/js/requests";

function getAuthenticatedUser() {
    let user = null;
    const username = getCookie("current_username");
    const commaSeperatedRoles = getCookie("current_user_roles");
    if(username && commaSeperatedRoles) {
        const roles = commaSeperatedRoles.split(",");
        user = {
            "username": username,
            "roles": roles
        }
    }
    return user;
}

function sendAuthenticationRequest(username, password, successCallback, errorCallback) {
    const postData = {
        "username": username,
        "password": password
    };
    sendApiRequest(
        "POST", "/users/login",
        function (jwtResponse) {
            setCookie("jwt", jwtResponse.jwt);
            const userData = parseJwtUser(jwtResponse.jwt);
            if (userData) {
                setCookie("current_username", userData.username);
                setCookie("current_user_roles", userData.roles.join(","));
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

export {getAuthenticatedUser};
export {sendAuthenticationRequest};