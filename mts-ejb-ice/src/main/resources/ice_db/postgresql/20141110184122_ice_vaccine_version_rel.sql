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

-- // ice_vaccine_version_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_vaccine_version_rel (
        version_rel_id VARCHAR(32) primary key,
        vaccine_id VARCHAR(32) NOT NULL,
        version_id VARCHAR(32) NOT NULL,
        LAST_MOD_ID VARCHAR(32) NOT NULL,
        LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CREATE_ID VARCHAR(32) NOT NULL,
        CREATE_DATETIME timestamp NOT NULL);

alter table ice_vaccine_version_rel add constraint un_ivvrvidvid unique (vaccine_id, version_id);

alter table ice_vaccine_version_rel add constraint fk_ivvrvid2ivvid4 foreign key (vaccine_id) references ice_vaccine (vaccine_id);

alter table ice_vaccine_version_rel add constraint fk_ivvrvid2cvvid foreign key (version_id) references cds_version (version_id);

INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('121c993dd6ff8712236a8e78d3cbbee7', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('155001f3a200035089aaff6a3b98e141', '1d6f24415002fc1ed3f31282eb3db7bb', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1b268addf22a4994d9d1a76f14cacabe', '5bd4d31540e5a654ac10aafd9221ac61', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1b4cb522330af5a0c79a714f3b972806', 'f45e4262faf28b856a6c2d55fccf62b6', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('1f25776bac569bd9646cf4691ed3a1f3', '8d94a63bed4e53e27c403fb72d68f884', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('217a0f271a096d86a9102d868504879d', '99acdd2bcedffac9e0205217da0357fa', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('22477aea53fe1400ad6ff1e47b853f8a', 'f715a2ea209729e852117d0a305799e1', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('24d5efddd1a72cb396bc56d02559a0f1', '5178e23c337a26a1ce17a4a73f8bf97b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('275840c750cf272078c49fb2e3fcfedb', 'e84fd85087efe8e35b9c3adabd0b4e4b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('299202d077789a212015e83371bc8908', '6fc9d6c656da7c741947e8bc321b721a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('2d0a9a7cd8dd68bff05692b43e323ad2', '807930df85c91a3ebe707f09b9c8c609', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('33cb550691b9c622e7739e0db2d97e9c', '3449a3ec9b382a55bcdc6dcb4f5110f5', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('377056b3cb748bafb291b436c66dbeb3', '6c7949af1810cd1183895825be4b8ce1', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3cbad3bd202c6bda2b6e3cc6a6a09b4d', '8193b4c4e4552b432a72232fe733d32a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3cc34694fc2f7869d0eac219c4ae3031', 'fe19b4a3be03ac3c524cf690ce4e03f5', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3e623d8b8f864f7a58b6a43993ddf8d4', '6699c4d58521edb9c091072e976bc67d', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('3e983d424c8d5c14cf1580d0eedd1a4c', '31afa04db3aa44e0e61e76c626a3be4e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('43635bdb6c828b1e45539f29c9404899', '59e02e084e71ebc4cf13c0a748e4f47a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('43f0177744cb3a563b822c7d392656ac', '9c47e492cb267923614e4827fa3d6d2b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('4c2ab154210823337e5eb3382d0efb01', 'ed444e8a59047e86b28e89e335b21bfb', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('52ee6e4b4b4d851e32dd87474aeb0504', '1a211cb73bc3b484d1e0852b18500a1e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('53014a3a85dd6a0fccb93c4c151dc91d', '6883576bd868da1d907142ade9f7b923', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('53f341783a0f9931bdacde0d6f09cd28', '50a0e2cd1feca24fab3422cda3367c26', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5c156a1bce2a8b12f769107748f553ce', '39b53d6016e88f5784bad89ecd3fde2e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('5c5ba8d6aea2cd5c7b65a637c30b34a0', '72f4c00c91390f7a5df4a0d6fc492894', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('641c6b8e589eddc5a62a432d788101d5', 'dcb01536cd425956165af9d374c2baa8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6440af9010fe40bd9237674caef6c244', '7a2b1bee78f886391cbd446123d9b20d', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('668384a36993edfc4f520cf67958ac11', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('66f3b856e0683d636db6f2a48bdde273', 'acf262fa464441cd22704f6f70839150', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6aa9c1da87b6923fa342a4fd2d4d94bf', '43c1aefb6b54e4f04bb00c87b206195e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6b357ebb875f5895d807bdefca52b06c', '6ed89869ac0ace491bec4526dec327f8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6c1eb0b2230647917cc04873eafcba28', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6f013269147e54ca366bdcddebb38a0c', '12eda6eda040afed588639de6250700c', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('6fa04fdbafbc7cadd836b2ed5f6c8a4e', 'ce46656efb01eeb469434730796ac0aa', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('707ad65eab77dc781c2fccedd30b6e5c', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('74e5e0f6137deb67b5fd2d85c0ffe17b', '7e1471be78ff8ead9772f37fd6aac6cf', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('78fb937e72d5243568dd35661981760c', 'd5ad3fae35b79aac029551e26912a799', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('792c715e10c0153ab1c8f3e42d499f47', '19a8fab71b99131a16ebf3e42e5f9fc7', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7d8a2fbb0fdc5718c35d3a51f1e0bbf7', 'cd4336f615b9ce6d8aa55209dd4b0b55', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('7df199a1f2cfc328f2fee68794a7aa8f', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('805e460fcda48d0bb5faf8646db32d3d', 'b6ffa4b8bc0847078f9be7209b5df876', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('83385802e1a552035cc9cb8d6fd3c28a', '90416702fd324f51efb285505a8cafa6', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('95ffa402a443ebe94291cbaafddd20d0', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9633ed42cb64df84270185d4785d06d3', '6a93b7b5eb793339480570defbe7fe14', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9744a6023d241ab0ff71609fafb23303', '9430c9781e2189b05c3cd6329af76fc6', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9808d45f8ca750b4ae15e74313a7d229', 'a58a28918894428bdefab782a7f1c215', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('997432442d1bcd306678bee198bdf4e0', 'dbb90d607a346452e82c6a526f6dd727', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9cf3b69ed461fc379e04970051ae5bd1', '6e89da7570445099ccaca472a4510dbd', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9e5691b552e70620f4a71f087f828bc9', '2880c23dd8ad8d9b3bba5028af2c4e4e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('9fcaf5d26930fb4082ed840a7a1cc14d', 'a4900f2c8af205febdade2da44b7aa71', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a0154549bfdc95dd398df4ed4ae34c31', 'af01738887787f03355fe627864bdaaf', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a2ea41a41131b4e110291b1435edff38', '4498350f8bdd4e60b0314fcb3c22e827', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a530b6772b6c2926100ac5fc39f9e7ca', '1b3b5e305c967a32e20a9a447bad47e5', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('a63d609825058b05c4029ee05f8f9bc9', '6ccad31d85f1a1bf991e46c3dc1e0e09', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ab6942afcef2b6c1b5c8e0112f000b87', 'dedba4495cfa131c96b4dc0b43138d83', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b0b9311c2eb6c09028abb3b13113a8a3', '26b5eadb78914bfa390dc8b1d7d7a37c', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b31392c72a01f3ba886e54a53c2e61b5', 'b8a06f21141c029b2833275a8f036ca3', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b68546dff797cefd5acabdff7279e519', '37e5fa116314b6dc5b698c08afea530c', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('b7dc8bae72763a926589a18c53c3643c', 'b041a921609bd35ceb980ef6fa484baf', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('bf46b63afc0717933be3684797cf838d', '47fe49b03811a40755c47cd4b74ccdfa', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c1181d212bb268f82e6163fc86ac3d19', 'e5d15051f018680a81ba7a0bb2f2e0dc', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c3dabb353dd723867e4c95fee9a1729e', 'f2e213c64816a94de7c4fbb58ffb6b70', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c3ecd69c203e8c0154e10f6b622266a0', '1a72a3652ad621ef6c142028bc827e1b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c3f8b74d3251a94248ebe2e033fa3ca5', 'bb59ba73a1072418faaa463ee5f472dc', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c40e67bd44e2e778f47e5c9a933dfa56', '5346f65f8d180de8781cdaaa5ffaff33', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('c947811ce27eed774d41d02797dc12c7', 'f9e3be777817ba68958c4fa7a3d89b90', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('cb81556f14020fcc14d85616aeff1467', 'ed635e962167812224f6a70a6c7705b6', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('d4a15d285d88427272bcd0591b88e7ca', '696564f7f1fc0533d4f5dd85ed05cfea', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('de66890053c137b628a1ccad48871005', '16b7163c2814a310a9d392c23d76e143', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e0ae884280dc70895188989fb9ae68d3', '886903982dada4949ab8488698671819', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e7f2a1d7e5356c0977152e685691fd09', '940719422398be2efc9ae08751bf417b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e883c773607404d985c9d9da35f87f23', '35282fcad02f488f22f57a746c944421', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('e8fbb9eef942c3797e52405a5a67a53a', '1063d02b120f84c7d2e30b098404284d', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ebec7afe55e56fa59dd8638c7f1dc959', '115011c96e54f5e1cc044f76f69e77b2', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('eea7a5a9e57df9dc7f72038764c04b82', '2e386bad52cbbb36c4dd91e3129c8922', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ef27a07c38d6ea468fc157441b7179b3', '5b04394f57e2cd25a8f995ff5d4d0854', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f41fcf7d543d3dd9307838f373df19dc', '50ff9267f5a8a21ef2086062d6e24343', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f50f380f414d7c294eb414e79eaa28f0', '33a5b231185ea648ac237f1989da814a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f5c7184fe8d609f0d42fbc2a6973d6ff', '7b0195669cf49dbbacac7d82f3c70042', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f94b88dc16fbe693aa495703199761ed', '260375739a4e64aa48a56f23a6d64cdf', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('f9c3733f652e9376f5a4d6af6eb8f1c5', '8e3f621a6368e6d294211b84b09da1b8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fa83bc06c46463ca51235735d59488ff', '2a724fd66373612cfcaa92f7341e246e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fd6bcc7ea31af3ff955d3e8a204c1dae', '5c921f606de397f71d2501771c38c192', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fdc5c16294f476786b3ae6ce8a91c044', '7d3d80b067c1fa9390eb3b50f20075d9', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('fe43e627461cd70e35a0e55485bde915', '46eb9a9d4a483694c03341125900c46b', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_vaccine_version_rel (version_rel_id, vaccine_id, version_id, last_mod_id, last_mod_datetime, create_id, create_datetime) 
    VALUES ('ffe7f02329f00c8bb5bcc3bfd32ec482', '73e1989170ca5c43a4767dfbf11f9c6a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_vaccine_version_rel;
