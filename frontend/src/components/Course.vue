<template>
  <Menubar msg="msg"/>
  <div class="providerList">
    <div class="course-image-box">
      <img id="courseImage" class="course-image" alt="Course Image">
      <div class="course-image-blur"></div>
    </div>
    <div class="courseLinkElement">
      <a href="/">Courses</a>
      <p>&nbsp;->&nbsp;</p>
      <a href="#" id="courseCategoryLink"></a>
      <p>&nbsp;->&nbsp;</p>
      <a href="#" id="courseTitleLink"></a>
    </div>
    <div class="course">
      <div class="course-description-box">
        <h3>Course Description</h3>
        <p id="courseKeywords"></p>
        <p id="courseDescription"></p>
      </div>
      <div class="course-info">
        <h2 id="courseTitle"></h2>
        <hr>
        <h3>Providers</h3>
        <div class="content-hbox" id="providerList">
          <!-- Providers will be added here by JavaScript -->
        </div>
        <hr>
        <div id="courseInformation">
          <div class="courseInfoElement" id="courseSize"></div>
          <div class="courseInfoElement" id="closestCourseSession"></div>
          <div class="courseInfoElement" id="hoursPerWeek"></div>
          <div class="courseInfoElement" id="relatedCertifications"></div>
          <div class="courseInfoElement" id="difficulty"></div>
          <div class="courseInfoElement" id="location"></div>
        </div>
        <hr>
        <div class="course-action-box">
          <button id="enrollButton" class="enroll-button">
            Buy now
          </button>
          <button class="CartBtn">
                    <span class="IconContainer">
                        <img class="cart-icon-small" src="../../../public/cart-small.svg" alt="Cart">
                    </span>
            <p class="text">Add to Cart</p>
          </button>
        </div>
      </div>
    </div>
  </div>
  <div id="map-container" class="map-container" style="display: none">
    <h3>Course Location</h3>
    <div id="map" ></div>

  </div>


  <div class="greeting">
    <h2>Similar Courses</h2>
  </div>

  <div class="featured">
    <!--- Featured courses will be appended here --->
  </div>

  <Footer msg="Footer"/>
</template>

<script>

export default {
  props: ['id'],
  mounted() {
    populateCoursePage();
    currency();
  },
  methods: {
    populateCoursePage() {
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
    },

    getOrdinalSuffix(day) {
      if (day % 10 === 1 && day !== 11) {
        return day + "st";
      } else if (day % 10 === 2 && day !== 12) {
        return day + "nd";
      } else if (day % 10 === 3 && day !== 13) {
        return day + "rd";
      } else {
        return day + "th";
      }
    },

    //Should format the date from DD.MM - DD.MM to "MMM DDth -> MMM DDth"
    formatDate(closestCourseSession) {
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
    },


    populateCourses(selector, filterFn) {
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
    },

    setDefaultCurrency() {
      const cookies = document.cookie.split('; ');
      const defaultCurrencyCookie = cookies.find(row => row.startsWith('defaultCurrency='));
      if (defaultCurrencyCookie) {
        return defaultCurrencyCookie.split('=')[1];
      }
    },

    async initMap(lat, lng) {

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

    },

    onlineCourse() {
      document.getElementById('map-container').style.display = "none";
    },

    currency() {
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

  }
}



(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})
({key: "AIzaSyB41DRUbKWJHPxaFjMAwdrzWzbVKartNGg", v: "weekly"});
</script>

<style scoped>
:root {
  --base-1: #584BEB;
  --base-2: #6E67FC;
  --light-1: #eaeaea;
  --light-2: #adadad;
  --dark-1: #090909;
  --dark-2: #1a1a1a;
  --dark-3: #07002c;
}

body {
  font-family: Inter, sans-serif;
  background-color: var(--light-1);
  color: var(--dark-1);
  margin: 0;
  padding: 0;
}


img {
  margin: auto;
  padding: 0;
}

button {
  background-color: transparent;
  border-color: transparent;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}



/* Header  */

#menubar {
  background-color: rgba(6, 6, 16, 0.9);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 100;
}

.left-content {
  max-height: 70px;
  padding: 0 2vw;
  display: flex;
  align-items: center;
}

.right-content {
  max-height: 70px;
  margin: 3vw;
  display: flex;
  max-width: min-content;
  align-items: center;
}

.left-content-general {
  display: flex;
  align-items: center;
  margin-left: 50px;
  flex: 1; /* Allow the right content to take up remaining space */
  justify-content: flex-end; /* Align content to the right */
}

