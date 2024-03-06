document.addEventListener('DOMContentLoaded', (event) => {

    var radios = document.querySelectorAll('input[type=radio][name="categories"]');


    function hideAllDivs() {
        var divs = document.querySelectorAll('div[id]');
        divs.forEach(function(div) {
            div.style.display = 'none';
        });
    }


    function showDiv(id) {
        var div = document.querySelector('#' + id);
        if (div) {
            div.style.display = 'block';
        }
    }


    radios.forEach(function(radio) {
        radio.addEventListener('change', function() {

            hideAllDivs();


            showDiv(this.value);
        });
    });


    hideAllDivs();


    var checkedRadio = document.querySelector('input[type=radio][name="categories"]:checked');
    if (checkedRadio) {
        showDiv(checkedRadio.value);
    }
});