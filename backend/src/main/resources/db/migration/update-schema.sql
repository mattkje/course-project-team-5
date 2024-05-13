CREATE DATABASE IF NOT EXISTS learniversedb;
USE learniversedb;

CREATE TABLE course_keywords
(
    course_keyword_id INT AUTO_INCREMENT NOT NULL,
    course_id         INT                NULL,
    keyword_id        INT                NULL,
    CONSTRAINT pk_course_keywords PRIMARY KEY (course_keyword_id)
);

CREATE TABLE course_providers
(
    course_provider_id INT AUTO_INCREMENT NOT NULL,
    course_id          INT                NULL,
    provider_id        INT                NULL,
    price              FLOAT              NULL,
    currency           VARCHAR(255)       NULL,
    latitude           DECIMAL(9, 6)      NULL,
    longitude          DECIMAL(9, 6)      NULL,
    CONSTRAINT pk_course_providers PRIMARY KEY (course_provider_id)
);

CREATE TABLE courses
(
    course_id              INT           NOT NULL,
    title                  VARCHAR(255)  NULL,
    category               VARCHAR(100)  NULL,
    level                  VARCHAR(50)   NULL,
    closest_course_session VARCHAR(255)  NULL,
    course_size            DOUBLE        NULL,
    hours_per_week         INT           NULL,
    related_certifications VARCHAR(255)  NULL,
    `description`          VARCHAR(6000) NULL,
    image                  VARCHAR(255)  NULL,
    CONSTRAINT pk_courses PRIMARY KEY (course_id)
);

CREATE TABLE currencies
(
    code   VARCHAR(255)   NOT NULL,
    name   VARCHAR(255)   NULL,
    symbol VARCHAR(255)   NULL,
    rate   DECIMAL(10, 2) NULL,
    CONSTRAINT pk_currencies PRIMARY KEY (code)
);

CREATE TABLE keywords
(
    keyword_id   INT          NOT NULL,
    keyword_name VARCHAR(255) NULL,
    CONSTRAINT pk_keywords PRIMARY KEY (keyword_id)
);

CREATE TABLE providers
(
    provider_id INT          NOT NULL,
    name        VARCHAR(255) NULL,
    CONSTRAINT pk_providers PRIMARY KEY (provider_id)
);

