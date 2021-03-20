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

-- // ice_series_dose_vaccine_rel
-- Migration SQL that makes the change goes here.

create table ice_series_dose_vaccine_rel (
            dose_vaccine_rel_id varchar(32) primary key,
            dose_id varchar(32),
            vaccine_id varchar(32),
            preferred CHAR(1) default 'N',
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_series_dose_vaccine_rel add constraint fk_isdvrdid2isddid foreign key (dose_id) references ice_series_dose (dose_id);

alter table ice_series_dose_vaccine_rel add constraint fk_isdvrvid2cvvid foreign key (vaccine_id) references ice_vaccine (vaccine_id);

alter table ice_series_dose_vaccine_rel add constraint un_isdvr_dis_vid unique (dose_id, vaccine_id);

INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10b3aa96417f99911a1de6904037f4e9', 'f67f25b2fbc8f459196f342dd6ac2f82', '5b04394f57e2cd25a8f995ff5d4d0854', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('117a0ed8af5759874d35b2fb566bf41e', '2298e495edd100771c312d8ee2b26da2', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('11f24d4b5806cf379d2babcd234f20c0', '95dd21057d7cf2de33cbfbab9f4bc291', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('12407543f9ad30bd48778c8391bab0c2', 'e1cf0e5f173de00791523707da5de575', '6fc9d6c656da7c741947e8bc321b721a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1290231d7772b6f329b153bce35cb9e9', '2c09e28a42318b546248f4993059034f', '33a5b231185ea648ac237f1989da814a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('15952fc95e07074cce5c97fb322969db', '2c09e28a42318b546248f4993059034f', 'acf262fa464441cd22704f6f70839150', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1887765e28bdb50b83cb3de66f56837d', 'a3f54f345abf037f8cb5030b5232d208', '807930df85c91a3ebe707f09b9c8c609', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('18a8237a82c15aa84f34776f8146c04c', '45707487b1eddbc74e6273da4abc90b2', '12eda6eda040afed588639de6250700c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('18df6586e8cd436edc25521c37bcf2da', '2b309c5885f7463f3c5be0ce48e0e31b', '26b5eadb78914bfa390dc8b1d7d7a37c', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('19377e636fd5c33e47165ecd473a1aeb', '2298e495edd100771c312d8ee2b26da2', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('194a69c17441328b6191599b10f3a0f1', 'cc5d8cc7e73fd3073833fc435511116b', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1984628de4828c1cf16bb42a3c1f0209', '67ff50c6cf948174455923c069f2ef44', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1a1e74a4a73c0015c271468960225f20', '95dd21057d7cf2de33cbfbab9f4bc291', '2e386bad52cbbb36c4dd91e3129c8922', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1af05aa839255ee5d948ad002e3e9273', 'd852403015f4db05f45b8a4573bef0bc', '1b3b5e305c967a32e20a9a447bad47e5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b20ff1def6091bad727a4cc18524f49', 'e1cf0e5f173de00791523707da5de575', '807930df85c91a3ebe707f09b9c8c609', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1bc8dfd28f6afc0a49ff8f61f817b4ae', '7b67f7e881f3007fb987174bcaddb725', 'ed635e962167812224f6a70a6c7705b6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1c002429ea6c656741a1f1b240451b3e', '84bf8d1f8261b4aee6796702fe16376f', '940719422398be2efc9ae08751bf417b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1ca5b4fd6e7d19536ba8d184542597b6', '1729cafa915bffbc9714b28be0bf68dc', '37e5fa116314b6dc5b698c08afea530c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('20b589232a20df2b504594ce23454c4b', '2298e495edd100771c312d8ee2b26da2', '59e02e084e71ebc4cf13c0a748e4f47a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('21bfe4e9e6a0f80a49403539633e0ea3', '84bf8d1f8261b4aee6796702fe16376f', '7d3d80b067c1fa9390eb3b50f20075d9', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('227905f55c7565f54a920f6792633956', '67ff50c6cf948174455923c069f2ef44', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('22c4f2b6147203f6e5eab1a1be7afbc6', '67f7bc27ab85e67aea2281e834f2266c', 'e84fd85087efe8e35b9c3adabd0b4e4b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2382135b23d9d391aaf26a01f010bc34', '45707487b1eddbc74e6273da4abc90b2', '72f4c00c91390f7a5df4a0d6fc492894', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2401a4e21dc82de7e593ffe47aa89f4a', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2405df005fb2d446f6dd3d1e379ffb0b', '36caf2cbb14e18b7db77a3d8ad0407fb', '1b3b5e305c967a32e20a9a447bad47e5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('24e8baeec404860252465f191b957085', 'a6602cd96b75a7eac79e417b1fea3916', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2550e7d1c7dcea66a4e575accd984fd9', '3b90437a1560e593dd39c8d5117530ca', '1a72a3652ad621ef6c142028bc827e1b', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('25ad88a06a270e5915aa6ba5142481f2', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '6ccad31d85f1a1bf991e46c3dc1e0e09', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('25d8f1cf49fc0e4f3886a197750334bb', '33ed4681b052cf22fdd726f7ad0b2ed1', '7d3d80b067c1fa9390eb3b50f20075d9', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('25dac249632045ac985355066ebf46fb', '33ed4681b052cf22fdd726f7ad0b2ed1', 'f2e213c64816a94de7c4fbb58ffb6b70', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('262603affcc71dd451b21c133fe314a2', '2298e495edd100771c312d8ee2b26da2', '7a2b1bee78f886391cbd446123d9b20d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('284ce2ef963befdf79d38fffdd45916c', '67ff50c6cf948174455923c069f2ef44', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('28c7144d8d00c3ab30ad2ef169b41ad2', 'e9d2a14e4cf25cf37f705aada62b89b4', '1063d02b120f84c7d2e30b098404284d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('293deb78a6a5ec453637a7917988e2d4', '33ed4681b052cf22fdd726f7ad0b2ed1', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2aa6647aef542f2e616548e3632dd42e', 'f15c59b825a88ad1b3b80281d6f899ad', '59e02e084e71ebc4cf13c0a748e4f47a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ab4219db47bd59926fa17cc769d4aa4', '4580b973f0ccf9473a93b4fd6b998df2', '5c921f606de397f71d2501771c38c192', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2b2fc1ec42d588987e2abeb43622c252', 'a3f54f345abf037f8cb5030b5232d208', '2880c23dd8ad8d9b3bba5028af2c4e4e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2c2fe6981e632a1ebeebb4061d87d599', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ca193faa302342729a6695f2ec4765c', 'a3f54f345abf037f8cb5030b5232d208', '72f4c00c91390f7a5df4a0d6fc492894', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ccbb30e6617b0a0e0737ee2ac2cc7d7', '1729cafa915bffbc9714b28be0bf68dc', '696564f7f1fc0533d4f5dd85ed05cfea', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2f17f70eeef5c7f5125570e741cf1db9', 'a3f54f345abf037f8cb5030b5232d208', '6fc9d6c656da7c741947e8bc321b721a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2f79c9f4d13a1e9b3b75eed5c07d187b', '3128c56d731dacbac17b7b3fe0c9e204', '43c1aefb6b54e4f04bb00c87b206195e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('304f9ecf6d2e04c86f878c6acfca90cd', '1ce2c52814c907498619608721de6577', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('305337b1f3ec8a4a1b4ce9b42f43d91b', 'f15c59b825a88ad1b3b80281d6f899ad', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('31ed2b9b44a21f3d14937ac1690d8863', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3561c5ac3c7efe5ae55aa88a4f37b321', 'd2b047eb648fe59abe42fd6ce30193e2', '12eda6eda040afed588639de6250700c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('37be4e061620359984ccce5e8be02640', '45707487b1eddbc74e6273da4abc90b2', '3449a3ec9b382a55bcdc6dcb4f5110f5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3822a8feecded82797209e797b922b8b', 'e9d2a14e4cf25cf37f705aada62b89b4', '46eb9a9d4a483694c03341125900c46b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3887e65fce0d078277ab963a6c59c6e7', '242481669d377685e7130fb81bbde1ee', '2a724fd66373612cfcaa92f7341e246e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('38d61761ad3b9dad01e2536f879ffd3b', 'bcbddb2bce1076eb6869328f6bc6e187', '50ff9267f5a8a21ef2086062d6e24343', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('38deb0a0939d736ed613f4e3d147c111', '3b90437a1560e593dd39c8d5117530ca', 'ed444e8a59047e86b28e89e335b21bfb', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c79812958b3aebab5775c543116f2c6', 'd2670c1f85a37865f9f1f6be1cb43eda', '260375739a4e64aa48a56f23a6d64cdf', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c7be2f679a907d46261d5528208816b', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '39b53d6016e88f5784bad89ecd3fde2e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3cafeb8febfd34ddb7d4f15defd9b2eb', '3b45b464bdfb9cd72c170d0b149d1d25', '2e386bad52cbbb36c4dd91e3129c8922', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3d233a3b9982285c8c278fb42c630031', 'e1cf0e5f173de00791523707da5de575', '12eda6eda040afed588639de6250700c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3e894cc46ba71f0a53bbad2d985f4c01', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', 'a4900f2c8af205febdade2da44b7aa71', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3f658e83ac6f136984f551f081c98f7e', 'a3f54f345abf037f8cb5030b5232d208', '12eda6eda040afed588639de6250700c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3f6e2c6e83f9368d7de7107285d3444d', 'e1cf0e5f173de00791523707da5de575', '6a93b7b5eb793339480570defbe7fe14', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4049ae6f742e0e83d5f510c6ba368b59', '95dd21057d7cf2de33cbfbab9f4bc291', 'ed635e962167812224f6a70a6c7705b6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40f675ad12429eed9e7ceb73aac546db', 'f15c59b825a88ad1b3b80281d6f899ad', 'a4900f2c8af205febdade2da44b7aa71', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40f6b631ff1d8a80fd28bb2477dcae78', 'dee2b6f7ec12d3872b797bb08d8d5d0d', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('41073da5a5ad3a19c4edb1f52a956dd6', '45707487b1eddbc74e6273da4abc90b2', '5bd4d31540e5a654ac10aafd9221ac61', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('41a99ba89f931189a805bf3530b64cbd', '1729cafa915bffbc9714b28be0bf68dc', '2880c23dd8ad8d9b3bba5028af2c4e4e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('41f8b2fff8f6f36c6fc35289ad9323d1', '1ce2c52814c907498619608721de6577', '33a5b231185ea648ac237f1989da814a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('438b2cd3f8ea6d814546b05c9f7461ec', '5efc533e3a703123e221b2a1407ee35c', '2e386bad52cbbb36c4dd91e3129c8922', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('450158d5e29efca6c4ccded647aa606d', 'a3f54f345abf037f8cb5030b5232d208', '696564f7f1fc0533d4f5dd85ed05cfea', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('467e69e35e9ee4c5e8c1e315d9ee5214', 'e31c630541081f746fce9c905081bfa9', 'acf262fa464441cd22704f6f70839150', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4729feb444faf2ab39818082199a7e4f', '67ff50c6cf948174455923c069f2ef44', '59e02e084e71ebc4cf13c0a748e4f47a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4770d1e5b1ac36622b672852af939aa0', '45707487b1eddbc74e6273da4abc90b2', '9c47e492cb267923614e4827fa3d6d2b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('478327f938bbb89f34f3c9ce1d161134', '45707487b1eddbc74e6273da4abc90b2', '2880c23dd8ad8d9b3bba5028af2c4e4e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('47db6144de8980f28cb31b6c87dcdf2b', 'd2b047eb648fe59abe42fd6ce30193e2', '5346f65f8d180de8781cdaaa5ffaff33', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4a0a4d3a3cc08f70d9132a0e38cf614f', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '5178e23c337a26a1ce17a4a73f8bf97b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4a1e326c8d9d3934a6aae383f12681a2', 'fc054f2b9d57869b399e140190012042', '43c1aefb6b54e4f04bb00c87b206195e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4a31cc8cb081edf0433b2c06d50c5cb7', 'f15c59b825a88ad1b3b80281d6f899ad', '5178e23c337a26a1ce17a4a73f8bf97b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4a52828e9346da9092b4b134b9dab7b2', 'd2b047eb648fe59abe42fd6ce30193e2', '807930df85c91a3ebe707f09b9c8c609', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4aaa4f737ebf58e51e3757ec74baf4c5', '2b309c5885f7463f3c5be0ce48e0e31b', '2a724fd66373612cfcaa92f7341e246e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4ac1332e42708c97efb8c26eb41e124a', 'd2b047eb648fe59abe42fd6ce30193e2', '6a93b7b5eb793339480570defbe7fe14', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4b62212b3c8b9f6d19b83841f844c1c6', '1729cafa915bffbc9714b28be0bf68dc', '807930df85c91a3ebe707f09b9c8c609', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4ca2aeb3783467a395d4ddc1a1025169', '2298e495edd100771c312d8ee2b26da2', '39b53d6016e88f5784bad89ecd3fde2e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4efe1c9feeae14f29571194f6b85bd3d', '8da755d4b33f996ae0304643d3515b85', 'ce46656efb01eeb469434730796ac0aa', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5024e836df176ecb69f8a1057cd8255a', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('50f455d0119c4116cdf280be39841a32', 'a3f54f345abf037f8cb5030b5232d208', '9c47e492cb267923614e4827fa3d6d2b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5352234012188f1bee0ca64644ee8587', 'e31c630541081f746fce9c905081bfa9', 'dedba4495cfa131c96b4dc0b43138d83', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('54f887f416f8f31c3e6463f5d7ca5db0', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '6ccad31d85f1a1bf991e46c3dc1e0e09', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5650bb52bffb4ba953569db250a6d5f0', '242481669d377685e7130fb81bbde1ee', '26b5eadb78914bfa390dc8b1d7d7a37c', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('56566f8d71bec5e51f0166bac3c2f3ce', '3b90437a1560e593dd39c8d5117530ca', 'b041a921609bd35ceb980ef6fa484baf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('56ae10e4275a357daf7050141cc21614', '1f9e982ec81f1bb2a35821d0e239dbf9', '260375739a4e64aa48a56f23a6d64cdf', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('56b480dc3d02e6b3320168755b1b0a18', '84bf8d1f8261b4aee6796702fe16376f', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('56ec7ab0cd80dcd1fcfab2f40038fd53', '95dd21057d7cf2de33cbfbab9f4bc291', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5761ce16a01d8da6cec09462b6360cf1', '4580b973f0ccf9473a93b4fd6b998df2', 'acf262fa464441cd22704f6f70839150', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5848d91b91d99453d2a0f97116a3ec28', '33ed4681b052cf22fdd726f7ad0b2ed1', '940719422398be2efc9ae08751bf417b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('58695d0afc532b56170dcd45eb91c251', '36caf2cbb14e18b7db77a3d8ad0407fb', '6c7949af1810cd1183895825be4b8ce1', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5885d99c4f068b619c229b7d2dd623d4', '2298e495edd100771c312d8ee2b26da2', 'a4900f2c8af205febdade2da44b7aa71', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('588ae5ec197b4db944092c688e45c890', 'e1cf0e5f173de00791523707da5de575', '37e5fa116314b6dc5b698c08afea530c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('59f06be6a10b7ef6723b1fb76a0cacae', '3b90437a1560e593dd39c8d5117530ca', '73e1989170ca5c43a4767dfbf11f9c6a', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5a1253164280f0f026ffe7921776459e', '3b90437a1560e593dd39c8d5117530ca', '7b0195669cf49dbbacac7d82f3c70042', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5a937856fb02f094c2a4fa4d31a1b062', 'dee2b6f7ec12d3872b797bb08d8d5d0d', 'b8a06f21141c029b2833275a8f036ca3', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5b4e2a4a2984b809f4ca33c71ceb40f0', 'b1db73da41f9cfcdc9007e2d2ca2571b', '6e89da7570445099ccaca472a4510dbd', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5cf76ccd7cf148b71ec7ecb3d7c69716', '67ff50c6cf948174455923c069f2ef44', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5d504a172e3295f7a3f867879e2e2b52', '2c09e28a42318b546248f4993059034f', 'dedba4495cfa131c96b4dc0b43138d83', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5d5bf5af1c1d4e0cdea6ba7ad45e5a87', '92afcb561533902ac44a39350b82ec2b', 'f715a2ea209729e852117d0a305799e1', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5d5e3d3c97b64385b9ff8cf4e7f10eb6', '45707487b1eddbc74e6273da4abc90b2', '6a93b7b5eb793339480570defbe7fe14', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5d87a0a5e00677aea86a8cce50eb46c5', 'e1cf0e5f173de00791523707da5de575', '5bd4d31540e5a654ac10aafd9221ac61', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5de3040a371b1a0b9d97af3980fe6c02', '1ce2c52814c907498619608721de6577', 'dedba4495cfa131c96b4dc0b43138d83', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5df46d6359d22abcece28778d1ade1c5', 'f15c59b825a88ad1b3b80281d6f899ad', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5ee75cf394a10dc3f7568d6e4b25eb68', 'fa56707a1620dc32dd6caf08dfc4d0f7', '33a5b231185ea648ac237f1989da814a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5f07ed00940aa712ca5fafa71cd293cb', 'b1db73da41f9cfcdc9007e2d2ca2571b', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5f7ec6a7f59cbfd5d80a20c3189ca2fa', 'd2b047eb648fe59abe42fd6ce30193e2', '2880c23dd8ad8d9b3bba5028af2c4e4e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6116452aff8410f3b7b41172de7626b4', 'bbe75cde3697024fdadeaa5bdee616f0', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('611764424f59cb905af215e5450ef867', '4580b973f0ccf9473a93b4fd6b998df2', 'dedba4495cfa131c96b4dc0b43138d83', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('61b93b1e8260b3319b30b805f331539d', '67f7bc27ab85e67aea2281e834f2266c', '6e89da7570445099ccaca472a4510dbd', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('61ee3e10a9544cdb7d81df16c5ad342c', '1ce2c52814c907498619608721de6577', '35282fcad02f488f22f57a746c944421', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('620cd912443d8bb374b20e634bad8289', 'bcbddb2bce1076eb6869328f6bc6e187', 'ed635e962167812224f6a70a6c7705b6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6314ecab2cb49c4e91f8b5b4d5fb008d', '3128c56d731dacbac17b7b3fe0c9e204', '2a724fd66373612cfcaa92f7341e246e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('638324604ce5d13ccc28a3605e374a33', '1ce2c52814c907498619608721de6577', '5c921f606de397f71d2501771c38c192', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6698ce79e3f518a5e1c44e72b485badd', 'a3f54f345abf037f8cb5030b5232d208', 'cd4336f615b9ce6d8aa55209dd4b0b55', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('688508201bdb0919ee20c1453f8d5b21', '3b90437a1560e593dd39c8d5117530ca', '16b7163c2814a310a9d392c23d76e143', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('68b31d351be14b6751ba1b58b17b5bc9', 'ebeec39ac8bd3796802f02fd95003c01', '1b3b5e305c967a32e20a9a447bad47e5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('68ba7e6861a0bc93a890c0cdae557682', '8968a05a1bdeceb0a406f88137c9d547', '115011c96e54f5e1cc044f76f69e77b2', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('693d9ecd4dab8ffa61be30f037200b23', '95dd21057d7cf2de33cbfbab9f4bc291', '886903982dada4949ab8488698671819', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6aa1e729cf2be7ee7f07ac81dae5d439', 'a3f54f345abf037f8cb5030b5232d208', '3449a3ec9b382a55bcdc6dcb4f5110f5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6b85a01cd4e8b88f7b6f8fc3a7bc7281', 'f67f25b2fbc8f459196f342dd6ac2f82', 'b041a921609bd35ceb980ef6fa484baf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d4fc270927c0973d68f0d0edb3f106b', '3602c7be9bdf19f861af59bba5d646d4', 'f2e213c64816a94de7c4fbb58ffb6b70', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6dbc7e1c663350912f892f3b2204866d', '92afcb561533902ac44a39350b82ec2b', 'ed444e8a59047e86b28e89e335b21bfb', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6e2ecc9f17d7b5d82dd37933a3b73dc8', 'bcbddb2bce1076eb6869328f6bc6e187', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6e907bbb7538e286af0bcb7ae077e804', '92afcb561533902ac44a39350b82ec2b', 'b041a921609bd35ceb980ef6fa484baf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6f7e9cc53b1f01375e5b3c41289ed856', '92afcb561533902ac44a39350b82ec2b', '7b0195669cf49dbbacac7d82f3c70042', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6fe61b9724d1307f9dd9a782f32376cc', 'fa56707a1620dc32dd6caf08dfc4d0f7', '5c921f606de397f71d2501771c38c192', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7229ddd89604ba36d4cb830b9f393362', '5efc533e3a703123e221b2a1407ee35c', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('72660e71423b0bbf9d587f3312cb7fab', 'e1cf0e5f173de00791523707da5de575', '2880c23dd8ad8d9b3bba5028af2c4e4e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('72cb06c7a6029fdebbf71402785fc4a6', '92afcb561533902ac44a39350b82ec2b', '73e1989170ca5c43a4767dfbf11f9c6a', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('73d8eb11f0e577151dd330d339dc36bf', '1729cafa915bffbc9714b28be0bf68dc', 'cd4336f615b9ce6d8aa55209dd4b0b55', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7413fb3629f5867fe86f2c2a51da749c', 'ebeec39ac8bd3796802f02fd95003c01', '6c7949af1810cd1183895825be4b8ce1', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('74fad13a97c0d60bfd26f96fa2f0a626', 'd2b047eb648fe59abe42fd6ce30193e2', '72f4c00c91390f7a5df4a0d6fc492894', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('75c88da12472684b5503cabf8f77d934', 'fc054f2b9d57869b399e140190012042', '26b5eadb78914bfa390dc8b1d7d7a37c', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7666915dca386d2164cbcb9b192d1391', '284cbb2891079ca9bc61b343b8d33492', '46eb9a9d4a483694c03341125900c46b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('76cbeb8ee158b95f18720d9ab8231b56', '8da755d4b33f996ae0304643d3515b85', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('773aa8b314337c66b4f3514d19384388', 'a3f54f345abf037f8cb5030b5232d208', '8d94a63bed4e53e27c403fb72d68f884', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('775487eb1586f20fad85ac3e2effb8c7', '3602c7be9bdf19f861af59bba5d646d4', '940719422398be2efc9ae08751bf417b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('782a0825315ee59889e01ce57dde005b', 'f15c59b825a88ad1b3b80281d6f899ad', '39b53d6016e88f5784bad89ecd3fde2e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('78cc2906eeefe1601cd382617547d518', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '59e02e084e71ebc4cf13c0a748e4f47a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('78d9a32e7ce3eb056f6ce188de02b1fc', '1729cafa915bffbc9714b28be0bf68dc', '5346f65f8d180de8781cdaaa5ffaff33', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('78f4e7be6ebb208e7e1b3c2327716152', '5efc533e3a703123e221b2a1407ee35c', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('79116c3744ed28b09b09c5d0af46535e', 'd852403015f4db05f45b8a4573bef0bc', '6c7949af1810cd1183895825be4b8ce1', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('791469e63005027ede4189c5770f4216', 'e31c630541081f746fce9c905081bfa9', '5c921f606de397f71d2501771c38c192', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('792c80a5b54057cf048b1129d280b991', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', 'b8a06f21141c029b2833275a8f036ca3', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('79dc52f3f1fec0a0fd8ad610411fdec1', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7aa38edc6403880023e9ac2e71b3bc43', '1729cafa915bffbc9714b28be0bf68dc', '5bd4d31540e5a654ac10aafd9221ac61', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b8ae350e7fefbedca3370abc6870575', 'd2b047eb648fe59abe42fd6ce30193e2', '696564f7f1fc0533d4f5dd85ed05cfea', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7c11039647d76c2e6546d610701b3576', '3602c7be9bdf19f861af59bba5d646d4', '7d3d80b067c1fa9390eb3b50f20075d9', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7e9dd9461842765ce5a7c04f15488b84', 'e1cf0e5f173de00791523707da5de575', 'cd4336f615b9ce6d8aa55209dd4b0b55', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7ee132cc2aade0ebc821527da8cd240a', 'd2670c1f85a37865f9f1f6be1cb43eda', '31afa04db3aa44e0e61e76c626a3be4e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8040b5d4c38bf32d892a49c8bfd8584e', '6b3bf3514986b89b2774b9bb33039a79', '5b04394f57e2cd25a8f995ff5d4d0854', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('80864edf1edb5e7189c8499e257c27a0', '95dd21057d7cf2de33cbfbab9f4bc291', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('81e5490c3d6d1fff3f844e1909939b87', '2298e495edd100771c312d8ee2b26da2', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('831bf5d18f4879bcb4301e4f8e0e4baa', 'fa56707a1620dc32dd6caf08dfc4d0f7', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8375b556545f2517fc40e7de925c4877', '7b67f7e881f3007fb987174bcaddb725', '19a8fab71b99131a16ebf3e42e5f9fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('846c15d114be079916c3ad9d6822d913', '45707487b1eddbc74e6273da4abc90b2', '807930df85c91a3ebe707f09b9c8c609', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('878b9f2072faba4ec9a16396cb157b7a', 'd2b047eb648fe59abe42fd6ce30193e2', '3449a3ec9b382a55bcdc6dcb4f5110f5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87a8e0ca1c7aa4f6254ef98bad969637', '7b67f7e881f3007fb987174bcaddb725', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('88b5afc97be2d845ef8d6b55113a2ef4', '67f7bc27ab85e67aea2281e834f2266c', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('88f489a3d9c87080f1b3a8e5f09983ab', 'd852403015f4db05f45b8a4573bef0bc', '7e1471be78ff8ead9772f37fd6aac6cf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('897aeaefbcefd85b12d43a936cf0b972', 'bcbddb2bce1076eb6869328f6bc6e187', '886903982dada4949ab8488698671819', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8cc65d50cbc177915a3cb757a97b3f2f', 'bbe75cde3697024fdadeaa5bdee616f0', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8d5195b47938caf45a2ac2bb14d35d35', 'b1db73da41f9cfcdc9007e2d2ca2571b', 'e84fd85087efe8e35b9c3adabd0b4e4b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8db983c86b2cac2aed69ea4e6eb0ecff', '3602c7be9bdf19f861af59bba5d646d4', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8ecdefa21efa2c1ea2285690ddef252a', '1729cafa915bffbc9714b28be0bf68dc', '9c47e492cb267923614e4827fa3d6d2b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('905c1ba67a3ff34782ec5f19b96d9a95', 'c39586cc9b29a83fb3c45dfe3553282e', '1063d02b120f84c7d2e30b098404284d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('90a4f9df56a81de7c600c317bf82747a', '95dd21057d7cf2de33cbfbab9f4bc291', 'a58a28918894428bdefab782a7f1c215', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('90d7861aa6e5a0366ce3c9570298e7a6', '36caf2cbb14e18b7db77a3d8ad0407fb', '7e1471be78ff8ead9772f37fd6aac6cf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('90fa8aa19250355fc2a9c31870767c8f', '7b67f7e881f3007fb987174bcaddb725', '50ff9267f5a8a21ef2086062d6e24343', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('90ff530b2409f9edc56b5ae243dbd409', '7b67f7e881f3007fb987174bcaddb725', '886903982dada4949ab8488698671819', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('920f0a9511cc8f905a62ae1e16ecd186', 'd2670c1f85a37865f9f1f6be1cb43eda', 'e5d15051f018680a81ba7a0bb2f2e0dc', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9320783827f8f3a465234abc1a883546', 'f15c59b825a88ad1b3b80281d6f899ad', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('94be9913d8c19fdd4f7a2a2c73187dbb', '3b45b464bdfb9cd72c170d0b149d1d25', 'a58a28918894428bdefab782a7f1c215', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('95ec4a6e7eb16c08ff7039764cd0eddf', '3128c56d731dacbac17b7b3fe0c9e204', '26b5eadb78914bfa390dc8b1d7d7a37c', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('965b56aedef1d4e15b5af4bf1373467d', '2298e495edd100771c312d8ee2b26da2', '5178e23c337a26a1ce17a4a73f8bf97b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('98c6ebcfa4101e969e2342f11abd7c76', '2298e495edd100771c312d8ee2b26da2', 'b8a06f21141c029b2833275a8f036ca3', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9a575b68f38ee4f992ac303317ddf78a', '2c09e28a42318b546248f4993059034f', '5c921f606de397f71d2501771c38c192', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9c66d8b1d599c2eaef1b7a2fb478d309', 'cc5d8cc7e73fd3073833fc435511116b', '19a8fab71b99131a16ebf3e42e5f9fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9caf383d87a0d532667ea6ff62d0caf5', 'e1cf0e5f173de00791523707da5de575', '696564f7f1fc0533d4f5dd85ed05cfea', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9d80ebc7eaf92137b1fbc08ca001f202', 'a3f54f345abf037f8cb5030b5232d208', '5346f65f8d180de8781cdaaa5ffaff33', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9fade96d6b8e144ab4f68bc41003e295', '45707487b1eddbc74e6273da4abc90b2', '696564f7f1fc0533d4f5dd85ed05cfea', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9fb4599112bc2d2996792d03ababd0f5', '7ccf7d6f5f0607dc64dc6ba28235cd28', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a0e429bf87d009112e134adda16cf4ca', '284cbb2891079ca9bc61b343b8d33492', '1063d02b120f84c7d2e30b098404284d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a0ea565739ab82b959f489976dcb2136', '1729cafa915bffbc9714b28be0bf68dc', '3449a3ec9b382a55bcdc6dcb4f5110f5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a0feaab27bdb6509e38e0f01ec44bacf', '5efc533e3a703123e221b2a1407ee35c', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a15c553c3823b0129f3f440612ca4b9d', 'd2670c1f85a37865f9f1f6be1cb43eda', '6699c4d58521edb9c091072e976bc67d', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a665a91fd38cf57cf1aef584126e846c', '67ff50c6cf948174455923c069f2ef44', '6ccad31d85f1a1bf991e46c3dc1e0e09', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a6f65b93b7b91d48d42c561ded290441', '67ff50c6cf948174455923c069f2ef44', 'b8a06f21141c029b2833275a8f036ca3', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a8147d726c23ac0496b36c34d0b5a356', 'cc5d8cc7e73fd3073833fc435511116b', '2e386bad52cbbb36c4dd91e3129c8922', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a86a8f61f84501de68058c505972b72b', '3b45b464bdfb9cd72c170d0b149d1d25', '886903982dada4949ab8488698671819', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a92363b73671eb5fe53935319380ae62', '95dd21057d7cf2de33cbfbab9f4bc291', '19a8fab71b99131a16ebf3e42e5f9fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aa11bf9e828e14aeb8296b7758454a72', '4580b973f0ccf9473a93b4fd6b998df2', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aa7ac0531c1efc7f6be6715953d16b57', '45707487b1eddbc74e6273da4abc90b2', '37e5fa116314b6dc5b698c08afea530c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aaaedca45868312efc7f42d152537ecf', 'e31c630541081f746fce9c905081bfa9', '33a5b231185ea648ac237f1989da814a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aaf9263cfe6fa7e1eaa73070aaeb6692', 'd852403015f4db05f45b8a4573bef0bc', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ab001d6589e3b5e427a8fc66a1931a5b', '3b45b464bdfb9cd72c170d0b149d1d25', '50ff9267f5a8a21ef2086062d6e24343', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ab5ffd3c195c22978078e99b97b7a5f3', 'c39586cc9b29a83fb3c45dfe3553282e', '46eb9a9d4a483694c03341125900c46b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('abb7f4484b51c7d84255f5047b5be091', '3b45b464bdfb9cd72c170d0b149d1d25', 'ed635e962167812224f6a70a6c7705b6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ac599b8576e348598a185dd147e71866', 'c39586cc9b29a83fb3c45dfe3553282e', '1d6f24415002fc1ed3f31282eb3db7bb', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ae15f753e19515727c93572546398359', '67f7bc27ab85e67aea2281e834f2266c', '115011c96e54f5e1cc044f76f69e77b2', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aff13fd57137f7ad5481a5f1a3f06be7', 'cc5d8cc7e73fd3073833fc435511116b', 'ed635e962167812224f6a70a6c7705b6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b023aff2beea1f1fa934109e95c8ab94', '7b67f7e881f3007fb987174bcaddb725', 'a58a28918894428bdefab782a7f1c215', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b1bf1a54fd83899168d41f3676838a43', 'd2670c1f85a37865f9f1f6be1cb43eda', 'dcb01536cd425956165af9d374c2baa8', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b1c09a6feb04b7dfe6669c9b45ebf31f', '45707487b1eddbc74e6273da4abc90b2', 'cd4336f615b9ce6d8aa55209dd4b0b55', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b310296012e9c89f15c944d69bb452b5', '45707487b1eddbc74e6273da4abc90b2', '5346f65f8d180de8781cdaaa5ffaff33', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b5226dd2d5432ed186a2a7f7c944eee7', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '59e02e084e71ebc4cf13c0a748e4f47a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b799d6715d1ab9be184b41f3a3888dbd', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b81ab4c728f66234d2cc3ddf5ad92b33', 'b1db73da41f9cfcdc9007e2d2ca2571b', '115011c96e54f5e1cc044f76f69e77b2', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b872e3ebd5f0f7cd889a03a7f7816eaf', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '5178e23c337a26a1ce17a4a73f8bf97b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ba86c34f8b5fc4d8c916f9b927f19746', '5efc533e3a703123e221b2a1407ee35c', 'a58a28918894428bdefab782a7f1c215', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bb97de036da3cbea5ba0ea7f4430f668', 'fa56707a1620dc32dd6caf08dfc4d0f7', '35282fcad02f488f22f57a746c944421', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bf61adc82c7b038cff02b39d6b0b9de0', 'd2b047eb648fe59abe42fd6ce30193e2', '5bd4d31540e5a654ac10aafd9221ac61', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bf997994d23d651e52a3daee2d1ad777', '67ff50c6cf948174455923c069f2ef44', '39b53d6016e88f5784bad89ecd3fde2e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c0021f28c014f011867bdb10d6d32ce0', '96ed8dc8f33d6cde6d89bf3da505770b', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c02317c1fc379eb753424f76aae7cf39', '4580b973f0ccf9473a93b4fd6b998df2', '33a5b231185ea648ac237f1989da814a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c03907ce5fcff69a66045b269f5a18f8', 'a3f54f345abf037f8cb5030b5232d208', '5bd4d31540e5a654ac10aafd9221ac61', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c0e7e14d43bed1e6b5eb467dd4ab37ac', 'e9d2a14e4cf25cf37f705aada62b89b4', '1d6f24415002fc1ed3f31282eb3db7bb', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c14c5c825a5c5e3e8a6c06f46f51b378', 'e31c630541081f746fce9c905081bfa9', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c19ee1b0cda73ab6c18eeeff8e5d8d2f', 'e31c630541081f746fce9c905081bfa9', '35282fcad02f488f22f57a746c944421', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c25d2e0cb7d924a581747a1b1b44a36a', '1ce2c52814c907498619608721de6577', 'acf262fa464441cd22704f6f70839150', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c26b599955788bfe13934573efd9eddd', 'd2b047eb648fe59abe42fd6ce30193e2', '37e5fa116314b6dc5b698c08afea530c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c3e2aafc58cdc063737e5feebeea1be6', '36caf2cbb14e18b7db77a3d8ad0407fb', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c4ab4afee30894bfe4ca6d7da4c9489b', '92afcb561533902ac44a39350b82ec2b', '16b7163c2814a310a9d392c23d76e143', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c51b2d04fbdd05071acf472291d68f8f', 'cc5d8cc7e73fd3073833fc435511116b', 'a58a28918894428bdefab782a7f1c215', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c58fab8363c9e489bbf60f08f0af0ea8', 'e1cf0e5f173de00791523707da5de575', '72f4c00c91390f7a5df4a0d6fc492894', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c5cc40c06c05be96389fc73816767104', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '39b53d6016e88f5784bad89ecd3fde2e', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c60163c63a1fff06a2608d25d61d0058', 'ebeec39ac8bd3796802f02fd95003c01', '7e1471be78ff8ead9772f37fd6aac6cf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c80dc33be68d21ed9ac8c73472342d67', '67ff50c6cf948174455923c069f2ef44', 'a4900f2c8af205febdade2da44b7aa71', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c8744b4167617f4a186bd121cf85f6a9', '96ed8dc8f33d6cde6d89bf3da505770b', '6e89da7570445099ccaca472a4510dbd', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c9a71dfbf810afd67050d7eb24d7d7a3', 'cc5d8cc7e73fd3073833fc435511116b', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ca1c3042fcd8fbbda8066af186a709b0', 'bcbddb2bce1076eb6869328f6bc6e187', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('caaa9419d39ab88513b6dcfc641e74b8', '5efc533e3a703123e221b2a1407ee35c', '19a8fab71b99131a16ebf3e42e5f9fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cc0c6b3bf004f42125db4a5d75233bae', 'f15c59b825a88ad1b3b80281d6f899ad', 'b8a06f21141c029b2833275a8f036ca3', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cd5efd7852888cc6a1ffcc40c6b1097a', 'a3f54f345abf037f8cb5030b5232d208', '37e5fa116314b6dc5b698c08afea530c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cd7053d22f580d94d426923c076d3615', '7ccf7d6f5f0607dc64dc6ba28235cd28', 'f2e213c64816a94de7c4fbb58ffb6b70', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ce776c83b1dc7b15da3a4a8c1f96612c', '7b67f7e881f3007fb987174bcaddb725', '2e386bad52cbbb36c4dd91e3129c8922', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cfecff4dff10fb1a8754f70841c70519', 'e1cf0e5f173de00791523707da5de575', '9c47e492cb267923614e4827fa3d6d2b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d057a190ae011511640ec37c9f875579', '4580b973f0ccf9473a93b4fd6b998df2', '35282fcad02f488f22f57a746c944421', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d0941bf38ed8e4d8c1cd646c827b752d', 'a6602cd96b75a7eac79e417b1fea3916', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d099e4205297d0273c9cd8215564f6e8', '7b67f7e881f3007fb987174bcaddb725', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d19bc176cfa9ad809e0550f0d499edf4', '1729cafa915bffbc9714b28be0bf68dc', '8d94a63bed4e53e27c403fb72d68f884', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d1a80b3ddb52dd6a90d731509abdbf23', 'f15c59b825a88ad1b3b80281d6f899ad', '6ccad31d85f1a1bf991e46c3dc1e0e09', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d2fbfa7e26d02dbfcc0d5319a573cc9a', 'f15c59b825a88ad1b3b80281d6f899ad', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d34bc8fdcbb4fc66d4c41d18ad1ecd24', '3b90437a1560e593dd39c8d5117530ca', 'f715a2ea209729e852117d0a305799e1', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d35c9c9c7d2037584db724e12975da52', '1729cafa915bffbc9714b28be0bf68dc', '6a93b7b5eb793339480570defbe7fe14', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d374919e1fbd7cee39853156ae41ff9a', '92afcb561533902ac44a39350b82ec2b', '1a72a3652ad621ef6c142028bc827e1b', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d576fce81f6692e21e484b11c6f89a8f', '1f9e982ec81f1bb2a35821d0e239dbf9', '31afa04db3aa44e0e61e76c626a3be4e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d771c5c299e3ab014831257dac3fa4d3', 'bcbddb2bce1076eb6869328f6bc6e187', '2e386bad52cbbb36c4dd91e3129c8922', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d7c3999d29a20e9b35751a6fb6905b49', '96ed8dc8f33d6cde6d89bf3da505770b', 'e84fd85087efe8e35b9c3adabd0b4e4b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d8b82f478f477d26732d75146fc142b0', '1729cafa915bffbc9714b28be0bf68dc', '72f4c00c91390f7a5df4a0d6fc492894', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d8eeaaefb8716d88415c8d582ebb778b', 'd2b047eb648fe59abe42fd6ce30193e2', '6fc9d6c656da7c741947e8bc321b721a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d9797c86f2ec4bb9ed93e2737245ab8f', 'd2b047eb648fe59abe42fd6ce30193e2', 'cd4336f615b9ce6d8aa55209dd4b0b55', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('daffd5ffb4cf4bd5623af21c947bbd1c', '2b309c5885f7463f3c5be0ce48e0e31b', '43c1aefb6b54e4f04bb00c87b206195e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc8f8598dffc09bb1c89636e562dcccd', '3b45b464bdfb9cd72c170d0b149d1d25', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc993b2e272fb54b35b13a1ee023d8dc', 'dee2b6f7ec12d3872b797bb08d8d5d0d', 'a4900f2c8af205febdade2da44b7aa71', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dd47f6026981609694fa28c8b707c6e3', '2298e495edd100771c312d8ee2b26da2', '6ccad31d85f1a1bf991e46c3dc1e0e09', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('de0c2ab2652faa11eed8f68b4c22d2b7', '1729cafa915bffbc9714b28be0bf68dc', '12eda6eda040afed588639de6250700c', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df72982157f06ad06d556406ce4e8050', '67ff50c6cf948174455923c069f2ef44', '5178e23c337a26a1ce17a4a73f8bf97b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dff64705ce39b33e95a098b0ca228ff5', '84bf8d1f8261b4aee6796702fe16376f', 'f2e213c64816a94de7c4fbb58ffb6b70', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e311ec2c4a86fb98a424aa5d1a6528fa', 'd2b047eb648fe59abe42fd6ce30193e2', '9c47e492cb267923614e4827fa3d6d2b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e40680fc146212c3fd9a0ee9f0fca50c', '1f9e982ec81f1bb2a35821d0e239dbf9', '6699c4d58521edb9c091072e976bc67d', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e4bb532ba431a9b7f2d38733cb924abc', 'ebeec39ac8bd3796802f02fd95003c01', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e4d83f417e7dffad17a23300e53ee4ac', '3b45b464bdfb9cd72c170d0b149d1d25', '19a8fab71b99131a16ebf3e42e5f9fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e4da09de94c9d87c7bf12d4fb37ce32b', '7ccf7d6f5f0607dc64dc6ba28235cd28', '7d3d80b067c1fa9390eb3b50f20075d9', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e6082ca8e9f2f65a7d9f5f5f8545d16b', '5efc533e3a703123e221b2a1407ee35c', 'ed635e962167812224f6a70a6c7705b6', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e64a418d17dff6ecaf51e80f2b28a106', '3b45b464bdfb9cd72c170d0b149d1d25', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e6f9cc6d44580b2e1883172a3a40fb00', 'bcbddb2bce1076eb6869328f6bc6e187', '19a8fab71b99131a16ebf3e42e5f9fc7', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e7090fc05f476d659dcb878b2e3b40e9', 'cc5d8cc7e73fd3073833fc435511116b', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e80abeb4aff8d814b86b129efee4bae8', '7b67f7e881f3007fb987174bcaddb725', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e88a1c0d3b62ce8b320ef1fdc9c3b4e6', '5efc533e3a703123e221b2a1407ee35c', '886903982dada4949ab8488698671819', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eade4c54bf710cf1d043c9c5e4013854', '95dd21057d7cf2de33cbfbab9f4bc291', '50ff9267f5a8a21ef2086062d6e24343', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ecf34820ad5dfb839c1106404ed971c9', '7ccf7d6f5f0607dc64dc6ba28235cd28', '940719422398be2efc9ae08751bf417b', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ed39538c4d87d658b794cdf8d2278bf3', '1729cafa915bffbc9714b28be0bf68dc', '6fc9d6c656da7c741947e8bc321b721a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ed3a8447df3e55fac5016f196ebbb7b3', 'e1cf0e5f173de00791523707da5de575', '3449a3ec9b382a55bcdc6dcb4f5110f5', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('edbaa0ae384765e8ab000b8540693525', '6b3bf3514986b89b2774b9bb33039a79', 'b041a921609bd35ceb980ef6fa484baf', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eed9324d327dd4cf53bf9cc3eb44cf95', '2c09e28a42318b546248f4993059034f', '35282fcad02f488f22f57a746c944421', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ef3668866c76d1b34e5dbc55634a1691', '284cbb2891079ca9bc61b343b8d33492', '1d6f24415002fc1ed3f31282eb3db7bb', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('efb14154de1135b8b709d00696b32263', '242481669d377685e7130fb81bbde1ee', '43c1aefb6b54e4f04bb00c87b206195e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('efb786ba50787e58750ffa7795c08e17', '2c09e28a42318b546248f4993059034f', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('efcb7852b8c9f708cddb552ca9337f0e', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '50a0e2cd1feca24fab3422cda3367c26', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f24c349ae553406c65c78a2f5775ae9e', '45707487b1eddbc74e6273da4abc90b2', '6fc9d6c656da7c741947e8bc321b721a', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f24ca332c189e248993fcbd13fc78c6b', '45707487b1eddbc74e6273da4abc90b2', '8d94a63bed4e53e27c403fb72d68f884', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f3cf8b4e7cbf9119cff081a78c008dfc', 'bcbddb2bce1076eb6869328f6bc6e187', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f52edfca7bc9f02cc8a0889086bdb6a5', '5efc533e3a703123e221b2a1407ee35c', '50ff9267f5a8a21ef2086062d6e24343', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f5481c9d63033541765122bf5971bd2b', 'fa56707a1620dc32dd6caf08dfc4d0f7', 'acf262fa464441cd22704f6f70839150', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f5a371bb9f298f60f86cf82460240995', 'a3f54f345abf037f8cb5030b5232d208', '6a93b7b5eb793339480570defbe7fe14', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f6b59786264b6d4860c04cfad9ada70c', 'cc5d8cc7e73fd3073833fc435511116b', '50ff9267f5a8a21ef2086062d6e24343', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f7cc8cd71702f6254cdaa55074a66dfe', '2298e495edd100771c312d8ee2b26da2', '6883576bd868da1d907142ade9f7b923', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f7f034ac44505578d5f9954fd58a864c', 'e1cf0e5f173de00791523707da5de575', '8d94a63bed4e53e27c403fb72d68f884', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f7ffc7b553432ed5655ecff23d07b8c6', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '7a2b1bee78f886391cbd446123d9b20d', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f9083a2dbd6ddfe8104d4e148e60b1fd', 'a38a33b130e2bc8d5816f70fb814010a', '115011c96e54f5e1cc044f76f69e77b2', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f97621ef3402d0f630d7f82852675165', '1f9e982ec81f1bb2a35821d0e239dbf9', 'dcb01536cd425956165af9d374c2baa8', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fa4f001d5d57f7ace887b3367ff656d2', 'fa56707a1620dc32dd6caf08dfc4d0f7', 'dedba4495cfa131c96b4dc0b43138d83', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fa5124dc22dcbc333b27ae365a71402e', 'cc5d8cc7e73fd3073833fc435511116b', '886903982dada4949ab8488698671819', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fa6af4fccf6df721bf687a8e963c33ec', 'bcbddb2bce1076eb6869328f6bc6e187', 'a58a28918894428bdefab782a7f1c215', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fa7230f5a8333f84b1a3f8ef79f48559', '3b45b464bdfb9cd72c170d0b149d1d25', '10ad21c2ca078ac4293e56dc5c7fb4a7', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fca76e25afe13486f0c9e5227431ff10', '96ed8dc8f33d6cde6d89bf3da505770b', '115011c96e54f5e1cc044f76f69e77b2', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fce223e176df44b0e5cd9343e8481d50', 'd2b047eb648fe59abe42fd6ce30193e2', '8d94a63bed4e53e27c403fb72d68f884', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fd49c450bc3a5e3cd5993427f73312b9', 'e1cf0e5f173de00791523707da5de575', '5346f65f8d180de8781cdaaa5ffaff33', 'Y', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ffb995a0a3e01eb47a00a46cff7fd79a', 'fc054f2b9d57869b399e140190012042', '2a724fd66373612cfcaa92f7341e246e', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_vaccine_rel (dose_vaccine_rel_id, dose_id, vaccine_id, preferred, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fff7c7067473ec4c351a37ba1464acc6', '1f9e982ec81f1bb2a35821d0e239dbf9', 'e5d15051f018680a81ba7a0bb2f2e0dc', 'N', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_series_dose_vaccine_rel;
