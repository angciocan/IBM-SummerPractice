insert into student(grade,study_year,faculty_section,name) values (10,4,'CTI','Robert');
insert into student(grade,study_year,faculty_section,name) values (7,3,'CTI','Angleo');
insert into student(grade,study_year,faculty_section,name) values (8,2,'IS','Mircea');
--
insert into teacher(name) values ('Nicolina');
-- --
insert into administrator(name) values ('BOSS');
--
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (2,99,3,'15:00',1,'mandatory','fizica');
--
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (4,23,3,'17:00',1,'mandatory','baze de date');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (4,23,3,'17:00',1,'mandatory','bazele programarii');
insert into course(day_of_week,max_students,study_year,time,teacher_id,category,course_name) values (4,23,3,'17:00',1,'mandatory','programare web');

insert into enrollment_administration(nr_of_mandatory_courses,nr_of_elective_courses, study_year) values (4,2, 2);
insert into enrollment_administration(nr_of_mandatory_courses,nr_of_elective_courses, study_year) values (3,3, 3);
insert into enrollment_administration(nr_of_mandatory_courses,nr_of_elective_courses, study_year) values (2,4, 4);
--
-- insert into enrollment(course_id,student_id,status) values (1,2,'PENDING');
-- insert into enrollment(course_id,student_id,status) values (1,3,'PENDING');
-- insert into enrollment(course_id,student_id,status) values (2,1,'ENROLLED');
-- insert into enrollment(course_id,student_id,status) values (2,2,'ENROLLED');
-- insert into enrollment(course_id,student_id,status) values (3,1,'PENDING');



-- insert into students(id) values (10);