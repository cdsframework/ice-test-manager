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

-- // ice_vaccine_group_disease_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_group_disease_rel (
            disease_rel_id varchar(32) primary key,
            disease_code_id VARCHAR(32) NOT NULL,
            group_id varchar(32) NOT NULL,
            LAST_MOD_ID VARCHAR(32) NOT NULL,
            LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
            CREATE_ID VARCHAR(32) NOT NULL,
            CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_group_disease_rel add constraint un_ivgdr_dcid_vgid unique (disease_code_id, group_id);

alter table ice_vaccine_group_disease_rel add constraint fk_ivgdrdcid2iddid foreign key (disease_code_id) references ice_disease (disease_id);

alter table ice_vaccine_group_disease_rel add constraint fk_ivgdrvgid2ivgvgid foreign key (group_id) references ice_vaccine_group (group_id);

INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('24cb4e1a88a1ca7fa948549162bcdf81', '5c138da625d6ff08ac681db987c1f4af', '6248e15c5d8a94c05c54f8efac5b3e62', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('277114888abd5d729dcf5a70caf09f06', '826a5180b6816dfe93bdeebfc3ddac49', '308d8b6530000d15b4c6c04670af5aba', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2ba965f016e2e78cbff65513f8ce8e51', '801bf6ead736240b7a238369c3005754', 'cee1aace28bc115685dbbfd1536c7307', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('309fcc215e7e9d68cc960cfc5d532504', 'ecd5a411d6a36beb26a66042261b5195', '308d8b6530000d15b4c6c04670af5aba', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('38b3a7c05ebaf8774092843f80184280', '83aeaca8fceb6f601c8ac5f19cc95fa0', '8faa56ba12d371ff805a9967c31c0fb5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('39f70381ce6e6100cb2b6251a6d869ab', 'd53eac28e4a8229cab9af40d2f5a3318', '6248e15c5d8a94c05c54f8efac5b3e62', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('50d746d6cd834b11bdb6f4b4bd04c5ea', '8d58b13fcafa8c26fe46417a188a4eb0', '9808d21f26aa9303abb790c5de6dfa81', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('83b586a36c219d48e4104c8412bc4fc5', '50a5fecb498c84202107718b940b9e5e', 'a97422fac07fed6db5cf2f95ea141e1c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('8cbbec3f53269837f353260c6bb111ec', 'b88473bbb2f9f8f524b6cb0c587b3a1a', '1ababe4ac2985098e7d4af77a9415a3d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9ec12413cdd128c18c2ff24dc6de36c5', '801bf6ead736240b7a238369c3005754', 'd031730eeb92154f78bc4f4c88827b45', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a3223f51f2c5d0b98e30165df3b034d2', 'f3dbf7b984af74335867bfabc6482b69', '28f2cf85fc083a569eb46a69671fe8b0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('be447c05918525c09d64529ef346d330', '81c85e43430ec651a931e5e4b8b19afd', '4192deec1e4503a7df2e74a4b7289f0b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('bee3b12e6b3e07ff5bf567296ae02088', '4f27cec9fd486d29935cc05b3ad185b4', '51785397ea25f92c2349b7b47e363a62', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c60a49f915dd4603c623cd48cda2290c', '50ebd184328992f2e1942a4d57abafa2', '6248e15c5d8a94c05c54f8efac5b3e62', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c71b1161bef6137ddaec841d38c383b7', '1094c6e572a39a0a13a1b534251f175f', '308d8b6530000d15b4c6c04670af5aba', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e9044cb9d9bdef94af684986bd8fa4e0', '3abf1c8772c4bae57d338812cd9997e9', '514c2a0658db7732b4fd1fb9cde3833e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ea1cffefc158802eaaf5e8a45ebd7d05', 'ac2cfcd44fc9a651f68fc8bb30d38238', 'da79a9a2186d87cd236c1d6a9fd86e28', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_disease_rel (disease_rel_id, disease_code_id, group_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ef5f4726ebfaac9e9cb94ab9e8f344e7', '330fbc8d4c137595f3adb54d93cc1978', '2cc45eefd7d0135e9ed5e4dcb81cd2d1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);


-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_group_disease_rel;
