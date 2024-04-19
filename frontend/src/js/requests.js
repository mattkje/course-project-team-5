import {redirectTo} from "@/js/navigation";

const API_BASE_URL = `http://localhost:8080/api`;
import { getCookie } from "../js/tools.js";

async function sendApiRequest(method, url, callback, requestBody, errorCallback) {
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
                responseJson = JSON.parse(responseText);
            }
            callback(responseJson);
        } else if (response.status === 500) {
            redirectTo("/login-failure");
        } else if (errorCallback) {
            errorCallback(responseText);
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

export { sendApiRequest };