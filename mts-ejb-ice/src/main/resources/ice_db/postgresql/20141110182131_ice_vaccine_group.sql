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

-- // ice_vaccine_group
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_group (
            group_id VARCHAR(32) primary key,
            vaccine_group_id VARCHAR(32) not null,
            primary_concept VARCHAR(32),
            priority integer,
            LAST_MOD_ID VARCHAR(32) NOT NULL,
            LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
            CREATE_ID VARCHAR(32) NOT NULL,
            CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_group add constraint fk_ivgvgid2cccid foreign key (vaccine_group_id) references cds_code (code_id);

INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1ababe4ac2985098e7d4af77a9415a3d', '7564ba9956b5e1231317f0d35c0c0562', '7a8ef07a731cf9a67a21aec0cb5ecf61', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('28f2cf85fc083a569eb46a69671fe8b0', '46b67611ecfde554df6e9fa2a03c0c44', '492d826422926c69c47a88296b24c5bf', 10, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2cc45eefd7d0135e9ed5e4dcb81cd2d1', 'a56086c8f7ea4be65a9872d610932f62', '9810e88f4c5b296d1991036ff3b95de8', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('308d8b6530000d15b4c6c04670af5aba', '8e7c271a449d8a0b43712eea56609953', '39cb978436a76ee52eac5d7a7bd6358f', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4192deec1e4503a7df2e74a4b7289f0b', '8b71825560392a83ebb8e4da645e31f9', '7a77007c21f94e7349cc9c6d39ee8e45', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('514c2a0658db7732b4fd1fb9cde3833e', 'faced2f118d43da024aee72e3fff7b99', 'f3273c9cadb1e052c534f3c046160c28', 10, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('51785397ea25f92c2349b7b47e363a62', '6081ee6ced25174c1dcee698a270be7e', '24055466bcc6ec0fa62cc9c7c733df14', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6248e15c5d8a94c05c54f8efac5b3e62', '310518a288bb55558f9e5a614528e551', 'c503f873a4b062a72509385156f08e5d', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('8faa56ba12d371ff805a9967c31c0fb5', '6efadfb4961c7e87a0be9bc0b6ed47a0', '87a047178bbf376a8f700265dae17e2d', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9808d21f26aa9303abb790c5de6dfa81', '1e30a4dfba31908875df46e6a39c87ff', '1413822a1d192f731cc2726d8c422001', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a97422fac07fed6db5cf2f95ea141e1c', 'd61dfbba9507faf0201b746027a614b1', '32d5d1da56bf18c9a42450cbe1a77ea3', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('bc0aadca211f5a453a5bdf274e387c20', '89c6de3ec6e652b0ea9adb32c51b3b2d', NULL, NULL, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cee1aace28bc115685dbbfd1536c7307', 'a0d81acc5ecff41b343d937031a2c3c7', 'c6eadd032e897c62fc6cef8939336ce5', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d031730eeb92154f78bc4f4c88827b45', 'a1bbadd1e2579d63fd83a3505c4e0c9c', '672c794bcf04406c28635f0c90904c43', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('da79a9a2186d87cd236c1d6a9fd86e28', 'ae074b7462f600e8f8f5b9e8972ff0aa', '27e528b13c8f5a59814117f0433da63c', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_group;

