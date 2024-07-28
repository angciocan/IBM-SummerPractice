-- Insert students
INSERT INTO student(id, name, grade, is_admin, study_year, faculty_section) VALUES (11, 'Robert', 10, true, 4, 'CTI');
INSERT INTO student(id, name, grade, is_admin, study_year, faculty_section) VALUES (23, 'Angelo', 7, false, 3, 'CTI');
INSERT INTO student(id, name, grade, is_admin, study_year, faculty_section) VALUES (47, 'Mircea', 8, true, 2, 'CTI-EN');

-- Insert teachers
INSERT INTO teacher(id, name, is_admin) VALUES (4, 'Nicolina', false);

-- Insert administrators (assuming administrator is a separate entity, otherwise modify as needed)
INSERT INTO administrator(id, name) VALUES (6, 'BOSS');

-- Insert courses
INSERT INTO course(id, course_name, day_of_week, max_students, time, year_of_study, category, teacher_id) VALUES (101, 'fizica', 2, 99, '15:00', 1, 'hardware', 4);

-- Insert enrollments
INSERT INTO enrollment(id, student_id, course_id) VALUES (1, 11, 101);
INSERT INTO enrollment(id, student_id, course_id) VALUES (2, 23, 101);
INSERT INTO enrollment(id, student_id, course_id) VALUES (3, 47, 101);

