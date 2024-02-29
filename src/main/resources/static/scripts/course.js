window.onload = function () {
    populateCoursePage();
};

// Function to populate the course page with the course data
function populateCoursePage() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');


    fetch(`/api/courses/${id}`)
        .then(response => response.json())
        .then(data => {

            document.getElementById('courseCategoryLink').innerText = data.course.category;
            document.getElementById('courseTitleLink').innerText = data.course.title;

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
            certificationText.innerText += `${data.course.relatedCertifications}` + " Certificate";

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


            // Providers
            data.providers.forEach(provider => {
                const providerElement = document.createElement('button');
                providerElement.className = 'provider-choose';
                providerElement.type = 'button';
                providerElement.innerHTML = provider.name + "<br>" + provider.price + "&nbsp;" + provider.currency;
                document.getElementById('providerList').appendChild(providerElement);
            });

            // If no data, hide the element. (Value from the database is "null", not null, hence the string comparison.)
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