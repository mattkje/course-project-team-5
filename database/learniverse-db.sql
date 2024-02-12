CREATE DATABASE IF NOT EXISTS LearniverseDB;
USE LearniverseDB;

CREATE TABLE IF NOT EXISTS users(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(16),
    email VARCHAR(45),
    password VARCHAR(64),
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    role TINYINT,
    enabled BIT,
    created_at DATE,
    updated_at DATE,
    phone_number VARCHAR(20)
);

INSERT INTO users (user_name, email, password, first_name, last_name, role, enabled, created_at, updated_at, phone_number)
VALUES
                                                                                                                              ('john_doe', 'john.doe@example.com', 'password123', 'John', 'Doe', 1, 1, '2024-02-12', '2024-02-12', '+1234567890'),
                                                                                                                              ('jane_smith', 'jane.smith@example.com', 'smithpassword', 'Jane', 'Smith', 2, 1, '2024-02-12', '2024-02-12', '+1987654321'),
                                                                                                                              ('bob_johnson', 'bob.johnson@example.com', 'b0b!pass', 'Bob', 'Johnson', 1, 0, '2024-02-12', '2024-02-12', '+1122334455'),
                                                                                                                              ('emily_davis', 'emily.davis@example.com', 'davispass123', 'Emily', 'Davis', 3, 1, '2024-02-12', '2024-02-12', '+1555666777');


CREATE TABLE IF NOT EXISTS courses (
    productID INTEGER PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(100),
    title VARCHAR(255),
    keywords VARCHAR(255),
    level VARCHAR(50),
    closest_course_session VARCHAR(50),
    course_size DECIMAL(5, 2),
    hours_per_week INT,
    related_certifications VARCHAR(255),
    description TEXT,
    image VARCHAR(255)
);



INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'Real-Time Programming in Java',
        'keyword',
        'Expert',
        '03.06 - 28.06',
        7.5,
        40,
        'Java SE 17 Programmer Professional',
        'Embark on a transformative learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization, and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions. Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement real-time solutions confidently. Elevate your Java programming expertise to new heights and stay ahead in the ever-evolving landscape of real-time systems with our comprehensive and immersive course.',
        'https://en.wikipedia.org/wiki/Java_%28programming_language%29#/media/File:Java_programming_language_logo.svg'

);

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'Introduction to SQL Essentials',
        'keyword',
        'Beginner',
        '10.06 - 28.06',
        2,
        20,
        'SQL Fundamentals',
        'Dive into the fundamentals of database management with our beginner-level online course, \"Introduction to SQL Essentials.\" Geared towards those new to the world of databases and SQL, this course provides a comprehensive foundation for understanding and utilizing SQL for effective data management. While MySQL is touched upon to broaden your practical knowledge, the core focus is on SQL''s universal principles applicable across various database systems. Led by seasoned instructors, the course covers database design, querying data, and basic data manipulation using SQL commands. With a hands-on approach, you''ll engage in practical exercises to reinforce your learning, ensuring you gain the skills necessary to navigate and interact with databases confidently. Whether you''re a budding developer, analyst, or anyone eager to harness the power of databases, this course offers an accessible entry point into the world of SQL, setting the stage for your future success in data-driven environments.',
        'https://thumb.tildacdn.com/tild6238-3035-4335-a333-306335373139/-/format/webp/IMG_3349.jpg'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'Creating Web Application with .Net',
        'keyword',
        'Beginner',
        '05.08 - 16.08',
        4,
        40,
        '.Net Developer Fundamentals',
        'Embark on your journey into web development with our beginner-level online course, Creating Web Applications with .NET. Tailored for those stepping into the dynamic world of web development, this course provides a solid foundation in utilizing the versatile .NET framework to build powerful and interactive web applications. Guided by experienced instructors, you will explore fundamental concepts such as web application architecture, user interface design, and server-side scripting using .NET technologies like ASP.NET. Throughout the course, you will engage in hands-on projects that walk you through the entire development process, from designing responsive user interfaces to implementing server-side functionality. Gain practical skills in C# programming and discover how to leverage the robust features of .NET to bring your web applications to life. Whether you are a programming novice or transitioning from another language, this course offers a welcoming entry point into the exciting realm of web application development with .NET, setting you on a path to create dynamic and engaging online experiences.',
        'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/223525883/original/75355314f2634045dbac0e3ba746934e4fa701eb/do-develop-in-asp-net-mvc-asp-net-core-asp-net-web-api.jpg'
);

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'Azure Cloud Fundamentals',
        'keyword',
        'Beginner',
        '05.08 - 30.08',
        2,
        10,
        'AZ-900 Azure Fundamentals',
        'Embark on your cloud computing journey with our beginner-level online course, "Azure Fundamentals," meticulously crafted to prepare you for the AZ-900 exam. Whether you are new to cloud technologies or seeking to validate your foundational knowledge, this course provides a comprehensive introduction to Microsoft Azure, one of the industrys leading cloud platforms. Delve into the essentials of cloud concepts, Azure services, pricing, and compliance, all while guided by expert instructors who understand the importance of building a strong cloud foundation. Through a combination of engaging lectures, hands-on labs, and real-world scenarios, you will gain practical insights into deploying solutions on Azure and mastering fundamental cloud principles. By the end of the course, youll not only be well-prepared to ace the AZ-900 exam but will also have a solid understanding of Azures capabilities, empowering you to confidently navigate the vast landscape of cloud computing. Join us on this educational journey and unlock the potential of cloud technology with Azure Fundamentals.',
        'https://digitaltransformation.org.au/sites/default/files/2022-01/azure.png'
);

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'Azure Administration (Intermediate)',
        'keyword',
        'Intermediate',
        '02.09 - 20.12',
        4,
        5,
        'AZ-104 Microsoft Certified Cloud Administrator',
        'Elevate your cloud expertise with our intermediate-level online course, "Azure Administrator," meticulously designed to prepare you for the AZ-104 exam – your gateway to becoming a Microsoft Certified Cloud Administrator. Tailored for individuals with a foundational understanding of Azure, this course takes a deep dive into advanced administration and management tasks, honing the skills required for efficient cloud operations. Led by seasoned Azure professionals, youll explore intricate topics such as virtual networking, identity management, and governance strategies, gaining hands-on experience through practical exercises and real-world scenarios. The courses comprehensive coverage aligns seamlessly with the AZ-104 exam objectives, ensuring that you not only pass the certification but emerge as a proficient Azure Administrator capable of implementing robust cloud solutions. Whether youre looking to enhance your career or solidify your position as a cloud expert, this course is your key to mastering the intricacies of Azure administration and achieving Microsoft Certified Cloud Administrator status. Join us on this transformative journey towards advanced Azure proficiency.',
        'https://digitaltransformation.org.au/sites/default/files/2022-01/azure.png'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'AWS Cloud Practitioner',
        'keyword',
        'Beginner',
        '09.09 - 20.09',
        2,
        20,
        'CLF-C02 AWS Certified Cloud Practitioner',
        'Discover the fundamentals of cloud computing in our beginner-level online course, "AWS Cloud Practitioner," designed to prepare you for the CLF-C02 certification exam. Tailored for individuals with minimal prior experience in cloud technologies, this course provides a robust foundation in understanding the essential concepts of Amazon Web Services (AWS). Led by experienced AWS professionals, the course delves into core topics, including cloud architecture, AWS services, security, and pricing models. Through dynamic lectures and hands-on labs, youll gain practical insights into navigating the AWS console, setting up basic infrastructure, and comprehending key cloud principles. By the courses end, youll be well-equipped to excel in the CLF-C02 exam and possess a foundational understanding of AWS, empowering you to confidently explore and leverage cloud services. Join us in this educational journey, and initiate your AWS Cloud Practitioner certification with assurance and proficiency.',
        'https://blog.adobe.com/en/publish/2021/08/31/media_1649ebc3fbbce0df508081913819d491fc3f7c7a9.png?width=750&format=png&optimize=medium'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Digital Marketing Courses',
        'Search Engine Optimization',
        'keyword',
        'Intermediate',
        '05.08 - 30.08',
        2,
        4,
        'SEO Wizard',
        'Deepen your expertise in the digital landscape with our intermediate-level online course, "Search Engine Optimization (SEO)." Tailored for marketers, business owners, and digital enthusiasts, this course offers an immersive exploration into the intricate world of SEO, empowering you to elevate your online visibility and drive targeted traffic to your website. Led by seasoned SEO professionals, the course covers advanced topics such as keyword research, on-page optimization, technical SEO, and link building strategies, providing practical insights and actionable techniques for optimizing your digital presence. Through a blend of theoretical knowledge and hands-on exercises, youll learn how to develop effective SEO strategies, analyze website performance metrics, and stay ahead of search engine algorithm updates. Whether youre looking to enhance your career in digital marketing or expand your businesss online reach, this course equips you with the skills and knowledge needed to thrive in the competitive landscape of search engine optimization.',
        'https://www.rgbwebtech.com/blogs/images/uploads/what-is-seo-search-engin-optimization.png'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Digital Marketing Courses',
        'Social Media Marketing',
        'keyword',
        'Intermediate',
        '05.08 - 30.08',
        2,
        4,
        'Certified Social Alchemist',
        'Elevate your digital marketing prowess with our intermediate-level online course, "Social Media Marketing." Tailored for marketers, business professionals, and enthusiasts seeking to harness the power of social platforms, this course provides an in-depth exploration of advanced social media marketing strategies. Led by industry experts, youll delve into nuanced topics such as audience targeting, content optimization, social media advertising, and analytics. Through a blend of theoretical insights and hands-on exercises, youll gain practical skills to create compelling social media campaigns, foster audience engagement, and measure the impact of your efforts. Stay at the forefront of digital marketing trends by mastering the art of crafting impactful narratives, building brand loyalty, and leveraging diverse social channels. Whether you aim to enhance your business online presence or advance your career in digital marketing, this course equips you with the tools and knowledge to navigate the dynamic landscape of social media marketing with confidence and proficiency. Join us and amplify your social media marketing expertise with our intermediate-level course.',
        'https://hotdogmarketing.net/wp-content/uploads/2021/11/110321-HDM-Social-Media-Marketing-02-1024x576.png'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Business and Entrepreneurship Courses',
        'Business Strategy',
        null,
        null,
        '03.06 - 29.11',
        10,
        10,
        'Certified Strategic Business Architect (CSBA)',
        'Master the art of strategic thinking with our expert-level online course, "Business Strategy." Tailored for seasoned professionals, entrepreneurs, and strategic leaders, this course offers an immersive exploration of advanced business strategy concepts and applications. Led by industry thought leaders, youll delve into intricate topics such as competitive analysis, market positioning, disruptive innovation, and global strategic management. Through case studies, simulations, and real-world scenarios, youll sharpen your ability to make informed strategic decisions that drive long-term success. This course goes beyond the basics, challenging you to synthesize diverse business elements into a cohesive and forward-thinking strategy. Whether you aspire to lead a multinational corporation or refine your entrepreneurial ventures, our expert-level Business Strategy course empowers you to navigate complex business landscapes with foresight and precision. Join us in this transformative learning journey and elevate your strategic acumen to new heights.',
        'https://granthigginson.com/wp-content/uploads/2017/09/business-strategy.jpg'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Data Science and Analytics Courses',
        'Machine Learning Basics with Python',
        'keyword',
        'Beginner',
        '19.08 - 30.08',
        2,
        10,
        'Machine Learning Fundamentals',
        'Embark on your journey into the exciting realm of artificial intelligence with our beginner-level online course, Machine Learning Basics with Python. Tailored for individuals new to the world of machine learning, this course provides a comprehensive introduction to the fundamental concepts and techniques using the versatile Python programming language. Led by experienced instructors, youll explore the basics of supervised and unsupervised learning, delve into popular machine learning algorithms, and gain hands-on experience through practical exercises. No prior coding experience is required, making this course an ideal starting point for beginners eager to grasp the essentials of machine learning. By the end of the course, youll have a solid foundation in using Python for machine learning applications, empowering you to unravel the mysteries of data and embark on a fascinating journey into the world of intelligent algorithms. Join us and demystify the basics of machine learning with Python in this accessible and empowering course.',
        'https://miro.medium.com/v2/resize:fit:1358/1*cG6U1qstYDijh9bPL42e-Q.jpeg'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Data Science and Analytics Courses',
        'Image Recognition',
        'keyword',
        'Intermediate',
        '02.09 - 27.09',
        4,
        20,
        'Machine Vision Associate',
        'Deepen your expertise in the realm of artificial intelligence with our intermediate-level online course, \"Image Recognition with Python.\" Tailored for those with a foundational understanding of machine learning, this course immerses you in the intricacies of image recognition techniques and technologies using the powerful Python programming language. Led by seasoned instructors, youll explore advanced concepts such as convolutional neural networks (CNNs), image preprocessing, and transfer learning. Through hands-on projects and real-world applications, youll sharpen your skills in training models to recognize and classify images with precision. This course is ideal for individuals looking to expand their knowledge in computer vision and image processing, and it serves as a stepping stone for professionals aspiring to integrate image recognition capabilities into their projects. Join us in this intermediate-level course, and unlock the potential of image recognition with Python, advancing your proficiency in the exciting field of artificial intelligence.',
        'https://www.perficient.com/-/media/images/insights/hero-images/research_image-recognition-accuracy-study_hero.ashx?h=931&iar=0&w=1400&hash=E0AED7417F234D3C66F9037149BB7612'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Data Science and Analytics Courses',
        'Databricks fundamentals',
        'keyword',
        'Beginner',
        '19.08 - 30.08',
        2,
        10,
        'Databricks Practitioner',
        'Embark on your data journey with our beginner-level online course, Databricks Fundamentals. Designed for individuals new to the world of big data and analytics, this course offers a comprehensive introduction to the essential concepts of Databricks, a leading unified analytics platform. Led by experienced instructors, youll navigate through the fundamentals of data exploration, data engineering, and collaborative data science using Databricks. No prior experience with big data technologies is required, making this course an ideal starting point for beginners eager to harness the power of Databricks for streamlined data processing and analysis. By the end of the course, youll have a solid foundation in using Databricks to uncover insights from large datasets, setting you on a path towards mastering the intricacies of modern data analytics. Join us and demystify the fundamentals of Databricks in this accessible and empowering course.',
        'https://upload.wikimedia.org/wikipedia/commons/6/63/Databricks_Logo.png'
       );

INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
        'Information Technology Courses',
        'Real-Time Programming in Java',
        'keyword',
        'Expert',
        '03.06 - 28.06',
        7.5,
        40,
        'Java SE 17 Programmer Professional',
        'Embark on a transformative learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization, and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions. Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement real-time solutions confidently. Elevate your Java programming expertise to new heights and stay ahead in the ever-evolving landscape of real-time systems with our comprehensive and immersive course.',
        'https://en.wikipedia.org/wiki/Java_%28programming_language%29#/media/File:Java_programming_language_logo.svg'
        );

-- Run this!!
INSERT INTO courses (category, title, keywords, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES
('Information Technology Courses', 'Real-Time Programming in Java', 'keyword', 'Expert', '03.06 - 28.06', 7.5, 40, 'Java SE 17 Programmer Professional', 'Embark on a transformative learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization, and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions. Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement real-time solutions confidently. Elevate your Java programming expertise to new heights and stay ahead in the ever-evolving landscape of real-time systems with our comprehensive and immersive course.', 'https://en.wikipedia.org/wiki/Java_%28programming_language%29#/media/File:Java_programming_language_logo.svg'),
('Information Technology Courses', 'Introduction to SQL Essentials', 'keyword', 'Beginner', '10.06 - 28.06', 2, 20, 'SQL Fundamentals', 'Dive into the fundamentals of database management with our beginner-level online course, "Introduction to SQL Essentials." Geared towards those new to the world of databases and SQL, this course provides a comprehensive foundation for understanding and utilizing SQL for effective data management. While MySQL is touched upon to broaden your practical knowledge, the core focus is on SQL''s universal principles applicable across various database systems. Led by seasoned instructors, the course covers database design, querying data, and basic data manipulation using SQL commands. With a hands-on approach, you''ll engage in practical exercises to reinforce your learning, ensuring you gain the skills necessary to navigate and interact with databases confidently. Whether you''re a budding developer, analyst, or anyone eager to harness the power of databases, this course offers an accessible entry point into the world of SQL, setting the stage for your future success in data-driven environments.', 'https://thumb.tildacdn.com/tild6238-3035-4335-a333-306335373139/-/format/webp/IMG_3349.jpg'),
('Information Technology Courses', 'Creating Web Application with .Net', 'keyword', 'Beginner', '05.08 - 16.08', 4, 40, '.Net Developer Fundamentals', 'Embark on your journey into web development with our beginner-level online course, Creating Web Applications with .NET. Tailored for those stepping into the dynamic world of web development, this course provides a solid foundation in utilizing the versatile .NET framework to build powerful and interactive web applications. Guided by experienced instructors, you will explore fundamental concepts such as web application architecture, user interface design, and server-side scripting using .NET technologies like ASP.NET. Throughout the course, you will engage in hands-on projects that walk you through the entire development process, from designing responsive user interfaces to implementing server-side functionality. Gain practical skills in C# programming and discover how to leverage the robust features of .NET to bring your web applications to life. Whether you are a programming novice or transitioning from another language, this course offers a welcoming entry point into the exciting realm of web application development with .NET, setting you on a path to create dynamic and engaging online experiences.', 'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/223525883/original/75355314f2634045dbac0e3ba746934e4fa701eb/do-develop-in-asp-net-mvc-asp-net-core-asp-net-web-api.jpg'),
('Information Technology Courses', 'Azure Cloud Fundamentals', 'keyword', 'Beginner', '05.08 - 30.08', 2, 10, 'AZ-900 Azure Fundamentals', 'Embark on your cloud computing journey with our beginner-level online course, "Azure Fundamentals," meticulously crafted to prepare you for the AZ-900 exam. Whether you are new to cloud technologies or seeking to validate your foundational knowledge, this course provides a comprehensive introduction to Microsoft Azure, one of the industrys leading cloud platforms. Delve into the essentials of cloud concepts, Azure services, pricing, and compliance, all while guided by expert instructors who understand the importance of building a strong cloud foundation. Through a combination of engaging lectures, hands-on labs, and real-world scenarios, you will gain practical insights into deploying solutions on Azure and mastering fundamental cloud principles. By the end of the course, youll not only be well-prepared to ace the AZ-900 exam but will also have a solid understanding of Azures capabilities, empowering you to confidently navigate the vast landscape of cloud computing. Join us on this educational journey and unlock the potential of cloud technology with Azure Fundamentals.', 'https://digitaltransformation.org.au/sites/default/files/2022-01/azure.png'),
('Information Technology Courses', 'Azure Administration (Intermediate)', 'keyword', 'Intermediate', '02.09 - 20.12', 4, 5, 'AZ-104 Microsoft Certified Cloud Administrator', 'Elevate your cloud expertise with our intermediate-level online course, "Azure Administrator," meticulously designed to prepare you for the AZ-104 exam – your gateway to becoming a Microsoft Certified Cloud Administrator. Tailored for individuals with a foundational understanding of Azure, this course takes a deep dive into advanced administration and management tasks, honing the skills required for efficient cloud operations. Led by seasoned Azure professionals, youll explore intricate topics such as virtual networking, identity management, and governance strategies, gaining hands-on experience through practical exercises and real-world scenarios. The courses comprehensive coverage aligns seamlessly with the AZ-104 exam objectives, ensuring that you not only pass the certification but emerge as a proficient Azure Administrator capable of implementing robust cloud solutions. Whether youre looking to enhance your career or solidify your position as a cloud expert, this course is your key to mastering the intricacies of Azure administration and achieving Microsoft Certified Cloud Administrator status. Join us on this transformative journey towards advanced Azure proficiency.', 'https://digitaltransformation.org.au/sites/default/files/2022-01/azure.png'),
('Information Technology Courses', 'AWS Cloud Practitioner', 'keyword', 'Beginner', '09.09 - 20.09', 2, 20, 'CLF-C02 AWS Certified Cloud Practitioner', 'Discover the fundamentals of cloud computing in our beginner-level online course, "AWS Cloud Practitioner," designed to prepare you for the CLF-C02 certification exam. Tailored for individuals with minimal prior experience in cloud technologies, this course provides a robust foundation in understanding the essential concepts of Amazon Web Services (AWS). Led by experienced AWS professionals, the course delves into core topics, including cloud architecture, AWS services, security, and pricing models. Through dynamic lectures and hands-on labs, youll gain practical insights into navigating the AWS console, setting up basic infrastructure, and comprehending key cloud principles. By the courses end, youll be well-equipped to excel in the CLF-C02 exam and possess a foundational understanding of AWS, empowering you to confidently explore and leverage cloud services. Join us in this educational journey, and initiate your AWS Cloud Practitioner certification with assurance and proficiency.', 'https://blog.adobe.com/en/publish/2021/08/31/media_1649ebc3fbbce0df508081913819d491fc3f7c7a9.png?width=750&format=png&optimize=medium'),
('Digital Marketing Courses', 'Search Engine Optimization', 'keyword', 'Intermediate', '05.08 - 30.08', 2, 4, 'SEO Wizard', 'Deepen your expertise in the digital landscape with our intermediate-level online course, "Search Engine Optimization (SEO)." Tailored for marketers, business owners, and digital enthusiasts, this course offers an immersive exploration into the intricate world of SEO, empowering you to elevate your online visibility and drive targeted traffic to your website. Led by seasoned SEO professionals, the course covers advanced topics such as keyword research, on-page optimization, technical SEO, and link building strategies, providing practical insights and actionable techniques for optimizing your digital presence. Through a blend of theoretical knowledge and hands-on exercises, youll learn how to develop effective SEO strategies, analyze website performance metrics, and stay ahead of search engine algorithm updates. Whether youre looking to enhance your career in digital marketing or expand your businesss online reach, this course equips you with the skills and knowledge needed to thrive in the competitive landscape of search engine optimization.', 'https://www.rgbwebtech.com/blogs/images/uploads/what-is-seo-search-engin-optimization.png'),
('Digital Marketing Courses', 'Social Media Marketing', 'keyword', 'Intermediate', '05.08 - 30.08', 2, 4, 'Certified Social Alchemist', 'Elevate your digital marketing prowess with our intermediate-level online course, "Social Media Marketing." Tailored for marketers, business professionals, and enthusiasts seeking to harness the power of social platforms, this course provides an in-depth exploration of advanced social media marketing strategies. Led by industry experts, youll delve into nuanced topics such as audience targeting, content optimization, social media advertising, and analytics. Through a blend of theoretical insights and hands-on exercises, youll gain practical skills to create compelling social media campaigns, foster audience engagement, and measure the impact of your efforts. Stay at the forefront of digital marketing trends by mastering the art of crafting impactful narratives, building brand loyalty, and leveraging diverse social channels. Whether you aim to enhance your business online presence or advance your career in digital marketing, this course equips you with the tools and knowledge to navigate the dynamic landscape of social media marketing with confidence and proficiency. Join us and amplify your social media marketing expertise with our intermediate-level course.', 'https://hotdogmarketing.net/wp-content/uploads/2021/11/110321-HDM-Social-Media-Marketing-02-1024x576.png'),
('Business and Entrepreneurship Courses', 'Business Strategy', null, null, '03.06 - 29.11', 10, 10, 'Certified Strategic Business Architect (CSBA)', 'Master the art of strategic thinking with our expert-level online course, "Business Strategy." Tailored for seasoned professionals, entrepreneurs, and strategic leaders, this course offers an immersive exploration of advanced business strategy concepts and applications. Led by industry thought leaders, youll delve into intricate topics such as competitive analysis, market positioning, disruptive innovation, and global strategic management. Through case studies, simulations, and real-world scenarios, youll sharpen your ability to make informed strategic decisions that drive long-term success. This course goes beyond the basics, challenging you to synthesize diverse business elements into a cohesive and forward-thinking strategy. Whether you aspire to lead a multinational corporation or refine your entrepreneurial ventures, our expert-level Business Strategy course empowers you to navigate complex business landscapes with foresight and precision. Join us in this transformative learning journey and elevate your strategic acumen to new heights.', 'https://granthigginson.com/wp-content/uploads/2017/09/business-strategy.jpg'),
('Data Science and Analytics Courses', 'Machine Learning Basics with Python', 'keyword', 'Beginner', '19.08 - 30.08', 2, 10, 'Machine Learning Fundamentals', 'Embark on your journey into the exciting realm of artificial intelligence with our beginner-level online course, Machine Learning Basics with Python. Tailored for individuals new to the world of machine learning, this course provides a comprehensive introduction to the fundamental concepts and techniques using the versatile Python programming language. Led by experienced instructors, youll explore the basics of supervised and unsupervised learning, delve into popular machine learning algorithms, and gain hands-on experience through practical exercises. No prior coding experience is required, making this course an ideal starting point for beginners eager to grasp the essentials of machine learning. By the end of the course, youll have a solid foundation in using Python for machine learning applications, empowering you to unravel the mysteries of data and embark on a fascinating journey into the world of intelligent algorithms. Join us and demystify the basics of machine learning with Python in this accessible and empowering course.', 'https://miro.medium.com/v2/resize:fit:1358/1*cG6U1qstYDijh9bPL42e-Q.jpeg'),
('Data Science and Analytics Courses', 'Image Recognition', 'keyword', 'Intermediate', '02.09 - 27.09', 4, 20, 'Machine Vision Associate', 'Deepen your expertise in the realm of artificial intelligence with our intermediate-level online course, "Image Recognition with Python." Tailored for those with a foundational understanding of machine learning, this course immerses you in the intricacies of image recognition techniques and technologies using the powerful Python programming language. Led by seasoned instructors, youll explore advanced concepts such as convolutional neural networks (CNNs), image preprocessing, and transfer learning. Through hands-on projects and real-world applications, youll sharpen your skills in training models to recognize and classify images with precision. This course is ideal for individuals looking to expand their knowledge in computer vision and image processing, and it serves as a stepping stone for professionals aspiring to integrate image recognition capabilities into their projects. Join us in this intermediate-level course, and unlock the potential of image recognition with Python, advancing your proficiency in the exciting field of artificial intelligence.', 'https://www.perficient.com/-/media/images/insights/hero-images/research_image-recognition-accuracy-study_hero.ashx?h=931&iar=0&w=1400&hash=E0AED7417F234D3C66F9037149BB7612'),
('Data Science and Analytics Courses', 'Databricks fundamentals', 'keyword', 'Beginner', '19.08 - 30.08', 2, 10, 'Databricks Practitioner', 'Embark on your data journey with our beginner-level online course, Databricks Fundamentals. Designed for individuals new to the world of big data and analytics, this course offers a comprehensive introduction to the essential concepts of Databricks, a leading unified analytics platform. Led by experienced instructors, youll navigate through the fundamentals of data exploration, data engineering, and collaborative data science using Databricks. No prior experience with big data technologies is required, making this course an ideal starting point for beginners eager to harness the power of Databricks for streamlined data processing and analysis. By the end of the course, youll have a solid foundation in using Databricks to uncover insights from large datasets, setting you on a path towards mastering the intricacies of modern data analytics. Join us and demystify the fundamentals of Databricks in this accessible and empowering course.', 'https://upload.wikimedia.org/wikipedia/commons/6/63/Databricks_Logo.png');


