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

-- // ice_series_dose_interval
-- Migration SQL that makes the change goes here.

create table ice_series_dose_interval (
        dose_interval_id varchar(32) primary key,
        series_id varchar(32) not null,
        from_dose_id varchar(32) not null,
        to_dose_id varchar(32) not null,
        abs_min_interval varchar(32),
        min_interval varchar(32),
        earliest_rec_interval varchar(32),
        latest_rec_interval varchar(32),
        last_mod_datetime timestamp not null,
        last_mod_id varchar(32) not null,
        create_datetime timestamp not null,
        create_id varchar(32) not null);

alter table ice_series_dose_interval add constraint fk_isdi_fdid2did foreign key (from_dose_id) references ice_series_dose (dose_id);

alter table ice_series_dose_interval add constraint fk_isdi_sid2issid foreign key (series_id) references ice_series (series_id);

alter table ice_series_dose_interval add constraint fk_isdi_tdid2did foreign key (to_dose_id) references ice_series_dose (dose_id);

alter table ice_series_dose_interval add constraint un_isdi_fdi_tdi unique (from_dose_id, to_dose_id);

INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2486626819f8f56375897783d5daf4eb', '8bd120a6fe32118dbcf560d717fedda2', '1729cafa915bffbc9714b28be0bf68dc', '1729cafa915bffbc9714b28be0bf68dc', NULL, NULL, '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('27b8952f3ac2a93dbe16a91979923b05', '928c115e9665b553885e27371966096e', 'bcbddb2bce1076eb6869328f6bc6e187', '7b67f7e881f3007fb987174bcaddb725', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ee50e12eaff17f343afe62ef9a7712d', '529cfa2cca9b51278537b45ce8d335a8', '1ce2c52814c907498619608721de6577', '2c09e28a42318b546248f4993059034f', '5m - 4d', '5m', '5m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2fc321b6b490768531d6b438e7e76771', '33e232eb34adb3df7a8916dfcb2b1f2f', 'e31c630541081f746fce9c905081bfa9', 'fa56707a1620dc32dd6caf08dfc4d0f7', '6m - 4d', '6m', '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('395528597dc732805ee8a847c1dd315a', 'f47ec85a3c19e7f3880e9b7e8b98e0f1', '3b45b464bdfb9cd72c170d0b149d1d25', '5efc533e3a703123e221b2a1407ee35c', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('46f2c35f2284536d596c6cb95f36751d', '7539e68f34859b98fb13d73a421d6bb9', 'd2b047eb648fe59abe42fd6ce30193e2', 'a3f54f345abf037f8cb5030b5232d208', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4ffd4e822fe6021361cf32735baae300', 'e4608e3c95c6365c4ebd145f752a2626', 'bbe75cde3697024fdadeaa5bdee616f0', 'd5d1c2c768eb158bbe8ae82f8f4a8e23', '52d', '56d', '56d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('54bc304d325cf2cc25b6b31fd655ccc6', 'b0390838dd8fc793f8ab69346065ef2a', '3128c56d731dacbac17b7b3fe0c9e204', 'fc054f2b9d57869b399e140190012042', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('54bcbb85ec0f41bf7a1787d92d7cb24c', 'e4608e3c95c6365c4ebd145f752a2626', 'a6602cd96b75a7eac79e417b1fea3916', 'bbe75cde3697024fdadeaa5bdee616f0', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('598b7f83cc45021c26394ceaad919b19', '4bd268a88de2a777eb2e891e642b7948', '284cbb2891079ca9bc61b343b8d33492', 'c39586cc9b29a83fb3c45dfe3553282e', '80d', '84d', '4m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('60edbee6b394803a4544ed824b2922f5', '18f5e3d26f8274a586939bae49eb02c8', 'dee2b6f7ec12d3872b797bb08d8d5d0d', '67ff50c6cf948174455923c069f2ef44', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6a11a5796126adc32058f128589b6590', 'd9d00bf9e1a1ee654de3f6af4ba91813', 'a38a33b130e2bc8d5816f70fb814010a', '8968a05a1bdeceb0a406f88137c9d547', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6dd8ee2265ae584d2d119a10d0e2cc2d', 'e4ce9fdf5eefebfb26e35a5a91e8861d', '1f9e982ec81f1bb2a35821d0e239dbf9', 'd2670c1f85a37865f9f1f6be1cb43eda', '52d', '56d', '3y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7356d06c6064a3551df8b9f1d6c85abb', '4ec29271e05178b3a2047c0d29db659a', '45707487b1eddbc74e6273da4abc90b2', 'e1cf0e5f173de00791523707da5de575', '24d', '28d', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('73b8379d4a1f97ce7f7c85fda3b2444f', '83253ed66030cefc5dba876dcb6b0fea', '84bf8d1f8261b4aee6796702fe16376f', '7ccf7d6f5f0607dc64dc6ba28235cd28', '52d', '56d', '56d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('775ee095f3cf2281ba9be23874017645', '659a3a9e672af9bcaa875a09e4a96322', '8da755d4b33f996ae0304643d3515b85', '8da755d4b33f996ae0304643d3515b85', '5y-4d', '5y', '5y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7ca2ef4d237ca8bb90a79ed3d80ba09e', '18f5e3d26f8274a586939bae49eb02c8', 'f15c59b825a88ad1b3b80281d6f899ad', '2298e495edd100771c312d8ee2b26da2', '52d', '56d', '56d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7e7aa643cbe9106c8003b7148c1e08c4', 'f47ec85a3c19e7f3880e9b7e8b98e0f1', '5efc533e3a703123e221b2a1407ee35c', 'cc5d8cc7e73fd3073833fc435511116b', '52d', '56d', '56d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8589b9e8599902f6865965f54b3038c6', '52f74394178186af2253a27b7f0cadcd', '67f7bc27ab85e67aea2281e834f2266c', '96ed8dc8f33d6cde6d89bf3da505770b', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('99cacb57390dbb1b7a59f4ed73987340', '4bd268a88de2a777eb2e891e642b7948', 'e9d2a14e4cf25cf37f705aada62b89b4', '284cbb2891079ca9bc61b343b8d33492', '24d', '28d', '2m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9c294b4da3344c7bd902f30d410234fa', '928c115e9665b553885e27371966096e', '7b67f7e881f3007fb987174bcaddb725', '95dd21057d7cf2de33cbfbab9f4bc291', '52d', '56d', '56d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9cc2eb4ee89437187b4fbb02c216258a', 'b0390838dd8fc793f8ab69346065ef2a', 'fc054f2b9d57869b399e140190012042', '242481669d377685e7130fb81bbde1ee', '4y - 4d', '4y', '4y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9ecf21ad84b7367ad30e5671422479e4', '529cfa2cca9b51278537b45ce8d335a8', '4580b973f0ccf9473a93b4fd6b998df2', '1ce2c52814c907498619608721de6577', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a651897a2fe0030810241f52be2153df', '4bd268a88de2a777eb2e891e642b7948', 'e9d2a14e4cf25cf37f705aada62b89b4', 'c39586cc9b29a83fb3c45dfe3553282e', '112d', '112d', '6m', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aac2dd74fe0117ef6e19a19bda433567', '83253ed66030cefc5dba876dcb6b0fea', '3602c7be9bdf19f861af59bba5d646d4', '84bf8d1f8261b4aee6796702fe16376f', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b1494afe420e304d5dd663904f8929d2', '5396a0ac694a89333a0f45f92c56df0c', '92afcb561533902ac44a39350b82ec2b', '3b90437a1560e593dd39c8d5117530ca', '28d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b6ef20a65e05dd2f7a973f8d7d9e8284', '18f5e3d26f8274a586939bae49eb02c8', '67ff50c6cf948174455923c069f2ef44', 'f15c59b825a88ad1b3b80281d6f899ad', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bbdda2fea930ad6aad19afb18b91f09a', '7a59daa316ccddbb9c84d97192b37394', 'f67f25b2fbc8f459196f342dd6ac2f82', '6b3bf3514986b89b2774b9bb33039a79', '28d', '28d', '84d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bc84d8e95c11578f7ff2acb6981343e9', '52f74394178186af2253a27b7f0cadcd', '96ed8dc8f33d6cde6d89bf3da505770b', 'b1db73da41f9cfcdc9007e2d2ca2571b', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c8fc37bcd1bbc4a51320508473a4050e', '83253ed66030cefc5dba876dcb6b0fea', '33ed4681b052cf22fdd726f7ad0b2ed1', '3602c7be9bdf19f861af59bba5d646d4', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d63c6fe641f0c07a3e1317df3d4ed6a1', 'a141e191cb89e0e97a5288ae59960bdc', '36caf2cbb14e18b7db77a3d8ad0407fb', 'ebeec39ac8bd3796802f02fd95003c01', '21d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_series_dose_interval (dose_interval_id, series_id, from_dose_id, to_dose_id, abs_min_interval, min_interval, earliest_rec_interval, latest_rec_interval, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f3dbcf6f600270e92c7ef6f9e7d8ce9c', 'b0390838dd8fc793f8ab69346065ef2a', '2b309c5885f7463f3c5be0ce48e0e31b', '3128c56d731dacbac17b7b3fe0c9e204', '24d', '28d', '28d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_series_dose_interval;
