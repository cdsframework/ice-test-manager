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

-- // ice_vaccine_component
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_component (
            component_id VARCHAR(32) PRIMARY KEY,
            live_virus boolean not null default false,
            LAST_MOD_ID VARCHAR(32) NOT NULL,
            LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
            CREATE_ID VARCHAR(32) NOT NULL,
            CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_component add constraint fk_ivc2cccid foreign key (component_id) references cds_code (code_id);

INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1063d02b120f84c7d2e30b098404284d', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('115011c96e54f5e1cc044f76f69e77b2', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('16b7163c2814a310a9d392c23d76e143', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1a211cb73bc3b484d1e0852b18500a1e', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1a72a3652ad621ef6c142028bc827e1b', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1b3b5e305c967a32e20a9a447bad47e5', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1d6f24415002fc1ed3f31282eb3db7bb', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2363716213b0f3a8e85243c976f3e7df', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('26b5eadb78914bfa390dc8b1d7d7a37c', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2fb18cfe6dc2701f7260e660ed0ee5c3', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('363f183aa6055d052fd7900e338cf559', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('36fea054a6b78d2a10139b43480b1083', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('37faef5213ff09e3ef4906c32ef40e0b', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('39d17b6e86a657837bb1b2dfe11bfdb6', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3fff865fe68daee74d69c5e0c7750d0e', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('43041e87d8e921484eee385ac621cf9a', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('43c1aefb6b54e4f04bb00c87b206195e', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('47fe49b03811a40755c47cd4b74ccdfa', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5178e23c337a26a1ce17a4a73f8bf97b', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('52ba1d7a1ccfe5be84f6d1a9b5e608a3', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5346f65f8d180de8781cdaaa5ffaff33', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('53f358d0b6e07c53d9a118c39a4182db', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('59e02e084e71ebc4cf13c0a748e4f47a', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5b04394f57e2cd25a8f995ff5d4d0854', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6334689712dcc4ebf302c90968682853', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6699c4d58521edb9c091072e976bc67d', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('68a94ed7b016986a40f6551b3f7a9cbf', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('696564f7f1fc0533d4f5dd85ed05cfea', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('699e36db6ca1ea07c9c4c2ee86053f96', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6a93b7b5eb793339480570defbe7fe14', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6b1fd318ceda97a08812b2a71ba80a67', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6c7949af1810cd1183895825be4b8ce1', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6d3e2c6f7b0de8a9785bea8df68f0c47', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6d78bb9f5eff5cbfa9d0b2343ba91360', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6e89da7570445099ccaca472a4510dbd', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6ed89869ac0ace491bec4526dec327f8', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('71a48f0d94463afe5ff7a3aed4e3ee2e', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('72992266a4c25a5e6e5f132a9ede2680', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('72f4c00c91390f7a5df4a0d6fc492894', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7b0195669cf49dbbacac7d82f3c70042', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7d3d80b067c1fa9390eb3b50f20075d9', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7e1471be78ff8ead9772f37fd6aac6cf', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7e7eeda75d1ea89921cab7d86b66c447', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('807930df85c91a3ebe707f09b9c8c609', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('84082e62128fabcd5a5364f78de9b631', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('89d9d0aa5f1e35db59a2734281bf3a63', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('8e3f621a6368e6d294211b84b09da1b8', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('940719422398be2efc9ae08751bf417b', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('99acdd2bcedffac9e0205217da0357fa', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9bb88b1daa2dafd48c265a9e3a7bc878', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9c47e492cb267923614e4827fa3d6d2b', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9d8e6e80e7805d2de2594a5f11fd7fc7', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9f4c6120a5c2be073a0519cb6f9b9d2f', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a0e226a6a44b1eafb30b6cb4ccef45c6', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a2cfcee1cefe42eab42e2318645d9f33', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a58a28918894428bdefab782a7f1c215', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a94856e326cc369c5a5ebb1e53fd76e5', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a9527a3639c6a6c5a04d2728b7fc42c0', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('acf262fa464441cd22704f6f70839150', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('bb59ba73a1072418faaa463ee5f472dc', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('bd1b49f5a89652d6db5c587594136f42', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c955ffa4dc71edfe7b4f831ac3729c7d', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cc422a1c3027b283a6caa0c7ef17e551', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d1fff1abfe5eab959f304de38f10d43c', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('dbb90d607a346452e82c6a526f6dd727', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('dcb01536cd425956165af9d374c2baa8', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e5d15051f018680a81ba7a0bb2f2e0dc', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ed444e8a59047e86b28e89e335b21bfb', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f43885acb83bafd76ebec01b6f53f6cf', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f715a2ea209729e852117d0a305799e1', true, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f8ef6fc7a9f39e0f6e8a56aeda960db3', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f9e3be777817ba68958c4fa7a3d89b90', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fb284f06c8a73851707a9610789c8381', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('993ea3ebe57ee235d2affb1173e40388', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d355695d931880b31ff86595b44f8985', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component (component_id, live_virus, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b6f1d7bd6d6e5fc6db728c48678967c5', false, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_component;
