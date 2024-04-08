window.onload = function () {
    loadComponent('footer');
    loadComponent('menubar');
    populateCoursePage();
    currency();
};

function loadComponent(component) {
    fetch('../components/desktop/' + component + '.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById(component).innerHTML = data;
        });
}

// Function to populate the course page with the course data

function populateCoursePage() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    // Fetch the currency data
    fetch('/api/currency')
        .then(response => response.json())
        .then(currencies => {

            fetch(`/api/courses/${id}`)
                //Checking if course exists
                .then(response => {
                    if (!response.ok) {
                        if (response.status === 404) {
                            //Use custom course not found page?
                            window.location.href = '/404';
                        } else {
                            throw new Error('Network response was not ok');
                        }
                    }
                    return response.json();
                })
                //Continuing if course exists
                .then(data => {

                    //Populating the similar courses box
                    populateCourses('.featured');

                    //Populating the course page
                    document.getElementById('courseCategoryLink').innerText = data.course.category;
                    document.getElementById('courseTitleLink').innerText = data.course.title;
                    document.getElementById('courseDescription').innerText = data.course.description;
                    document.getElementById('courseImage').src = data.course.image || 'media/noImage.svg';
                    document.getElementById('courseTitle').innerText = data.course.title;

                    const sizeIcon = document.createElement('img');
                    sizeIcon.className = 'content-box-icon';
                    sizeIcon.src = 'media/credits.svg';

                    const sizeText = document.createElement('p');
                    sizeText.innerText += `${data.course.courseSize}` + " ECTS Credits";

                    const courseSizeElement = document.getElementById('courseSize');
                    courseSizeElement.appendChild(sizeIcon);
                    courseSizeElement.appendChild(sizeText);

                    // Course duration
                    const durationIcon = document.createElement('img');
                    durationIcon.className = 'content-box-icon';
                    durationIcon.src = 'media/calendar.svg';

                    const durationText = document.createElement('p');
                    const rawDate = `${data.course.closestCourseSession}`;
                    durationText.innerText += formatDate(rawDate);

                    const courseDurationElement = document.getElementById('closestCourseSession');
                    courseDurationElement.appendChild(durationIcon);
                    courseDurationElement.appendChild(durationText);

                    // Course hours per week
                    const hoursIcon = document.createElement('img');
                    hoursIcon.className = 'content-box-icon';
                    hoursIcon.src = 'media/size.svg';

                    const hoursText = document.createElement('p');
                    hoursText.innerText += `${data.course.hoursPerWeek}` + " h/w";

                    const courseHoursElement = document.getElementById('hoursPerWeek');
                    courseHoursElement.appendChild(hoursIcon);
                    courseHoursElement.appendChild(hoursText);

                    // Course certification
                    const certificationIcon = document.createElement('img');
                    certificationIcon.className = 'content-box-icon';
                    certificationIcon.src = 'media/cert.svg';

                    const certificationText = document.createElement('p');
                    certificationText.innerText += `${data.course.relatedCertifications}`;

                    const courseCertElement = document.getElementById('relatedCertifications');
                    courseCertElement.appendChild(certificationIcon);
                    courseCertElement.appendChild(certificationText);

                    // Course difficulty
                    const difficultyIcon = document.createElement('img');
                    difficultyIcon.className = 'content-box-icon';
                    difficultyIcon.src = 'media/level.svg';

                    const difficultyText = document.createElement('p');
                    difficultyText.innerText += `${data.course.level}`;

                    const courseDifficultyElement = document.getElementById('difficulty');
                    courseDifficultyElement.appendChild(difficultyIcon);
                    courseDifficultyElement.appendChild(difficultyText);

                    // Course location
                    const locationIcon = document.createElement('img');
                    locationIcon.className = 'content-box-icon';
                    locationIcon.src = 'media/physical.svg';

                    const locationText = document.createElement('p');
                    locationText.innerText += "On-site";

                    const locationElement = document.getElementById('location');
                    locationElement.appendChild(locationIcon);
                    locationElement.appendChild(locationText);


                    // Providers
                    data.providers.forEach(provider => {
                        const providerElement = document.createElement('button');
                        providerElement.className = 'provider-choose';
                        providerElement.type = 'button';

                        let defaultCurrency = setDefaultCurrency() || 'USD';

                        let symbol = '';
                        let rate = 1;

                        for (let i = 0; i < currencies.length; i++) {
                            if (currencies[i].code === provider.currency) {
                                rate = currencies[i].rate;
                                break;
                            }
                        }

                        const priceInDefaultCurrency = provider.price / rate;

                        for (let i = 0; i < currencies.length; i++) {
                            if (currencies[i].code === defaultCurrency) {
                                symbol = currencies[i].symbol;
                                rate = currencies[i].rate;
                                break;
                            }
                        }

                        const finalPrice = priceInDefaultCurrency * rate;

                        if (provider.currency === "SUB") {
                            providerElement.innerHTML = provider.name;
                            locationIcon.src = 'media/online.svg';
                            locationText.innerText = 'Online';
                            document.getElementById('providerList').appendChild(providerElement);
                            providerElement.addEventListener('click', function() {
                                document.getElementById('enrollButton').textContent = "Enroll for " + symbol + finalPrice.toFixed(2) + "/month";
                                initMap(provider.latitude, provider.longitude);
                            });
                        } else {
                            providerElement.innerHTML = provider.name;
                            document.getElementById('providerList').appendChild(providerElement);
                            providerElement.addEventListener('click', function() {
                                document.getElementById('enrollButton').textContent = "Buy for " + symbol + finalPrice.toFixed(2);
                                initMap(provider.latitude, provider.longitude);
                            });
                        }

                    });

                    if (`${data.course.relatedCertifications}` === "null") {
                        courseCertElement.style.display = "none";
                    }
                    if (`${data.course.courseSize}` === "null") {
                        courseSizeElement.style.display = "none";
                    }
                    if (`${data.course.closestCourseSession}` === "null") {
                        courseDurationElement.style.display = "none";
                    }
                    if (`${data.course.level}` === "null") {
                        courseDifficultyElement.style.display = "none";
                    }
                    if (`${data.course.hoursPerWeek}` === "null") {
                        courseHoursElement.style.display = "none";
                    }
                })
                .catch(error => console.error('Error:', error));
        })
        .catch(error => console.error('Error:', error));
}


