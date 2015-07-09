insert into contact_group (id, version, name, parent_id) values (1, 1, 'All Groups', NULL);
insert into contact_group (id, version, name, parent_id) values (2, 1, 'Colleagues', 1);
insert into contact_group (id, version, name, parent_id) values (3, 1, 'Family', 1);
insert into contact_group (id, version, name, parent_id) values (4, 1, 'Friends', 1);
insert into contact_group (id, version, name, parent_id) values (5, 1, 'Bosses', 2);
insert into contact (id, version, birthday, description, firstname, lastname, gender, likeness, city, country, street1, street2, zipcode, contactgroup_id) VALUES (1, 1, '1980-07-02', NULL, 'John', 'Doe', 'MALE', 4, 'Antwerp', 'Belgium', NULL, NULL, NULL, 2);
insert into contact (id, version, birthday, description, firstname, lastname, gender, likeness, city, country, street1, street2, zipcode, contactgroup_id) VALUES (2, 1, '1981-12-01', NULL, 'Theo', 'Tester', 'MALE', 5, 'Brussels', 'Belgium', NULL, NULL, NULL, 4);