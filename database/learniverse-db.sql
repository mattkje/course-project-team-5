CREATE TABLE user(userName, email, password, firstName, lastName, role, enabled, createdAt, updatedAt, phoneNumber);

INSERT INTO user (userName, email, password, firstName, lastName, role, enabled, createdAt, updatedAt, phoneNumber)
VALUES
    ('john_doe', 'john@example.com', 'password123', 'John', 'Doe', 'ROLE_USER', true, '2024-02-10 08:00:00', '2024-02-10 08:00:00', '123456789'),
    ('jane_smith', 'jane@example.com', 'password456', 'Jane', 'Smith', 'ROLE_ADMIN', true, '2024-02-10 08:15:00', '2024-02-10 08:15:00', '987654321'),
    ('bob_jones', 'bob@example.com', 'password789', 'Bob', 'Jones', 'ROLE_USER', false, '2024-02-10 08:30:00', '2024-02-10 08:30:00', '456123789');


CREATE TABLE courses (
    productID INT PRIMARY KEY,
    title VARCHAR(255),
    level VARCHAR(50),
    closest_course_session VARCHAR(50),
    course_size DECIMAL(5, 2),
    hours_per_week INT,
    related_certifications VARCHAR(255),
    description TEXT,
    image VARCHAR(255)
);


INSERT INTO courses (productID, title, level, closest_course_session, course_size, hours_per_week, related_certifications, description, image)
VALUES (
    1,
    'Real-Time Programming in Java',
    'Expert',
    '03.06 - 28.06',
    7.5,
    40,
    'Java SE 17 Programmer Professional',
    'Embark on a transformative learning experience with our expert-level online course, "Real-Time Programming in Java." Designed for seasoned developers and Java enthusiasts seeking mastery in real-time applications, this advanced course delves deep into the intricacies of leveraging Java for mission-critical systems. Explore cutting-edge concepts such as multithreading, synchronization, and low-latency programming, equipping you with the skills needed to build responsive and robust real-time solutions. Led by industry experts with extensive hands-on experience, this course combines theoretical insights with practical application, ensuring you not only grasp the theoretical underpinnings but also gain the proficiency to implement real-time solutions confidently. Elevate your Java programming expertise to new heights and stay ahead in the ever-evolving landscape of real-time systems with our comprehensive and immersive course.',
    'https://en.wikipedia.org/wiki/Java_%28programming_language%29#/media/File:Java_programming_language_logo.svg'
);
