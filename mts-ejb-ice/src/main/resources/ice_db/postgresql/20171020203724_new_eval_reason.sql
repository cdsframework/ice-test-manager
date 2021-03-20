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

-- // new eval reason
-- Migration SQL that makes the change goes here.

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('8cd3b008aad916f8127675bca8d7dc5a', '26bb57f1188231aef8d95c48c9599225', 'MISSING_ANTIGEN', 'Missing Antigen', current_timestamp, 'amoniz', current_timestamp, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('4f99a1fd18b962791a11ac4255ae0011', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '8cd3b008aad916f8127675bca8d7dc5a', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('b7928b25fea1c8d18a1e98d18aefb2c1', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '8cd3b008aad916f8127675bca8d7dc5a', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('742a906355f46cf62971824133b24e6d', '26bb57f1188231aef8d95c48c9599225', 'VACCINE_NOT_COUNTED_BASED_ON_MOST_RECENT_VACCINE_GIVEN', 'Vaccine Not Counted Based on Most Recent Vaccine Given', current_timestamp, 'amoniz', current_timestamp, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('151af60fbfd005695cfbe1099208b374', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '742a906355f46cf62971824133b24e6d', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('fdaa8bb85cc071e01e87d8a7b7780370', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '742a906355f46cf62971824133b24e6d', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('9fc941e0f1a9143dd79c7407eb45aaf5', '26bb57f1188231aef8d95c48c9599225', 'VACCINE_NOT_SUPPORTED', 'Vaccine Not Supported', current_timestamp, 'amoniz', current_timestamp, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('aeff58e2f6f62cc424c290213e211a51', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '9fc941e0f1a9143dd79c7407eb45aaf5', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('47630a56d21400da619d4dd24783ff0a', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '9fc941e0f1a9143dd79c7407eb45aaf5', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

update cds_code set code = 'VACCINE_NOT_LICENSED_FOR_MALES', display_name = 'Vaccine Not Licensed for Males' where code = 'NOT_LICENSED_FOR_MALES';

-- //@UNDO
-- SQL to undo the change goes here.

delete from cds_list_item where code_id = '9fc941e0f1a9143dd79c7407eb45aaf5';
delete from cds_code where code_id = '9fc941e0f1a9143dd79c7407eb45aaf5';

delete from cds_list_item where code_id = '742a906355f46cf62971824133b24e6d';
delete from cds_code where code_id = '742a906355f46cf62971824133b24e6d';

delete from cds_list_item where code_id = 'f77b1b98bab03121c3da6d49a1204b35';
delete from cds_code where code_id = 'f77b1b98bab03121c3da6d49a1204b35';

update cds_code set code = 'NOT_LICENSED_FOR_MALES', display_name = 'Not Licensed for Males' where code = 'VACCINE_NOT_LICENSED_FOR_MALES';
