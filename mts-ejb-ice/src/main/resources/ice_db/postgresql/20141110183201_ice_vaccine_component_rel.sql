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

-- // ice_vaccine_component_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_component_rel (
            component_rel_id VARCHAR(32) primary key,
            vaccine_id VARCHAR(32) NOT NULL,
            component_id VARCHAR(32) NOT NULL,
            LAST_MOD_ID VARCHAR(32) NOT NULL,
            LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
            CREATE_ID VARCHAR(32) NOT NULL,
            CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_component_rel add constraint un_ivcrvidcid unique (vaccine_id, component_id);

alter table ice_vaccine_component_rel add constraint fk_ivcrvid2ivvid foreign key (vaccine_id) references ice_vaccine (vaccine_id);

alter table ice_vaccine_component_rel add constraint fk_ivcrcid2ivccid foreign key (component_id) references ice_vaccine_component (component_id);

INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('10d81f6f6eedd99bb292ee153e949e14', '2880c23dd8ad8d9b3bba5028af2c4e4e', '43041e87d8e921484eee385ac621cf9a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('155b070cd773fc9f4997466eed039af0', '19a8fab71b99131a16ebf3e42e5f9fc7', '7e7eeda75d1ea89921cab7d86b66c447', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('15c4d123c8482790118cd7ae6a49b9c1', '7a2b1bee78f886391cbd446123d9b20d', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1ed3622ecf4855e11598e17f5872cd97', 'ed635e962167812224f6a70a6c7705b6', '71a48f0d94463afe5ff7a3aed4e3ee2e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1fdcce89425e1de0e76f722f8d9ea49a', '5178e23c337a26a1ce17a4a73f8bf97b', '5178e23c337a26a1ce17a4a73f8bf97b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('225add289e6e28d615d1e494dea486aa', 'dedba4495cfa131c96b4dc0b43138d83', '68a94ed7b016986a40f6551b3f7a9cbf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('22c890e5c2972992f87ad8f49bea19f8', '807930df85c91a3ebe707f09b9c8c609', '807930df85c91a3ebe707f09b9c8c609', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2440e83b7fec44897f43efd45313221d', 'acf262fa464441cd22704f6f70839150', 'acf262fa464441cd22704f6f70839150', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('28ae8789cb631ba4bba6d1f9d3163a12', '12eda6eda040afed588639de6250700c', '52ba1d7a1ccfe5be84f6d1a9b5e608a3', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2c5ba7fddc217dc0a75c1929e3d0875a', '73e1989170ca5c43a4767dfbf11f9c6a', 'a0e226a6a44b1eafb30b6cb4ccef45c6', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2c62ef36ce619afbe83d2fca658461ba', '886903982dada4949ab8488698671819', 'a58a28918894428bdefab782a7f1c215', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2e3104b2b7ec13057d84a6743d370b9b', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'acf262fa464441cd22704f6f70839150', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2f66331c96bf3267df0f87eba27c4030', 'ce46656efb01eeb469434730796ac0aa', '36fea054a6b78d2a10139b43480b1083', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2fcf01f24023d036cdd89a7710f23471', '1d6f24415002fc1ed3f31282eb3db7bb', '1d6f24415002fc1ed3f31282eb3db7bb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('31cdcfb9a963ddfca07b477bcb1c7b83', '33a5b231185ea648ac237f1989da814a', '9f4c6120a5c2be073a0519cb6f9b9d2f', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3714df6c29f2f5f2f2d59a91551ac4fc', 'f9e3be777817ba68958c4fa7a3d89b90', 'f9e3be777817ba68958c4fa7a3d89b90', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('39c49403341b4b9b196fc06d3b5815f0', '4498350f8bdd4e60b0314fcb3c22e827', '37faef5213ff09e3ef4906c32ef40e0b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3a4ff1e9cfba6ab9152adbee337868cd', '886903982dada4949ab8488698671819', '26b5eadb78914bfa390dc8b1d7d7a37c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3a9e7d8fa3e026c25e7a365d1c6f41ac', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'f9e3be777817ba68958c4fa7a3d89b90', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3ac01eed8a2fbd8bc4f8783fe5b541be', '2a724fd66373612cfcaa92f7341e246e', '2fb18cfe6dc2701f7260e660ed0ee5c3', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3c829e4a1e31c761b1184e29b90d7400', 'b8a06f21141c029b2833275a8f036ca3', 'cc422a1c3027b283a6caa0c7ef17e551', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3d50091378ef06dba2175683a936e494', '940719422398be2efc9ae08751bf417b', '940719422398be2efc9ae08751bf417b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4009aeb3feee9c87b0efd7cce381d262', '886903982dada4949ab8488698671819', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('42399ead68255f14153394e75040916c', '59e02e084e71ebc4cf13c0a748e4f47a', '59e02e084e71ebc4cf13c0a748e4f47a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('46a8d47173dd887371cee56d787c02af', '50a0e2cd1feca24fab3422cda3367c26', '6334689712dcc4ebf302c90968682853', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('47614d516422801bf07f8eee7563f342', '39b53d6016e88f5784bad89ecd3fde2e', '59e02e084e71ebc4cf13c0a748e4f47a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4769d569b708d117971f3c80c5874ced', '7b0195669cf49dbbacac7d82f3c70042', '7b0195669cf49dbbacac7d82f3c70042', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('50439f9da1339e29b16253034ba12ad1', '1a211cb73bc3b484d1e0852b18500a1e', '1a211cb73bc3b484d1e0852b18500a1e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('50b66cb62c190386e5aad58b9a60c08e', '1b3b5e305c967a32e20a9a447bad47e5', '1b3b5e305c967a32e20a9a447bad47e5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('55ee12b36dd5ea1096b1bc5a2593903b', 'a4900f2c8af205febdade2da44b7aa71', '39d17b6e86a657837bb1b2dfe11bfdb6', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('57e50d0a4088e4bb4a33cba31624ec9b', '6d3e2c6f7b0de8a9785bea8df68f0c47', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('58742d835b55ba9d873c419e7d109f85', '8d94a63bed4e53e27c403fb72d68f884', '9bb88b1daa2dafd48c265a9e3a7bc878', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5b72a012dc78a7294a92d22b9f4e704e', '37e5fa116314b6dc5b698c08afea530c', 'f43885acb83bafd76ebec01b6f53f6cf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5cc26f19d8362202de8f93ec5b8184f4', '72f4c00c91390f7a5df4a0d6fc492894', '72f4c00c91390f7a5df4a0d6fc492894', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5ddc4483f8a7e1b5eb77e8b31e493b4e', '7d3d80b067c1fa9390eb3b50f20075d9', '7d3d80b067c1fa9390eb3b50f20075d9', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5e0582dbb8740476816825856b12c1ca', '8193b4c4e4552b432a72232fe733d32a', 'a2cfcee1cefe42eab42e2318645d9f33', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6b2ec72e9974ca83c1cbf8138dd561a8', '8e3f621a6368e6d294211b84b09da1b8', '8e3f621a6368e6d294211b84b09da1b8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6c5c947042fb14cda1698b123408e90a', 'b6ffa4b8bc0847078f9be7209b5df876', '26b5eadb78914bfa390dc8b1d7d7a37c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6d951de4af171f872446ced1aff3515a', 'f2e213c64816a94de7c4fbb58ffb6b70', '84082e62128fabcd5a5364f78de9b631', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('772f085249fb4507aab3b633c2fae356', '5bd4d31540e5a654ac10aafd9221ac61', 'bd1b49f5a89652d6db5c587594136f42', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('77d2e4916fd56ae683270f0cef29f987', 'be777d762c0f14a4bf003e0a8a9d0fc6', '26b5eadb78914bfa390dc8b1d7d7a37c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('781a263ad789579bafb4d1b6da51b917', '6ccad31d85f1a1bf991e46c3dc1e0e09', '6b1fd318ceda97a08812b2a71ba80a67', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7e20a9fe757713151293e02b2eb9e4e1', 'a58a28918894428bdefab782a7f1c215', 'a58a28918894428bdefab782a7f1c215', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('85d2f540a87550dced7506e17c1fdace', 'b041a921609bd35ceb980ef6fa484baf', 'ed444e8a59047e86b28e89e335b21bfb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('88ead0b7f9e98f9a20fa305c6e3c966d', '7e1471be78ff8ead9772f37fd6aac6cf', '7e1471be78ff8ead9772f37fd6aac6cf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('8c76d611867edf1d8c6da425abb9c078', '46eb9a9d4a483694c03341125900c46b', 'a9527a3639c6a6c5a04d2728b7fc42c0', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('92f275ec8bd270f139ec0ea0f9341ae2', 'be777d762c0f14a4bf003e0a8a9d0fc6', '5178e23c337a26a1ce17a4a73f8bf97b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('966aa0af9670ac284983d6bf1659ffbf', 'e3f3ab4bb0643e385b69f33b34c01cf8', '53f358d0b6e07c53d9a118c39a4182db', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('98be8d434abc151642daf812d57ae957', '16b7163c2814a310a9d392c23d76e143', '16b7163c2814a310a9d392c23d76e143', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('99a409e514f0a93dea3733794d269b1b', '115011c96e54f5e1cc044f76f69e77b2', '115011c96e54f5e1cc044f76f69e77b2', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9f0522ca22dba9606be380abd5c2c488', 'b6ffa4b8bc0847078f9be7209b5df876', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a0e6fd07d08b74c19fdbd5b7d994b11c', 'fe19b4a3be03ac3c524cf690ce4e03f5', '2363716213b0f3a8e85243c976f3e7df', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a0f8d18078e68a7087f058eb3427cbbe', '9430c9781e2189b05c3cd6329af76fc6', 'cc422a1c3027b283a6caa0c7ef17e551', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a2cf59c2a3c2c64734dd3072d8c1de45', '35282fcad02f488f22f57a746c944421', '72992266a4c25a5e6e5f132a9ede2680', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a4fc6b4f204635adfb222ffbc0b16fc9', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ac3193518ac5c2a45eeeced3a4751803', '9d8e6e80e7805d2de2594a5f11fd7fc7', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ae8c50dff93d17ddde60a7a973ae491b', 'dbb90d607a346452e82c6a526f6dd727', 'dbb90d607a346452e82c6a526f6dd727', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ae90e3f2386b2b1413677ebb4a320511', '10ad21c2ca078ac4293e56dc5c7fb4a7', '59e02e084e71ebc4cf13c0a748e4f47a', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b06c82199d3dc0f963ac244e9ffbcb90', '6883576bd868da1d907142ade9f7b923', 'a58a28918894428bdefab782a7f1c215', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b975f9f32c4bea611c7902d42d61c99b', 'ed444e8a59047e86b28e89e335b21bfb', 'ed444e8a59047e86b28e89e335b21bfb', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('bdaa29f7eeda299af7fabe4488dabd7b', '6699c4d58521edb9c091072e976bc67d', '6699c4d58521edb9c091072e976bc67d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c27b8cf3ea8df41cea74b2b611a58beb', '6883576bd868da1d907142ade9f7b923', '6334689712dcc4ebf302c90968682853', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c29806a9894aac1e7339428ca7e33691', '7a2b1bee78f886391cbd446123d9b20d', '5178e23c337a26a1ce17a4a73f8bf97b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c438b3031b65644ec1e447d6d0c1acd1', '1a72a3652ad621ef6c142028bc827e1b', '1a72a3652ad621ef6c142028bc827e1b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c7050401c4fb8130e5f24c5f14b4dd5a', 'f715a2ea209729e852117d0a305799e1', 'f715a2ea209729e852117d0a305799e1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ca741cbc6935796ad3ee3d5dadeaef2a', '31afa04db3aa44e0e61e76c626a3be4e', '363f183aa6055d052fd7900e338cf559', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cbfabd3d2c85b4372ac91c6fed68e3dc', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cc32b93f18055c5eaedc5724730f7715', 'dcb01536cd425956165af9d374c2baa8', 'dcb01536cd425956165af9d374c2baa8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cec1a85ed612eb71185cd9a21492e88f', '9c47e492cb267923614e4827fa3d6d2b', '9c47e492cb267923614e4827fa3d6d2b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cf77368c4a4dbd10985c5da79e77b518', '10ad21c2ca078ac4293e56dc5c7fb4a7', '89d9d0aa5f1e35db59a2734281bf3a63', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d0e4c182676b52be7dfcde57a5affbe3', 'e5d15051f018680a81ba7a0bb2f2e0dc', 'e5d15051f018680a81ba7a0bb2f2e0dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d1ad2162fac11989ff64acb172af4ef3', '1063d02b120f84c7d2e30b098404284d', '1063d02b120f84c7d2e30b098404284d', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d366dce0b11fe0ab74f642871631ee34', '5b04394f57e2cd25a8f995ff5d4d0854', '5b04394f57e2cd25a8f995ff5d4d0854', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d3cf3b6722fdc484f3cc1d15efa1199a', '6fc9d6c656da7c741947e8bc321b721a', '699e36db6ca1ea07c9c4c2ee86053f96', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d45f5b74b82072ce831df77a11752f48', '39b53d6016e88f5784bad89ecd3fde2e', '6ed89869ac0ace491bec4526dec327f8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d70111133857cbb0c6d265616077f524', '6c7949af1810cd1183895825be4b8ce1', '6c7949af1810cd1183895825be4b8ce1', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d92bc8d88648240d48dd98b2f3e27276', '26b5eadb78914bfa390dc8b1d7d7a37c', '26b5eadb78914bfa390dc8b1d7d7a37c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d96ca46920f7602aa631b63828454fb6', '260375739a4e64aa48a56f23a6d64cdf', 'd1fff1abfe5eab959f304de38f10d43c', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('db289c4fe03885bef55e0c2840c75e4e', 'e84fd85087efe8e35b9c3adabd0b4e4b', 'fb284f06c8a73851707a9610789c8381', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e11908207e7b45e3c3c32656b99eec6a', '6a93b7b5eb793339480570defbe7fe14', '6a93b7b5eb793339480570defbe7fe14', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e307cf79fea8a0cda14e53cab4865e26', 'b041a921609bd35ceb980ef6fa484baf', '5b04394f57e2cd25a8f995ff5d4d0854', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e4e1a3873640aa71895694f93815ba68', '99acdd2bcedffac9e0205217da0357fa', '99acdd2bcedffac9e0205217da0357fa', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e56997c0d5fb4907eca22ecfd219efa3', '43c1aefb6b54e4f04bb00c87b206195e', '43c1aefb6b54e4f04bb00c87b206195e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e887c10f1576042d8a9592f4fab743dd', '3449a3ec9b382a55bcdc6dcb4f5110f5', '3fff865fe68daee74d69c5e0c7750d0e', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e9d8adf0ea4e711935fe231caf76ac09', '6e89da7570445099ccaca472a4510dbd', '6e89da7570445099ccaca472a4510dbd', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ecf5f6d78cd4563a25ce97cb8c7192da', '6ed89869ac0ace491bec4526dec327f8', '6ed89869ac0ace491bec4526dec327f8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ee48178259aba7f8a4c59e92ee904ddc', '2e386bad52cbbb36c4dd91e3129c8922', '53f358d0b6e07c53d9a118c39a4182db', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ef0dc562d7060b374dda1c4bf3d8a1f5', 'b8a06f21141c029b2833275a8f036ca3', '5178e23c337a26a1ce17a4a73f8bf97b', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f02a6a6684c5f05be928a5da670a008f', '5346f65f8d180de8781cdaaa5ffaff33', '5346f65f8d180de8781cdaaa5ffaff33', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f18a2ec6afbb50d3ea48d8f5cdc1fb85', '47fe49b03811a40755c47cd4b74ccdfa', '47fe49b03811a40755c47cd4b74ccdfa', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f2017be6214733518920a9874cfb60cd', 'af01738887787f03355fe627864bdaaf', 'a94856e326cc369c5a5ebb1e53fd76e5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f341c8c33eb2984aa9df829f96b76e56', '50ff9267f5a8a21ef2086062d6e24343', '89d9d0aa5f1e35db59a2734281bf3a63', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f643b6b77086755de5fb14709c1d312b', 'cd4336f615b9ce6d8aa55209dd4b0b55', '6d78bb9f5eff5cbfa9d0b2343ba91360', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f8a0da80c88912026ffc3028ae067ce2', '696564f7f1fc0533d4f5dd85ed05cfea', '696564f7f1fc0533d4f5dd85ed05cfea', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fbb97be6901ae2e0ea5483b9124ea2f4', 'bb59ba73a1072418faaa463ee5f472dc', 'bb59ba73a1072418faaa463ee5f472dc', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fd75d87ddbb9fe8b1f6db25352febc77', '5c921f606de397f71d2501771c38c192', 'f8ef6fc7a9f39e0f6e8a56aeda960db3', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c01d2d223f8245059257a33993d619d0', 'd5ad3fae35b79aac029551e26912a799', '993ea3ebe57ee235d2affb1173e40388', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('de6b5ad5ce19b2223dbfd927b754b70f', 'f45e4262faf28b856a6c2d55fccf62b6', 'd355695d931880b31ff86595b44f8985', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_component_rel (component_rel_id, vaccine_id, component_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c8bf07fa83734ada9d83f0cb7c6577cf', '90416702fd324f51efb285505a8cafa6', 'b6f1d7bd6d6e5fc6db728c48678967c5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_component_rel;
