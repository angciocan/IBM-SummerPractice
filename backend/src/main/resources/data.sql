insert into student(grade,study_year,id,faculty_section,name) values (10,4,11,'CTI','Robert');
insert into student(grade,study_year,id,faculty_section,name) values (7,3,23,'CTI','Angleo');
insert into student(grade,study_year,id,faculty_section,name) values (8,2,47,'IS','Mircea');
--
insert into teacher(id,name) values (4,'Nicolina');
-- --
insert into administrator(id,name) values (6,'BOSS');
--
insert into course(day_of_week,max_students,study_year,time,id,teacher_id,category,course_name) values (2,99,1,'15:00',101,4,'hardware','fizica');
--
insert into course(day_of_week,max_students,study_year,time,id,teacher_id,category,course_name) values (4,23,3,'17:00',345,4,'software','baze de date');

insert into enrollment_administration(nr_of_courses, study_year) values (2, 2);
insert into enrollment_administration(nr_of_courses, study_year) values (3, 3);
insert into enrollment_administration(nr_of_courses, study_year) values (4, 4);

insert into enrollment(course_id,id,student_id,status) values (101,1,11,'ENROLLED');
insert into enrollment(course_id,id,student_id,status) values (345,2,47,'ENROLLED');
-- insert into enrollment(course_id,id,student_id,status) values (101,3,23,'ENROLLED');
-- insert into enrollment(course_id,id,student_id,status) values (101,4,47,'ENROLLED');
insert into enrollment(course_id,id,student_id,status) values (345,5,11,'ENROLLED');


-- insert into students(id) values (10);