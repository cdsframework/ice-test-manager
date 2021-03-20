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

-- // ice_test_event_component
-- Migration SQL that makes the change goes here.

create table ice_test_event_component (
            event_component_id varchar(32) primary key,
            event_id varchar(32),
            eval_value_id varchar(32),
            component_id varchar(32),
            group_id varchar(32),
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_test_event_component add constraint ice_iecvgid2cvvid foreign key (group_id) references ICE_VACCINE_GROUP (group_id);

alter table ice_test_event_component add constraint ice_iecvid2cvvid2 foreign key (component_id) references ICE_VACCINE_COMPONENT (component_id);

alter table ice_test_event_component add constraint fk_iecevid2cccid foreign key (eval_value_id) references cds_code (code_id);

alter table ice_test_event_component add constraint cec2ce_eid foreign key (event_id) references ice_test_event (event_id);

INSERT INTO ice_test_event_component (event_component_id, event_id, eval_value_id, component_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2be163741ff13e7ea23d32c25d228315', 'd68ec7cc310eec1acfa97c5e96bc6f24', 'f773fab3de9c467d37130b2e8e02a7f1', '6334689712dcc4ebf302c90968682853', '9808d21f26aa9303abb790c5de6dfa81', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event_component (event_component_id, event_id, eval_value_id, component_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('57c2cd095e76b225f493d62948c104bb', 'd68ec7cc310eec1acfa97c5e96bc6f24', 'f773fab3de9c467d37130b2e8e02a7f1', 'a58a28918894428bdefab782a7f1c215', '28f2cf85fc083a569eb46a69671fe8b0', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event_component (event_component_id, event_id, eval_value_id, component_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f789c34b5552dc4b9d53750b196b8254', 'baf55bbcef88153cabd5d07b421e7487', 'd46a55919980fa73aa4e2c42cf01916d', '5178e23c337a26a1ce17a4a73f8bf97b', '9808d21f26aa9303abb790c5de6dfa81', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event_component (event_component_id, event_id, eval_value_id, component_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4cd2dd4c386e8c2dac97dba31f58a1f6', 'aaff41775c694190b556e14157fa01d5', 'f773fab3de9c467d37130b2e8e02a7f1', '5b04394f57e2cd25a8f995ff5d4d0854', '4192deec1e4503a7df2e74a4b7289f0b', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event_component (event_component_id, event_id, eval_value_id, component_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7249711c4f173341c0f21099261145fb', 'aaff41775c694190b556e14157fa01d5', 'f773fab3de9c467d37130b2e8e02a7f1', 'ed444e8a59047e86b28e89e335b21bfb', '6248e15c5d8a94c05c54f8efac5b3e62', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_test_event_component (event_component_id, event_id, eval_value_id, component_id, group_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f8927a2fe579b1f22a741bf507d506a8', '23d25e4fae64bab576343a7cf438af53', 'f773fab3de9c467d37130b2e8e02a7f1', '59e02e084e71ebc4cf13c0a748e4f47a', '9808d21f26aa9303abb790c5de6dfa81', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_test_event_component;
