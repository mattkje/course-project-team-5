

window.onload = function() {
    fetch('/api/courses')
        .then(response => response.json())
        .then(data => {

            data.forEach(course => {

                const contentBox = document.createElement('a');
                contentBox.href = `/api/courses/${course.courseid}/${course.providerid}`;
                contentBox.className = 'content-box';


                const image = document.createElement('img');
                image.src = course.image;
                image.alt = 'Course Logo';
                image.className = 'content-box-image';
                contentBox.appendChild(image);


                const descriptionBox = document.createElement('div');
                descriptionBox.className = 'content-box-description';
                contentBox.appendChild(descriptionBox);


                const title = document.createElement('h2');
                title.className = 'content-box-title';
                title.textContent = course.title;
                descriptionBox.appendChild(title);


                const contentDescription = document.createElement('div');
                contentDescription.className = 'content-description';
                descriptionBox.appendChild(contentDescription);

                // Create and append the attributes
                const attributes = document.createElement('div');
                attributes.className = 'content-box-attributes';
                contentDescription.appendChild(attributes);

                // Create and append the category
                const category = document.createElement('p');
                category.className = 'content-box-text';
                category.textContent = course.category;
                attributes.appendChild(category);

                // Create and append the providers
                const providers = document.createElement('p');
                providers.className = 'content-box-text';
                providers.textContent = `${course.providers} Providers`;
                attributes.appendChild(providers);

                // Create and append the price
                const price = document.createElement('p');
                price.className = 'content-button';
                price.textContent = course.price;
                contentDescription.appendChild(price);

                // Append the content box to the parent container
                document.querySelector('.parent-container').appendChild(contentBox);
            });
        })
        .catch(error => console.error('Error:', error));

};