CREATE TABLE community_courses (
                                   id INT AUTO_INCREMENT,
                                   author VARCHAR(20),
                                   title VARCHAR(255),
                                   category VARCHAR(100),
                                   description VARCHAR(100),
                                   content VARCHAR(6000),
                                   post_date   datetime,
                                   image VARCHAR(255),
                                   PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id INT    NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id           INT AUTO_INCREMENT NOT NULL,
    username     VARCHAR(16)        NULL,
    email        VARCHAR(45)        NULL,
    password     VARCHAR(64)        NULL,
    first_name   VARCHAR(20)        NULL,
    last_name    VARCHAR(20)        NULL,
    active       BIT(1)             NOT NULL,
    created_at   datetime           NULL,
    updated_at   datetime           NULL,
    phone_number VARCHAR(20)        NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE user_courses
(
    user_course_id INT AUTO_INCREMENT NOT NULL,
    user_id  INT NOT NULL,
    course_id INT NOT NULL,
    CONSTRAINT pk_user_courses PRIMARY KEY (user_course_id)
);

ALTER TABLE course_keywords
    ADD CONSTRAINT FK_COURSE_KEYWORDS_ON_KEYWORD FOREIGN KEY (keyword_id) REFERENCES keywords (keyword_id);

ALTER TABLE course_providers
    ADD CONSTRAINT FK_COURSE_PROVIDERS_ON_PROVIDER FOREIGN KEY (provider_id) REFERENCES providers (provider_id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_courses
    ADD CONSTRAINT fk_userc_on_course FOREIGN KEY (course_id) REFERENCES courses (course_id);

INSERT INTO users (id, username, email, password, first_name, last_name, active, created_at, updated_at,
                   phone_number)
VALUES
    -- PASSWORD: Dave2002
    (1, 'dave', 'dave@mail.com', '$2a$10$eizgqz56BxeRy2Bt7bYCbOMIyrUlFE0Rz8oSoMFILqd4mQcTXLGfi', 'Dave', 'Johnson',
     true, '2024-02-12', '2024-02-12',
     '234098434'),
    -- PASSWORD: Nunchucks
    (2, 'chuck', 'hideyograndma@mail.com', '$2a$10$2f1AbpqP36WOiR1h9nHR9e.BZRhwRSQR7FD2sXDZk5orPd.IO2u2m', 'Chuck',
     'Norris', true, '2024-02-12', '2024-02-12',
     '123123442'),
    -- PASSWORD: JoeBiden
    (3, 'john_doe', 'john.doe@example.com', '$2a$10$sCuzU/RymH5ZJrGUFxEf5uT4zSJEpr/J4oDSRrrQi5SrFXjNy.Zsq', 'John',
     'Doe', true, '2024-02-12', '2024-02-12',
     '+1234567890');



INSERT INTO courses (course_id, category, title, level, closest_course_session, course_size, hours_per_week,
                     related_certifications, description, image)
VALUES (1, 'Information Technology', 'Real-Time Programming in Java', 'Expert', '03.06 - 28.06', 7.5, 40,
        'Java SE 17 Programmer Professional',
        'Embark on a transformative learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization, and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions. Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement real-time solutions confidently. Elevate your Java programming expertise to new heights and stay ahead in the ever-evolving landscape of real-time systems with our comprehensive and immersive course.',
        'https://media.istockphoto.com/id/1319189527/photo/professional-woman-working-from-home.jpg?s=612x612&w=0&k=20&c=YRJg2VQKunBqEqZh3RlV-xehcBRHhNF9gwdcW7x_lLo='),
       (2, 'Information Technology', 'Introduction to SQL Essentials',
        'Beginner', '10.06 - 28.06', 2, 20, 'SQL Fundamentals',
        'Dive into the fundamentals of database management with our beginner-level online course, "Introduction to SQL Essentials." Geared towards those new to the world of databases and SQL, this course provides a comprehensive foundation for understanding and utilizing SQL for effective data management. While MySQL is touched upon to broaden your practical knowledge, the core focus is on SQL''s universal principles applicable across various database systems. Led by seasoned instructors, the course covers database design, querying data, and basic data manipulation using SQL commands. With a hands-on approach, you''ll engage in practical exercises to reinforce your learning, ensuring you gain the skills necessary to navigate and interact with databases confidently. Whether you''re a budding developer, analyst, or anyone eager to harness the power of databases, this course offers an accessible entry point into the world of SQL, setting the stage for your future success in data-driven environments.',
        'https://thumb.tildacdn.com/tild6238-3035-4335-a333-306335373139/-/format/webp/IMG_3349.jpg'),
       (3, 'Information Technology', 'Creating Web Application with .Net',
        'Beginner', '05.08 - 16.08', 4, 40, '.Net Developer Fundamentals',
        'Embark on your journey into web development with our beginner-level online course, Creating Web Applications with .NET. Tailored for those stepping into the dynamic world of web development, this course provides a solid foundation in utilizing the versatile .NET framework to build powerful and interactive web applications. Guided by experienced instructors, you will explore fundamental concepts such as web application architecture, user interface design, and server-side scripting using .NET technologies like ASP.NET. Throughout the course, you will engage in hands-on projects that walk you through the entire development process, from designing responsive user interfaces to implementing server-side functionality. Gain practical skills in C# programming and discover how to leverage the robust features of .NET to bring your web applications to life. Whether you are a programming novice or transitioning from another language, this course offers a welcoming entry point into the exciting realm of web application development with .NET, setting you on a path to create dynamic and engaging online experiences.',
        'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/223525883/original/75355314f2634045dbac0e3ba746934e4fa701eb/do-develop-in-asp-net-mvc-asp-net-core-asp-net-web-api.jpg'),
       (4, 'Information Technology', 'Azure Cloud Fundamentals', 'Beginner',
        '05.08 - 30.08', 2, 10, 'AZ-900 Azure Fundamentals',
        'Embark on your cloud computing journey with our beginner-level online course, "Azure Fundamentals," meticulously crafted to prepare you for the AZ-900 exam. Whether you are new to cloud technologies or seeking to validate your foundational knowledge, this course provides a comprehensive introduction to Microsoft Azure, one of the industrys leading cloud platforms. Delve into the essentials of cloud concepts, Azure services, pricing, and compliance, all while guided by expert instructors who understand the importance of building a strong cloud foundation. Through a combination of engaging lectures, hands-on labs, and real-world scenarios, you will gain practical insights into deploying solutions on Azure and mastering fundamental cloud principles. By the end of the course, youll not only be well-prepared to ace the AZ-900 exam but will also have a solid understanding of Azures capabilities, empowering you to confidently navigate the vast landscape of cloud computing. Join us on this educational journey and unlock the potential of cloud technology with Azure Fundamentals.',
        'https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_azure.jpg'),
       (5, 'Information Technology', 'Azure Administration (Intermediate)',
        'Intermediate', '02.09 - 20.12', 4, 5,
        'AZ-104 Microsoft Certified Cloud Administrator',
        'Elevate your cloud expertise with our intermediate-level online course, "Azure Administrator," meticulously designed to prepare you for the AZ-104 exam – your gateway to becoming a Microsoft Certified Cloud Administrator. Tailored for individuals with a foundational understanding of Azure, this course takes a deep dive into advanced administration and management tasks, honing the skills required for efficient cloud operations. Led by seasoned Azure professionals, youll explore intricate topics such as virtual networking, identity management, and governance strategies, gaining hands-on experience through practical exercises and real-world scenarios. The courses comprehensive coverage aligns seamlessly with the AZ-104 exam objectives, ensuring that you not only pass the certification but emerge as a proficient Azure Administrator capable of implementing robust cloud solutions. Whether youre looking to enhance your career or solidify your position as a cloud expert, this course is your key to mastering the intricacies of Azure administration and achieving Microsoft Certified Cloud Administrator status. Join us on this transformative journey towards advanced Azure proficiency.',
        'https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_azure.jpg'),
       (6, 'Information Technology', 'AWS Cloud Practitioner', 'Beginner',
        '09.09 - 20.09', 2, 20, 'CLF-C02 AWS Certified Cloud Practitioner',
        'Discover the fundamentals of cloud computing in our beginner-level online course, "AWS Cloud Practitioner," designed to prepare you for the CLF-C02 certification exam. Tailored for individuals with minimal prior experience in cloud technologies, this course provides a robust foundation in understanding the essential concepts of Amazon Web Services (AWS). Led by experienced AWS professionals, the course delves into core topics, including cloud architecture, AWS services, security, and pricing models. Through dynamic lectures and hands-on labs, youll gain practical insights into navigating the AWS console, setting up basic infrastructure, and comprehending key cloud principles. By the courses end, youll be well-equipped to excel in the CLF-C02 exam and possess a foundational understanding of AWS, empowering you to confidently explore and leverage cloud services. Join us in this educational journey, and initiate your AWS Cloud Practitioner certification with assurance and proficiency.',
        'https://blog.adobe.com/en/publish/2021/08/31/media_1649ebc3fbbce0df508081913819d491fc3f7c7a9.png?width=750&format=png&optimize=medium'),
       (7, 'Digital Marketing', 'Search Engine Optimization',
        'Intermediate', '05.08 - 30.08', 2, 4, 'SEO Wizard',
        'Deepen your expertise in the digital landscape with our intermediate-level online course, "Search Engine Optimization (SEO)." Tailored for marketers, business owners, and digital enthusiasts, this course offers an immersive exploration into the intricate world of SEO, empowering you to elevate your online visibility and drive targeted traffic to your website. Led by seasoned SEO professionals, the course covers advanced topics such as keyword research, on-page optimization, technical SEO, and link building strategies, providing practical insights and actionable techniques for optimizing your digital presence. Through a blend of theoretical knowledge and hands-on exercises, youll learn how to develop effective SEO strategies, analyze website performance metrics, and stay ahead of search engine algorithm updates. Whether youre looking to enhance your career in digital marketing or expand your businesss online reach, this course equips you with the skills and knowledge needed to thrive in the competitive landscape of search engine optimization.',
        'https://www.rgbwebtech.com/blogs/images/uploads/what-is-seo-search-engin-optimization.png'),
       (8, 'Digital Marketing', 'Social Media Marketing', 'Intermediate',
        '05.08 - 30.08', 2, 4, 'Certified Social Alchemist',
        'Elevate your digital marketing prowess with our intermediate-level online course, "Social Media Marketing." Tailored for marketers, business professionals, and enthusiasts seeking to harness the power of social platforms, this course provides an in-depth exploration of advanced social media marketing strategies. Led by industry experts, youll delve into nuanced topics such as audience targeting, content optimization, social media advertising, and analytics. Through a blend of theoretical insights and hands-on exercises, youll gain practical skills to create compelling social media campaigns, foster audience engagement, and measure the impact of your efforts. Stay at the forefront of digital marketing trends by mastering the art of crafting impactful narratives, building brand loyalty, and leveraging diverse social channels. Whether you aim to enhance your business online presence or advance your career in digital marketing, this course equips you with the tools and knowledge to navigate the dynamic landscape of social media marketing with confidence and proficiency. Join us and amplify your social media marketing expertise with our intermediate-level course.',
        'https://hotdogmarketing.net/wp-content/uploads/2021/11/110321-HDM-Social-Media-Marketing-02-1024x576.png'),
       (9, 'Business and Entrepreneurship', 'Business Strategy', null, '03.06 - 29.11', 10, 10,
        'Certified Strategic Business Architect (CSBA)',
        'Master the art of strategic thinking with our expert-level online course, "Business Strategy." Tailored for seasoned professionals, entrepreneurs, and strategic leaders, this course offers an immersive exploration of advanced business strategy concepts and applications. Led by industry thought leaders, you''ll delve into intricate topics such as competitive analysis, market positioning, disruptive innovation, and global strategic management. Through case studies, simulations, and real-world scenarios, youll sharpen your ability to make informed strategic decisions that drive long-term success. This course goes beyond the basics, challenging you to synthesize diverse business elements into a cohesive and forward-thinking strategy. Whether you aspire to lead a multinational corporation or refine your entrepreneurial ventures, our expert-level Business Strategy course empowers you to navigate complex business landscapes with foresight and precision. Join us in this transformative learning journey and elevate your strategic acumen to new heights.',
        'https://granthigginson.com/wp-content/uploads/2017/09/business-strategy.jpg'),
       (10, 'Data Science and Analytics', 'Machine Learning Basics with Python',
        'Beginner', '19.08 - 30.08', 2, 10,
        'Machine Learning Fundamentals',
        'Embark on your journey into the exciting realm of artificial intelligence with our beginner-level online course, Machine Learning Basics with Python. Tailored for individuals new to the world of machine learning, this course provides a comprehensive introduction to the fundamental concepts and techniques using the versatile Python programming language. Led by experienced instructors, youll explore the basics of supervised and unsupervised learning, delve into popular machine learning algorithms, and gain hands-on experience through practical exercises. No prior coding experience is required, making this course an ideal starting point for beginners eager to grasp the essentials of machine learning. By the end of the course, youll have a solid foundation in using Python for machine learning applications, empowering you to unravel the mysteries of data and embark on a fascinating journey into the world of intelligent algorithms. Join us and demystify the basics of machine learning with Python in this accessible and empowering course.',
        'https://miro.medium.com/v2/resize:fit:1358/1*cG6U1qstYDijh9bPL42e-Q.jpeg'),
       (11, 'Data Science and Analytics', 'Image Recognition',
        'Intermediate', '02.09 - 27.09', 4, 20, 'Machine Vision Associate',
        'Deepen your expertise in the realm of artificial intelligence with our intermediate-level online course, "Image Recognition with Python." Tailored for those with a foundational understanding of machine learning, this course immerses you in the intricacies of image recognition techniques and technologies using the powerful Python programming language. Led by seasoned instructors, youll explore advanced concepts such as convolutional neural networks (CNNs), image preprocessing, and transfer learning. Through hands-on projects and real-world applications, youll sharpen your skills in training models to recognize and classify images with precision. This course is ideal for individuals looking to expand their knowledge in computer vision and image processing, and it serves as a stepping stone for professionals aspiring to integrate image recognition capabilities into their projects. Join us in this intermediate-level course, and unlock the potential of image recognition with Python, advancing your proficiency in the exciting field of artificial intelligence.',
        'https://www.perficient.com/-/media/images/insights/hero-images/research_image-recognition-accuracy-study_hero.ashx?h=931&iar=0&w=1400&hash=E0AED7417F234D3C66F9037149BB7612'),
       (12, 'Data Science and Analytics', 'Databricks fundamentals', 'Beginner',
        '19.08 - 30.08', 2, 10, 'Databricks Practitioner',
        'Embark on your data journey with our beginner-level online course, Databricks Fundamentals. Designed for individuals new to the world of big data and analytics, this course offers a comprehensive introduction to the essential concepts of Databricks, a leading unified analytics platform. Led by experienced instructors, youll navigate through the fundamentals of data exploration, data engineering, and collaborative data science using Databricks. No prior experience with big data technologies is required, making this course an ideal starting point for beginners eager to harness the power of Databricks for streamlined data processing and analysis. By the end of the course, youll have a solid foundation in using Databricks to uncover insights from large datasets, setting you on a path towards mastering the intricacies of modern data analytics. Join us and demystify the fundamentals of Databricks in this accessible and empowering course.',
        'https://www.databricks.com/sites/default/files/2023-11/databricks-og-universal.png'),
       (13, 'Data Science and Analytics', 'Håkon Sex Course', 'Beginner',
        '19.08 - 30.08', 2, 10, 'Certified gay',
        'Håkon will show you his willy and such...',
        'https://github.com/mattkje/Paths/blob/e79ddbafb1992dec6dea7ca006c37754a7ae0cdc/Data/currentGpaths/Your%20Baby.png?raw=true');

INSERT INTO providers (provider_id, name)
VALUES (1, 'NTNU'),
       (2, 'Oracle'),
       (3, 'Apache Software Foundation'),
       (4, 'Pearson'),
       (5, 'Microsoft'),
       (6, 'Amazon'),
       (7, 'Adobe'),
       (8, 'Apple'),
       (9, 'Google'),
       (10, 'Handelshøyskolen BI'),
       (11, 'University of Oslo'),
       (12, 'University of Bergen'),
       (13, 'Learniverse');

INSERT INTO course_providers (course_id, provider_id, price, currency, latitude, longitude)
VALUES (1, 1, 29999.00, 'NOK', 62.49151100481676, 6.2356728695082415),
       (1, 2, 32000.00, 'NOK', 51.507351, -0.127758),
       (2, 3, 800.00, 'USD', 48.856614, 2.352222),
       (2, 1, 10000.00, 'NOK', 63.51776318231693, 10.424351177364008),
       (4, 4, 899.00, 'USD', -33.868820, 151.209296),
       (3, 5, 2999.00, 'NOK', 34.052235, -118.243683),
       (3, 4, 3000.00, 'NOK', 41.902782, 12.496366),
       (3, 2, 200.00, 'USD', -23.550520, -46.633308),
       (4, 5, 200.00, 'USD', 28.613939, 77.209023),
       (4, 1, 1800.00, 'NOK', 62.49151100481676, 6.2356728695082415),
       (4, 4, 200.00, 'USD', 40.712776, -74.005974),
       (5, 5, 400.00, 'USD', 51.507351, -0.127758),
       (5, 1, 3600.00, 'NOK', 63.51776318231693, 10.424351177364008),
       (5, 4, 400.00, 'USD', 35.689487, 139.691706),
       (6, 6, 100.00, 'USD', -33.868820, 151.209296),
       (6, 4, 120.00, 'USD', 34.052235, -118.243683),
       (6, 1, 1800.00, 'NOK', 60.88667108646742, 10.646121369851581),
       (7, 7, 66000.00, 'NOK', -23.550520, -46.633308),
       (7, 8, 80000.00, 'NOK', 28.613939, 77.209023),
       (7, 9, 6000.00, 'USD', 39.904200, 116.407396),
       (7, 5, 6000.00, 'USD', 40.712776, -74.005974),
       (7, 6, 6000.00, 'USD', 51.507351, -0.127758),
       (8, 7, 66000.00, 'NOK', 48.856614, 2.352222),
       (8, 8, 80000.00, 'NOK', 35.689487, 139.691706),
       (8, 9, 6000.00, 'USD', -33.868820, 151.209296),
       (8, 5, 6000.00, 'USD', 34.052235, -118.243683),
       (8, 6, 6000.00, 'USD', 41.902782, 12.496366),
       (9, 1, 50000.00, 'NOK', 60.88667108646742, 10.646121369851581),
       (9, 5, 50000.00, 'NOK', 28.613939, 77.209023),
       (9, 10, 50000.00, 'NOK', 60.015120210169904, 10.75110418722284),
       (10, 1, 20000.00, 'NOK', 63.51776318231693, 10.424351177364008),
       (10, 11, 20000.00, 'NOK', 59.94055289029062, 10.721540432557507),
       (10, 12, 20000.00, 'NOK', 60.387980480834656, 5.321840740337752),
       (11, 1, 30000.00, 'NOK', 62.49151100481676, 6.2356728695082415),
       (11, 11, 30000.00, 'NOK', 59.94055289029062, 10.721540432557507),
       (11, 12, 30000.00, 'NOK', 60.387980480834656, 5.321840740337752),
       (12, 1, 20000.00, 'NOK', 63.51776318231693, 10.424351177364008),
       (12, 11, 20000.00, 'NOK', 59.94055289029062, 10.721540432557507),
       (12, 12, 20000.00, 'NOK', 60.387980480834656, 5.321840740337752),
       (13, 12, 20000.00, 'NOK', 60.387980480834656, 5.321840740337752),
       (14, 13, 9.99, 'SUB', null, null),
       (15, 13, 9.99, 'SUB', null, null),
       (16, 13, 9.99, 'SUB', null, null),
       (17, 13, 9.99, 'SUB', null, null),
       (18, 13, 9.99, 'SUB', null, null),
       (19, 12, 1000.00, 'USD', 60.387980480834656, 5.321840740337752),
       (20, 11, 100.00, 'USD', 59.94055289029062, 10.721540432557507);

INSERT INTO keywords (keyword_id, keyword_name)
VALUES (1, 'Java'),
       (2, 'Python'),
       (3, 'C+');

INSERT INTO course_keywords (course_id, keyword_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 3),
       (1, 3);


-- Exclusive courses

INSERT INTO courses (course_id, category, title, level, closest_course_session, course_size, hours_per_week,
                     related_certifications, description, image)
VALUES (14, 'Data Science and Analytics', 'Data Visualization with Python',
        'Intermediate', '01.10 - 30.10', 2, 15,
        'Python Data Visualization Specialist',
        'Dive into the world of data visualization with our intermediate-level online course, "Data Visualization with Python." This course is designed for individuals with a basic understanding of Python and data science, and it aims to provide a comprehensive introduction to the use of Python in creating meaningful and insightful data visualizations. Led by experienced instructors, you will learn how to use popular Python libraries such as Matplotlib and Seaborn to create plots, charts, and other visual representations of data. By the end of the course, you will have a solid foundation in using Python for data visualization, enabling you to effectively communicate data-driven insights.',
        'https://datascientest.com/en/wp-content/uploads/sites/9/2023/09/matplotlib-graphs-1024x576.png'),
       (15, 'Software Development', 'Advanced Java Programming',
        'Advanced', '01.05 - 30.05', 4, 20,
        'Oracle Certified Professional, Java SE 8 Programmer',
        'This course is designed for students who are looking for an in-depth understanding of Java and OO principles. This course takes a deep dive into the intricacies of Java, covering topics such as exception handling, Java I/O, collections, generics, threads, JDBC, and more.',
        'https://www.mindsmapped.com/wp-content/uploads/2016/08/Advanced-Java-J2EE.jpeg'),
       (16, 'Software Development', 'Web Development with JavaScript',
        'Intermediate', '01.06 - 30.06', 3, 15,
        'JavaScript Developer Certificate',
        'This course covers the essential concepts and techniques for programming in JavaScript. Students will learn how to create dynamic web pages using JavaScript, including topics such as data types, control structures, functions, objects, arrays, and the Document Object Model (DOM).',
        'https://www.digitalsilk.com/wp-content/uploads/2020/06/website-development-process-hero-image.png.webp'),
       (17, 'Software Development', 'Advanced C++ Programming',
        'Advanced', '01.07 - 30.07', 4, 20,
        'C++ Certified Professional Programmer',
        'This course is designed for students who are looking for an in-depth understanding of C++ and OO principles. This course takes a deep dive into the intricacies of C++, covering topics such as exception handling, C++ I/O, collections, generics, threads, and more.',
        'https://familycomputercentre.com/wp-content/uploads/fp-cpp-adv.jpg'),
       (18, 'Data Science and Analytics', 'Data Analysis with R',
        'Intermediate', '01.08 - 30.08', 3, 15,
        'R Certified Data Analyst',
        'This course covers the essential concepts and techniques for data analysis in R. Students will learn how to manipulate and analyze data using R, including topics such as data types, control structures, functions, objects, arrays, and data visualization.',
        'https://www.orbitanalytics.com/wp-content/uploads/2019/06/R-language-advanced-analytics1024x560.jpg');

INSERT INTO course_providers (course_id, provider_id, price, currency)
VALUES (14, 13, 9.99, 'SUB');


-- Extra courses

INSERT INTO courses (course_id, category, title, level, closest_course_session, course_size, hours_per_week,
                     related_certifications, description, image)
VALUES (19, 'Digital Marketing', 'Advanced SEO Techniques', 'Advanced', '01.07 - 30.07', 4, 20,
        'Google Analytics Individual Qualification',
        'This course covers advanced SEO techniques including keyword research, link building, and technical SEO. Students will learn how to optimize their websites for search engines and improve their website''s ranking on search engine results pages.',
        null),
       (20, 'Digital Marketing', 'Social Media Marketing Strategies', 'Intermediate', '01.08 - 30.08', 3, 15,
        'Hootsuite Social Marketing Certification',
        'This course covers the essential concepts and techniques for social media marketing. Students will learn how to create engaging content, use analytics to measure success, and develop a comprehensive social media marketing strategy.',
        null);

INSERT INTO currencies (code, name, symbol, rate)
VALUES ('USD', 'United States Dollar', '$', 1),
       ('EUR', 'Euro', '€', 0.93),
       ('GBP', 'British Pound', '£', 0.80),
       ('JPY', 'Japanese Yen', '¥', 151.68),
       ('AUD', 'Australian Dollar', '$', 1.54),
       ('CAD', 'Canadian Dollar', '$', 1.36),
       ('CHF', 'Swiss Franc', 'CHF', 0.91),
       ('CNY', 'Chinese Yuan', '¥', 7.24),
       ('SEK', 'Swedish Krona', 'kr', 10.80),
       ('NZD', 'New Zealand Dollar', '$', 1.68),
       ('NOK', 'Norwegian Krone', 'kr', 10.95);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_PRO'),
       (3, 'ROLE_ADMIN');

INSERT INTO community_courses (title, author, category, description, content, post_date, image)
VALUES
    ('Introduction to Python', 'john_doe','Programming', 'This course will introduce the basics of Python programming language.','This is the course content','2024-02-02', null),
    ('Web Development with JavaScript', 'chucknorris21', 'Web Development', 'This course covers the essential concepts and techniques for web development using JavaScript.','This is the course content','2024-02-12',  null),
    ('Data Structures in Java', 'woodpecker4', 'Data Structures', 'This course provides a comprehensive introduction to data structures using Java.','This is the course content','2024-03-16',  null),
    ('Machine Learning with R', 'waltjohnson', 'Machine Learning', 'This course covers the fundamental concepts of machine learning using R.','This is the course content','2024-03-24',  null),
    ('Database Management with SQL', 'john_doe', 'Database', 'This course provides a comprehensive introduction to SQL and database management.','This is the course content','2024-04-07',  'https://s33046.pcdn.co/wp-content/uploads/2022/09/options-command.png');

INSERT INTO user_courses (user_id, course_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 1),
       (2, 3),
       (3, 4);

INSERT INTO user_roles (role_id, user_id)
VALUES (1, 1),
       (3, 2),
       (1, 2),
       (2, 2),
       (1, 3),
       (2, 3);
