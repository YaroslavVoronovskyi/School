DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
    group_id SERIAL NOT NULL,
    group_name CHARACTER VARYING(5) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id),
    CONSTRAINT groups_group_name_ukey UNIQUE (group_name)
    );

DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses (
    course_id SERIAL NOT NULL,
    course_name CHARACTER VARYING(100) NOT NULL,
    course_description CHARACTER VARYING(300) NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id),
    CONSTRAINT courses_course_name_ukey UNIQUE (course_name)
    );

DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students (
    student_id SERIAL NOT NULL,
    group_ref INTEGER DEFAULT NULL,
    first_name CHARACTER VARYING(100) NOT NULL,
    last_name CHARACTER VARYING(100) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT students_group_id_fkey FOREIGN KEY (group_ref)
        REFERENCES groups (group_id) ON DELETE SET DEFAULT
    );

DROP TABLE IF EXISTS students_courses CASCADE;
CREATE TABLE students_courses (
    student_ref INTEGER NOT NULL,
    course_ref INTEGER NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (student_ref, course_ref),
    CONSTRAINT students_courses_student_id_fkey FOREIGN KEY (student_ref)
         REFERENCES students (student_id) ON DELETE CASCADE,
    CONSTRAINT students_courses_course_id_fkey FOREIGN KEY (course_ref)
         REFERENCES courses (course_id) ON DELETE CASCADE
    );
    