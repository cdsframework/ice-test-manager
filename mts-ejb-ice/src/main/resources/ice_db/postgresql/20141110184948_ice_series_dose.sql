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

-- // ice_series_dose
-- Migration SQL that makes the change goes here.

create table ice_series_dose (
            dose_id varchar(32) primary key,
            series_id varchar(32) not null,
            dose_number integer not null,
            abs_min_age varchar(32),
            min_age varchar(32),
            max_age varchar(32),
            earliest_rec_age varchar(32),
            latest_rec_age varchar(32),
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_series_dose add constraint fk_isdsid2issid foreign key (series_id) references ice_series (series_id);

INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1729cafa915bffbc9714b28be0bf68dc', '8bd120a6fe32118dbcf560d717fedda2', 1, '6m - 4d', '6m', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1ce2c52814c907498619608721de6577', '529cfa2cca9b51278537b45ce8d335a8', 2, NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1f9e982ec81f1bb2a35821d0e239dbf9', 'e4ce9fdf5eefebfb26e35a5a91e8861d', 1, '10y - 4d', '10y', NULL, '11y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2298e495edd100771c312d8ee2b26da2', '18f5e3d26f8274a586939bae49eb02c8', 4, '361d', '365d', NULL, '12m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('242481669d377685e7130fb81bbde1ee', 'b0390838dd8fc793f8ab69346065ef2a', 4, '4y - 4d', '4y', NULL, '4y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('284cbb2891079ca9bc61b343b8d33492', '4bd268a88de2a777eb2e891e642b7948', 2, '9y 24d', '9y 28d', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2b309c5885f7463f3c5be0ce48e0e31b', 'b0390838dd8fc793f8ab69346065ef2a', 1, '38d', '42d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2c09e28a42318b546248f4993059034f', '529cfa2cca9b51278537b45ce8d335a8', 3, NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3128c56d731dacbac17b7b3fe0c9e204', 'b0390838dd8fc793f8ab69346065ef2a', 2, '66d', '70d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('33ed4681b052cf22fdd726f7ad0b2ed1', '83253ed66030cefc5dba876dcb6b0fea', 1, '28d', '42d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3602c7be9bdf19f861af59bba5d646d4', '83253ed66030cefc5dba876dcb6b0fea', 2, '66d', '70d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('36caf2cbb14e18b7db77a3d8ad0407fb', 'a141e191cb89e0e97a5288ae59960bdc', 1, '6m-4d', '6m', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3b45b464bdfb9cd72c170d0b149d1d25', 'f47ec85a3c19e7f3880e9b7e8b98e0f1', 1, '0d', '0d', NULL, '0d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3b90437a1560e593dd39c8d5117530ca', '5396a0ac694a89333a0f45f92c56df0c', 2, '389d', '389d', NULL, '4y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45707487b1eddbc74e6273da4abc90b2', '4ec29271e05178b3a2047c0d29db659a', 1, '6m - 4d', '6m', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4580b973f0ccf9473a93b4fd6b998df2', '529cfa2cca9b51278537b45ce8d335a8', 1, '19y', '19y', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5efc533e3a703123e221b2a1407ee35c', 'f47ec85a3c19e7f3880e9b7e8b98e0f1', 2, '24d', '28d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('67f7bc27ab85e67aea2281e834f2266c', '52f74394178186af2253a27b7f0cadcd', 1, '38d', '42d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('67ff50c6cf948174455923c069f2ef44', '18f5e3d26f8274a586939bae49eb02c8', 2, '66d', '70d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6b3bf3514986b89b2774b9bb33039a79', '7a59daa316ccddbb9c84d97192b37394', 2, '389d', '389d', NULL, '4y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b67f7e881f3007fb987174bcaddb725', '928c115e9665b553885e27371966096e', 2, '52d', '56d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7ccf7d6f5f0607dc64dc6ba28235cd28', '83253ed66030cefc5dba876dcb6b0fea', 4, '361d', '365d', NULL, '12m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('84bf8d1f8261b4aee6796702fe16376f', '83253ed66030cefc5dba876dcb6b0fea', 3, '94d', '98d', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8968a05a1bdeceb0a406f88137c9d547', 'd9d00bf9e1a1ee654de3f6af4ba91813', 2, '66d', '70d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8da755d4b33f996ae0304643d3515b85', '659a3a9e672af9bcaa875a09e4a96322', 1, '65y-4d', '65y', NULL, '65y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('92afcb561533902ac44a39350b82ec2b', '5396a0ac694a89333a0f45f92c56df0c', 1, '361d', '1y', NULL, '1y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('95dd21057d7cf2de33cbfbab9f4bc291', '928c115e9665b553885e27371966096e', 3, '164d', '168d', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('96ed8dc8f33d6cde6d89bf3da505770b', '52f74394178186af2253a27b7f0cadcd', 2, '66d', '70d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a38a33b130e2bc8d5816f70fb814010a', 'd9d00bf9e1a1ee654de3f6af4ba91813', 1, '38d', '42d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a3f54f345abf037f8cb5030b5232d208', '7539e68f34859b98fb13d73a421d6bb9', 2, NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a6602cd96b75a7eac79e417b1fea3916', 'e4608e3c95c6365c4ebd145f752a2626', 1, '38d', '42d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b1db73da41f9cfcdc9007e2d2ca2571b', '52f74394178186af2253a27b7f0cadcd', 3, '94d', '98d', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bbe75cde3697024fdadeaa5bdee616f0', 'e4608e3c95c6365c4ebd145f752a2626', 2, '66d', '70d', NULL, '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bcbddb2bce1076eb6869328f6bc6e187', '928c115e9665b553885e27371966096e', 1, '28d', '28d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c39586cc9b29a83fb3c45dfe3553282e', '4bd268a88de2a777eb2e891e642b7948', 3, '9y 112d', '9y 112d', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cc5d8cc7e73fd3073833fc435511116b', 'f47ec85a3c19e7f3880e9b7e8b98e0f1', 3, '164d', '168d', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d2670c1f85a37865f9f1f6be1cb43eda', 'e4ce9fdf5eefebfb26e35a5a91e8861d', 2, NULL, '9y 28d', NULL, '16y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d2b047eb648fe59abe42fd6ce30193e2', '7539e68f34859b98fb13d73a421d6bb9', 1, '6m - 4d', '6m', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d5d1c2c768eb158bbe8ae82f8f4a8e23', 'e4608e3c95c6365c4ebd145f752a2626', 3, '361d', '365d', NULL, '12m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d852403015f4db05f45b8a4573bef0bc', 'c16a87d755094a2d85717aa2ef228028', 1, '6m-4d', '6m', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dee2b6f7ec12d3872b797bb08d8d5d0d', '18f5e3d26f8274a586939bae49eb02c8', 1, '38d', '42d', NULL, '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e1cf0e5f173de00791523707da5de575', '4ec29271e05178b3a2047c0d29db659a', 2, NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e31c630541081f746fce9c905081bfa9', '33e232eb34adb3df7a8916dfcb2b1f2f', 1, '361d', '1y', NULL, '1y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e9d2a14e4cf25cf37f705aada62b89b4', '4bd268a88de2a777eb2e891e642b7948', 1, '9y - 4d', '9y', NULL, '11y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ebeec39ac8bd3796802f02fd95003c01', 'a141e191cb89e0e97a5288ae59960bdc', 2, NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f15c59b825a88ad1b3b80281d6f899ad', '18f5e3d26f8274a586939bae49eb02c8', 3, '94d', '98d', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f67f25b2fbc8f459196f342dd6ac2f82', '7a59daa316ccddbb9c84d97192b37394', 1, '361d', '365d', NULL, '1y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fa56707a1620dc32dd6caf08dfc4d0f7', '33e232eb34adb3df7a8916dfcb2b1f2f', 2, '18m - 4d', '18m', NULL, '18m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose (dose_id, series_id, dose_number, abs_min_age, min_age, max_age, earliest_rec_age, latest_rec_age, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fc054f2b9d57869b399e140190012042', 'b0390838dd8fc793f8ab69346065ef2a', 3, '94d', '98d', NULL, '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_series_dose;