.right-content-general {
  display: flex;
  align-items: center;
  margin-right: 50px;
  flex: 1; /* Allow the right content to take up remaining space */
  justify-content: flex-end; /* Align content to the right */
}

.content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
}

.menu-links {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 50px;
}

.logo-text-container {
  margin: 0;
  padding: 0;
}

.logo-top {
  font-weight: 900;
  padding-left: 5px;
}

.logo-bottom {
  color: var(--base-1);
}


.background-overlay {
  background: linear-gradient(rgba(0, 0, 33, 0.83), rgba(0, 0, 33, 0.74));
}

/* Hero  */

.hero-hbox {
  display: flex;
  justify-content: left;
  align-items: center;
  padding-left: 10vw;
}

.hero-container {
  display: flex;
}

.hero-box {
  flex: 1;
  max-width: 600px;
}

.mail-box {
  display: flex;
}

.mail-prompt {
  width: 100%;
  padding: 20px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 22px;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
}

.hbox {
  display: flex;
}

.stack-pane {
  position: relative;
}

.hero-image {
  max-width: 400px;
  height: auto;
  position: absolute;
  top: 150px;
  left: 0;
}

.hero-graphic {
  max-width: 700px;
  width: 50%;
  top: 0;
  left: 0;
  opacity: 0;
}

input:focus {
  outline: none;
}

.search-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh; /* Adjust as needed */
}

.search-bar {
  box-sizing: border-box;
  width: 60%;
  border: 3px solid #b2aece;
  border-radius: 20px;
  position: relative; /* Make it a positioning context for the absolute position of the button */
}


.search-prompt {
  background: none;
  width: 60%;
  padding: 20px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}


.greeting {
  border: none;
  padding: 10px 20px;
  border-radius: 15px;
  width: 70%;
  align-content: center;
  text-decoration: none;
  display: flex;
  margin: auto;
}

.course-image {
  background-color: #3c3c3c;
  position: absolute;
  height: 600px;
  width: 100%;
  object-fit: cover;
  margin: 0;
}

.course-image-blur {
  position: relative;
  height: 600px;
  padding: 0;
  width: 100%;
  margin: 0;
  background: linear-gradient(to top, rgba(15, 16, 19, 0.68) 0%, transparent 20%);
}

.course-image-box {
  position: absolute;
  height: 600px;
  width: 100%;
  margin: 0;
}

.course {
  padding: 10px 20px;
  width: 1400px;
  align-content: center;
  justify-content: center;
  text-decoration: none;
  display: flex;
  margin: 200px auto 0 auto;
  height: min-content;
}

.course-info {
  position: relative;
  padding: 10px 20px;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 27px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  width: 400px;
  height: min-content;
}

.course-description-box {
  border: none;
  padding: 10px 20px;
  margin: 400px auto 0 auto;
}

.search-box {
  border: none;
  padding: 40px 20px;
  border-radius: 15px;
  width: 70%;
  align-content: center;
  text-decoration: none;
  display: flex;
  margin: auto;
}

.content-box {
  color: var(--dark-1);
  border: none;
  border-radius: 15px;
  align-content: center;
  text-decoration: none;
  margin-left: 15px;
  margin-right: 15px;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
}

.content-box-description {
  color: var(--dark-1);
  padding: 20px 0;
  border-radius: 0 0 15px 15px;
  width: 100%;

}

.content-box-text {
  padding: 0 5px;
}

.content-box-image {
  height: 250px;
  object-fit: cover;
  border-radius: 15px;
}

.content-price {
  padding: 0 15px 0 0;
  position: absolute;
  right: 0;
}

.hbox {
  display: flex;
}

.product-slide {
  display: flex;
  margin: 30px auto auto;
}

.hgrow {
  margin: auto;
}

.logo {
  width: 40px;
  height: 40px;
  padding-left: 20px;
  transform:translateY(-3px);
}

.cart {
  width: 35px;
  height: 35px;
}

.menu-icons {
  width: 30px;
  height: 30px;
  padding: 0;
}


.additional-info {
  display: none;
}

#carlBox:hover .additional-info,
#mattBox:hover .additional-info
,#alejandroBox:hover .additional-info
,#liBox:hover .additional-info {
  display: block;

}


.content-hbox {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: auto;
  justify-content: space-between;
}

hr {
  border: 0;
  height: 3px;
  background:  #282828;
  margin: 20px 0;
}

.content-box-icon {
  width: 20px;
  height: 20px;
  margin: auto;
  padding: 5px;
}



