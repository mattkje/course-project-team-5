export function currency(API_URL){
    document.getElementById('currencySelect').innerHTML = '';
    fetch(API_URL + '/currency')
        .then(response => response.json())
        .then(currencies => {
            const select = document.getElementById('currencySelect');
            currencies.forEach(currency => {
                const option = document.createElement('option');
                option.value = currency.code;
                option.text = currency.code + ' - ' + currency.symbol;
                select.appendChild(option);
            });

            const defaultCurrency = setDefaultCurrency();
            if (defaultCurrency) {
                select.value = defaultCurrency;
            }
            document.getElementById('currencySelect').addEventListener('change', function() {
                document.cookie = `defaultCurrency=${this.value}; path=/; max-age=31536000`;
                location.reload();
            });
        })
        .catch(error => console.error('Error:', error));

}

export function setDefaultCurrency() {
    const cookies = document.cookie.split('; ');
    const defaultCurrencyCookie = cookies.find(row => row.startsWith('defaultCurrency='));
    if (defaultCurrencyCookie) {
        return defaultCurrencyCookie.split('=')[1];
    }
}