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

-- // ice_test
-- Migration SQL that makes the change goes here.

create table ice_test (
            test_id varchar(32) primary key,
            group_id varchar(32) not null,
            name varchar(1024) not null,
            ignore_test varchar(1) not null,
            encoded_name varchar(512) not null,
            rule_to_test varchar(4000),
            notes varchar(4000),
            execution_date date not null,
            task_context varchar(32),
            dob date not null,
            gender varchar(32) not null,
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_test add constraint fk_itg2cccid foreign key (gender) references cds_code (code_id);

alter table ice_test add constraint tc2tcs_sid foreign key (group_id) references ice_test_group (group_id);

INSERT INTO ice_test (test_id, group_id, name, ignore_test, encoded_name, rule_to_test, notes, execution_date, task_context, dob, gender, last_mod_datetime, last_mod_id, create_datetime, create_id) VALUES ('51c05495139a1c0c7b31c338c8510856', '3ccf7d6d7ef0882a87eee5c5b68ec457', 'Combination vaccine - CVX 51. (HepB component CVX 08 - Hib component CVX 49)', 'N', '6ed216e61a8b0391cfb6711de6d0b7c7b136a9c690cde50a28619aedf01911c7', NULL, NULL, '2013-01-01', NULL, '2012-11-01', '537030c4d4a618263258a7d819b71b16', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'ddce5b65bceb5d26a449e9076d31da9d');

INSERT INTO ice_test (test_id, group_id, name, ignore_test, encoded_name, rule_to_test, notes, execution_date, task_context, dob, gender, last_mod_datetime, last_mod_id, create_datetime, create_id) VALUES ('937480dbbaf10a2f6665e36a2e8b78bf', '3ccf7d6d7ef0882a87eee5c5b68ec457', 'Combination vaccine - CVX 50. (Hib component = CVX 48 - DTaP component = CVX 20)', 'N', '5c84caf5a57633b4e5697c1ef824468c90a34dbc89727d31517377a2176a3459', 'Hib 4-dose Series Rule for CVX 50
•The Hib component of CVX 50 (which is CVX 48) is evaluated as Valid only when it is the final dose of the series, follows a previous dose of Hib, and patient is => 361 days - otherwise, the Hib component of CVX 50 (which is CVX 48) is Invalid and the reason code is BOOSTER_ONLY).1 

•This rule applies only to CVX 48 when it is the Hib component of the combination vaccine CVX 50 - this rule does not apply when CVX 48 is given by itself or as part of any other combination vaccine.  
', NULL, '2010-03-01', NULL, '2010-01-01', '161a3206589043b77094ef410e707121', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'ddce5b65bceb5d26a449e9076d31da9d');

INSERT INTO ice_test (test_id, group_id, name, ignore_test, encoded_name, rule_to_test, notes, execution_date, task_context, dob, gender, last_mod_datetime, last_mod_id, create_datetime, create_id) VALUES ('abf217d657ab7ceb4b31fbe1ef860a64', '3ccf7d6d7ef0882a87eee5c5b68ec457', 'Combination vaccine - CVX 94 (MMR component CVX 03 - Varicella component CVX 21)', 'N', '716a43b0290f4651bac0e9a531b9a921413094a69ab388bbe874b0ede69df566', NULL, NULL, '2011-01-01', NULL, '2010-01-01', '537030c4d4a618263258a7d819b71b16', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'ddce5b65bceb5d26a449e9076d31da9d');

INSERT INTO ice_test (test_id, group_id, name, ignore_test, encoded_name, rule_to_test, notes, execution_date, task_context, dob, gender, last_mod_datetime, last_mod_id, create_datetime, create_id) VALUES ('eb0f9f74ecd130b14db5c1e9bae33b51', '3ccf7d6d7ef0882a87eee5c5b68ec457', 'Combination vaccine - CVX 22. (Hib component = CVX 17 - DTaP component = CVX 20)', 'N', '685d4404355b7fc5f2e81ef919bc6db3979fbf5cfd0584dc34d1491396abefa3', NULL, NULL, '2010-03-01', NULL, '2010-01-01', '161a3206589043b77094ef410e707121', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'ddce5b65bceb5d26a449e9076d31da9d');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_test;
