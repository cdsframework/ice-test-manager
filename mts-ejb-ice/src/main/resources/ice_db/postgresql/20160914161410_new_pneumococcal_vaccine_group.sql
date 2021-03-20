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

-- // new pneumococcal vaccine group
-- Migration SQL that makes the change goes here.

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('3027a089ab6250b9adec347aeac67aa5', 'e6b7f6483524ff91e2feae09fe4b1619', '750', 'Pneumococcal (New)', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('c740077ddce164d432f00de1f345b491', 'ICE299', 'Immunization Focus (Pneumococcal New)', 'Immunization Focus (Pneumococcal New)', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('55726fcbd2717376bc053abe1036ca84', 'c740077ddce164d432f00de1f345b491', '801bf6ead736240b7a238369c3005754', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl', '8a74891e484ecf4a221d15d973427416', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('b7975e0e9c0be1fe92f165ad51732adf', 'c740077ddce164d432f00de1f345b491', '3027a089ab6250b9adec347aeac67aa5', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl', 'e6b7f6483524ff91e2feae09fe4b1619', NULL, 'CODE');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('f36c90d334196fba39a38970d160a4f3', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'c740077ddce164d432f00de1f345b491', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');

INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('1301efbf263c78e3accc1b716f83c8b2', 'ICE750', 'Immunization Evaluation (Pneumococcal Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'sdn', CURRENT_TIMESTAMP, 'sdn');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('b2294996679c7bc3fc9349e58234b0a1', '1301efbf263c78e3accc1b716f83c8b2', '3027a089ab6250b9adec347aeac67aa5', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'sdn', CURRENT_TIMESTAMP, 'sdn', 'e6b7f6483524ff91e2feae09fe4b1619', NULL, 'CODE');


INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, priority, last_mod_id, last_mod_datetime, create_id, create_datetime, primary_concept) 
	VALUES ('b8366039f9eec78f287436b33966b277', '3027a089ab6250b9adec347aeac67aa5', 0, 'sdn', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, '1301efbf263c78e3accc1b716f83c8b2');

INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('ae7aa357fe14436f8abcdcc9670f84b8', '801bf6ead736240b7a238369c3005754', 'b8366039f9eec78f287436b33966b277', 'daryl', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP);

INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('387d61c0f677f81dd3e623a019b5b524', 'b8366039f9eec78f287436b33966b277', 'f89c7a73aff266756922d437e0ad8044', 'daryl', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

delete from ice_vaccine_group_version_rel where version_rel_id = '387d61c0f677f81dd3e623a019b5b524';
delete from ice_vaccine_group_disease_rel where disease_rel_id = 'ae7aa357fe14436f8abcdcc9670f84b8';
delete from ice_vaccine_group where group_id = 'b8366039f9eec78f287436b33966b277';
delete from opencds_concept_rel where relationship_id = 'b2294996679c7bc3fc9349e58234b0a1';
delete from opencds_concept where code_id = '1301efbf263c78e3accc1b716f83c8b2';
delete from cds_list_item where item_id = 'f36c90d334196fba39a38970d160a4f3';
delete from opencds_concept_rel where relationship_id = 'b7975e0e9c0be1fe92f165ad51732adf';
delete from opencds_concept_rel where relationship_id = '55726fcbd2717376bc053abe1036ca84';
delete from opencds_concept where code_id = 'c740077ddce164d432f00de1f345b491';
delete from cds_code where code_id = '3027a089ab6250b9adec347aeac67aa5';