//Should add "st", "nd", "rd" or "th" to the day number.
function getOrdinalSuffix(day) {
    if (day % 10 === 1 && day !== 11) {
        return day + "st";
    } else if (day % 10 === 2 && day !== 12) {
        return day + "nd";
    } else if (day % 10 === 3 && day !== 13) {
        return day + "rd";
    } else {
        return day + "th";
    }
}

//Should format the date from DD.MM - DD.MM to "MMM DDth -> MMM DDth"
function formatDate(closestCourseSession) {
    let currentYear = new Date().getFullYear();
    let dates = closestCourseSession.split(' - ');
    let startDateComponents = dates[0].split('.');
    let endDateComponents = dates[1].split('.');

    let startDate = new Date(`${startDateComponents[1]}/${startDateComponents[0]}/${currentYear}`);
    let endDate = new Date(`${endDateComponents[1]}/${endDateComponents[0]}/${currentYear}`);

    let options = {month: 'short', day: 'numeric', timeZone: 'UTC'};

    let formattedStartDate = startDate.toLocaleString('en-US', options);
    let formattedEndDate = endDate.toLocaleString('en-US', options);

    formattedStartDate = `${formattedStartDate.slice(0, -2)} ${getOrdinalSuffix(startDate.getDate())}`;
    formattedEndDate = `${formattedEndDate.slice(0, -2)}${getOrdinalSuffix(endDate.getDate())}`;

    return `${formattedStartDate} -> ${formattedEndDate}`;
}


