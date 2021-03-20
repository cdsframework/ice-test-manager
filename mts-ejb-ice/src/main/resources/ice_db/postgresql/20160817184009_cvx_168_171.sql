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

-- // cvx 168 171
-- Migration SQL that makes the change goes here.

-- codes
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('4da29ee3ae9801e2dcb330c215c35a21', 'e0bc41f880dc19d217656508b6cf9908', '168', 'Influenza, IIV3, Adjuvanted,IM(>=65yrs)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('757fa0ebb10e2dd5ce72bd9dd2267276', 'e0bc41f880dc19d217656508b6cf9908', '171', 'Influenza-ccIIV4, IM (>=4yrs)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for ICE_IMM_VACCINE
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('1aee0f4a584c02f983e1dbebd5a19110', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '4da29ee3ae9801e2dcb330c215c35a21', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('1823efbb4a37b178904ea33641087253', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '757fa0ebb10e2dd5ce72bd9dd2267276', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');

-- components
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('81eee6e4410e129aafac720db52c36bb', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '4da29ee3ae9801e2dcb330c215c35a21');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('779a445ddb315e4d49cd0d0376de9111', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '757fa0ebb10e2dd5ce72bd9dd2267276');

-- component diseases
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('1c940e4b21a60ec4374f53fbc79f53a6', '4f27cec9fd486d29935cc05b3ad185b4', '81eee6e4410e129aafac720db52c36bb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('346db8e3dd2b267d21ee8daa413e7448', '4f27cec9fd486d29935cc05b3ad185b4', '779a445ddb315e4d49cd0d0376de9111', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- concepts
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('7ec94296c6596cd1ab70b2f9c7978fa2', 'ICE171', 'Influenza-ccIIV4, IM (>=4yrs)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('b78f2c2eeb593f96606e9ebadc78a734', 'ICE168', 'Influenza, IIV3, Adjuvanted,IM(>=65yrs)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- concept code mappings
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('ba58caa3168f46fb3b58cc58ebea77ac', '7ec94296c6596cd1ab70b2f9c7978fa2', '757fa0ebb10e2dd5ce72bd9dd2267276', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('96652bf28d851564d01aaf276925851d', 'b78f2c2eeb593f96606e9ebadc78a734', '4da29ee3ae9801e2dcb330c215c35a21', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');

-- concept list items
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('1608426288a0c2dc1f5e5ee6434d3994', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'b78f2c2eeb593f96606e9ebadc78a734', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('1338426288a0c2dc1f5e5ff6434d3994', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '7ec94296c6596cd1ab70b2f9c7978fa2', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- vaccines
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('42735ade82a15fce816493c1af6ba53c', '4da29ee3ae9801e2dcb330c215c35a21', 'Influenza, IIV3, Adjuvanted,IM(>=65yrs)', 'b78f2c2eeb593f96606e9ebadc78a734', NULL, NULL, 'cfad7f2189f4a1ec8c63bc542496ed5c', NULL, NULL, NULL, true, NULL, NULL, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('18264580bbb99333ae571cdd385ec7a3', '757fa0ebb10e2dd5ce72bd9dd2267276', 'Influenza-ccIIV4, IM (>=4yrs)', '7ec94296c6596cd1ab70b2f9c7978fa2', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- vaccine versions
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('2fff4427ddf3fbb5a2f6415d8d8a64cb', '42735ade82a15fce816493c1af6ba53c', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('b103ca573400422b3494d74196d7edb9', '18264580bbb99333ae571cdd385ec7a3', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- vaccine components
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('9ea8c704d90f9816a9e050ee707a77c6', '42735ade82a15fce816493c1af6ba53c', '81eee6e4410e129aafac720db52c36bb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('d1d5e924d32f02c42b3b94ada492c8a6', '18264580bbb99333ae571cdd385ec7a3', '779a445ddb315e4d49cd0d0376de9111', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

delete from ice_vaccine_component_rel where component_rel_id in ('9ea8c704d90f9816a9e050ee707a77c6', 'd1d5e924d32f02c42b3b94ada492c8a6');
delete from ice_vaccine_version_rel where version_rel_id in ('2fff4427ddf3fbb5a2f6415d8d8a64cb', 'b103ca573400422b3494d74196d7edb9');
delete from ice_vaccine where vaccine_id in ('42735ade82a15fce816493c1af6ba53c', '18264580bbb99333ae571cdd385ec7a3');
delete from cds_list_item where item_id in ('1608426288a0c2dc1f5e5ee6434d3994', '1338426288a0c2dc1f5e5ff6434d3994');
delete from opencds_concept_rel where relationship_id in ('ba58caa3168f46fb3b58cc58ebea77ac', '96652bf28d851564d01aaf276925851d');
delete from opencds_concept where code_id in ('7ec94296c6596cd1ab70b2f9c7978fa2', 'b78f2c2eeb593f96606e9ebadc78a734');
delete from ice_vaccine_disease_rel where disease_rel_id in ('1c940e4b21a60ec4374f53fbc79f53a6', '346db8e3dd2b267d21ee8daa413e7448');
delete from ice_vaccine_component where component_id in ('81eee6e4410e129aafac720db52c36bb', '779a445ddb315e4d49cd0d0376de9111');
delete from cds_list_item where item_id in ('1aee0f4a584c02f983e1dbebd5a19110', '1823efbb4a37b178904ea33641087253');
delete from cds_code where code_id in ('4da29ee3ae9801e2dcb330c215c35a21', '757fa0ebb10e2dd5ce72bd9dd2267276');