#courseInformation {
  display: grid;
  grid-template-columns: repeat(2, min-content);
  grid-template-rows: repeat(3, min-content);
  justify-items: start;
  align-items: start;
  grid-gap: 0 1vw;
}

.courseInfoElement {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  width: max-content;
}

#relatedCertifications p{
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 200px;
}

.courseInfoElement p {
  font-weight: bold;
  color: #282828;
}

.courseLinkElement {
  padding: 0 7px;
  background-color: rgba(29, 28, 32, 0.8);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255,255,255,.08);
  border-radius: 25px;
  box-shadow: 0 2px 5px #000;
  z-index: 50;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  margin-left: 100px;
  position: relative;
  top: 7px;
  width: min-content;
  white-space: nowrap;
}

.courseLinkElement p {
  font-weight: bold;
  color: white;
}

.courseLinkElement a {
  font-weight: bold;
  color: white;
  text-decoration: none;
}

.course-action-box {
  display: flex;
  justify-content: left;
  align-items: center;
}

.search-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh; /* Adjust as needed */
}

.search-bar {
  box-sizing: border-box;
  width: 60%;
  border: 2px solid var(--light-2);
  border-radius: 20px;
  position: relative; /* Make it a positioning context for the absolute position of the button */
}

.search-icon {
  position: absolute;
  top: 50%;
  left: 30px;
  transform: translateY(-50%);
  width: 30px;
  height: 30px;
  margin: auto;
  opacity: 50%;
}


.search-prompt {
  background: none;
  width: 60%;
  padding: 20px 20px 20px 100px;
  margin-right: 10px;
  border: none;
  border-radius: 15px;
  font-size: 16px;
}



.clear {
  clear: both;
}

.checkBox {
  display: block;
  cursor: pointer;
  width: 30px;
  height: 30px;
  border: 3px solid rgba(255, 255, 255, 0);
  border-radius: 10px;
  position: relative;
  overflow: hidden;
  box-shadow: 0px 0px 0px 2px #fff;
}

.checkBox div {
  width: 60px;
  height: 60px;
  background-color: #fff;
  top: -52px;
  left: -52px;
  position: absolute;
  transform: rotateZ(45deg);
  z-index: 100;
}

.checkBox input[type=checkbox]:checked + div {
  left: -10px;
  top: -10px;
}

.checkBox input[type=checkbox] {
  position: absolute;
  left: 50px;
  visibility: hidden;
}

.transition {
  transition: 300ms ease;
}

.filter-box {
  background-color: rgba(29, 28, 32, 0.7);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(255,255,255,.08);
  margin: 3vw;
  padding:2vw;
  border-radius: 17px;
}

.explore-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  grid-gap: 20px;
  padding: 20px;
}

.featured {
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 17px;
  padding: 3vw 0;
  margin: 2vw 3vw;
  display: flex;
}

.content-box {
  display: flex;
  flex-direction: column;
  padding: 1vw;
  justify-content: space-between;
  width: 12vw;
  height: 34vh;
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(5px);
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 27px;
  text-decoration: none;
  margin: 0 0 0 2vw;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25);
  transition: transform 0.5s ease;

  &:hover {
    transform: scale(1.05);
  }
}

* {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}

.content-box-description {
  color: var(--dark-3);

}

.content-description {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  height: 4vw;
}

.content-box-title {
  font-size: 1vw;
  font-weight: bold;

}

.content-box-text {
  font-size: 0.5vw;
  font-weight: 900;
  color: var(--light-2);
  padding: 0.2vw;
  margin: auto;
}

.content-box-image {
  margin: 0;
  height: 16vh;
  width: 12vw;
  object-fit: cover;
  border-radius: 15px;
}

.content-box-icon {
  width: 20px;
  height: 20px;
  padding: 5px;
}

.content-box-attribute {
  width: min-content;
  display: flex;
  align-items: flex-start;
  flex-direction: row;
  margin: 0;
  padding: 0;
}

.content-box-attributes {
  padding: 0;
  margin: 0;
}

.content-price {
  padding: 0 0 0 0;
  position: absolute;
  right: 0;
}

.price-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  border-width: 0.15vw;
  max-height: 3vw;
  border-radius: 1vw;
  margin: 0;
}

.content-button {
  padding: 0 0.2vw;
  font-weight: bold;
  font-size: 1vw;
  margin: 0;
  color: #282828;
}
.map-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 1400px;
  margin: auto;
  border-radius: 30px;
}
#map {
  height: 400px;
  width: 1400px;
  object-fit: cover;
  border-radius: 15px;
  box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  transition: transform 0.5s ease;
}

</style>