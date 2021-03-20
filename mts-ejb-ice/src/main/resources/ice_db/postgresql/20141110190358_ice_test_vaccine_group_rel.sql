--
-- The MTS ice EJB project is the base framework for the CDS Framework Middle Tier Service.
--
-- Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
-- Contributions by HLN Consulting, LLC
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the GNU
-- Lesser General Public License as published by the Free Software Foundation, either version 3 of the
-- License, or (at your option) any later version. You should have received a copy of the GNU Lesser
-- General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
-- details.
--
-- The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
-- Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
-- limitation, and warranty) complete irrevocable access and rights to this project.
--
-- This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
--
-- SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
-- BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
-- NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
-- ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
-- THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
--
-- For more information about this software, see https://www.hln.com/services/open-source/ or send
-- correspondence to ice@hln.com.
--

-- // ice_test_vaccine_group_rel
-- Migration SQL that makes the change goes here.

create table ice_test_vaccine_group_rel (
            test_id varchar(32) not null,
            group_id varchar(32) not null,
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null,
            PRIMARY KEY(test_id, group_id));

alter table ice_test_vaccine_group_rel add constraint fk_itvgrvg2cvggs foreign key (group_id) references ice_VACCINE_GROUP (group_id);

alter table ice_test_vaccine_group_rel add constraint fk_itvgrtid2ittid foreign key (test_id) references ice_test (test_id);

INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('51c05495139a1c0c7b31c338c8510856', '28f2cf85fc083a569eb46a69671fe8b0', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('51c05495139a1c0c7b31c338c8510856', '9808d21f26aa9303abb790c5de6dfa81', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('937480dbbaf10a2f6665e36a2e8b78bf', '9808d21f26aa9303abb790c5de6dfa81', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('937480dbbaf10a2f6665e36a2e8b78bf', '308d8b6530000d15b4c6c04670af5aba', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('abf217d657ab7ceb4b31fbe1ef860a64', '6248e15c5d8a94c05c54f8efac5b3e62', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('abf217d657ab7ceb4b31fbe1ef860a64', '4192deec1e4503a7df2e74a4b7289f0b', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb0f9f74ecd130b14db5c1e9bae33b51', '9808d21f26aa9303abb790c5de6dfa81', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_vaccine_group_rel (test_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb0f9f74ecd130b14db5c1e9bae33b51', '308d8b6530000d15b4c6c04670af5aba', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.


drop table ice_test_vaccine_group_rel;
