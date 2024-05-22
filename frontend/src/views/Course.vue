<script setup>
import {getCurrentInstance, onMounted, ref} from 'vue';
import "@/assets/coursePage.css"
import {getAuthenticatedUser, hasRole} from "@/js/authentication";
import MarkdownIt from "markdown-it";
import myStore from '@/js/store.js';
import {createContentBox, fetchCourses, fetchCurrencies} from "@/js/populationTools";
import { setCookie } from "@/js/tools";
import {sendApiRequest} from "@/js/requests";

const loading = ref(true);
const addProvider = ref(false);

//Test cookies
function addCourseToCart() {

}



onMounted(() => {
  populateCoursePage();
  populateProviders();
});





const { appContext } = getCurrentInstance();
const API_URL = appContext.config.globalProperties.$apiAddress;

// Google Maps API (Looks like nonsense, but is not. So Do Not Delete!)
(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})
({key: "AIzaSyAhRVHfIjsffeR6UTu-ypmHJvnlZqg5a2s", v: "weekly"});

// Function to populate the course page with the course data
function populateCoursePage() {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');

  // Fetch the currency data
  fetch(API_URL + '/currency')
      .then(response => response.json())
      .then(currencies => {

        fetch(API_URL + `/courses/${id}`)
            //Checking if course exists
            .then(response => {
              if (!response.ok) {
                if (response.status === 404) {
                  //Use custom course not found page?
                  window.location.href = '/no-access';
                } else {
                  throw new Error('Network response was not ok');
                }
              }
              return response.json();
            })
            //Continuing if course exists
            .then(data => {

              if(data.course.active === false && !hasRole('ROLE_ADMIN')) {
                window.location.href = '/no-access';
              }
              //Populating the similar courses box
              populateCourses('.featured');

              //Populating the course page
              const markdownText = data.course.description;
              const md = new MarkdownIt();
              const htmlContent = md.render(markdownText);


              document.getElementById('courseCategoryLink').innerText = data.course.category;
              document.getElementById('courseTitleLink').innerText = data.course.title;
              document.getElementById('courseDescription').innerHTML = htmlContent
              document.getElementById('courseImage').src = data.course.image || '/noImage.svg';
              document.getElementById('courseTitle').innerText = data.course.title;

              const sizeIcon = document.createElement('img');
              sizeIcon.className = 'content-box-icon';
              sizeIcon.src = '/credits.svg';

              const sizeText = document.createElement('p');
              sizeText.innerText += `${data.course.courseSize}` + " ECTS Credits";
              sizeText.style.color ='#6c6c6c'

              const courseSizeElement = document.getElementById('courseSize');
              courseSizeElement.appendChild(sizeIcon);
              courseSizeElement.appendChild(sizeText);

              // Course duration
              const durationIcon = document.createElement('img');
              durationIcon.className = 'content-box-icon';
              durationIcon.src = '/calendar.svg';

              const durationText = document.createElement('p');
              const rawStartDate = `${data.course.startDate}`;
              const rawEndDate = `${data.course.endDate}`;
              durationText.innerText += formatDate(rawStartDate,rawEndDate);
              durationText.style.color ='#6c6c6c'

              const courseDurationElement = document.getElementById('closestCourseSession');
              courseDurationElement.appendChild(durationIcon);
              courseDurationElement.appendChild(durationText);

              // Course hours per week
              const hoursIcon = document.createElement('img');
              hoursIcon.className = 'content-box-icon';
              hoursIcon.src = '/size.svg';

              const hoursText = document.createElement('p');
              hoursText.innerText += `${data.course.hoursPerWeek}` + " h/w";
              hoursText.style.color ='#6c6c6c'

              const courseHoursElement = document.getElementById('hoursPerWeek');
              courseHoursElement.appendChild(hoursIcon);
              courseHoursElement.appendChild(hoursText);

              // Course certification
              const certificationIcon = document.createElement('img');
              certificationIcon.className = 'content-box-icon';
              certificationIcon.src = '/cert.svg';

              const certificationText = document.createElement('p');
              certificationText.innerText += `${data.course.relatedCertifications}`;
              certificationText.style.color ='#6c6c6c'

              const courseCertElement = document.getElementById('relatedCertifications');
              courseCertElement.appendChild(certificationIcon);
              courseCertElement.appendChild(certificationText);

              // Course difficulty
              const difficultyIcon = document.createElement('img');
              difficultyIcon.className = 'content-box-icon';
              difficultyIcon.src = '/level.svg';

              const difficultyText = document.createElement('p');
              difficultyText.innerText += `${data.course.level}`;
              difficultyText.style.color ='#6c6c6c'

              const courseDifficultyElement = document.getElementById('difficulty');
              courseDifficultyElement.appendChild(difficultyIcon);
              courseDifficultyElement.appendChild(difficultyText);

              // Course location
              const locationIcon = document.createElement('img');
              locationIcon.className = 'content-box-icon';
              locationIcon.src = '/physical.svg';

              const locationText = document.createElement('p');
              locationText.innerText += "On-site";
              locationText.style.color ='#6c6c6c'

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
                const providerID = provider.courseProviderId; //244
                addCourseToCart(providerID);


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
                  locationIcon.src = '/online.svg';
                  locationText.innerText = 'Online';
                  document.getElementById('providerList').appendChild(providerElement);
                  providerElement.addEventListener('click', function() {
                    if (hasRole('ROLE_PRO')){
                      document.getElementById('enrollButton').className = 'pro-enroll-button'
                      document.getElementById('enrollButton').textContent = "Go to Course";
                    } else {
                      document.getElementById('enrollButton').textContent = "Enroll for " + symbol + finalPrice.toFixed(2) + "/month";
                    }

                    initMap(provider.latitude, provider.longitude);
                  });
                } else {
                  providerElement.innerHTML = provider.name;
                  document.getElementById('providerList').appendChild(providerElement);
                  providerElement.addEventListener('click', function() {
                    document.getElementById('enrollButton').textContent = "Buy for " + symbol + finalPrice.toFixed(2);
                    document.getElementById('notShowingLocationText').innerText = 'Showing location for provider ' + provider.name
                    initMap(provider.latitude, provider.longitude);

                    const urlParams = new URLSearchParams(window.location.search);
                    const courseId = urlParams.get('id');
                    const buyButton = document.getElementById('enrollButton');
                    const addCartButton = document.getElementById('cartButton');
                    addCartButton.addEventListener('click', function() {
                      setCookie('courseId_' + courseId, courseId, 1);
                      setCookie('providerId_' + courseId, providerID, 1);
                    })

                    //This is just a temporary solution to make the button work as it should
                    Object.assign(buyButton.style, {
                      fontFamily: 'Inter, sans-serif',
                      fontWeight: 'bold',
                      color: 'white',
                      backgroundColor: '#584BEB',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      width: '70%',
                      height: '50px',
                      borderRadius: '12px',
                      cursor: 'pointer',
                      transition: 'all .5s',
                      marginRight: '10px',
                      pointerEvents: 'all',
                    });

                    Object.assign(addCartButton.style, {
                      width: '40%',
                      height: '50px',
                      borderRadius: '12px',
                      border: 'none',
                      backgroundColor: '#090909',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      cursor: 'pointer',
                      transitionDuration: '.5s',
                      overflow: 'hidden',
                      boxShadow: '0px 5px 10px rgba(0, 0, 0, 0.103)',
                      position: 'relative',
                      pointerEvents: 'all',
                    });



                  });
                }

              });

              if (hasRole("ROLE_ADMIN")) {
                let addProviderButton = document.createElement("button");
                addProviderButton.innerHTML = "Add Provider";
                addProviderButton.classList.add("provider-choose");
                addProviderButton.id = "add-provider";
                document.getElementById('providerList').appendChild(addProviderButton);
                addProviderButton.addEventListener('click', function() {
                  changeProviderValue();
                });

                let active = document.getElementById('active');
                if (data.course.active) {
                  active.innerText = "True";
                } else {
                  active.innerText = "False";
                }
              }


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
              loading.value = false;
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
function formatDate(startDate, endDate) {
  let options = {month: 'short', day: 'numeric', timeZone: 'UTC'};

  let start = new Date(startDate);
  let end = new Date(endDate);

  let formattedStartDate = start.toLocaleString('en-US', options);
  let formattedEndDate = end.toLocaleString('en-US', options);

  formattedStartDate = `${formattedStartDate.slice(0, -2)} ${getOrdinalSuffix(start.getDate())}`;
  formattedEndDate = `${formattedEndDate.slice(0, -2)}${getOrdinalSuffix(end.getDate())}`;

  return `${formattedStartDate} -> ${formattedEndDate}`;
}

async function populateCourses(selector) {
  document.querySelector(selector).innerHTML = '';
  const defaultCurrency = setDefaultCurrency() || 'USD';
  try {
    const [data, currencies] = await Promise.all([fetchCourses(API_URL), fetchCurrencies(API_URL)]);

    data.forEach(courseProvider => {

      if (courseProvider.course.category === document.getElementById('courseCategoryLink').innerText) {
        const contentBox = createContentBox(courseProvider, currencies, defaultCurrency);
        document.querySelector(selector).appendChild(contentBox.cloneNode(true));
      }
    });
    loading.value = false;
  } catch (error) {
    console.error('Error:', error);
  }
}


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
  document.getElementById('notShowingLocationText').innerText = "This provider does not offer an on-site course";
}

function currency() {
  fetch(API_URL + '/currency')
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

let providers = ref([]);
let currencies = ref([]);

const singleProvider = ref({
  providerId: '',
  price: '',
  currency: ''
});

function changeProviderValue() {
  addProvider.value = !addProvider.value;
}

function populateProviders() {
  sendApiRequest(API_URL,'GET', '/providers', providerComplete, providerFailed);
  sendApiRequest(API_URL,'GET', '/currency', currencyComplete, currencyFailed);
}

function currencyComplete(data) {
  currencies.value = data;
}

function currencyFailed() {
  console.log('Failed to fetch currencies');
}

function providerComplete(data) {
  providers.value = data;
}

function providerFailed() {
  console.log('Failed to fetch providers');
}

function addNewProvider() {
  const urlParams = new URLSearchParams(window.location.search);

  const providerData = {
    courseId: urlParams.get('id'),
    provider: {
      providerId: singleProvider.value.providerId,
      name: providers.value.find(provider => provider.providerId === singleProvider.value.providerId).name
    },
    price: singleProvider.value.price,
    currency: singleProvider.value.currency,
  };

  console.log(providerData);

  sendApiRequest(API_URL,'POST', '/providers/' + providerData.courseId, addProviderSuccess, providerData, addProviderFailed);

}

function addProviderSuccess() {
  alert('Provider added successfully');
  window.location.reload();
}

function addProviderFailed() {
  alert('Failed to add provider');
}

function changeActive() {
  const urlParams = new URLSearchParams(window.location.search);
  sendApiRequest(API_URL,'PUT', '/courses/active/' + urlParams.get('id'), activeSuccess, null, activeFailed);
}

function activeSuccess() {
  alert('Course active status changed successfully');
  window.location.reload();
}

function activeFailed() {
  alert('Failed to change active status');
}
</script>

<template>
  <div class="course-page-background" v-show="!addProvider">

    <div v-show="!loading" class="courseLinkElement">
      <a href="/">Courses</a>
      <p>&nbsp;->&nbsp;</p>
      <a href="#" id="courseCategoryLink"></a>
      <p>&nbsp;->&nbsp;</p>
      <a href="#" id="courseTitleLink"></a>
    </div>
    <div v-show="!loading" class="providerList">
      <div class="administrator" v-show="hasRole('ROLE_ADMIN')">
        <div id="title">
          <p>Admin View</p>
          <hr>
        </div>
        <div class="administrator-content">
          <div class="active-status">
            <p>Active status: <span id="active"></span></p>
            <button class="fancy-button" @click="changeActive">Change status</button>
          </div>
        </div>
      </div>
      <div class=""></div>
      <div class="course-image-box">
        <img id="courseImage" class="course-image" alt="Course Image">
        <div class="course-image-blur"></div>
        <div class="course-info">
          <h1 id="courseTitle"></h1>
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

            <router-link to="/payment" id="enrollButton" class="enroll-button">Buy now</router-link>
            <button class="cartButton" id="cartButton">
                    <span class="IconContainer">
                        <img class="cart-icon-small" src="/cart-small.svg" alt="Cart">
                    </span>
              <p class="text">Add to Cart</p>
            </button>
          </div>
        </div>
      </div>
      <div class="course-description-box">
        <h3>Description</h3>
        <p id="courseKeywords"></p>
        <p id="courseDescription"></p>
      </div>
    </div>
    <div class="course-description-box">
      <h3>Location</h3>
      <p id="notShowingLocationText">Please select a provider to show location</p>
      <div id="map-container" class="map-container" style="display: none">
        <div id="map" ></div>
      </div>
  </div>


    <div v-show="!loading"class="greeting">
      <h2>Similar Courses</h2>
    </div>

    <div v-show="!loading" class="featured">
      <!--- Featured courses will be appended here --->
    </div>
    <div v-show="!loading" class="greeting">
    </div>
  </div>
  <div class="addProvider" v-show="addProvider">
    <div class="provider-box">
      <div class="provider-box-content">
        <h1>Add Provider</h1>
        <form @submit.prevent="addNewProvider">
          <div class="provider-form">
            <label for="provider-name">Provider name:</label>
            <select name="providers" id="provider-name" v-model="singleProvider.providerId">
              <option v-for="provider in providers" :key="provider.providerId" :value="provider.providerId">
                {{ provider.name }}
              </option>
            </select>
          </div>
          <div class="provider-form">
            <label for="provider-price">Price:</label>
            <input type="text" id="provider-price" required v-model="singleProvider.price">
          </div>
          <div class="provider-form">
            <label for="provider-currency">Currency:</label>
            <select type="text" id="provider-currency" required v-model="singleProvider.currency">
              <option v-for="currency in currencies" :key="currency.code" :value="currency.code">
                {{ currency.code }} - {{ currency.symbol }}
              </option>
            </select>
          </div>
          <div class="provider-options">
            <button type="submit" >Add Provider</button>
            <button @click="changeProviderValue">Cancel</button>
          </div>
          </form>
      </div>
    </div>
  </div>
</template>


<style scoped>

@media (max-width: 769px) {
  .course-image-box {
    display: flex;
    flex-direction: column;
    align-content: center;
    justify-content: center;
    width: 100%;
    margin: 0;
  }

  .course-image {
    border-radius: 20px;
    background-color: #3c3c3c;
    height: auto;
    width: 400px;
    object-fit: cover;
    margin: 10px auto;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .courseLinkElement {
    display: flex;
    max-width: 400px;
    margin: 0 auto;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    white-space: nowrap;
  }
  .courseLinkElement p {
    font-weight: bold;
    font-size: 10px;
    color: #5d5d5d;
  }

  .courseLinkElement a {
    font-weight: bold;
    font-size: 12px;
    color: #5d5d5d;
    text-decoration: none;
  }

  .course-info {
    background-color: var(--light-1);
    border-radius: 20px;
    padding: 20px;
    margin: 10px auto;
    width: 400px;
    height: max-content;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .course-description-box {
    background-color: var(--light-1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    width: 400px;
    margin: 10px auto;
    padding: 20px;
    border-radius: 20px;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .featured {

    border-radius: 20px;
    justify-content: flex-start;
    align-content: center;
    width: 100%;
    height: min-content;
    margin: 0;
    display: flex;
    flex-direction: row;
    overflow: auto;

    scrollbar-width: none;
    -ms-overflow-style: none;
  }

  .provider-box {
    background-color: var(--light-1);
    border-radius: 20px;
    padding: 20px;
    margin: auto;
    width: 400px;
    height: max-content;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }
  .provider-form {
    display: flex;
    flex-direction: column;
  }
  .provider-form input, .provider-form select {
    padding: 10px;
    border: none;
    border-radius: 10px;
    background-color: white;
  }

  .provider-options {
    display: flex;
    justify-content: space-between;
    margin: auto;
  }

  .provider-options button {

    font-family: 'Inter', sans-serif;
    font-weight: 900;
    color: #ffffff;
    font-size: 20px;
    background: #000000;
    border: none;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 70px;
    border-radius: 15px;
    cursor: pointer;
    margin: 0 10px;
    transition: all .5s;

    &:active {
      transform: scale(0.95);
      transition-duration: .5s;
    }
  }

}

@media (min-width: 769px) {
  .course-image-box {
    display: flex;
    align-content: center;
    justify-content: center;
    width: 100%;
    margin: 0;
  }

  .course-image {
    border-radius: 20px 0 0 20px;
    background-color: #3c3c3c;
    height: auto;
    width: 700px;
    object-fit: cover;
    margin: 0;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .courseLinkElement {
    padding: 30px 7px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    margin-left: 100px;
    width: min-content;
    white-space: nowrap;
  }

  .courseLinkElement p {
    font-weight: bold;
    color: #5d5d5d;
  }

  .courseLinkElement a {
    font-weight: bold;
    color: #5d5d5d;
    text-decoration: none;
  }

  .course-info {
    background-color: var(--light-1);
    border-radius: 0 20px 20px 0;
    padding: 20px;
    width: 600px;
    height: max-content;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .course-description-box {
    background-color: var(--light-1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    width: 1300px;
    margin: 50px auto;
    padding: 20px;
    border-radius: 20px;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }

  .featured {
    justify-content: space-between;
    background: #f8f8ff;
    border: 1px solid rgba(0, 0, 0, 0.08);
    border-radius: 20px;
    width: 100%;
    min-height: 550px;
    padding: 50px 30px;
    margin: 30px 0;
    display: flex;
    overflow: auto;

    scrollbar-width: none;
    -ms-overflow-style: none;
  }

  .administrator {
    display: flex;
    justify-content: center;
    background-color: var(--light-1);
    border-radius: 20px;
    padding: 20px;
    width: 1300px;
    margin: 50px auto;
    flex-direction: column;
    align-items: center;
  }

  .administrator #title p {
    font-size: 30px;
    font-weight: bold;
    color: black;
  }

  .provider-box {
    background-color: var(--light-1);
    border-radius: 20px;
    padding: 20px;
    width: 50%;
    height: 50%;
    box-shadow: 0 0 8px 2px rgba(0, 0, 0, 0.1);
  }
}



.course-page-background {
  font-family: Inter, sans-serif;
  background-color: var(--light-3);
  color: #090909;
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
  cursor: pointer;
}

.button-disabled {
   background-color: grey;
   cursor: not-allowed;
 }

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

h1 {
  font-size: 30px;
}

h3 {
  font-weight: bold;
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


.course-description-box h3{
  font-size: 20px;
  font-weight: bold;
}

#courseDescription {
  color: #282828;
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


.content-hbox {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: auto;
  justify-content: space-between;
}

hr {
  border: 0;
  height: 3px;
  background:  #6c6c6c;
  margin: 20px 0;
}

.courseInfoElement {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  width: max-content;
}

.courseInfoElement p {
  font-weight: bold;
  color: #575757;
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


.map-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-top: 20px;
  align-items: center;
  width: 100%;
  margin: auto;
  border-radius: 20px;
}
#map {
  height: 400px;
  max-width: 1400px;
  object-fit: cover;
  border-radius: 15px;
  transition: transform 0.5s ease;
}

button {
  background-color: transparent;
  border-color: transparent;
}

.header-button .text {
  font-size: 0;
  width: 0;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.header-button:hover .text {
  font-size: medium;
  opacity: 1;
}

.enroll-button {
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  color: white;
  font-size: 1.04em;
  background-color: grey;
  text-decoration: none;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70%;
  height: 50px;
  border-radius: 12px;
  transition: all .5s;
  margin-right: 10px;
  pointer-events: none;
}

.enroll-button:hover {
  background-color: #6E67FC;
  box-shadow: 0 -1px 0 rgba(0, 0, 0, .04), 0 2px 4px rgba(0, 0, 0, .25);
}

.enroll-button:active {
  transform: scale(0.95);
  transition-duration: .5s;
}

.pro-enroll-button {
  font-family: 'Inter', sans-serif;
  font-weight: bold;
  color: white;
  font-size: 1.04em;
  background: #efb94f;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70%;
  height: 50px;
  border-radius: 12px;
  cursor: pointer;
  transition: all .5s;
  margin-right: 10px;
}

.cartButton {
  width: 40%;
  height: 50px;
  border-radius: 12px;
  border: none;
  background-color: grey;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition-duration: .5s;
  overflow: hidden;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.103);
  position: relative;
  pointer-events: none;
}

.IconContainer {
  position: absolute;
  left: -50px;
  width: 30px;
  height: 30px;
  background-color: transparent;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  z-index: 2;
  transition-duration: .5s;
}

.text {
  height: 100%;
  width: fit-content;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgb(255, 255, 255);
  z-index: 1;
  transition-duration: .5s;
  font-size: 1.04em;
  font-weight: 600;
}

.CartBtn:hover .IconContainer {
  transform: translateX(58px);
  border-radius: 40px;
  transition-duration: .5s;
}

.CartBtn:hover .text {
  transform: translate(10px,0px);
  transition-duration: .5s;
}

.CartBtn:active {
  transform: scale(0.95);
  transition-duration: .5s;
}

.addProvider {
  background-color: var(--light-3);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
  width: 100%;
}

.provider-form {
  margin-bottom: 10px;
}

.administrator-content button {
  background-color: #e8e8e8;
}

.administrator hr {
  width: 100%;
  margin: 10px 0 10px 0;
  background-color: black;
}

.administrator-content {
  display: flex;
  flex-direction: column;
  align-items: start;
  width: 100%;
}

.active-status {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

</style>
