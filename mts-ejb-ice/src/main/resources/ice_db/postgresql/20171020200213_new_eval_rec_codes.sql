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

-- // new eval rec codes
-- Migration SQL that makes the change goes here.

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('6ffd6284d7960710a8cdf833a4a7ea15', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'BELOW_MINIMUM_AGE_HIGH_RISK_SERIES', 'Below Minimum Age High Risk Series', current_timestamp, 'amoniz', current_timestamp, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('6c1aba411d4d7ccab7c4a183975bb9a8', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '6ffd6284d7960710a8cdf833a4a7ea15', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('be58a0872c22f27e8605c1f6a5352359', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '6ffd6284d7960710a8cdf833a4a7ea15', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('cbfdb2d22d6b461121a3463335c74bd0', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'CLINICAL_PATIENT_DISCRETION', 'Clinical Patient Discretion', current_timestamp, 'amoniz', current_timestamp, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('23fab00c9c8a58c457d56cd734155ec5', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'cbfdb2d22d6b461121a3463335c74bd0', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('20da8f23dddb94fbb57b3980c55a393a', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'cbfdb2d22d6b461121a3463335c74bd0', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('f77b1b98bab03121c3da6d49a1204b35', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'OTHER_VACCINE_PRODUCT_POSSIBLE', 'Other Vaccine Product Possible', current_timestamp, 'amoniz', current_timestamp, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('e94fa2b83e9fe7a06f1a1071e0bbcb4a', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'f77b1b98bab03121c3da6d49a1204b35', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('4e46c02d385121781ba92b3c27f5ffc4', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'f77b1b98bab03121c3da6d49a1204b35', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

-- //@UNDO
-- SQL to undo the change goes here.

delete from cds_list_item where code_id = 'f77b1b98bab03121c3da6d49a1204b35';
delete from cds_code where code_id = 'f77b1b98bab03121c3da6d49a1204b35';

delete from cds_list_item where code_id = 'cbfdb2d22d6b461121a3463335c74bd0';
delete from cds_code where code_id = 'cbfdb2d22d6b461121a3463335c74bd0';

delete from cds_list_item where code_id = '6ffd6284d7960710a8cdf833a4a7ea15';
delete from cds_code where code_id = '6ffd6284d7960710a8cdf833a4a7ea15';
