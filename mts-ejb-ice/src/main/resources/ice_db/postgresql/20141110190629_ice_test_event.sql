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

-- // ice_test_event
-- Migration SQL that makes the change goes here.

create table ice_test_event (
            event_id varchar(32) primary key,
            test_id varchar(32),
            vaccine_id varchar(32),
            administration_time date,
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_test_event add constraint ice_iecvid2cvvid foreign key (vaccine_id) references ICE_VACCINE (vaccine_id);

alter table ice_test_event add constraint ce2tc_tcid foreign key (test_id) references ice_test (test_id);

INSERT INTO ice_test_event (event_id, test_id, vaccine_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d68ec7cc310eec1acfa97c5e96bc6f24', '51c05495139a1c0c7b31c338c8510856', '6883576bd868da1d907142ade9f7b923', '2013-01-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event (event_id, test_id, vaccine_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('baf55bbcef88153cabd5d07b421e7487', '937480dbbaf10a2f6665e36a2e8b78bf', '7a2b1bee78f886391cbd446123d9b20d', '2010-03-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event (event_id, test_id, vaccine_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aaff41775c694190b556e14157fa01d5', 'abf217d657ab7ceb4b31fbe1ef860a64', 'b041a921609bd35ceb980ef6fa484baf', '2011-01-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event (event_id, test_id, vaccine_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('23d25e4fae64bab576343a7cf438af53', 'eb0f9f74ecd130b14db5c1e9bae33b51', '39b53d6016e88f5784bad89ecd3fde2e', '2010-03-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_test_event;
