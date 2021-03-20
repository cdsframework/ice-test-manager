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

-- // ice_test_group
-- Migration SQL that makes the change goes here.

create table ice_test_group (
            group_id varchar(32) primary key,
            suite_id varchar(32) not null,
            name varchar(256) not null,
            ignore_test varchar(1) not null,
            description varchar(3072),
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_test_group add constraint tcg2tcs foreign key (suite_id) references ice_test_suite (suite_id);

INSERT INTO ice_test_group (group_id, suite_id, name, ignore_test, description, last_mod_datetime, last_mod_id, create_datetime, create_id) VALUES ('3ccf7d6d7ef0882a87eee5c5b68ec457', 'cf5b3ba59651b669f4304903f5dfc231', 'Testing Combination Vaccines', 'N', NULL, CURRENT_TIMESTAMP, 'ddce5b65bceb5d26a449e9076d31da9d', CURRENT_TIMESTAMP, 'ddce5b65bceb5d26a449e9076d31da9d');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_test_group;
