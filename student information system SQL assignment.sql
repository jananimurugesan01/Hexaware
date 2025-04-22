create database SISDB;
use SISDB;
-- 1. Students Table
CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20)
);

INSERT INTO Students VALUES
(1, 'Alice', 'Johnson', '2000-05-14', 'alice.johnson@example.com', '1234567890'),
(2, 'Bob', 'Smith', '1999-11-23', 'bob.smith@example.com', '1234567891'),
(3, 'Cathy', 'Williams', '2001-02-18', 'cathy.williams@example.com', '1234567892'),
(4, 'David', 'Brown', '2000-07-04', 'david.brown@example.com', '1234567893'),
(5, 'Eva', 'Davis', '1998-03-30', 'eva.davis@example.com', '1234567894'),
(6, 'Frank', 'Miller', '2002-01-10', 'frank.miller@example.com', '1234567895'),
(7, 'Grace', 'Wilson', '1999-06-25', 'grace.wilson@example.com', '1234567896'),
(8, 'Hank', 'Moore', '2001-08-12', 'hank.moore@example.com', '1234567897'),
(9, 'Ivy', 'Taylor', '2000-09-17', 'ivy.taylor@example.com', '1234567898'),
(10, 'Jack', 'Anderson', '1999-12-03', 'jack.anderson@example.com', '1234567899');


-- 2. Teacher Table
CREATE TABLE Teacher (
    teacher_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE
);

INSERT INTO Teacher VALUES
(1, 'Laura', 'Green', 'laura.green@example.com'),
(2, 'Mark', 'Hall', 'mark.hall@example.com'),
(3, 'Nina', 'Young', 'nina.young@example.com'),
(4, 'Oscar', 'Allen', 'oscar.allen@example.com'),
(5, 'Paul', 'King', 'paul.king@example.com'),
(6, 'Quinn', 'Scott', 'quinn.scott@example.com'),
(7, 'Rachel', 'Adams', 'rachel.adams@example.com'),
(8, 'Steve', 'Baker', 'steve.baker@example.com'),
(9, 'Tina', 'Clark', 'tina.clark@example.com'),
(10, 'Umar', 'Wright', 'umar.wright@example.com');


-- 3. Courses Table
CREATE TABLE Courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100),
    credits INT,
    teacher_id INT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher(teacher_id)
);

INSERT INTO Courses VALUES
(101, 'Mathematics', 4, 1),
(102, 'Physics', 3, 2),
(103, 'Chemistry', 4, 3),
(104, 'Biology', 3, 4),
(105, 'Computer Science', 5, 5),
(106, 'History', 2, 6),
(107, 'Geography', 2, 7),
(108, 'English Literature', 3, 8),
(109, 'Economics', 4, 9),
(110, 'Psychology', 3, 10);


-- 4. Enrollments Table
CREATE TABLE Enrollments (
    enrollment_id INT PRIMARY KEY,
    student_id INT,
    course_id INT,
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

INSERT INTO Enrollments VALUES
(1, 1, 101, '2024-01-10'),
(2, 2, 102, '2024-01-11'),
(3, 3, 103, '2024-01-12'),
(4, 4, 104, '2024-01-13'),
(5, 5, 105, '2024-01-14'),
(6, 6, 106, '2024-01-15'),
(7, 7, 107, '2024-01-16'),
(8, 8, 108, '2024-01-17'),
(9, 9, 109, '2024-01-18'),
(10, 10, 110, '2024-01-19');


-- 5. Payments Table
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY,
    student_id INT,
    amount DECIMAL(10, 2),
    payment_date DATE,
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);

INSERT INTO Payments VALUES
(1, 1, 500.00, '2024-02-01'),
(2, 2, 450.00, '2024-02-02'),
(3, 3, 600.00, '2024-02-03'),
(4, 4, 700.00, '2024-02-04'),
(5, 5, 400.00, '2024-02-05'),
(6, 6, 550.00, '2024-02-06'),
(7, 7, 500.00, '2024-02-07'),
(8, 8, 650.00, '2024-02-08'),
(9, 9, 300.00, '2024-02-09'),
(10, 10, 480.00, '2024-02-10');

 -- TASK 2
 
 -- 1
 INSERT INTO Students (student_id, first_name, last_name, date_of_birth, email, phone_number)
VALUES (11, 'John', 'Doe', '1995-08-15', 'john.doe@example.com', '1234567890');

-- 2
INSERT INTO Enrollments (enrollment_id, student_id, course_id, enrollment_date)
VALUES (11, 3, 105, '2025-04-08');

-- 3
UPDATE Teacher
SET email = 'david.brown21@newmail.com'
WHERE teacher_id = 4;

-- 4
DELETE FROM Enrollments
WHERE student_id = 5 AND course_id = 105;

-- 5
UPDATE Courses
SET teacher_id = 2
WHERE course_id = 104;

-- 6
-- Delete enrollments
DELETE FROM Enrollments
WHERE student_id = 6;

-- Delete the student
DELETE FROM Students
WHERE student_id = 6;

-- 7
UPDATE Payments
SET amount = 750.00
WHERE payment_id = 4;


-- TASK 3

-- 1
SELECT s.first_name, s.last_name, SUM(p.amount) AS total_payments
FROM Students s
JOIN Payments p ON s.student_id = p.student_id
WHERE s.student_id = 3
GROUP BY s.first_name, s.last_name;

-- 2
SELECT c.course_name, COUNT(e.student_id) AS enrollment_count
FROM Courses c
LEFT JOIN Enrollments e ON c.course_id = e.course_id
GROUP BY c.course_name;

-- 3
SELECT s.first_name, s.last_name
FROM Students s
LEFT JOIN Enrollments e ON s.student_id = e.student_id
WHERE e.enrollment_id IS NULL;

-- 4
SELECT s.first_name, s.last_name, c.course_name
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
JOIN Courses c ON e.course_id = c.course_id;

-- 5
SELECT t.first_name, t.last_name, c.course_name
FROM Teacher t
JOIN Courses c ON t.teacher_id = c.teacher_id;

-- 6
SELECT s.first_name, s.last_name, e.enrollment_date
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
JOIN Courses c ON e.course_id = c.course_id
WHERE c.course_id = 105;

-- 7
SELECT s.first_name, s.last_name
FROM Students s
LEFT JOIN Payments p ON s.student_id = p.student_id
WHERE p.payment_id IS NULL;

-- 8
SELECT c.course_name
FROM Courses c
LEFT JOIN Enrollments e ON c.course_id = e.course_id
WHERE e.enrollment_id IS NULL;

-- 9
SELECT s.first_name, s.last_name, COUNT(e.course_id) AS course_count
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
GROUP BY s.first_name, s.last_name
HAVING COUNT(e.course_id) > 1;

-- 10
SELECT t.first_name, t.last_name
FROM Teacher t
LEFT JOIN Courses c ON t.teacher_id = c.teacher_id
WHERE c.course_id IS NULL;

-- TASK 4

-- 1
SELECT AVG(enrollment_count) AS average_enrollment
FROM (
    SELECT course_id, COUNT(student_id) AS enrollment_count
    FROM Enrollments
    GROUP BY course_id
) AS course_enrollments;

