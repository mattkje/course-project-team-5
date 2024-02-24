window.onload = function() {
    populateCoursePage();

};
function populateCoursePage() {
    // Get the course ID from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const courseId = urlParams.get('courseId');

    // Fetch the course data from the API
    fetch(`/api/courses/${courseId}`)
        .then(response => response.json())
        .then(data => {
            // Use the data to populate the HTML elements
            document.getElementById('courseTitle').textContent = data.title;
            document.getElementById('courseImage').src = data.image;
            document.getElementById('courseSize').textContent = data.size;
            document.getElementById('closestCourseSession').textContent = data.closestSession;
            document.getElementById('hoursPerWeek').textContent = data.hoursPerWeek;
            document.getElementById('courseKeywords').textContent = data.keywords.join(', ');
            document.getElementById('courseDescription').textContent = data.description;

            // Add the providers to the provider list
            const providerList = document.getElementById('providerList');
            data.providers.forEach(provider => {
                const providerElement = document.createElement('div');
                providerElement.textContent = provider.name;
                providerList.appendChild(providerElement);
            });
        })
        .catch(error => console.error('Error:', error));
}
}
