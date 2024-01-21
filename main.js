document.getElementById('carlBox').addEventListener('mouseover', function() {
    showAdditionalInfo('carlBox');
});

document.getElementById('carlBox').addEventListener('mouseout', function() {
    hideAdditionalInfo('carlBox');
});

function showAdditionalInfo(boxId) {
    var additionalInfo = document.getElementById(boxId).getElementsByClassName('additional-info')[0];
    additionalInfo.style.maxHeight = additionalInfo.scrollHeight + 'px';
    additionalInfo.addEventListener('transitionend', function() {
        additionalInfo.style.maxHeight = 'none';
    }, { once: true });
}

function hideAdditionalInfo(boxId) {
    var additionalInfo = document.getElementById(boxId).getElementsByClassName('additional-info')[0];
    additionalInfo.style.maxHeight = '0';
}
