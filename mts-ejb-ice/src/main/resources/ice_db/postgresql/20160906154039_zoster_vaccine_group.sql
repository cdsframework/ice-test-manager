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

-- // zoster vaccine group
-- Migration SQL that makes the change goes here.


-- add Zoster disease and immunity concept
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('38aca45b177c6c4635d1890da761ca5b', '8a74891e484ecf4a221d15d973427416', 'ZOSTER', 'Herpes Zoster', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('e368140abbfc519aa746c6c78c3c368c', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '38aca45b177c6c4635d1890da761ca5b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('5329c48164fcba91f3c30310616d07fb', 'ICE00559', 'Disease Immunity Focus (Zoster)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('e683b2e759636cee7193f6bd908b0bc7', '5329c48164fcba91f3c30310616d07fb', '38aca45b177c6c4635d1890da761ca5b', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', '8a74891e484ecf4a221d15d973427416', NULL, 'CODE');

-- update the disease list to reference the generic disease code system instead of icd9
UPDATE cds_list  set code_system_id = '8a74891e484ecf4a221d15d973427416' where list_id = '2b9926a4d682759612a1bc99645479a3';
DELETE from cds_list_item where list_id = '2b9926a4d682759612a1bc99645479a3';
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('36db26bfea17ce168bdc711053900ab3', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '5b08a0ba2e2bdd259f137a57b6cc83d4', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('3caf6fae22e52353c86f3a04d9a14caa', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'ed1bf058913d4806d4c7aa8fc774a87b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('3e766bb4abf2101e51cc317b1f4be32a', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '49b289efc9a44e8cee2a7d1ff9f6d319', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('4fbadee6781569be7fd3a16d55b280c1', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '850090943836db29602c1e1fb7df5d2f', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('59c3c2dddbf5f6e955cc7b8df286380b', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'a5b18dc53cdb88393d7b7ab475653254', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('626366b9d8885d8b1ce98d791cfebc8d', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '46a524937c6b47beb74e67977ab44aef', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('638b5242a4d7d3dce6f2d361ceff2dcf', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'd21031b91c51d50177dfb897a59cb3e8', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('6ed6f2a02b01d1df2eeee7fae6d6356e', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'd09f820290275497949a2dc4b63e2cb9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('7b70d27c6f4b7dac293364a69f90b6a1', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'e5d0ee72c6eb914b702d283bde516b9e', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('8dacb6fb159f230023e2d853666cf73a', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '8e39694d8fe12457edcda500f74e902d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('b4858cced4865074559309cb8a40bbd4', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '8750f40889cb260882e190ea585228d6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('d95491a468e5cf217c53d3ab7a95de3a', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'e10591982a5a9530c49e4989ce749af6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('e4d79b78966a27ade0930a6529592ef5', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '336d08336003fc6656e1820666989895', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('ea1e1aa9608193284fab31064305588f', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'e4e29339f120d6e5a9a060d9446d2a9d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('ee07b8ddd29d044fb83ba7c6225946c1', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '7e932a8c23ad4eec37aebf9ae1e2e101', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('f477272aeac22c46ed9951599ab7de9c', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '2fbe722c84f17892db0c660d40f2770c', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('fd24704861e8af518b3b57c68a882c5e', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '3f5b8c2360690eefeb33888f1834c75b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('8378df35520e8b31f7ffd35e1cba6498', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '5329c48164fcba91f3c30310616d07fb', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- add zoster disease
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('95176a1290628ba537fad2184a8a734f', '38aca45b177c6c4635d1890da761ca5b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('ecad59accbfb95d9a6b3563b5c833f4a', '5329c48164fcba91f3c30310616d07fb', 'f89c7a73aff266756922d437e0ad8044', '95176a1290628ba537fad2184a8a734f', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- add the vaccine group
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('f6c36442ee3bd624929ca18ad2124a57', 'e6b7f6483524ff91e2feae09fe4b1619', '620', 'Zoster', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('7fdd73ea76bec3fbc366ae32b7466732', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'f6c36442ee3bd624929ca18ad2124a57', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('800ea854eff6c2b21a954b9e1287fa98', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'f6c36442ee3bd624929ca18ad2124a57', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('94b95d76a37dfca26acff8ce9f5fe161', 'ICE620', 'Immunization Evaluation (Zoster Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('8ea064df2e29ca3d9bd6e997ef7ce92a', '94b95d76a37dfca26acff8ce9f5fe161', 'f6c36442ee3bd624929ca18ad2124a57', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'e6b7f6483524ff91e2feae09fe4b1619', NULL, 'CODE');
INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('2cb176e8867fb7141b7a7f34bddf2472', 'f6c36442ee3bd624929ca18ad2124a57', '94b95d76a37dfca26acff8ce9f5fe161', 0, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('47740d981bab0f963e1368b96af95628', '2cb176e8867fb7141b7a7f34bddf2472', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('f1eb1458d12aff96a92eb4a759310101', '95176a1290628ba537fad2184a8a734f', '2cb176e8867fb7141b7a7f34bddf2472', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- icd 9 code
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('ce7891a4accc7280ef32974b33af9fdf', '734d727366047ef1b6292d310d36b657', '053.9', 'Herpes Zoster', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

delete from ice_vaccine_group_disease_rel where disease_rel_id = 'f1eb1458d12aff96a92eb4a759310101';
delete from ice_vaccine_group_version_rel where version_rel_id = '47740d981bab0f963e1368b96af95628';
delete from ice_vaccine_group where group_id = '2cb176e8867fb7141b7a7f34bddf2472';
delete from opencds_concept_rel where relationship_id = '8ea064df2e29ca3d9bd6e997ef7ce92a';
delete from opencds_concept where code_id = '94b95d76a37dfca26acff8ce9f5fe161';
delete from cds_list_item where code_id = 'f6c36442ee3bd624929ca18ad2124a57';
delete from cds_code where code_id = 'f6c36442ee3bd624929ca18ad2124a57';
delete from ice_disease_vers_concept_rel where rel_id = 'ecad59accbfb95d9a6b3563b5c833f4a';
delete from ice_disease where disease_id = '95176a1290628ba537fad2184a8a734f';
delete from cds_list_item where item_id = '8378df35520e8b31f7ffd35e1cba6498';
delete from opencds_concept_rel where relationship_id = 'e683b2e759636cee7193f6bd908b0bc7';
delete from opencds_concept where code_id = '5329c48164fcba91f3c30310616d07fb';
delete from cds_list_item where code_id = '38aca45b177c6c4635d1890da761ca5b';
delete from cds_code where code_id = '38aca45b177c6c4635d1890da761ca5b';