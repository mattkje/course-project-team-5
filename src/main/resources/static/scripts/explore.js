window.onload = function() {
    loadComponent('footer');
    loadComponent('menubar');
};

function loadComponent(component) {
    fetch('components/' + component + '.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById(component).innerHTML = data;
        });
}



