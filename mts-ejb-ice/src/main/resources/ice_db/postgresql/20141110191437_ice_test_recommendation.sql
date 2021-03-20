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

-- // ice_test_recommendation
-- Migration SQL that makes the change goes here.

create table ice_test_recommendation (
            recommendation_id varchar(32) primary key,
            proposal_id varchar(32),
            rec_interpret_id varchar(32),
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_test_recommendation add constraint fk_iriid2cccid foreign key (rec_interpret_id) references cds_code (code_id);

alter table ice_test_recommendation add constraint fk_iprid2irrid foreign key (proposal_id) references ice_test_proposal (proposal_id);

INSERT INTO ice_test_recommendation (recommendation_id, proposal_id, rec_interpret_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('532f2efe4e120ecfc3c7774ac746e13a', '1cd73de6e15b8919ba5065432ffd7635', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_recommendation (recommendation_id, proposal_id, rec_interpret_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9a29b17abae323abf0c0cb865cab7d3e', 'ff9a6f2d184dcc1f393af23ab66c5db3', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_recommendation (recommendation_id, proposal_id, rec_interpret_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6f1c8b99b60557b22a77ed3b3b032dc8', '128846ddcfae6a995f9f0985263e8a9a', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_recommendation (recommendation_id, proposal_id, rec_interpret_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cc3240afb407612251fe918a6b611bfd', 'a080b65f14227ea85b5253e464cd45c9', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_recommendation (recommendation_id, proposal_id, rec_interpret_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e80b10d38bddbc063f07921b6855bfb5', '6b1e5e061bff603fe3946c1ab5ad2037', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_recommendation (recommendation_id, proposal_id, rec_interpret_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ff494872999f1ffededa4be46cf58a07', 'fe41c1d7607564796baeb74cc0839fa1', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_test_recommendation;
