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

-- // ice_disease
-- Migration SQL that makes the change goes here.


CREATE TABLE ice_disease (
    disease_id VARCHAR(32) PRIMARY KEY,
    code_id VARCHAR(32) NOT NULL,
    LAST_MOD_ID VARCHAR(32) NOT NULL,
    LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATE_ID VARCHAR(32) NOT NULL,
    CREATE_DATETIME timestamp NOT NULL);

alter table ice_disease add constraint fk_idcid2cccid foreign key (code_id) references cds_code (code_id);

INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1094c6e572a39a0a13a1b534251f175f', '1094c6e572a39a0a13a1b534251f175f', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('330fbc8d4c137595f3adb54d93cc1978', '330fbc8d4c137595f3adb54d93cc1978', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3abf1c8772c4bae57d338812cd9997e9', '3abf1c8772c4bae57d338812cd9997e9', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4f27cec9fd486d29935cc05b3ad185b4', '4f27cec9fd486d29935cc05b3ad185b4', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('50a5fecb498c84202107718b940b9e5e', '50a5fecb498c84202107718b940b9e5e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('50ebd184328992f2e1942a4d57abafa2', '50ebd184328992f2e1942a4d57abafa2', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5c138da625d6ff08ac681db987c1f4af', '5c138da625d6ff08ac681db987c1f4af', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('801bf6ead736240b7a238369c3005754', '801bf6ead736240b7a238369c3005754', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('81c85e43430ec651a931e5e4b8b19afd', '81c85e43430ec651a931e5e4b8b19afd', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('826a5180b6816dfe93bdeebfc3ddac49', '826a5180b6816dfe93bdeebfc3ddac49', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('83aeaca8fceb6f601c8ac5f19cc95fa0', '83aeaca8fceb6f601c8ac5f19cc95fa0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('8d58b13fcafa8c26fe46417a188a4eb0', '8d58b13fcafa8c26fe46417a188a4eb0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ac2cfcd44fc9a651f68fc8bb30d38238', 'ac2cfcd44fc9a651f68fc8bb30d38238', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b88473bbb2f9f8f524b6cb0c587b3a1a', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d53eac28e4a8229cab9af40d2f5a3318', 'd53eac28e4a8229cab9af40d2f5a3318', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ecd5a411d6a36beb26a66042261b5195', 'ecd5a411d6a36beb26a66042261b5195', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_disease (disease_id, code_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f3dbf7b984af74335867bfabc6482b69', 'f3dbf7b984af74335867bfabc6482b69', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_disease;
