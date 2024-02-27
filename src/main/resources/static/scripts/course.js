window.onload = function() {
    populateCoursePage();
};

function populateCoursePage() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');


    fetch(`/api/courses/${id}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('courseDescription').innerText = data.course.description;
            document.getElementById('courseImage').src = data.course.image;
            document.getElementById('courseTitle').innerText = data.course.title;
            document.getElementById('courseSize').innerText = `Course size: ${data.course.courseSize}`;
            document.getElementById('closestCourseSession').innerText = `Closest course session: ${data.course.closestCourseSession}`;
            document.getElementById('hoursPerWeek').innerText = `Hours per week: ${data.course.hoursPerWeek}`;
            document.getElementById('providerList').dir = data.providers.forEach(provider => {
                const providerElement = document.createElement('a');
                providerElement.className = 'fancy-button';
                providerElement.type = 'button';
                providerElement.textContent = provider.name;
                document.getElementById('providerList').appendChild(providerElement);
            });

        })
        .catch(error => console.error('Error:', error));
}