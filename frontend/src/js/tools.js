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

export { getCookie };