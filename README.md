# course-project-team-5
Exam project for the IDATA2301 and IDATA2306 courses

<p align="center">
  <img src="design/logoApp.svg" alt="Logo" style="width:25%;height:25%;">
</p>


## About the project

This projects aim to create a price aggregator for courses within the computer industry. The project currently offers IT, digital marketing, business, data science, and software-development-courses from a range of providers. This project also features in-house courses with a subscription model attached.

The website is currently hosted [here](http://connect.learniverse.store/)

The website is designed according to:
- Requirements from a customer
- Design guidelines
- Accessibility rules
- Using the best software dev practices: sprints, GIT
- Javascript for interaction and dynamic updates
- Communication with a backend API

An example of a competitor website: https://www.coursera.org

## Technologies Used

This project is built using two frameworks, each representing a different side of the web application. The backend is built using Spring Boot, while the frontend is built using Vue.js. Below is a brief description of each of the:

<div style="display: flex; flex-direction: column">

## [Spring Boot](https://spring.io/projects/spring-boot)

<div style="display: flex">

Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". It takes an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss.

<img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="Spring Boot Logo" width="15%" height="15%">

</div>
</div>

<div style="display: flex; flex-direction: column">

## [Vue.js](https://vuejs.org/ )

<div  style="display: flex">

Vue.js is a progressive JavaScript framework for building user interfaces. Unlike other monolithic frameworks, Vue is designed from the ground up to be incrementally adoptable.

<img src="https://vuejs.org/images/logo.png" alt="Vue.js Logo" width="15%" height="15%">

</div>

</div>

## How To
Before you begin, ensure you have installed the latest version of Node.js.

 ```
   Setup MySQL -  Setup New Connection - Host: localhost Port 3306 - User: root - Password: 'Your password'
   run /backend/src/main/resources/db.migration/update-schema.sql
   run /backend/src/main/java/no/ntnu/Application (This has to be configured with a .env file, which is not provided)

   run 'npm install' to install dependencies.
   To set up the frontend, you have to set up a npm configuration which runs 'run' as command 
    and 'serve' as Scripts (you have to select the /frontend/package.json) as package.json.
```

## Requirements

### Functional requirements
1. An online web shop with landing page, price
   comparison page, deal and discount information,
   and product filter and search.
2. Application state changes during the lifecycle
   (example: booking a hotel)
3. DOM modification with Javascript
4. At least one REST API request

### Non-functional requirements
1. Responsive design
2. Accessibility principles
3. Aesthetically pleasing
4. Follow a given theme/genre
5. Code stored in Git
6. Agile work in Sprints
7. Reasonable code design and structure

### Possible extras
- Authentication (frontend side)
- Secured site with HTTPS (when on your own server)
- Containers for the solution (docker-compose)
- Emails
- Advanced functionality beyond the minimum (wish lists,
shareable links, stock of products)
- Live updates with WebSockets

**All extras must be web-related (no extras for statistics)**

## Contributors

- Adrian Johansen
- Di Xie
- Håkon Svensen Karlsen
- Matti Kjellstadli

## External resources

- **[Documentation](https://drive.google.com/drive/folders/1dnp1F-qoZYDw4lbvyHWyZIOfUUeed7VV)** Google Drive folder containing all documents.
- **[Wireframes](https://www.figma.com/file/5t3L0Wnz9uqJoAvucoNp63/IDATA-2301-%26-2306-Wireframes?type=design&node-id=0%3A1&mode=design&t=kIWWhxp0uJbGogJn-1)** Sketches for the website, reference point for building web application.
- **[Mindmap](https://mm.tt/app/map/3112803398?t=xNWqS5i0rP)** Ideas and notes related to the project.
