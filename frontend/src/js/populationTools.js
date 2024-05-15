// Fetch the courses from the API
export async function fetchCourses(API_URL) {
    const response = await fetch(API_URL + '/courses');
    return response.json();
}

// Fetch the currencies from the API
export async function fetchCurrencies(API_URL) {
    const response = await fetch(API_URL + '/currency');
    return response.json();
}


export function createContentBox(courseProvider, currencies, defaultCurrency) {
    const contentBox = document.createElement('a');
    contentBox.href = `/courses?id=${courseProvider.course.courseId}`;
    contentBox.className = 'content-box';

    const image = document.createElement('img');
    image.src = courseProvider.course.image || '/noImage.svg';
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

    const attributes = document.createElement('div');
    attributes.className = 'content-box-attributes';
    descriptionBox.appendChild(attributes);

    // Create and append the category attribute
    const categoryAttribute = document.createElement('div');
    categoryAttribute.className = 'content-box-attribute';
    attributes.appendChild(categoryAttribute);

    const categoryIcon = document.createElement('img');
    categoryIcon.className = 'content-box-icon';
    categoryIcon.src = '/category.svg';
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
    providersIcon.src = '/providers.svg';
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
    const priceBox = createPrice(courseProviders, currencies, defaultCurrency);

    // importing date as hidden to be able to access it´s value
    const durationText = document.createElement('p');
    const rawDate = `${courseProvider.course.closestCourseSession}`;
    durationText.innerText += rawDate;
    durationText.style.display = 'none';
    contentBox.appendChild(durationText);


    // importing provider name as hidden to be able to access it´s value
    const providerNameText = document.createElement('p');
    providerNameText.className = 'provider-name';
    courseProviders.forEach(provider => {
        providerNameText.innerText += provider.name + " ";
    });
    providerNameText.style.display = 'none';
    contentBox.appendChild(providerNameText);

    // importing difficulty as hidden to be able to access it´s value
    const difficultyText = document.createElement('p');
    difficultyText.className = 'content-box-difficulty';
    difficultyText.innerText += courseProvider.course.level;
    difficultyText.style.display = 'none';
    contentBox.appendChild(difficultyText);

    // Importing credit as hidden to be able to access it´s value
    const creditText = document.createElement('p');
    creditText.className = 'content-box-credit';
    creditText.innerText += courseProvider.course.courseSize;
    creditText.style.display = 'none';
    contentBox.appendChild(creditText);

    const hr2 = document.createElement('hr');
    descriptionBox.appendChild(hr2);
    descriptionBox.appendChild(priceBox);

    return contentBox;
}

function createPrice(courseProviders, currencies, defaultCurrency) {
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
    const priceBox = document.createElement('div');
    priceBox.className = 'price-box';

    if (currency === 'SUB') {
        const image2 = document.createElement('img');
        image2.className = 'content-box-image-banner';
        image2.src = '/proBanner.svg';
        priceBox.appendChild(image2);
        price.textContent = symbol + finalPrice.toFixed(2) + "/month";
    } else {
        const startsAt = document.createElement('p');
        startsAt.className = 'content-box-text';
        startsAt.textContent = 'Starts at';
        priceBox.appendChild(startsAt);
        price.textContent = symbol + finalPrice.toFixed(2);
    }
    priceBox.appendChild(price);
    return priceBox;
}

export function checkIfProCourse(courseProvider) {
    return courseProvider.providers.some(provider => provider.name === "Learniverse");
}

export function checkIfCourseInCategory(courseProvider, category) {
    return courseProvider.course.category === category;
}
