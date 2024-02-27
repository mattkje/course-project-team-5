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

            // Course size
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
            durationText.innerText += `${data.course.closestCourseSession}`;

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
            certificationText.innerText += `${data.course.relatedCertifications}` + " Certificate";

            const courseCertElement = document.getElementById('relatedCertifications');
            courseCertElement.appendChild(certificationIcon);
            courseCertElement.appendChild(certificationText);


            // Providers
            document.getElementById('providerList').dir = data.providers.forEach(provider => {
                const providerElement = document.createElement('button');
                providerElement.className = 'provider-choose';
                providerElement.type = 'button';
                providerElement.innerHTML = provider.name + "<br>" + provider.price + " " + provider.currency;
                document.getElementById('providerList').appendChild(providerElement);
            });


        })
        .catch(error => console.error('Error:', error));
}