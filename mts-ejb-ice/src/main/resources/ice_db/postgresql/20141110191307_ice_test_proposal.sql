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

-- // ice_test_proposal
-- Migration SQL that makes the change goes here.

create table ice_test_proposal (
            proposal_id varchar(32) primary key,
            rec_value_id varchar(32),
            test_id varchar(32) not null,
            vaccine_id varchar(32),
            group_id varchar(32),
            administration_time date,
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_test_proposal add constraint fk_irvid2cccid foreign key (rec_value_id) references cds_code (code_id);

alter table ice_test_proposal add constraint ice_ipvgid2cvvid foreign key (vaccine_id) references ice_VACCINE (vaccine_id);

alter table ice_test_proposal add constraint ice_ipvgid2cvgvgid foreign key (group_id) references ICE_VACCINE_GROUP (group_id);

alter table ice_test_proposal add constraint cp2tc_tcid foreign key (test_id) references ice_test (test_id);

INSERT INTO ice_test_proposal (proposal_id, rec_value_id, test_id, vaccine_id, group_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1cd73de6e15b8919ba5065432ffd7635', '6caad4861f355a6176d2ddcd338541b0', '51c05495139a1c0c7b31c338c8510856', NULL, '28f2cf85fc083a569eb46a69671fe8b0', '2013-03-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_proposal (proposal_id, rec_value_id, test_id, vaccine_id, group_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ff9a6f2d184dcc1f393af23ab66c5db3', '6caad4861f355a6176d2ddcd338541b0', '51c05495139a1c0c7b31c338c8510856', NULL, '9808d21f26aa9303abb790c5de6dfa81', '2013-03-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_proposal (proposal_id, rec_value_id, test_id, vaccine_id, group_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('128846ddcfae6a995f9f0985263e8a9a', '6caad4861f355a6176d2ddcd338541b0', '937480dbbaf10a2f6665e36a2e8b78bf', NULL, '9808d21f26aa9303abb790c5de6dfa81', '2010-03-29', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_proposal (proposal_id, rec_value_id, test_id, vaccine_id, group_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a080b65f14227ea85b5253e464cd45c9', '6caad4861f355a6176d2ddcd338541b0', 'abf217d657ab7ceb4b31fbe1ef860a64', NULL, '4192deec1e4503a7df2e74a4b7289f0b', '2014-01-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_proposal (proposal_id, rec_value_id, test_id, vaccine_id, group_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6b1e5e061bff603fe3946c1ab5ad2037', '6caad4861f355a6176d2ddcd338541b0', 'abf217d657ab7ceb4b31fbe1ef860a64', NULL, '6248e15c5d8a94c05c54f8efac5b3e62', '2014-01-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_proposal (proposal_id, rec_value_id, test_id, vaccine_id, group_id, administration_time, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fe41c1d7607564796baeb74cc0839fa1', '6caad4861f355a6176d2ddcd338541b0', 'eb0f9f74ecd130b14db5c1e9bae33b51', NULL, '9808d21f26aa9303abb790c5de6dfa81', '2010-05-01', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_test_proposal;
