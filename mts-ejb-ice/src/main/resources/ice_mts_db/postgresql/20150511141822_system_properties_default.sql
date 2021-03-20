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

-- // system_properties_default
-- Migration SQL that makes the change goes here.
delete from system_property where name = 'CDS_DEFAULT_BUSINESS_SCOPE' and scope = 'cds';
delete from system_property where name = 'CDS_DEFAULT_CONCEPT_DETERMINATION_METHOD' and scope = 'cds';
delete from system_property where name = 'CDS_DEFAULT_VERSION' and scope = 'cds';
delete from system_property where name = 'TEST_MGR_TEST_SUBMISSION_Q_SIZE' and scope = 'ice';
delete from system_property where name = 'ICE_DEFAULT_BUSINESS_SCOPE' and scope = 'ice';
delete from system_property where name = 'ICE_DEFAULT_BUSINESS_ID' and scope = 'ice';
delete from system_property where name = 'ICE_DEFAULT_SCOPING_ENTITY_ID' and scope = 'ice';
delete from system_property where name = 'ICE_DEFAULT_VERSION' and scope = 'ice';
delete from system_property where name = 'ICE_DEFAULT_CONCEPT_DETERMINATION_METHOD' and scope = 'ice';
INSERT INTO system_property (property_id, name, type, property_group, scope, value, obscure, mts_only, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('47e353c5d84e9bc6150bf9fb64f93667', 'ICE_DEFAULT_BUSINESS_ID', 'java.lang.String', NULL, 'ice', 'ICE', false, false, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO system_property (property_id, name, type, property_group, scope, value, obscure, mts_only, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('23fab00c9c8a58c457d56cd734155ec5', 'ICE_DEFAULT_SCOPING_ENTITY_ID', 'java.lang.String', NULL, 'ice', 'org.nyc.cir', false, false, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO system_property (property_id, name, type, property_group, scope, value, obscure, mts_only, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7d6ca07836d969b539d0625cea909806', 'ICE_DEFAULT_VERSION', 'java.lang.String', NULL, 'ice', '1.0.0', false, false, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO system_property (property_id, name, type, property_group, scope, value, obscure, mts_only, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ee7fe4375b628dc45169223db94d249', 'ICE_DEFAULT_CONCEPT_DETERMINATION_METHOD', 'java.lang.String', NULL, 'ice', 'C36', false, false, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO system_property (property_id, name, type, property_group, scope, value, obscure, mts_only, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('73c67099a607a7f8048501735667c1fa', 'TEST_MGR_TEST_SUBMISSION_Q_SIZE', 'java.lang.Integer', 'TEST_MGR', 'ice', '10', false, false, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

delete from system_property where property_id in
    ('47e353c5d84e9bc6150bf9fb64f93667','7d6ca07836d969b539d0625cea909806','23fab00c9c8a58c457d56cd734155ec5','2ee7fe4375b628dc45169223db94d249', '73c67099a607a7f8048501735667c1fa');