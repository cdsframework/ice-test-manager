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

-- // zoster vaccines
-- Migration SQL that makes the change goes here.

-- update display name for zoster 121
UPDATE cds_code set display_name = 'zoster vaccine, live' where code_id = 'dbfd3987a6a6b4b21e7e13ae5db1f787';

-- create ICE121 concept
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('8db2e79e95abec7f75c5bdf8194138f1', 'ICE121', 'Zoster Vaccine', 'Zoster Vaccine', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('611789ac9e90ce2bd47d7172dd893cc6', '8db2e79e95abec7f75c5bdf8194138f1', 'dbfd3987a6a6b4b21e7e13ae5db1f787', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');

-- list items
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('c63892d32a461b6040354e98391dbe4f', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '8db2e79e95abec7f75c5bdf8194138f1', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- vaccine component
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('8cdaf7f7d58617e83dd5d6aa432e11b0', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'dbfd3987a6a6b4b21e7e13ae5db1f787');
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('f9d9e2551ec90795a1533cc3294ac06c', '95176a1290628ba537fad2184a8a734f', '8cdaf7f7d58617e83dd5d6aa432e11b0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- insert vaccine
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('44e55a06ac4884a78846d849cf1534b7', 'dbfd3987a6a6b4b21e7e13ae5db1f787', 'zoster vaccine, live', '8db2e79e95abec7f75c5bdf8194138f1', '50y', NULL, '21e1fa8e8288d90ef82ba31ad0ec2533', 'Zostavax', NULL, NULL, true, NULL, NULL, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('5441c26657ef84e04c265272a7d14cc6', '44e55a06ac4884a78846d849cf1534b7', '8cdaf7f7d58617e83dd5d6aa432e11b0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('23e24c80284a144c86e87f9fb6bb0686', '44e55a06ac4884a78846d849cf1534b7', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

delete from ice_vaccine_component_rel where component_rel_id in ('5441c26657ef84e04c265272a7d14cc6');
delete from ice_vaccine_version_rel where version_rel_id in ('23e24c80284a144c86e87f9fb6bb0686');
delete from ice_vaccine where vaccine_id in ('44e55a06ac4884a78846d849cf1534b7');
delete from cds_list_item where item_id in ('c63892d32a461b6040354e98391dbe4f');
delete from opencds_concept_rel where relationship_id in ('611789ac9e90ce2bd47d7172dd893cc6');
delete from opencds_concept where code_id in ('8db2e79e95abec7f75c5bdf8194138f1');
delete from ice_vaccine_disease_rel where disease_rel_id in ('f9d9e2551ec90795a1533cc3294ac06c');
delete from ice_vaccine_component where component_id in ('8cdaf7f7d58617e83dd5d6aa432e11b0');
