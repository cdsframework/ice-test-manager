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

-- // ice_vaccine_disease_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_disease_rel (
            disease_rel_id varchar(32) primary key,
            disease_code_id VARCHAR(32) NOT NULL,
            component_id varchar(32) NOT NULL,
            LAST_MOD_ID VARCHAR(32) NOT NULL,
            LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
            CREATE_ID VARCHAR(32) NOT NULL,
            CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_disease_rel add constraint un_ivdrdcidcid unique (disease_code_id, component_id);

alter table ice_vaccine_disease_rel add constraint fk_ivdrdcid2iddid foreign key (disease_code_id) references ice_disease (disease_id);

alter table ice_vaccine_disease_rel add constraint fk_ivdrcid2ivccid foreign key (component_id) references ice_vaccine_component (component_id);

INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1097a5ad93aefcdd2cdd4928a3e9d50b', '4f27cec9fd486d29935cc05b3ad185b4', '3fff865fe68daee74d69c5e0c7750d0e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('13610534e9fe4ca2e9c2f5428b630828', '8d58b13fcafa8c26fe46417a188a4eb0', '6b1fd318ceda97a08812b2a71ba80a67', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('13ecf696c9ccd4c7da726955614d0dfd', 'b88473bbb2f9f8f524b6cb0c587b3a1a', '363f183aa6055d052fd7900e338cf559', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1a388d1e3f0f2483b749fd4d68d8281b', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'dcb01536cd425956165af9d374c2baa8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1ad881c858effe3d2fc988ec4abdc745', '801bf6ead736240b7a238369c3005754', '36fea054a6b78d2a10139b43480b1083', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1fe6a1a4b60cf37ce151e68f88c1db32', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'cc422a1c3027b283a6caa0c7ef17e551', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('205a8ca1d4027a9fb0879f3c56a815a4', '4f27cec9fd486d29935cc05b3ad185b4', '52ba1d7a1ccfe5be84f6d1a9b5e608a3', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('209dfe8cba6b8448e26a6582a33148b7', 'f3dbf7b984af74335867bfabc6482b69', '7e7eeda75d1ea89921cab7d86b66c447', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('222b7900304c6c03b9cb9955933a6034', '826a5180b6816dfe93bdeebfc3ddac49', '37faef5213ff09e3ef4906c32ef40e0b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('261cc31cec72c96f78690c84a3f8f159', '4f27cec9fd486d29935cc05b3ad185b4', '699e36db6ca1ea07c9c4c2ee86053f96', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('27800bf825ecfb7f41ab9eae35574443', '826a5180b6816dfe93bdeebfc3ddac49', '2363716213b0f3a8e85243c976f3e7df', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('29b83341323b535347b876cf89f2ddc2', '826a5180b6816dfe93bdeebfc3ddac49', '8e3f621a6368e6d294211b84b09da1b8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2a2ecaa1e2ff792e7a956b2cd5de585a', '4f27cec9fd486d29935cc05b3ad185b4', 'b6f1d7bd6d6e5fc6db728c48678967c5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2a8a03604a737c4cfccfec68765c9937', 'ac2cfcd44fc9a651f68fc8bb30d38238', 'f8ef6fc7a9f39e0f6e8a56aeda960db3', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2b7c09da7e9803c1ad48136334e7dea5', 'd53eac28e4a8229cab9af40d2f5a3318', '7b0195669cf49dbbacac7d82f3c70042', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2fa0d2ca6b5d885695f4ebe1739f45b6', 'ecd5a411d6a36beb26a66042261b5195', 'f9e3be777817ba68958c4fa7a3d89b90', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3a259d1caccfc78392eeda44b349feb5', '3abf1c8772c4bae57d338812cd9997e9', '7e1471be78ff8ead9772f37fd6aac6cf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('41adf1f656a67ccab62521c135f12020', '83aeaca8fceb6f601c8ac5f19cc95fa0', '2fb18cfe6dc2701f7260e660ed0ee5c3', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('43b695367250e1b978b584a32a1fedf6', '50a5fecb498c84202107718b940b9e5e', 'a9527a3639c6a6c5a04d2728b7fc42c0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('43d92659a9675f9e1d34c0e91c41892a', '4f27cec9fd486d29935cc05b3ad185b4', 'f43885acb83bafd76ebec01b6f53f6cf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('44d01b15bbfa59c8c67bac55843408cf', 'ecd5a411d6a36beb26a66042261b5195', '47fe49b03811a40755c47cd4b74ccdfa', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4685e0e49660ec4f5b2281c4e342dc36', '826a5180b6816dfe93bdeebfc3ddac49', '6ed89869ac0ace491bec4526dec327f8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('47c18c9a8721a0dd5e11e24ebdee3c77', '50a5fecb498c84202107718b940b9e5e', 'd355695d931880b31ff86595b44f8985', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4a8bdb4c8345773f7a12b91015277337', '826a5180b6816dfe93bdeebfc3ddac49', 'f9e3be777817ba68958c4fa7a3d89b90', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4d7c089cd453399f9099347d11f230c0', '4f27cec9fd486d29935cc05b3ad185b4', '72f4c00c91390f7a5df4a0d6fc492894', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4dcf57b8af7f8272ba4e0b8a501b8a7f', '801bf6ead736240b7a238369c3005754', '84082e62128fabcd5a5364f78de9b631', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4f48ca8f6760857ca18b8660c40283a2', '50ebd184328992f2e1942a4d57abafa2', 'ed444e8a59047e86b28e89e335b21bfb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('51a345f97517b96f7dca8dd38b72fbee', '1094c6e572a39a0a13a1b534251f175f', '1a211cb73bc3b484d1e0852b18500a1e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('527d8bfc100ddeab27348a6195bfece7', '801bf6ead736240b7a238369c3005754', '940719422398be2efc9ae08751bf417b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('546db640ae440567dbb347f6c387cfc2', '50ebd184328992f2e1942a4d57abafa2', '1a72a3652ad621ef6c142028bc827e1b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5472c2f3e43f8131686c7a7239658265', '330fbc8d4c137595f3adb54d93cc1978', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5c2c565d70361f0acfd6b6b52e5cd157', 'f3dbf7b984af74335867bfabc6482b69', '89d9d0aa5f1e35db59a2734281bf3a63', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('66d1f83950c068e783bc804d23a4345e', 'ac2cfcd44fc9a651f68fc8bb30d38238', '9f4c6120a5c2be073a0519cb6f9b9d2f', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('68f597d1b9a300d471acb81ca874401d', '826a5180b6816dfe93bdeebfc3ddac49', 'dbb90d607a346452e82c6a526f6dd727', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6b7be04e18ff71c067439e036c0f9ef0', 'ecd5a411d6a36beb26a66042261b5195', 'dbb90d607a346452e82c6a526f6dd727', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6bb908746e2b4e31213c3b71f3eac9d4', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'd1fff1abfe5eab959f304de38f10d43c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6bef528499bdd7fe3be4b478eb6078ab', '1094c6e572a39a0a13a1b534251f175f', '99acdd2bcedffac9e0205217da0357fa', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('72d77464b858c6f8094a268be451de92', '50ebd184328992f2e1942a4d57abafa2', 'f715a2ea209729e852117d0a305799e1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('739ae94b3138aa937b56d8e5d871918a', '3abf1c8772c4bae57d338812cd9997e9', '1b3b5e305c967a32e20a9a447bad47e5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('73c0f3d443098a6b92112fe69b2a5d9f', '3abf1c8772c4bae57d338812cd9997e9', '6c7949af1810cd1183895825be4b8ce1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('744a1b1daf14e70cae10bd1d71268bb5', 'b88473bbb2f9f8f524b6cb0c587b3a1a', '6699c4d58521edb9c091072e976bc67d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('74964e4856ebae89aa43b0355d1e7da5', '8d58b13fcafa8c26fe46417a188a4eb0', '39d17b6e86a657837bb1b2dfe11bfdb6', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('75d7e784800972f9f286954a980b3676', 'f3dbf7b984af74335867bfabc6482b69', '53f358d0b6e07c53d9a118c39a4182db', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7709f3e94f0e67f734020195e0d34a41', '801bf6ead736240b7a238369c3005754', '7d3d80b067c1fa9390eb3b50f20075d9', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7b9c2b865a5943093c3f1977193bc8ed', '826a5180b6816dfe93bdeebfc3ddac49', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7cd857dcf4a6b5755bb9a3d57f6777e6', '5c138da625d6ff08ac681db987c1f4af', 'a0e226a6a44b1eafb30b6cb4ccef45c6', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7d220105ac82b30320e6816845c8d9db', '83aeaca8fceb6f601c8ac5f19cc95fa0', '43c1aefb6b54e4f04bb00c87b206195e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('82919ded55f6ea0a3b5243af59c9af96', '330fbc8d4c137595f3adb54d93cc1978', '115011c96e54f5e1cc044f76f69e77b2', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('88de014551e0dd31f443a4ee0f1b7c99', '4f27cec9fd486d29935cc05b3ad185b4', 'bd1b49f5a89652d6db5c587594136f42', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('8cdde5ab497159ba99f7068ad49c73dc', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'e5d15051f018680a81ba7a0bb2f2e0dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9084a80d1612a50bc33dd0fe4d47428e', '4f27cec9fd486d29935cc05b3ad185b4', '807930df85c91a3ebe707f09b9c8c609', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('90b15d1c2d7e36d0abeab63ed461824a', '4f27cec9fd486d29935cc05b3ad185b4', '9c47e492cb267923614e4827fa3d6d2b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('916d4b464ff3c6841da5495868acee85', '826a5180b6816dfe93bdeebfc3ddac49', '47fe49b03811a40755c47cd4b74ccdfa', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('92a6f1555b983b5c99d9534057d3e93e', 'ac2cfcd44fc9a651f68fc8bb30d38238', 'acf262fa464441cd22704f6f70839150', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('940cc50d5f13473afa532c0aae0b212c', '826a5180b6816dfe93bdeebfc3ddac49', 'a94856e326cc369c5a5ebb1e53fd76e5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9897f8cdfcafe301769fa0e975a128cc', '4f27cec9fd486d29935cc05b3ad185b4', '6a93b7b5eb793339480570defbe7fe14', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('99cdaf4afda6503f51ce2e1349c20f59', '826a5180b6816dfe93bdeebfc3ddac49', '1a211cb73bc3b484d1e0852b18500a1e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9a8d88c01b89024da7ef9e5950530301', 'f3dbf7b984af74335867bfabc6482b69', 'a58a28918894428bdefab782a7f1c215', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9c18bc9c4cfb17de9e9334b7649aea5a', '50a5fecb498c84202107718b940b9e5e', '1d6f24415002fc1ed3f31282eb3db7bb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9c3760491b733bf6a0b2282af0f9c958', '1094c6e572a39a0a13a1b534251f175f', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a12a953d8937889ccecfe89b8d378ea3', '3abf1c8772c4bae57d338812cd9997e9', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a4c0b851e62f515545c51dcaf3948b4b', '1094c6e572a39a0a13a1b534251f175f', 'f9e3be777817ba68958c4fa7a3d89b90', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a509ed00a9b2cc8aea4ec4a13b36f40c', '8d58b13fcafa8c26fe46417a188a4eb0', '6334689712dcc4ebf302c90968682853', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a833850efd42b469ee3942c4a287f920', 'd53eac28e4a8229cab9af40d2f5a3318', 'a0e226a6a44b1eafb30b6cb4ccef45c6', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a93603ce640e5a866d1e8c86c337003d', 'ecd5a411d6a36beb26a66042261b5195', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a957c27d404756051588ca6d51d73d47', 'f3dbf7b984af74335867bfabc6482b69', '71a48f0d94463afe5ff7a3aed4e3ee2e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('aa9544bdb563408f7e20b96e2a9c093a', 'ecd5a411d6a36beb26a66042261b5195', '37faef5213ff09e3ef4906c32ef40e0b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('abc376252cd3e94bff0d5836b8afa91e', '4f27cec9fd486d29935cc05b3ad185b4', '993ea3ebe57ee235d2affb1173e40388', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b4304d19d1fd6dda10d9e263ecf78276', '4f27cec9fd486d29935cc05b3ad185b4', '6d78bb9f5eff5cbfa9d0b2343ba91360', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b94379ed81867f3c8145e1d18680984c', 'ac2cfcd44fc9a651f68fc8bb30d38238', '72992266a4c25a5e6e5f132a9ede2680', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b96828ef3c6b7f63212045a137695548', 'ecd5a411d6a36beb26a66042261b5195', '6ed89869ac0ace491bec4526dec327f8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c4a928377f47e6707ac83cd62e0a31fe', 'ecd5a411d6a36beb26a66042261b5195', '1a211cb73bc3b484d1e0852b18500a1e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c5bc1bcf356567d772f761775c81f202', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'a2cfcee1cefe42eab42e2318645d9f33', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c5de6cb67182dc33afb88148241950f0', '5c138da625d6ff08ac681db987c1f4af', '16b7163c2814a310a9d392c23d76e143', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cbbfd08ec166c9503a0f3ddd5a60d581', '8d58b13fcafa8c26fe46417a188a4eb0', '5178e23c337a26a1ce17a4a73f8bf97b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d6e76d6b5615c562967377e3d5f4d073', '81c85e43430ec651a931e5e4b8b19afd', '5b04394f57e2cd25a8f995ff5d4d0854', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d77daed41d0dc86ee52f890adf899494', '1094c6e572a39a0a13a1b534251f175f', '6ed89869ac0ace491bec4526dec327f8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('def1f43597fbc54edfdc60e29a159c95', '4f27cec9fd486d29935cc05b3ad185b4', '5346f65f8d180de8781cdaaa5ffaff33', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e09b0a4ed261ce54424dbe6010413a55', '330fbc8d4c137595f3adb54d93cc1978', '6e89da7570445099ccaca472a4510dbd', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e29f8733c6f0f995d847a702cf82712d', '801bf6ead736240b7a238369c3005754', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e73a16334f2f582e8cdf2a6b672ea826', 'd53eac28e4a8229cab9af40d2f5a3318', 'ed444e8a59047e86b28e89e335b21bfb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ea9571af2bff925dc4810fa5bfe6def0', '83aeaca8fceb6f601c8ac5f19cc95fa0', '26b5eadb78914bfa390dc8b1d7d7a37c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ed1cf11476f4b067720b8b27f747a852', 'ecd5a411d6a36beb26a66042261b5195', 'a94856e326cc369c5a5ebb1e53fd76e5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('eeb45b00ad601c5bb276eaa6b60c29bd', 'ac2cfcd44fc9a651f68fc8bb30d38238', '68a94ed7b016986a40f6551b3f7a9cbf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('efc6d99b9a176817340bdbce84fe9229', '4f27cec9fd486d29935cc05b3ad185b4', '43041e87d8e921484eee385ac621cf9a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f02ec56339977556492cfeb7c9ed06bb', '5c138da625d6ff08ac681db987c1f4af', 'ed444e8a59047e86b28e89e335b21bfb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f038c6d37f3838a2e80bc70ec1fdda4f', '4f27cec9fd486d29935cc05b3ad185b4', '9bb88b1daa2dafd48c265a9e3a7bc878', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f07705379cb257971e03a856681d58d7', '4f27cec9fd486d29935cc05b3ad185b4', '696564f7f1fc0533d4f5dd85ed05cfea', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f0c634e97918619529f2533f6d8a8c51', 'ecd5a411d6a36beb26a66042261b5195', '2363716213b0f3a8e85243c976f3e7df', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f61101c6c843744ae0bc95b7b037caef', '1094c6e572a39a0a13a1b534251f175f', 'dbb90d607a346452e82c6a526f6dd727', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f7b3b8fdc199bda49ed77d70ee3e6b06', '50a5fecb498c84202107718b940b9e5e', '1063d02b120f84c7d2e30b098404284d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fd997229e71c5c629431d28ae2fb546a', '330fbc8d4c137595f3adb54d93cc1978', 'fb284f06c8a73851707a9610789c8381', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fda163d3ca5d0ad77f7b88297ea9f70d', 'd53eac28e4a8229cab9af40d2f5a3318', 'f715a2ea209729e852117d0a305799e1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ff2f0eb5e36e99e926ee73bd509bf6d4', 'ecd5a411d6a36beb26a66042261b5195', '8e3f621a6368e6d294211b84b09da1b8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_disease_rel (disease_rel_id, disease_code_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ffa902e82675faab7950fa9bc687c478', '8d58b13fcafa8c26fe46417a188a4eb0', '59e02e084e71ebc4cf13c0a748e4f47a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_disease_rel;
