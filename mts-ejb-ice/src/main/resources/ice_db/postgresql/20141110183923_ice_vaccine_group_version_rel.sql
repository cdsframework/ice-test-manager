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

-- // ice_vaccine_group_version_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_group_version_rel (
            version_rel_id VARCHAR(32) primary key,
            group_id VARCHAR(32) NOT NULL,
            version_id VARCHAR(32) NOT NULL,
            LAST_MOD_ID VARCHAR(32) NOT NULL,
            LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
            CREATE_ID VARCHAR(32) NOT NULL,
            CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_group_version_rel add constraint un_ivgvrvgidvid unique (group_id, version_id);

alter table ice_vaccine_group_version_rel add constraint fk_ivgvrvgid2ivvid4 foreign key (group_id) references ice_vaccine_group (group_id);

alter table ice_vaccine_group_version_rel add constraint fk_ivgvrvid2cvvid foreign key (version_id) references cds_version (version_id);

INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('11a12f984e1eed4a6cd17203820714b3', '9808d21f26aa9303abb790c5de6dfa81', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1f5bce73dd9d67923c7d91efae0fee23', '514c2a0658db7732b4fd1fb9cde3833e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2274cc33641fcf1feec5da38b88909a3', '6248e15c5d8a94c05c54f8efac5b3e62', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('276894663af275075c6c08e9c9559674', '51785397ea25f92c2349b7b47e363a62', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('27b8dd1cf39bb03c33b2b3c2e6c72665', '28f2cf85fc083a569eb46a69671fe8b0', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5d2a773e9763378be74db3eb98b94dfc', 'a97422fac07fed6db5cf2f95ea141e1c', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6793c33b66c3d13f4d066b09c8e0e9bc', 'cee1aace28bc115685dbbfd1536c7307', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6bf658db20521e36750c1b6ea2604092', 'd031730eeb92154f78bc4f4c88827b45', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('90a7e5aa7acf4fcb5dfe95777393ab1b', '4192deec1e4503a7df2e74a4b7289f0b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a1cc7c1ac65cd1f5805fdba569482362', '1ababe4ac2985098e7d4af77a9415a3d', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ad11e89c94909be9242b65dbcf109c81', '8faa56ba12d371ff805a9967c31c0fb5', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d8a20db16ad1a783db91841a99ed3a01', '308d8b6530000d15b4c6c04670af5aba', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('df4291241bc9fd896beb136467914a36', 'da79a9a2186d87cd236c1d6a9fd86e28', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('eb4928a6f87f5697a367ff1c756c7587', 'bc0aadca211f5a453a5bdf274e387c20', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_group_version_rel (version_rel_id, group_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f014a102aa325002f47d18fea409cb06', '2cc45eefd7d0135e9ed5e4dcb81cd2d1', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_group_version_rel;
