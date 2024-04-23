import {parseJwt} from "@/js/authentication";

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(";");
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function showFormError(errorMessage) {
    showFormResult(errorMessage, "error");
}

function showFormResult(message, resultType) {
    const resultElement = document.getElementById("result-message");
    resultElement.classList.add(resultType);
    resultElement.classList.remove("hidden");
    resultElement.innerText = message;
}

function setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function deleteCookie(cookieName) {
    setCookie(cookieName, "", -1);
}

function isTokenAboutToExpire(jwt) {
    const jwtObject = parseJwt(jwt);
    const currentTime = Math.floor(Date.now() / 1000);
    const expTime = jwtObject.exp;
    const timeLeft = expTime - currentTime;
    return timeLeft < 300;
}

export { getCookie };
export { setCookie };
export { showFormError };
export { deleteCookie };
export { isTokenAboutToExpire };