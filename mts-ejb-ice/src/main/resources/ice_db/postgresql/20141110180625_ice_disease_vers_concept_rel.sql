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

-- // ice_disease_vers_concept_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_disease_vers_concept_rel (
    rel_id VARCHAR(32) PRIMARY KEY,
    primary_concept VARCHAR(32) NOT NULL,
    version_id varchar(32) not null,
    disease_id varchar(32) not null,
    LAST_MOD_ID VARCHAR(32) NOT NULL,
    LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATE_ID VARCHAR(32) NOT NULL,
    CREATE_DATETIME timestamp NOT NULL);

alter table ice_disease_vers_concept_rel add constraint un_idvcrpcviddid unique (primary_concept, version_id, disease_id);

alter table ice_disease_vers_concept_rel add constraint fk_idvcrdid2iddid foreign key (disease_id) references ice_disease (disease_id);

alter table ice_disease_vers_concept_rel add constraint fk_idvcrvid2cvvid foreign key (version_id) references cds_version (version_id);

alter table ice_disease_vers_concept_rel add constraint fk_idvcrpc2occcid foreign key (primary_concept) references opencds_concept (code_id);

INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3ef5f271b84e7874cea9ea8d795e39f3', '850090943836db29602c1e1fb7df5d2f', 'f89c7a73aff266756922d437e0ad8044', '83aeaca8fceb6f601c8ac5f19cc95fa0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c07b550dca986e70952b573f4513d0d0', 'e10591982a5a9530c49e4989ce749af6', 'f89c7a73aff266756922d437e0ad8044', 'ecd5a411d6a36beb26a66042261b5195', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('366a9eadaab4fad5f86c885ffc297598', '7e932a8c23ad4eec37aebf9ae1e2e101', 'f89c7a73aff266756922d437e0ad8044', '3abf1c8772c4bae57d338812cd9997e9', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('194e0993a67e0d7be922fc16d73aa713', '5b08a0ba2e2bdd259f137a57b6cc83d4', 'f89c7a73aff266756922d437e0ad8044', 'ac2cfcd44fc9a651f68fc8bb30d38238', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5122382b4e2979f356db025649465bbc', 'e5d0ee72c6eb914b702d283bde516b9e', 'f89c7a73aff266756922d437e0ad8044', 'f3dbf7b984af74335867bfabc6482b69', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a7e4fdb4139ec2d4135e27d19afa87b9', 'e4e29339f120d6e5a9a060d9446d2a9d', 'f89c7a73aff266756922d437e0ad8044', '8d58b13fcafa8c26fe46417a188a4eb0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3aa971209b2bfcce622bdd764d44080a', '49b289efc9a44e8cee2a7d1ff9f6d319', 'f89c7a73aff266756922d437e0ad8044', '50a5fecb498c84202107718b940b9e5e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('aa1fca3e8b3d4efe6c56943ba6158cff', '3f5b8c2360690eefeb33888f1834c75b', 'f89c7a73aff266756922d437e0ad8044', '4f27cec9fd486d29935cc05b3ad185b4', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7cdb6d1a8a9ba483e130511d128e37eb', 'd09f820290275497949a2dc4b63e2cb9', 'f89c7a73aff266756922d437e0ad8044', '50ebd184328992f2e1942a4d57abafa2', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1563c7a3b7ec6350f95dfd64c6ffe016', '46a524937c6b47beb74e67977ab44aef', 'f89c7a73aff266756922d437e0ad8044', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9fc2e58ef6753c433d98f72bf8e032f7', 'a5b18dc53cdb88393d7b7ab475653254', 'f89c7a73aff266756922d437e0ad8044', '5c138da625d6ff08ac681db987c1f4af', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('280ede7ac0c46877c0daa61fe7af23a2', '336d08336003fc6656e1820666989895', 'f89c7a73aff266756922d437e0ad8044', '1094c6e572a39a0a13a1b534251f175f', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c39982552b0ee7d5d7261887b905d7f1', '8750f40889cb260882e190ea585228d6', 'f89c7a73aff266756922d437e0ad8044', '801bf6ead736240b7a238369c3005754', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('921b94f1e2dfec8381fcae3c5d29c22a', '8e39694d8fe12457edcda500f74e902d', 'f89c7a73aff266756922d437e0ad8044', '330fbc8d4c137595f3adb54d93cc1978', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('69ab22134ae50998bdcb9b0470b29e8a', '2fbe722c84f17892db0c660d40f2770c', 'f89c7a73aff266756922d437e0ad8044', 'd53eac28e4a8229cab9af40d2f5a3318', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('53ba53927f94ee6b972494f06855732d', 'ed1bf058913d4806d4c7aa8fc774a87b', 'f89c7a73aff266756922d437e0ad8044', '826a5180b6816dfe93bdeebfc3ddac49', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease_vers_concept_rel (rel_id, primary_concept, version_id, disease_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('51fd0b689d0c96ccccea80bd6a3d1e23', 'd21031b91c51d50177dfb897a59cb3e8', 'f89c7a73aff266756922d437e0ad8044', '81c85e43430ec651a931e5e4b8b19afd', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);


-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_disease_vers_concept_rel;

