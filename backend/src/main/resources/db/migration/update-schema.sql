CREATE DATABASE IF NOT EXISTS learniversedb;
USE learniversedb;

CREATE TABLE IF NOT EXISTS course_keywords
(
    course_keyword_id INT AUTO_INCREMENT NOT NULL,
    course_id         INT                NULL,
    keyword_id        INT                NULL,
    CONSTRAINT pk_course_keywords PRIMARY KEY (course_keyword_id)
);

CREATE TABLE IF NOT EXISTS course_providers
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

CREATE TABLE IF NOT EXISTS courses
(
    course_id              INT AUTO_INCREMENT NOT NULL,
    title                  VARCHAR(255)  NULL,
    category               VARCHAR(100)  NULL,
    level                  VARCHAR(50)   NULL,
    start_date             DATE          NULL,
    end_date               DATE          NULL,
    course_size            DOUBLE        NULL,
    hours_per_week         INT           NULL,
    related_certifications VARCHAR(255)  NULL,
    description            VARCHAR(6000) NULL,
    image                  VARCHAR(255)  NULL,
    active                 BIT(1)        NOT NULL DEFAULT 1,
    CONSTRAINT pk_courses PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS currencies
(
    code   VARCHAR(255)   NOT NULL,
    name   VARCHAR(255)   NULL,
    symbol VARCHAR(255)   NULL,
    rate   DECIMAL(10, 2) NULL,
    CONSTRAINT pk_currencies PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS keywords
(
    keyword_id   INT    AUTO_INCREMENT      NOT NULL,
    keyword_name VARCHAR(255) NULL,
    CONSTRAINT pk_keywords PRIMARY KEY (keyword_id)
);

CREATE TABLE IF NOT EXISTS providers
(
    provider_id INT         AUTO_INCREMENT,
    name        VARCHAR(255) NULL,
    CONSTRAINT pk_providers PRIMARY KEY (provider_id)
);

CREATE TABLE IF NOT EXISTS community_posts (
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

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    role_id BIGINT NOT NULL,
    user_id INT    NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE IF NOT EXISTS users
(
    id              INT AUTO_INCREMENT NOT NULL,
    username        VARCHAR(16)        NULL,
    email           VARCHAR(45)        NULL,
    password        VARCHAR(64)        NULL,
    first_name      VARCHAR(20)        NULL,
    last_name       VARCHAR(20)        NULL,
    image           LONGTEXT           NULL,
    active          BIT(1)             NOT NULL,
    created_at      datetime           NULL,
    updated_at      datetime           NULL,
    phone_number    VARCHAR(20)        NULL,
    subscription_expire date           NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_courses
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

INSERT INTO users (id, username, email, password, first_name, last_name, image, active, created_at, updated_at,
                   phone_number, subscription_expire)
VALUES
    -- PASSWORD: Dangerous2024
    (1, 'dave', 'dave@mail.com', '$2a$10$uJPj04nmHniCEvl9oJY2beZEUdR.vdtbVOvQhKcw5wgmHrBa4W.ou', 'Dave', 'Johnson', null,
     true, '2024-02-12', '2024-02-12',
     '234098434', null),
    -- PASSWORD: Nunchucks2024
    (2, 'chuck', 'hideyograndma@mail.com', '$2a$10$ymIsyzTMnOfwBW.aeMq2G.wbGi7xv5gbkYKNfj1Vp.WgHrAB.1Pia', 'Chuck',
     'Norris',
     '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'
     , true, '2024-02-12', '2024-02-12',
     '123123442', null),
    -- PASSWORD: JohnDoe2024
    (3, 'john_doe', 'john.doe@example.com', '$2a$10$QCFd4gAgjRceV8aXapSKReDkYXSHdcqkXKg5Lt4gdcozp53SnadSm', 'John',
     'Doe', null , true, '2024-02-12', '2024-02-12', '+1234567890', null),
    -- PASSWORD: Anne12345
    (4, 'anne', 'annedavies@mail.com', '$2a$10$SUyIHgfa3qPARl4l2/WzhOa08gm5bJ7/RlZGlphIxC7ZJVsO/q9pm', 'Anne',
     'Davies', null , true, '2024-02-12', '2024-02-12',
     '123123442', null);



INSERT INTO courses (course_id, category, title, level, start_date, end_date, course_size, hours_per_week,
                     related_certifications, description, image)
VALUES (1, 'Information Technology', 'Real-Time Programming in Java', 'Expert', '2024.06.03', '2024.06.28', 7.5, 40,
        'Java SE 17 Programmer Professional',
        'Embark on a transformative learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization, and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions. Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement real-time solutions confidently. Elevate your Java programming expertise to new heights and stay ahead in the ever-evolving landscape of real-time systems with our comprehensive and immersive course.',
        'https://media.istockphoto.com/id/1319189527/photo/professional-woman-working-from-home.jpg?s=612x612&w=0&k=20&c=YRJg2VQKunBqEqZh3RlV-xehcBRHhNF9gwdcW7x_lLo='),
        (2, 'Information Technology', 'Introduction to SQL Essentials',
        'Beginner', '2024.06.10','2024.06.28', 2, 20, 'SQL Fundamentals',
        'Dive into the fundamentals of database management with our beginner-level online course, "Introduction to SQL Essentials." Geared towards those new to the world of databases and SQL, this course provides a comprehensive foundation for understanding and utilizing SQL for effective data management. While MySQL is touched upon to broaden your practical knowledge, the core focus is on SQL''s universal principles applicable across various database systems. Led by seasoned instructors, the course covers database design, querying data, and basic data manipulation using SQL commands. With a hands-on approach, you''ll engage in practical exercises to reinforce your learning, ensuring you gain the skills necessary to navigate and interact with databases confidently. Whether you''re a budding developer, analyst, or anyone eager to harness the power of databases, this course offers an accessible entry point into the world of SQL, setting the stage for your future success in data-driven environments.',
        'https://thumb.tildacdn.com/tild6238-3035-4335-a333-306335373139/-/format/webp/IMG_3349.jpg'),
       (3, 'Information Technology', 'Creating Web Application with .Net',
        'Beginner', '2024.08.05','2024.08.16', 4, 40, '.Net Developer Fundamentals',
        'Embark on your journey into web development with our beginner-level online course, Creating Web Applications with .NET. Tailored for those stepping into the dynamic world of web development, this course provides a solid foundation in utilizing the versatile .NET framework to build powerful and interactive web applications. Guided by experienced instructors, you will explore fundamental concepts such as web application architecture, user interface design, and server-side scripting using .NET technologies like ASP.NET. Throughout the course, you will engage in hands-on projects that walk you through the entire development process, from designing responsive user interfaces to implementing server-side functionality. Gain practical skills in C# programming and discover how to leverage the robust features of .NET to bring your web applications to life. Whether you are a programming novice or transitioning from another language, this course offers a welcoming entry point into the exciting realm of web application development with .NET, setting you on a path to create dynamic and engaging online experiences.',
        'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/223525883/original/75355314f2634045dbac0e3ba746934e4fa701eb/do-develop-in-asp-net-mvc-asp-net-core-asp-net-web-api.jpg'),
       (4, 'Information Technology', 'Azure Cloud Fundamentals', 'Beginner',
        '2024.08.05','2024.08.30', 2, 10, 'AZ-900 Azure Fundamentals',
        'Embark on your cloud computing journey with our beginner-level online course, "Azure Fundamentals," meticulously crafted to prepare you for the AZ-900 exam. Whether you are new to cloud technologies or seeking to validate your foundational knowledge, this course provides a comprehensive introduction to Microsoft Azure, one of the industrys leading cloud platforms. Delve into the essentials of cloud concepts, Azure services, pricing, and compliance, all while guided by expert instructors who understand the importance of building a strong cloud foundation. Through a combination of engaging lectures, hands-on labs, and real-world scenarios, you will gain practical insights into deploying solutions on Azure and mastering fundamental cloud principles. By the end of the course, youll not only be well-prepared to ace the AZ-900 exam but will also have a solid understanding of Azures capabilities, empowering you to confidently navigate the vast landscape of cloud computing. Join us on this educational journey and unlock the potential of cloud technology with Azure Fundamentals.',
        'https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_azure.jpg'),
       (5, 'Information Technology', 'Azure Administration (Intermediate)',
        'Intermediate', '2024.09.02','2024.12.20', 4, 5,
        'AZ-104 Microsoft Certified Cloud Administrator',
        'Elevate your cloud expertise with our intermediate-level online course, "Azure Administrator," meticulously designed to prepare you for the AZ-104 exam – your gateway to becoming a Microsoft Certified Cloud Administrator. Tailored for individuals with a foundational understanding of Azure, this course takes a deep dive into advanced administration and management tasks, honing the skills required for efficient cloud operations. Led by seasoned Azure professionals, youll explore intricate topics such as virtual networking, identity management, and governance strategies, gaining hands-on experience through practical exercises and real-world scenarios. The courses comprehensive coverage aligns seamlessly with the AZ-104 exam objectives, ensuring that you not only pass the certification but emerge as a proficient Azure Administrator capable of implementing robust cloud solutions. Whether youre looking to enhance your career or solidify your position as a cloud expert, this course is your key to mastering the intricacies of Azure administration and achieving Microsoft Certified Cloud Administrator status. Join us on this transformative journey towards advanced Azure proficiency.',
        'https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_azure.jpg'),
       (6, 'Information Technology', 'AWS Cloud Practitioner', 'Beginner',
        '2024.09.09','2024.09.20', 2, 20, 'CLF-C02 AWS Certified Cloud Practitioner',
        'Discover the fundamentals of cloud computing in our beginner-level online course, "AWS Cloud Practitioner," designed to prepare you for the CLF-C02 certification exam. Tailored for individuals with minimal prior experience in cloud technologies, this course provides a robust foundation in understanding the essential concepts of Amazon Web Services (AWS). Led by experienced AWS professionals, the course delves into core topics, including cloud architecture, AWS services, security, and pricing models. Through dynamic lectures and hands-on labs, youll gain practical insights into navigating the AWS console, setting up basic infrastructure, and comprehending key cloud principles. By the courses end, youll be well-equipped to excel in the CLF-C02 exam and possess a foundational understanding of AWS, empowering you to confidently explore and leverage cloud services. Join us in this educational journey, and initiate your AWS Cloud Practitioner certification with assurance and proficiency.',
        'https://blog.adobe.com/en/publish/2021/08/31/media_1649ebc3fbbce0df508081913819d491fc3f7c7a9.png?width=750&format=png&optimize=medium'),
       (7, 'Digital Marketing', 'Search Engine Optimization',
        'Intermediate', '2024.08.05','2024.08.30', 2, 4, 'SEO Wizard',
        'Deepen your expertise in the digital landscape with our intermediate-level online course, "Search Engine Optimization (SEO)." Tailored for marketers, business owners, and digital enthusiasts, this course offers an immersive exploration into the intricate world of SEO, empowering you to elevate your online visibility and drive targeted traffic to your website. Led by seasoned SEO professionals, the course covers advanced topics such as keyword research, on-page optimization, technical SEO, and link building strategies, providing practical insights and actionable techniques for optimizing your digital presence. Through a blend of theoretical knowledge and hands-on exercises, youll learn how to develop effective SEO strategies, analyze website performance metrics, and stay ahead of search engine algorithm updates. Whether youre looking to enhance your career in digital marketing or expand your businesss online reach, this course equips you with the skills and knowledge needed to thrive in the competitive landscape of search engine optimization.',
        'https://www.rgbwebtech.com/blogs/images/uploads/what-is-seo-search-engin-optimization.png'),
       (8, 'Digital Marketing', 'Social Media Marketing', 'Intermediate',
        '2024.08.05','2024.08.30', 2, 4, 'Certified Social Alchemist',
        'Elevate your digital marketing prowess with our intermediate-level online course, "Social Media Marketing." Tailored for marketers, business professionals, and enthusiasts seeking to harness the power of social platforms, this course provides an in-depth exploration of advanced social media marketing strategies. Led by industry experts, youll delve into nuanced topics such as audience targeting, content optimization, social media advertising, and analytics. Through a blend of theoretical insights and hands-on exercises, youll gain practical skills to create compelling social media campaigns, foster audience engagement, and measure the impact of your efforts. Stay at the forefront of digital marketing trends by mastering the art of crafting impactful narratives, building brand loyalty, and leveraging diverse social channels. Whether you aim to enhance your business online presence or advance your career in digital marketing, this course equips you with the tools and knowledge to navigate the dynamic landscape of social media marketing with confidence and proficiency. Join us and amplify your social media marketing expertise with our intermediate-level course.',
        'https://hotdogmarketing.net/wp-content/uploads/2021/11/110321-HDM-Social-Media-Marketing-02-1024x576.png'),
       (9, 'Business and Entrepreneurship', 'Business Strategy', null, '2024.06.03','2024.11.29', 10, 10,
        'Certified Strategic Business Architect (CSBA)',
        'Master the art of strategic thinking with our expert-level online course, "Business Strategy." Tailored for seasoned professionals, entrepreneurs, and strategic leaders, this course offers an immersive exploration of advanced business strategy concepts and applications. Led by industry thought leaders, you''ll delve into intricate topics such as competitive analysis, market positioning, disruptive innovation, and global strategic management. Through case studies, simulations, and real-world scenarios, youll sharpen your ability to make informed strategic decisions that drive long-term success. This course goes beyond the basics, challenging you to synthesize diverse business elements into a cohesive and forward-thinking strategy. Whether you aspire to lead a multinational corporation or refine your entrepreneurial ventures, our expert-level Business Strategy course empowers you to navigate complex business landscapes with foresight and precision. Join us in this transformative learning journey and elevate your strategic acumen to new heights.',
        'https://granthigginson.com/wp-content/uploads/2017/09/business-strategy.jpg'),
       (10, 'Data Science and Analytics', 'Machine Learning Basics with Python',
        'Beginner', '2024.08.19','2024.08.30', 2, 10,
        'Machine Learning Fundamentals',
        'Embark on your journey into the exciting realm of artificial intelligence with our beginner-level online course, Machine Learning Basics with Python. Tailored for individuals new to the world of machine learning, this course provides a comprehensive introduction to the fundamental concepts and techniques using the versatile Python programming language. Led by experienced instructors, youll explore the basics of supervised and unsupervised learning, delve into popular machine learning algorithms, and gain hands-on experience through practical exercises. No prior coding experience is required, making this course an ideal starting point for beginners eager to grasp the essentials of machine learning. By the end of the course, youll have a solid foundation in using Python for machine learning applications, empowering you to unravel the mysteries of data and embark on a fascinating journey into the world of intelligent algorithms. Join us and demystify the basics of machine learning with Python in this accessible and empowering course.',
        'https://miro.medium.com/v2/resize:fit:1358/1*cG6U1qstYDijh9bPL42e-Q.jpeg'),
       (11, 'Data Science and Analytics', 'Image Recognition',
        'Intermediate', '2024.09.02','2024.09.27', 4, 20, 'Machine Vision Associate',
        'Deepen your expertise in the realm of artificial intelligence with our intermediate-level online course, "Image Recognition with Python." Tailored for those with a foundational understanding of machine learning, this course immerses you in the intricacies of image recognition techniques and technologies using the powerful Python programming language. Led by seasoned instructors, youll explore advanced concepts such as convolutional neural networks (CNNs), image preprocessing, and transfer learning. Through hands-on projects and real-world applications, youll sharpen your skills in training models to recognize and classify images with precision. This course is ideal for individuals looking to expand their knowledge in computer vision and image processing, and it serves as a stepping stone for professionals aspiring to integrate image recognition capabilities into their projects. Join us in this intermediate-level course, and unlock the potential of image recognition with Python, advancing your proficiency in the exciting field of artificial intelligence.',
        'https://www.perficient.com/-/media/images/insights/hero-images/research_image-recognition-accuracy-study_hero.ashx?h=931&iar=0&w=1400&hash=E0AED7417F234D3C66F9037149BB7612'),
       (12, 'Data Science and Analytics', 'Databricks fundamentals', 'Beginner',
        '2024.08.19','2024.08.30', 2, 10, 'Databricks Practitioner',
        'Embark on your data journey with our beginner-level online course, Databricks Fundamentals. Designed for individuals new to the world of big data and analytics, this course offers a comprehensive introduction to the essential concepts of Databricks, a leading unified analytics platform. Led by experienced instructors, youll navigate through the fundamentals of data exploration, data engineering, and collaborative data science using Databricks. No prior experience with big data technologies is required, making this course an ideal starting point for beginners eager to harness the power of Databricks for streamlined data processing and analysis. By the end of the course, youll have a solid foundation in using Databricks to uncover insights from large datasets, setting you on a path towards mastering the intricacies of modern data analytics. Join us and demystify the fundamentals of Databricks in this accessible and empowering course.',
        'https://www.databricks.com/sites/default/files/2023-11/databricks-og-universal.png'),
       (13, 'Data Science and Analytics', 'Algorithms and Data structures', 'Intermediate',
        '2024.08.19','2024.08.30', 2, 10, 'Algorithm specialist',
        'Embark on your data journey with our beginner-level online course, Databricks Fundamentals. Designed for individuals new to the world of big data and analytics, this course offers a comprehensive introduction to the essential concepts of Databricks, a leading unified analytics platform. Led by experienced instructors, youll navigate through the fundamentals of data exploration, data engineering, and collaborative data science using Databricks. No prior experience with big data technologies is required, making this course an ideal starting point for beginners eager to harness the power of Databricks for streamlined data processing and analysis. By the end of the course, youll have a solid foundation in using Databricks to uncover insights from large datasets, setting you on a path towards mastering the intricacies of modern data analytics. Join us and demystify the fundamentals of Databricks in this accessible and empowering course.',
        'https://media.gcflearnfree.org/content/5be1de13686707122ccd266f_11_06_2018/algorithms_illustration.jpg');



-- Exclusive courses

INSERT INTO courses (course_id, category, title, level, start_date,end_date, course_size, hours_per_week,
                     related_certifications, description, image)
VALUES (14, 'Data Science and Analytics', 'Data Visualization with Python',
        'Intermediate', '2024.10.01','2024.10.30', 2, 15,
        'Python Data Visualization Specialist',
        'Dive into the world of data visualization with our intermediate-level online course, "Data Visualization with Python." This course is designed for individuals with a basic understanding of Python and data science, and it aims to provide a comprehensive introduction to the use of Python in creating meaningful and insightful data visualizations. Led by experienced instructors, you will learn how to use popular Python libraries such as Matplotlib and Seaborn to create plots, charts, and other visual representations of data. By the end of the course, you will have a solid foundation in using Python for data visualization, enabling you to effectively communicate data-driven insights.',
        'https://datascientest.com/en/wp-content/uploads/sites/9/2023/09/matplotlib-graphs-1024x576.png'),
       (15, 'Software Development', 'Advanced Java Programming',
        'Advanced', '2024.05.01','2024.05.30', 4, 20,
        'Oracle Certified Professional, Java SE 8 Programmer',
        'This course is designed for students who are looking for an in-depth understanding of Java and OO principles. This course takes a deep dive into the intricacies of Java, covering topics such as exception handling, Java I/O, collections, generics, threads, JDBC, and more.',
        'https://www.mindsmapped.com/wp-content/uploads/2016/08/Advanced-Java-J2EE.jpeg'),
       (16, 'Software Development', 'Web Development with JavaScript',
        'Intermediate', '2024.06.01','2024.06.30', 3, 15,
        'JavaScript Developer Certificate',
        'This course covers the essential concepts and techniques for programming in JavaScript. Students will learn how to create dynamic web pages using JavaScript, including topics such as data types, control structures, functions, objects, arrays, and the Document Object Model (DOM).',
        'https://www.digitalsilk.com/wp-content/uploads/2020/06/website-development-process-hero-image.png.webp'),
       (17, 'Software Development', 'Advanced C++ Programming',
        'Advanced', '2024.07.01','2024.07.30', 4, 20,
        'C++ Certified Professional Programmer',
        'This course is designed for students who are looking for an in-depth understanding of C++ and OO principles. This course takes a deep dive into the intricacies of C++, covering topics such as exception handling, C++ I/O, collections, generics, threads, and more.',
        'https://familycomputercentre.com/wp-content/uploads/fp-cpp-adv.jpg'),
       (18, 'Data Science and Analytics', 'Data Analysis with R',
        'Intermediate', '2024.08.01','2024.08.30', 3, 15,
        'R Certified Data Analyst',
        'This course covers the essential concepts and techniques for data analysis in R. Students will learn how to manipulate and analyze data using R, including topics such as data types, control structures, functions, objects, arrays, and data visualization.',
        'https://www.orbitanalytics.com/wp-content/uploads/2019/06/R-language-advanced-analytics1024x560.jpg');



-- Extra courses

INSERT INTO courses (course_id, category, title, level, start_date,end_Date, course_size, hours_per_week,
                     related_certifications, description, image)
VALUES (19, 'Digital Marketing', 'Advanced SEO Techniques', 'Advanced', '2024.07.01','2024.07.30', 4, 20,
        'Google Analytics Individual Qualification',
        'This course covers advanced SEO techniques including keyword research, link building, and technical SEO. Students will learn how to optimize their websites for search engines and improve their website''s ranking on search engine results pages.',
        null),
       (20, 'Digital Marketing', 'Social Media Marketing Strategies', 'Intermediate', '2024.08.01','2024.08.30', 3, 15,
        'Hootsuite Social Marketing Certification',
        'This course covers the essential concepts and techniques for social media marketing. Students will learn how to create engaging content, use analytics to measure success, and develop a comprehensive social media marketing strategy.',
        null);

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

INSERT INTO keywords (keyword_id, keyword_name)
VALUES (1, 'Java'),
       (2, 'real-time programming'),
       (3, 'multi-threading'),
       (4, 'programming'),
       (5, 'SQL'),
       (6, 'relational databases'),
       (7, 'MySQL'),
       (8, 'web'),
       (9, '.net'),
       (10, 'C#'),
       (11, 'Azure'),
       (12, 'cloud services'),
       (13, 'administration'),
       (14, 'AWS'),
       (15, 'keyword research and analysis'),
       (16, 'technical SEO optimization'),
       (17, 'off-page SEO strategies'),
       (18, 'advanced analytics and reporting'),
       (19, 'strategic storytelling'),
       (20, 'targeted engagement techniques'),
       (21, 'data-driven optimization'),
       (22, 'Python'),
       (23, 'machine learning'),
       (24, 'data science'),
       (25, 'neural networks'),
       (26, 'image processing'),
       (27, 'Databricks');

INSERT INTO course_keywords (course_id, keyword_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (3, 8),
       (3, 4),
       (3, 9),
       (3, 10),
       (4, 11),
       (4, 12),
       (5, 11),
       (5, 12),
       (5, 13),
       (6, 14),
       (6, 12),
       (7, 15),
       (7, 16),
       (7, 17),
       (7, 18),
       (8, 19),
       (8, 20),
       (8, 21),
       (10, 22),
       (10, 23),
       (10, 4),
       (10, 24),
       (11, 22),
       (11, 23),
       (11, 4),
       (11, 24),
       (11, 25),
       (11, 26),
       (12, 22),
       (12, 23),
       (12, 4),
       (12, 24),
       (12, 25),
       (12, 27);




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

INSERT INTO community_posts (title, author, category, description, content, post_date, image)
VALUES
    ('Introduction to Python', 'john_doe','Programming', 'This course will introduce the basics of Python programming language.','This is the course content','2024-02-02', null),
    ('Web Development with JavaScript', 'chucknorris21', 'Web Development', 'This course covers the essential concepts and techniques for web development using JavaScript.','This is the course content','2024-02-12',  null),
    ('Data Structures in Java', 'woodpecker4', 'Data Structures', 'This course provides a comprehensive introduction to data structures using Java.','This is the course content','2024-03-16',  null),
    ('Machine Learning with R', 'waltjohnson', 'Machine Learning', 'This course covers the fundamental concepts of machine learning using R.','This is the course content','2024-03-24',  null),
    ('Database Management with SQL', 'john_doe', 'Database', 'This course provides a comprehensive introduction to SQL and database management.','This is the course content','2024-04-07',  'https://s33046.pcdn.co/wp-content/uploads/2022/09/options-command.png');


INSERT INTO community_posts (author, title, category, description, content, post_date, image)
VALUES
    ('alice_smith', 'Introduction to Machine Learning', 'Machine Learning', 'Basics of ML', 'This post covers the basics of machine learning, including key concepts and common algorithms.', '2024-05-20', 'https://helios-i.mashable.com/imagery/articles/03RCOjKBQZkI3SIj1edJKU0/hero-image.fill.size_1248x702.v1623390541.png'),
    ('bob_jones', 'Understanding Neural Networks', 'Artificial Intelligence', 'Neural networks explained', 'An in-depth explanation of neural networks, how they work, and their applications in AI.', '2024-05-18', 'https://www.trentonsystems.com/hubfs/Neural_Networks.png'),

    ('carol_lee', 'Python Programming for Beginners', 'Programming', 'Learn Python basics', 'A comprehensive guide for beginners to learn Python programming, including syntax and basic functions.', '2024-05-15', 'https://files.realpython.com/media/Python-Basics-Your-First-Python-Program_Watermarked.bee327fe83d3.jpg'),

    ('dave_brown', 'Data Structures and Algorithms', 'Computer Science', 'DSA fundamentals', 'An overview of essential data structures and algorithms every computer science student should know.', '2024-05-12', 'https://media.geeksforgeeks.org/wp-content/cdn-uploads/20230726161506/Data-structure-algorithm-%E2%80%93-1-3.png'),

    ('eve_wilson', 'Building Web Applications with JavaScript', 'Web Development', 'JavaScript web apps', 'A tutorial on how to build dynamic web applications using JavaScript and modern frameworks.', '2024-05-10', 'https://i.ytimg.com/vi/XnM3TxqXdP0/maxresdefault.jpg'),

    ('frank_johnson', 'Cybersecurity Best Practices', 'Cybersecurity', 'Stay safe online', 'This post discusses essential cybersecurity practices to protect yourself and your data online.', '2024-05-08', 'https://www.theforage.com/blog/wp-content/uploads/2022/12/what-is-cybersecurity.jpg'),

    ('grace_martin', 'Introduction to Cloud Computing', 'Cloud Computing', 'Cloud basics', 'An introductory guide to cloud computing, including key concepts and benefits of using cloud services.', '2024-05-05', 'https://assets-eu-01.kc-usercontent.com/77bbf83a-1306-0152-fea5-3b5eaf937634/3cfa2b2c-5fca-4182-bed4-5e8a36b0d277/GettyImages-1428916501.webp'),

    ('henry_clark', 'Advanced SQL Techniques', 'Database', 'Master SQL', 'A post on advanced SQL techniques to optimize and enhance your database queries and operations.', '2024-05-03', 'https://cdn.sanity.io/images/oaglaatp/production/feb9c80a48a201140dcfa20559b73ab1b803e59b-1200x800.png?w=1200&h=800&auto=format'),

    ('isabel_moore', 'Exploring Artificial Intelligence', 'Artificial Intelligence', 'AI applications', 'An exploration of various applications of artificial intelligence in different industries.', '2024-05-01', 'https://media.geeksforgeeks.org/wp-content/uploads/20230606132338/Artificial-Intelligence-Applications.webp'),

    ('jack_kim', 'Introduction to DevOps', 'DevOps', 'DevOps fundamentals', 'A beginner\'s guide to DevOps, including key practices and tools used in the industry.', '2024-04-28', 'https://www.whizlabs.com/blog/wp-content/uploads/2019/10/devops-introduction.jpg');


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
       (1, 3),
       (2, 4);
