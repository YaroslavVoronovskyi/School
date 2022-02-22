INSERT INTO groups (group_name) VALUES 
    ('QW-11'), 
    ('ER-12'), 
    ('TY-21'),
    ('UI-22'),
    ('OP-31'),
    ('AS-32'),
    ('DF-41'),
    ('GH-42'),
    ('JK-51'),
    ('LZ-52');
    
INSERT INTO students (group_ref, first_name, last_name) VALUES 
   (1, 'Viktor', 'Tsygankov'),
   (2, 'Denys', 'Tsygankov'),
   (3, 'Olena', 'Tsygankov'),
   (4, 'Darya', 'Tsygankov'),
   (5, 'Mykola', 'Tsygankov'),
   (6, 'Mariya', 'Tsygankov'),
   (7, 'Vlad', 'Tsygankov'),
   (8, 'Svitlana', 'Tsygankov'),
   (9, 'Andriy', 'Tsygankov'),
   (10, 'Marina', 'Tsygankov');
    
INSERT INTO courses (course_name, course_description) VALUES 
    ('Math', 'Math includes the study of such topics as number theory, algebra, geometry and mathematical analysis'), 
    ('Physics', 'Physics is the natural science that studies matter, its motion and behavior through space and time, and the related entities of energy and force.'), 
    ('Chemistry', 'Chemistry is the scientific discipline involved with elements and compounds composed of atoms, molecules and ions: their composition, structure, properties, behavior and the changes they undergo during a reaction with other substances.'),
    ('Astronomy', 'Astronomy is a natural science that studies celestial objects and phenomena.'),
    ('Biology', 'Biology is the natural science that studies life and living organisms, including their physical structure, chemical processes, molecular interactions, physiological mechanisms, development and evolution.'),
    ('Geography', 'Geography is a field of science devoted to the study of the lands, features, inhabitants, and phenomena of the Earth and planets.'),
    ('Literature', 'Literature broadly is any collection of written work, but it is also used more narrowly for writings specifically considered to be an art form, especially prose fiction, drama, and poetry.'),
    ('Language', 'Ukrainian is an East Slavic language. It is the official state language of Ukraine an.'),
    ('Philosophy', 'Philosophy is the study of general and fundamental questions about existence, knowledge, values, reason, mind, and language.'),
    ('History', 'History is the study of the past.');
    