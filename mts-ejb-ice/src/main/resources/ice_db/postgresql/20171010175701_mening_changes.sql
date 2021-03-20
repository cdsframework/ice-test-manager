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

-- // mening changes
-- Migration SQL that makes the change goes here.

update cds_code set display_name = 'Meningococcal ACWY' where code = '830' and display_name = 'Meningococcal';
update opencds_concept set display_name = 'Immunization Evaluation (Meningococcal ACWY Vaccine Group)' where code = 'ICE278';

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('fec3b9614dabde493db41defb5500770', 'e6b7f6483524ff91e2feae09fe4b1619', '835', 'Meningococcal B', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('8b7c3e32e89e8dbd5980ac816e59d5fc', 'ICE279', 'Immunization Evaluation (Meningococcal B Vaccine Group)', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type)
    VALUES ('612fcd4db1c0ea5b18134cb9f2d60986', '8b7c3e32e89e8dbd5980ac816e59d5fc', 'fec3b9614dabde493db41defb5500770', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e6b7f6483524ff91e2feae09fe4b1619', NULL, 'CODE');

INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('13579d34317e9d142f896b2642a2e54b', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'fec3b9614dabde493db41defb5500770', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('a74377a028acb781783c934c8ec5f308', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'fec3b9614dabde493db41defb5500770', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'admin',current_timestamp, 'admin');

INSERT INTO ice_vaccine_group (group_id, vaccine_group_id, primary_concept, priority, last_mod_id, last_mod_datetime, create_id, create_datetime)
    VALUES ('91b85fcb5082f4e2de03f204415e611e', 'fec3b9614dabde493db41defb5500770', '8b7c3e32e89e8dbd5980ac816e59d5fc', 0, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime)
    VALUES ('1812a02f76ad29c43983d5ea4be0da1d', 'b88473bbb2f9f8f524b6cb0c587b3a1a', '91b85fcb5082f4e2de03f204415e611e', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime)
    VALUES ('20a7c425155aa1074c8658a4aa4d0f31', '91b85fcb5082f4e2de03f204415e611e', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);

INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('9f974676661e008dccb3ff5e4b698083', 'e0bc41f880dc19d217656508b6cf9908', '186', 'Influenza, injectable, MDCK, quadrivalent', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('c180deed39f000ac7e34080c987fa964', 'e0bc41f880dc19d217656508b6cf9908', '185', 'Influenza, recombinant, quadrivalent,injectable, preservative free', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('5e4a31b994cadb063733295b6f5de716', 'e0bc41f880dc19d217656508b6cf9908', '182', 'OPV, Unspecified (NOS)', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('d893be9238fb2518914dfc11a807c623', 'e0bc41f880dc19d217656508b6cf9908', '179', 'OPV, monovalent, unspecified (NOS)', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('35540349ae4b7e58a19069ff6a4ad0e0', 'e0bc41f880dc19d217656508b6cf9908', '178', 'OPV bivalent', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('ce821104896277fa845aca2741605663', 'e0bc41f880dc19d217656508b6cf9908', '170', 'DTaP-IPV-Hib', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('92f5b3f09f0a9f33253db081d9645750', 'e0bc41f880dc19d217656508b6cf9908', '163', 'Meningococcal B4C, OMV (Bexsero)', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('6501a8c2152d816c1c1b7f2cfdabf19f', 'e0bc41f880dc19d217656508b6cf9908', '162', 'Meningococcal B FHbp, recombinant (Trumenba)', current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('32b948d5a785045c4b469184a8ac769a', 'e0bc41f880dc19d217656508b6cf9908', '164', 'Meningococcal B, NOS', current_timestamp, 'admin', current_timestamp, 'admin');

INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('e9fe315e853e0c07fb8ff0371e42cedc', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9f974676661e008dccb3ff5e4b698083', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('83a6b682e0eec46362063ae6b894cf1a', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'c180deed39f000ac7e34080c987fa964', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('4a55964c463853e7e15870f352216d05', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '5e4a31b994cadb063733295b6f5de716', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('b111fccf8b1ac94810cbf1bf1fb89854', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'd893be9238fb2518914dfc11a807c623', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('f28521efae22512832aa638ca5fccffc', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '35540349ae4b7e58a19069ff6a4ad0e0', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('9e2f1878c8d4e93e71a19a0f4832f1ce', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'ce821104896277fa845aca2741605663', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('815777a2c9702402bf88705b4f977985', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '92f5b3f09f0a9f33253db081d9645750', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('8c61a87049ad00149513008d8e411040', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6501a8c2152d816c1c1b7f2cfdabf19f', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('b4d2036ce488eda0d75b04a9b2cdd8da', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '32b948d5a785045c4b469184a8ac769a', NULL, NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('8aa24b23305e6a51c2ad868761395a62', 'ICE186', 'Influenza, injectable, MDCK, quadrivalent', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('e9f15512b63b6f801cff766c7379128b', 'ICE185', 'Influenza, recombinant, quadrivalent,injectable, preservative free', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('4689494b299568f53af8ca8826d9786d', 'ICE182', 'OPV, Unspecified (NOS)', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('9dd8c84ab42dc984a5c6ec272476def8', 'ICE179', 'OPV, monovalent, unspecified (NOS)', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('c1203635705795b22e028dc99267e334', 'ICE178', 'OPV bivalent', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('356fcfb83472f4c2ae64daa45d2c6539', 'ICE170', 'DTaP-IPV-Hib', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('94b07215d674c4405e71418b95cd7c3a', 'ICE163', 'Meningococcal B4C, OMV (Bexsero)', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('5d4235fa78db088b132f413fd6db56d8', 'ICE162', 'Meningococcal B FHbp, recombinant (Trumenba)', NULL, current_timestamp, 'admin', current_timestamp, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('67f10a38d6398f8b8d7630efedc7d11a', 'ICE164', 'Meningococcal B, NOS', NULL, current_timestamp, 'admin', current_timestamp, 'admin');

INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('302e40eb8c04c6d3be739607ceeb2293', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '8aa24b23305e6a51c2ad868761395a62', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('199dec931eae049fd0847e03e6f40274', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'e9f15512b63b6f801cff766c7379128b', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('174d99d2bfd8734057dcf520b4ae7cc4', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '4689494b299568f53af8ca8826d9786d', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('15dd678b71205be3ed22917bf103aa20', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '9dd8c84ab42dc984a5c6ec272476def8', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('bf8df87ba10a71fea11aa7d71915fc8f', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'c1203635705795b22e028dc99267e334', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('4067a11075d0d18a16d6bcc41682c485', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '356fcfb83472f4c2ae64daa45d2c6539', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('af2395cec34457321c568b57a0753c8d', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '94b07215d674c4405e71418b95cd7c3a', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('e7578384996c4751433f0c24b1d7ca22', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '5d4235fa78db088b132f413fd6db56d8', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
	VALUES ('512d879a366e184a2142632b7229237e', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '67f10a38d6398f8b8d7630efedc7d11a', NULL, NULL, NULL, NULL, current_timestamp, 'INTERNAL', current_timestamp, 'INTERNAL');

INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('363c7df49878a6465e30001921d57b2d', '8aa24b23305e6a51c2ad868761395a62', '9f974676661e008dccb3ff5e4b698083', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('642118391903e46ef21c34a72bf1959f', 'e9f15512b63b6f801cff766c7379128b', 'c180deed39f000ac7e34080c987fa964', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('95d3a2f09c2b080e9b9c5e8423fc5763', '4689494b299568f53af8ca8826d9786d', '5e4a31b994cadb063733295b6f5de716', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('ce5a05c73184fca9e7a12c47ff574a47', '9dd8c84ab42dc984a5c6ec272476def8', 'd893be9238fb2518914dfc11a807c623', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('c8216d9ce1cc05321ae11d945a0768d2', 'c1203635705795b22e028dc99267e334', '35540349ae4b7e58a19069ff6a4ad0e0', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('10109d7958acf6df2f8745eec3f0913f', '356fcfb83472f4c2ae64daa45d2c6539', 'ce821104896277fa845aca2741605663', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('77aaa94daf4d786f7424ca71352f6e6e', '94b07215d674c4405e71418b95cd7c3a', '92f5b3f09f0a9f33253db081d9645750', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('69951d55a9d9c8a4592d6edc3ae67818', '5d4235fa78db088b132f413fd6db56d8', '6501a8c2152d816c1c1b7f2cfdabf19f', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code_system_id, value_set_id, mapping_type) 
	VALUES ('c5bb1fd589ea709f9ccf84f937c0da21', '67f10a38d6398f8b8d7630efedc7d11a', '32b948d5a785045c4b469184a8ac769a', 'cdb46802ca1e8385a2175f566a47b20d', NULL, current_timestamp, 'admin', current_timestamp, 'admin', 'e0bc41f880dc19d217656508b6cf9908', NULL, 'CODE');

INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('4257f35d1a044c7381689eccc98604bd', false, 'admin', current_timestamp, 'admin', current_timestamp, '6501a8c2152d816c1c1b7f2cfdabf19f');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('ac71d25258590eb322b87579d411d5af', false, 'admin', current_timestamp, 'admin', current_timestamp, '92f5b3f09f0a9f33253db081d9645750');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('d55cc52d52fd7f866dade9b8f3ed4569', false, 'admin', current_timestamp, 'admin', current_timestamp, '35540349ae4b7e58a19069ff6a4ad0e0');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('48ff259f123199c8ceaf8f24c82f509f', false, 'admin', current_timestamp, 'admin', current_timestamp, 'd893be9238fb2518914dfc11a807c623');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('57be3e0bd05f4ef4a264a993f4366869', false, 'admin', current_timestamp, 'admin', current_timestamp, '5e4a31b994cadb063733295b6f5de716');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('f6eeaeb7cb8607cc570c408ad1268df4', false, 'admin', current_timestamp, 'admin', current_timestamp, 'c180deed39f000ac7e34080c987fa964');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('e0d2b8ec408cc866260c9dc1c444e4ce', false, 'admin', current_timestamp, 'admin', current_timestamp, '9f974676661e008dccb3ff5e4b698083');
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime, code_id) 
	VALUES ('c2301ad749401f8bafcf6d9c5371c36b', false, 'admin', current_timestamp, 'admin', current_timestamp, '32b948d5a785045c4b469184a8ac769a');

INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('f61721f9a9c7905fa53031379c027d86', 'b88473bbb2f9f8f524b6cb0c587b3a1a', '4257f35d1a044c7381689eccc98604bd', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('c18d4c37e1e98c7f4f4671bc43a75104', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'ac71d25258590eb322b87579d411d5af', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('2fe432db84ffab89a1926673b7b14b14', '83aeaca8fceb6f601c8ac5f19cc95fa0', 'd55cc52d52fd7f866dade9b8f3ed4569', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('bb57bb855f7731ba779288dfa7e2beaa', '83aeaca8fceb6f601c8ac5f19cc95fa0', '48ff259f123199c8ceaf8f24c82f509f', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('8ad92360ddaebca77d022f1106659d2b', '83aeaca8fceb6f601c8ac5f19cc95fa0', '57be3e0bd05f4ef4a264a993f4366869', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('6ce9491bd4c4890b84b120d53240ffcf', '4f27cec9fd486d29935cc05b3ad185b4', 'f6eeaeb7cb8607cc570c408ad1268df4', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('f1629d34c18e70ea0ff39b8e2781446f', '4f27cec9fd486d29935cc05b3ad185b4', 'e0d2b8ec408cc866260c9dc1c444e4ce', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('7371c63293a258b5d317c752336edca4', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'c2301ad749401f8bafcf6d9c5371c36b', 'admin', current_timestamp, 'admin', current_timestamp);

INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('a724b3f371679462713eccc7c81ab583', '6501a8c2152d816c1c1b7f2cfdabf19f', 'Meningococcal B FHbp, recombinant (Trumenba)', '5d4235fa78db088b132f413fd6db56d8', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('b01217dc549e955a15dda66527513eb6', '92f5b3f09f0a9f33253db081d9645750', 'Meningococcal B4C, OMV (Bexsero)', '94b07215d674c4405e71418b95cd7c3a', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('abcb43b771dd28d2da934fab6cb4ae43', 'ce821104896277fa845aca2741605663', 'DTaP-IPV-Hib', '356fcfb83472f4c2ae64daa45d2c6539', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('61418c28ca4c3b005ff90096f9927829', '35540349ae4b7e58a19069ff6a4ad0e0', 'OPV bivalent', 'c1203635705795b22e028dc99267e334', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('11a43d95b29d5754ca91e70dadb1671b', 'd893be9238fb2518914dfc11a807c623', 'OPV, monovalent, unspecified (NOS)', '9dd8c84ab42dc984a5c6ec272476def8', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('8ad7e56b65a0f75f82e5606f379b0262', '5e4a31b994cadb063733295b6f5de716', 'OPV, Unspecified (NOS)', '4689494b299568f53af8ca8826d9786d', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('db8de3a548567fab4724afa39423ae65', 'c180deed39f000ac7e34080c987fa964', 'Influenza, recombinant, quadrivalent,injectable, preservative free', 'e9f15512b63b6f801cff766c7379128b', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('ef1cefda2c28e569f606c17454fb6d83', '9f974676661e008dccb3ff5e4b698083', 'Influenza, injectable, MDCK, quadrivalent', '8aa24b23305e6a51c2ad868761395a62', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine (vaccine_id, vaccine_code_id, vaccine_name, primary_concept, licensed_min_age_for_use, licensed_max_age_for_use, manufacturer_code_id, trade_name, valid_min_age_for_use, valid_max_age_for_use, active, min_date_for_use, max_date_for_use, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('ed779c23a58be2de26c42dd343a87a6e', '32b948d5a785045c4b469184a8ac769a', 'Meningococcal B, NOS', '67f10a38d6398f8b8d7630efedc7d11a', NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'admin', current_timestamp, 'admin', current_timestamp);

INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('abb92abb73484d9de82b75be668b3929', 'a724b3f371679462713eccc7c81ab583', '4257f35d1a044c7381689eccc98604bd', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('2bb7dc0b762a9f987b7127e370f1dbfa', 'b01217dc549e955a15dda66527513eb6', 'ac71d25258590eb322b87579d411d5af', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('430c5eb11f3d0e1b902005f629d31b14', 'abcb43b771dd28d2da934fab6cb4ae43', '5178e23c337a26a1ce17a4a73f8bf97b', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('cc2a45bbe4d04f11a5786152cbf9b90b', 'abcb43b771dd28d2da934fab6cb4ae43', '26b5eadb78914bfa390dc8b1d7d7a37c', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('54b931afc08b112ebeda99b31c37290d', 'abcb43b771dd28d2da934fab6cb4ae43', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('ecb5ca26695d64775d68e976e090057e', '61418c28ca4c3b005ff90096f9927829', 'd55cc52d52fd7f866dade9b8f3ed4569', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('ecd34fad894ed0267ab59a8b3ca67302', '11a43d95b29d5754ca91e70dadb1671b', '48ff259f123199c8ceaf8f24c82f509f', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('8992b2721c4ba3cac136bd762a34c9bd', '8ad7e56b65a0f75f82e5606f379b0262', '57be3e0bd05f4ef4a264a993f4366869', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('12eddd7eccaae8a745b481ae8723f6ad', 'db8de3a548567fab4724afa39423ae65', 'f6eeaeb7cb8607cc570c408ad1268df4', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('bcdb795cf0c022c23f3dccf235e338dd', 'ef1cefda2c28e569f606c17454fb6d83', 'e0d2b8ec408cc866260c9dc1c444e4ce', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('de2c982918f87e4eaf846e10d9a04d4a', 'ed779c23a58be2de26c42dd343a87a6e', 'c2301ad749401f8bafcf6d9c5371c36b', 'admin', current_timestamp, 'admin', current_timestamp);

INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('f31d4394cf3bfa3ad0c84abdf49a1315', 'a724b3f371679462713eccc7c81ab583', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('b086fd21e3dec27ae3b05d76c8d615fd', 'b01217dc549e955a15dda66527513eb6', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('3fa6048c63dbc2d24457d72ca8e0f19f', 'abcb43b771dd28d2da934fab6cb4ae43', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('4d7a013cf2efb74ca0af733ab0b86e4f', '61418c28ca4c3b005ff90096f9927829', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('2c614ce8e0cf9714c80a986ad28391e9', '11a43d95b29d5754ca91e70dadb1671b', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('188845e45a5c444b5b0d5e179ce25c17', '8ad7e56b65a0f75f82e5606f379b0262', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('589690b01c078e064bf495144721f313', 'db8de3a548567fab4724afa39423ae65', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('a769871e5efa3ddee2493798c1083a7a', 'ef1cefda2c28e569f606c17454fb6d83', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
	VALUES ('d2661aa560eb8f7d9d4b97fbfb0ea8da', 'ed779c23a58be2de26c42dd343a87a6e', 'f89c7a73aff266756922d437e0ad8044', 'admin', current_timestamp, 'admin', current_timestamp);

-- //@UNDO
-- SQL to undo the change goes here.

delete from ice_vaccine_version_rel where vaccine_id = 'a724b3f371679462713eccc7c81ab583';
delete from ice_vaccine_version_rel where vaccine_id = 'b01217dc549e955a15dda66527513eb6';
delete from ice_vaccine_version_rel where vaccine_id = 'abcb43b771dd28d2da934fab6cb4ae43';
delete from ice_vaccine_version_rel where vaccine_id = '61418c28ca4c3b005ff90096f9927829';
delete from ice_vaccine_version_rel where vaccine_id = '11a43d95b29d5754ca91e70dadb1671b';
delete from ice_vaccine_version_rel where vaccine_id = '8ad7e56b65a0f75f82e5606f379b0262';
delete from ice_vaccine_version_rel where vaccine_id = 'db8de3a548567fab4724afa39423ae65';
delete from ice_vaccine_version_rel where vaccine_id = 'ef1cefda2c28e569f606c17454fb6d83';
delete from ice_vaccine_version_rel where vaccine_id = 'ed779c23a58be2de26c42dd343a87a6e';

delete from ice_vaccine_component_rel where vaccine_id = 'a724b3f371679462713eccc7c81ab583';
delete from ice_vaccine_component_rel where vaccine_id = 'b01217dc549e955a15dda66527513eb6';
delete from ice_vaccine_component_rel where vaccine_id = 'abcb43b771dd28d2da934fab6cb4ae43';
delete from ice_vaccine_component_rel where vaccine_id = '61418c28ca4c3b005ff90096f9927829';
delete from ice_vaccine_component_rel where vaccine_id = '11a43d95b29d5754ca91e70dadb1671b';
delete from ice_vaccine_component_rel where vaccine_id = '8ad7e56b65a0f75f82e5606f379b0262';
delete from ice_vaccine_component_rel where vaccine_id = 'db8de3a548567fab4724afa39423ae65';
delete from ice_vaccine_component_rel where vaccine_id = 'ef1cefda2c28e569f606c17454fb6d83';
delete from ice_vaccine_component_rel where vaccine_id = 'ed779c23a58be2de26c42dd343a87a6e';

delete from ice_vaccine where vaccine_id = 'a724b3f371679462713eccc7c81ab583';
delete from ice_vaccine where vaccine_id = 'b01217dc549e955a15dda66527513eb6';
delete from ice_vaccine where vaccine_id = 'abcb43b771dd28d2da934fab6cb4ae43';
delete from ice_vaccine where vaccine_id = '61418c28ca4c3b005ff90096f9927829';
delete from ice_vaccine where vaccine_id = '11a43d95b29d5754ca91e70dadb1671b';
delete from ice_vaccine where vaccine_id = '8ad7e56b65a0f75f82e5606f379b0262';
delete from ice_vaccine where vaccine_id = 'db8de3a548567fab4724afa39423ae65';
delete from ice_vaccine where vaccine_id = 'ef1cefda2c28e569f606c17454fb6d83';
delete from ice_vaccine where vaccine_id = 'ed779c23a58be2de26c42dd343a87a6e';

delete from ice_vaccine_disease_rel where component_id = '4257f35d1a044c7381689eccc98604bd';
delete from ice_vaccine_disease_rel where component_id = 'ac71d25258590eb322b87579d411d5af';
delete from ice_vaccine_disease_rel where component_id = 'd55cc52d52fd7f866dade9b8f3ed4569';
delete from ice_vaccine_disease_rel where component_id = '48ff259f123199c8ceaf8f24c82f509f';
delete from ice_vaccine_disease_rel where component_id = '57be3e0bd05f4ef4a264a993f4366869';
delete from ice_vaccine_disease_rel where component_id = 'f6eeaeb7cb8607cc570c408ad1268df4';
delete from ice_vaccine_disease_rel where component_id = 'e0d2b8ec408cc866260c9dc1c444e4ce';
delete from ice_vaccine_disease_rel where component_id = 'c2301ad749401f8bafcf6d9c5371c36b';

delete from ice_vaccine_component where component_id = '4257f35d1a044c7381689eccc98604bd';
delete from ice_vaccine_component where component_id = 'ac71d25258590eb322b87579d411d5af';
delete from ice_vaccine_component where component_id = 'd55cc52d52fd7f866dade9b8f3ed4569';
delete from ice_vaccine_component where component_id = '48ff259f123199c8ceaf8f24c82f509f';
delete from ice_vaccine_component where component_id = '57be3e0bd05f4ef4a264a993f4366869';
delete from ice_vaccine_component where component_id = 'f6eeaeb7cb8607cc570c408ad1268df4';
delete from ice_vaccine_component where component_id = 'e0d2b8ec408cc866260c9dc1c444e4ce';
delete from ice_vaccine_component where component_id = 'c2301ad749401f8bafcf6d9c5371c36b';

delete from ice_vaccine_group_version_rel where version_rel_id = '20a7c425155aa1074c8658a4aa4d0f31';
delete from ice_vaccine_group_disease_rel where disease_rel_id = '1812a02f76ad29c43983d5ea4be0da1d';
delete from ice_vaccine_group where group_id = '91b85fcb5082f4e2de03f204415e611e';

update opencds_concept set display_name = 'Immunization Evaluation (Meningococcal Vaccine Group)' where code = 'ICE278';
update cds_code set display_name = 'Meningococcal' where code = '830' and display_name = 'Meningococcal ACWY';

delete from cds_list_item where opencds_code_id = '8b7c3e32e89e8dbd5980ac816e59d5fc';
delete from cds_list_item where opencds_code_id = '8aa24b23305e6a51c2ad868761395a62';
delete from cds_list_item where opencds_code_id = 'e9f15512b63b6f801cff766c7379128b';
delete from cds_list_item where opencds_code_id = '4689494b299568f53af8ca8826d9786d';
delete from cds_list_item where opencds_code_id = '9dd8c84ab42dc984a5c6ec272476def8';
delete from cds_list_item where opencds_code_id = 'c1203635705795b22e028dc99267e334';
delete from cds_list_item where opencds_code_id = '356fcfb83472f4c2ae64daa45d2c6539';
delete from cds_list_item where opencds_code_id = '94b07215d674c4405e71418b95cd7c3a';
delete from cds_list_item where opencds_code_id = '5d4235fa78db088b132f413fd6db56d8';
delete from cds_list_item where opencds_code_id = '67f10a38d6398f8b8d7630efedc7d11a';

delete from cds_list_item where code_id = 'fec3b9614dabde493db41defb5500770';
delete from cds_list_item where code_id = '9f974676661e008dccb3ff5e4b698083';
delete from cds_list_item where code_id = 'c180deed39f000ac7e34080c987fa964';
delete from cds_list_item where code_id = '5e4a31b994cadb063733295b6f5de716';
delete from cds_list_item where code_id = 'd893be9238fb2518914dfc11a807c623';
delete from cds_list_item where code_id = '35540349ae4b7e58a19069ff6a4ad0e0';
delete from cds_list_item where code_id = 'ce821104896277fa845aca2741605663';
delete from cds_list_item where code_id = '92f5b3f09f0a9f33253db081d9645750';
delete from cds_list_item where code_id = '6501a8c2152d816c1c1b7f2cfdabf19f';
delete from cds_list_item where code_id = '32b948d5a785045c4b469184a8ac769a';

delete from opencds_concept_rel where concept_code_id = '8b7c3e32e89e8dbd5980ac816e59d5fc';
delete from opencds_concept_rel where concept_code_id = '8aa24b23305e6a51c2ad868761395a62';
delete from opencds_concept_rel where concept_code_id = 'e9f15512b63b6f801cff766c7379128b';
delete from opencds_concept_rel where concept_code_id = '4689494b299568f53af8ca8826d9786d';
delete from opencds_concept_rel where concept_code_id = '9dd8c84ab42dc984a5c6ec272476def8';
delete from opencds_concept_rel where concept_code_id = 'c1203635705795b22e028dc99267e334';
delete from opencds_concept_rel where concept_code_id = '356fcfb83472f4c2ae64daa45d2c6539';
delete from opencds_concept_rel where concept_code_id = '94b07215d674c4405e71418b95cd7c3a';
delete from opencds_concept_rel where concept_code_id = '5d4235fa78db088b132f413fd6db56d8';
delete from opencds_concept_rel where concept_code_id = '67f10a38d6398f8b8d7630efedc7d11a';

delete from opencds_concept where code_id = '8b7c3e32e89e8dbd5980ac816e59d5fc';
delete from opencds_concept where code_id = '8aa24b23305e6a51c2ad868761395a62';
delete from opencds_concept where code_id = 'e9f15512b63b6f801cff766c7379128b';
delete from opencds_concept where code_id = '4689494b299568f53af8ca8826d9786d';
delete from opencds_concept where code_id = '9dd8c84ab42dc984a5c6ec272476def8';
delete from opencds_concept where code_id = 'c1203635705795b22e028dc99267e334';
delete from opencds_concept where code_id = '356fcfb83472f4c2ae64daa45d2c6539';
delete from opencds_concept where code_id = '94b07215d674c4405e71418b95cd7c3a';
delete from opencds_concept where code_id = '5d4235fa78db088b132f413fd6db56d8';
delete from opencds_concept where code_id = '67f10a38d6398f8b8d7630efedc7d11a';

delete from cds_code where code_id = 'fec3b9614dabde493db41defb5500770';
delete from cds_code where code_id = '9f974676661e008dccb3ff5e4b698083';
delete from cds_code where code_id = 'c180deed39f000ac7e34080c987fa964';
delete from cds_code where code_id = '5e4a31b994cadb063733295b6f5de716';
delete from cds_code where code_id = 'd893be9238fb2518914dfc11a807c623';
delete from cds_code where code_id = '35540349ae4b7e58a19069ff6a4ad0e0';
delete from cds_code where code_id = 'ce821104896277fa845aca2741605663';
delete from cds_code where code_id = '92f5b3f09f0a9f33253db081d9645750';
delete from cds_code where code_id = '6501a8c2152d816c1c1b7f2cfdabf19f';
delete from cds_code where code_id = '32b948d5a785045c4b469184a8ac769a';
