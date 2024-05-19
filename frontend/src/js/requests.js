import {redirectTo} from "@/js/navigation";

const API_BASE_URL = `https://localhost:8443/api`;
import {getCookie, setCookie} from "../js/tools.js";
import {doLogout} from "@/js/authentication";

export async function sendApiRequest(method, url, callback, requestBody, errorCallback) {
    let parameters = { method: method, headers: constructRequestHeaders(method)};
    if (requestBody) {
        parameters.body = JSON.stringify(requestBody);
    }

    try {
        const response = await fetch(API_BASE_URL + url, parameters);
        const responseText = await response.text();

        if (response.status === 200) {
            let responseJson = "";
            if (responseText) {
                try {
                    responseJson = JSON.parse(responseText);
                } catch (error) {
                    console.error("Error parsing JSON: ", error);
                }
            }
            console.log("Status code is 200, calling callback function");
            callback(responseJson);
        } else if (errorCallback) {
            console.log("Status code is not 200, calling errorCallback function");
            errorCallback(responseText);
        } else {
            console.log("Status code is not 200, no errorCallback function provided");
            console.error(responseText);
        }
    } catch (error) {
        if (errorCallback) {
            errorCallback(error);
        }
    }
}

function constructRequestHeaders(method) {
    let headers = {};

    if (method.toLowerCase() !== "get") {
        headers["Content-Type"] = "application/json";
    }

    const jwtToken = getCookie("jwt");
    if (jwtToken) {
        headers["Authorization"] = "Bearer " + jwtToken;
    }
    return headers;
}

function sendTokenRefreshRequest(successCallback, errorCallback) {
    const refreshToken = getCookie("refresh_token");
    if (!refreshToken) {
        errorCallback("No refresh token found");
        return;
    }

    sendApiRequest("POST", "/users/refresh-token", function (jwtResponse) {
        setCookie("jwt", jwtResponse.jwt);
        successCallback();
    },
        {refreshToken: refreshToken},
        errorCallback
    );
}

function refreshToken() {
    const refreshToken = getCookie("refresh_token");
    if (!refreshToken) {
        console.log("No refresh token found");
        return;
    }

    sendApiRequest("POST", "/users/refresh-token", function (jwtResponse) {
        setCookie("jwt", jwtResponse.jwt, 1);
    },
        {refreshToken: refreshToken},
        function (error) {
            console.log("Error refreshing token: " + error);
        }).catch((error) => {
            console.error("Error: ", error);
    });
}

setInterval(refreshToken, 15 * 60 * 1000);

export { sendTokenRefreshRequest };