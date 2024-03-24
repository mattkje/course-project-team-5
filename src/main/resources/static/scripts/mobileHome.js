//This is a temporary file to test the mobile home page.

window.onload = function() {

    loadComponent('footer');
    loadComponent('menubar');
    populateCourses('.featured', () => true);
    populateCourses('.learniverse-pro', checkIfProCourse);
    populateCourses('.digital-marketing', checkIfDmCourse);
    populateCourses('.data-Science', checkIfDsCourse);
    populateCourses('.information-technologies', checkIfItCourse);
};

function loadComponent(component) {
    fetch('../components/mobile/' + component + '.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById(component).innerHTML = data;
        });
}

function populateCourses(selector, filterFn) {
    fetch('/api/courses')
        .then(response => response.json())
        .then(data => {
            data.forEach(courseProvider => {
                if (document.querySelector(selector).childElementCount >= 5) {
                    return false;
                }
                if (filterFn(courseProvider)) {
                    const contentBox = document.createElement('a');
                    contentBox.href = `/m-courses?id=${courseProvider.course.courseId}`;
                    contentBox.className = 'content-box';

                    const image = document.createElement('img');
                    image.src = courseProvider.course.image;
                    image.alt = 'Course Logo';
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

                    const hr2 = document.createElement('hr');
                    descriptionBox.appendChild(hr2);

                    const price = document.createElement('p');
                    price.className = 'content-button';
                    const lowestPriceProvider = courseProviders.reduce((prev, curr) => {
                        return (prev.price < curr.price) ? prev : curr;
                    });

                    const currency = lowestPriceProvider.currency;

                    if (currency === 'USD') {
                        price.textContent = "$" + lowestPriceProvider.price;
                    } else if (currency === 'EUR') {
                        price.textContent = "€" + lowestPriceProvider.price;
                    } else if (currency === 'GBP') {
                        price.textContent = "£" + lowestPriceProvider.price;
                    } else if (currency === 'SUB') {
                        price.textContent = "$" + lowestPriceProvider.price + "/month";

                    } else {
                        price.textContent = currency + " " + lowestPriceProvider.price;
                    }

                    const priceBox = document.createElement('div');
                    priceBox.className = 'price-box';
                    priceBox.appendChild(price);

                    descriptionBox.appendChild(priceBox);

                    document.querySelector(selector).appendChild(contentBox.cloneNode(true));

                }
            });
        })
        .catch(error => console.error('Error:', error));
}

function checkIfProCourse(courseProvider) {
    return courseProvider.providers.some(provider => provider.name === "Learniverse");
}

function checkIfDmCourse(courseProvider) {
    return courseProvider.course.category === "Digital Marketing";
}

function checkIfDsCourse(courseProvider) {
    return courseProvider.course.category === "Data Science and Analytics";
}

function checkIfItCourse(courseProvider) {
    return courseProvider.course.category === "Information Technology";
}