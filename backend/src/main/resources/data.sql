insert into student(grade,study_year,faculty_section,name) values (10,4,'CTI','Robert');
insert into student(grade,study_year,faculty_section,name) values (7,3,'CTI','Angelo');
insert into student(grade,study_year,faculty_section,name) values (8,2,'IS','Mircea');
insert into student(grade,study_year,faculty_section,name) values (9,2,'CTI-EN','Marian');
--
insert into teacher(name) values ('Nicolina');
-- --
insert into administrator(name) values ('BOSS');
-- COURSES 1-5
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (2,99,2,'15:00',1,'mandatory','fizica');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (3,99,2,'15:00',1,'mandatory','electrotehnica');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (5,99,2,'15:00',1,'mandatory','electronica');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (4,23,3,'17:00',1,'mandatory','bazele programarii');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (4,23,3,'17:00',1,'mandatory','programare web');
-- COURSES 6-10
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (5,23,2,'17:00',1,'elective','microeconomie');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (6,23,2,'17:00',1,'elective','management');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (4,23,3,'17:00',1,'elective','baze de date');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (2,23,3,'17:00',1,'elective','bazele programarii 2');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (3,23,3,'17:00',1,'elective','programare web 2');



insert into enrollment_administration(nr_of_mandatory_courses,nr_of_elective_courses, study_year) values (4,2, 2);
insert into enrollment_administration(nr_of_mandatory_courses,nr_of_elective_courses, study_year) values (3,3, 3);
insert into enrollment_administration(nr_of_mandatory_courses,nr_of_elective_courses, study_year) values (2,4, 4);
-- ENROLLMENT 1-5 MANDATORY
insert into enrollment(course_id,student_id,status) values (1,3,'ENROLLED');
insert into enrollment(course_id,student_id,status) values (2,3,'ENROLLED');
insert into enrollment(course_id,student_id,status) values (3,3,'PENDING');
insert into enrollment(course_id,student_id,status) values (4,2,'ENROLLED');
insert into enrollment(course_id,student_id,status) values (5,2,'ENROLLED');
-- ENROLLMENT 6-9 ELECTIVE
-- insert into enrollment(course_id,student_id,status) values (6,3,'ENROLLED');
-- insert into enrollment(course_id,student_id,status) values (7,3,'ENROLLED');
-- insert into enrollment(course_id,student_id,status) values (4,4,'ENROLLED');
-- insert into enrollment(course_id,student_id,status) values (5,4,'ENROLLED');




-- insert into students(id) values (10);