function populateCourses(selector, filterFn) {
    document.querySelector(selector).innerHTML = '';
    const defaultCurrency = setDefaultCurrency() || 'USD';
    fetch('/api/courses')
        .then(response => response.json())
        .then(data => {
            fetch('/api/currency')
                .then(response => response.json())
                .then(currencies => {
                    data.forEach(courseProvider => {
                        if (document.querySelector(selector).childElementCount >= 5) {
                            return false;
                        }
                        if (courseProvider.course.category === document.getElementById('courseCategoryLink').innerText) {
                            const contentBox = document.createElement('a');
                            contentBox.href = `/courses?id=${courseProvider.course.courseId}`;
                            contentBox.className = 'content-box';

                            const image = document.createElement('img');
                            image.src = courseProvider.course.image || 'media/noImage.svg';
                            image.alt = 'Course image';
                            image.className = 'content-box-image';
                            contentBox.appendChild(image);

                            const descriptionBox = document.createElement('div');
                            descriptionBox.className = 'content-box-description';
                            contentBox.appendChild(descriptionBox);

                            const title = document.createElement('h2');
                            title.className = 'content-box-title';
                            title.textContent = courseProvider.course.title;
                            descriptionBox.appendChild(title);

                            const hr = document.createElement('hr');
                            descriptionBox.appendChild(hr);

                            const contentDescription = document.createElement('div');
                            contentDescription.className = 'content-description';
                            descriptionBox.appendChild(contentDescription);

                            const attributes = document.createElement('div');
                            attributes.className = 'content-box-attributes';
                            contentDescription.appendChild(attributes);

                            // Create and append the category attribute
                            const categoryAttribute = document.createElement('div');
                            categoryAttribute.className = 'content-box-attribute';
                            attributes.appendChild(categoryAttribute);

                            const categoryIcon = document.createElement('img');
                            categoryIcon.className = 'content-box-icon';
                            categoryIcon.src = 'media/category.svg'; // Replace with actual path
                            categoryAttribute.appendChild(categoryIcon);

                            const category = document.createElement('p');
                            category.className = 'content-box-text';
                            category.textContent = courseProvider.course.category;
                            categoryAttribute.appendChild(category);

                            // Create and append the providers attribute
                            const providersAttribute = document.createElement('div');
                            providersAttribute.className = 'content-box-attribute';
                            attributes.appendChild(providersAttribute);

                            const providersIcon = document.createElement('img');
                            providersIcon.className = 'content-box-icon';
                            providersIcon.src = 'media/providers.svg';
                            providersAttribute.appendChild(providersIcon);

                            const providersElement = document.createElement('p');
                            providersElement.className = 'content-box-text';

                            const courseProviders = courseProvider.providers.filter(provider => provider.courseId === courseProvider.course.courseId);



                            if (Array.isArray(courseProviders) && courseProviders.length) {
                                providersElement.innerHTML = `${courseProviders.length}&nbsp;Providers`;
                            } else {
                                providersElement.innerHTML = 'No&nbsp;Providers';
                            }

                            providersAttribute.appendChild(providersElement);

                            const price = document.createElement('p');
                            price.className = 'content-button';


                            const lowestPriceProvider = courseProviders.reduce((prev, curr) => {
                                return (prev.price < curr.price) ? prev : curr;
                            });

                            const currency = lowestPriceProvider.currency;
                            const priceInCurrency = lowestPriceProvider.price;


                            let symbol = '';
                            let rate = 1;

                            for (let i = 0; i < currencies.length; i++) {
                                if (currencies[i].code === currency) {
                                    rate = currencies[i].rate;
                                    break;
                                }
                            }

                            const priceInDefaultCurrency = priceInCurrency / rate;

                            for (let i = 0; i < currencies.length; i++) {
                                if (currencies[i].code === defaultCurrency) {
                                    symbol = currencies[i].symbol;
                                    rate = currencies[i].rate;
                                    break;
                                }
                            }

                            const finalPrice = priceInDefaultCurrency * rate;

                            if (currency === 'SUB') {
                                price.textContent = symbol + finalPrice.toFixed(2) + "/month";
                            } else {
                                price.textContent = symbol + finalPrice.toFixed(2);
                            }

                            const priceBox = document.createElement('div');
                            priceBox.className = 'price-box';
                            priceBox.appendChild(price);

                            contentDescription.appendChild(priceBox);

                            document.querySelector(selector).appendChild(contentBox.cloneNode(true));
                        }
                    });
                })
                .catch(error => console.error('Error:', error));
        })
        .catch(error => console.error('Error:', error));
}

function currency() {
    fetch('/api/currency')
        .then(response => response.json())
        .then(currencies => {
            const select = document.getElementById('currencySelect');
            currencies.forEach(currency => {
                const option = document.createElement('option');
                option.value = currency.code;
                option.text = currency.name;
                select.appendChild(option);
            });
            document.getElementById('currencySelect').addEventListener('change', function() {
                document.cookie = `defaultCurrency=${this.value}; path=/; max-age=31536000`;
                location.reload();
            });
        })
        .catch(error => console.error('Error:', error));

}

function setDefaultCurrency() {
    const cookies = document.cookie.split('; ');
    const defaultCurrencyCookie = cookies.find(row => row.startsWith('defaultCurrency='));
    if (defaultCurrencyCookie) {
        return defaultCurrencyCookie.split('=')[1];
    }
}

// Initialize and add the map
// Initialize and add the map
let map;

async function initMap(lat, lng) {

    if (lat === null || lng === null) {
        onlineCourse();
    } else {

        document.getElementById('map-container').style.display = "block";
        const position = { lat: lat, lng: lng };

        const { Map } = await google.maps.importLibrary("maps");
        const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");

        // The map, centered at Uluru
        map = new Map(document.getElementById("map"), {
            zoom: 8,
            center: position,
            mapId: "DEMO_MAP_ID",
        });

        // The marker, positioned at Uluru
        const marker = new AdvancedMarkerElement({
            map: map,
            position: position,
            title: "Uluru",
        });
    }

}

function onlineCourse() {
    document.getElementById('map-container').style.display = "none";
}

function currency() {
    fetch('/api/currency')
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

function setDefaultCurrency() {
    const cookies = document.cookie.split('; ');
    const defaultCurrencyCookie = cookies.find(row => row.startsWith('defaultCurrency='));
    if (defaultCurrencyCookie) {
        return defaultCurrencyCookie.split('=')[1];
    }
}


