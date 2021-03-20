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

-- // new ice eval reasons for pnuemo
-- Migration SQL that makes the change goes here.

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('a6a845b0cb12e5f77b5c2198f3dde0a1', '26bb57f1188231aef8d95c48c9599225', 'VACCINE_NOT_ALLOWED_FOR_THIS_DOSE', 'Vaccine Not Allowed for this Dose', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'amoniz');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('aaf5d011cabb1aa74dfd6b341541789e', '26bb57f1188231aef8d95c48c9599225', 'VACCINE_NOT_ALLOWED', 'Vaccine Not Allowed', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'amoniz');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('befc3cc9c18fb6d7198188971f0bd9a9', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'aaf5d011cabb1aa74dfd6b341541789e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('64d0e2f5fdc76fe792e5666926dd7a78', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'aaf5d011cabb1aa74dfd6b341541789e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');

-- //@UNDO
-- SQL to undo the change goes here.

delete from cds_list_item where item_id = '64d0e2f5fdc76fe792e5666926dd7a78';
delete from cds_list_item where item_id = 'befc3cc9c18fb6d7198188971f0bd9a9';
delete from cds_code where code_id = 'a6a845b0cb12e5f77b5c2198f3dde0a1';
delete from cds_code where code_id = 'aaf5d011cabb1aa74dfd6b341541789e';