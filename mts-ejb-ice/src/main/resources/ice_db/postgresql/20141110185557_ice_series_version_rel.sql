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

-- // ice_series_version_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_series_version_rel (
        series_rel_id VARCHAR(32) primary key,
        series_id VARCHAR(32) NOT NULL,
        version_id VARCHAR(32) NOT NULL,
        LAST_MOD_ID VARCHAR(32) NOT NULL,
        LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CREATE_ID VARCHAR(32) NOT NULL,
        CREATE_DATETIME timestamp NOT NULL);

alter table ice_series_version_rel add constraint fk_isvrsid_2_issid foreign key (series_id) references ice_series (series_id);

alter table ice_series_version_rel add constraint fk_isvrvid_2_cvvid foreign key (version_id) references cds_version (version_id);

alter table ice_series_version_rel add constraint un_isvrvid_2_vid unique (series_id, version_id);

INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('2248f2394df1569087e937765d5eb059', '529cfa2cca9b51278537b45ce8d335a8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('36240fc19a1e6ca675ec7fdbda0401b6', '4ec29271e05178b3a2047c0d29db659a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('40aea3ba8ad331a8376182cfa94d1cbd', 'e4608e3c95c6365c4ebd145f752a2626', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('43196ee6d8c7fe63d0ee6cdf6c9d7f3d', '5396a0ac694a89333a0f45f92c56df0c', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('4bd268a88de2a777eb2e891e642b7948', '4bd268a88de2a777eb2e891e642b7948', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('52f74394178186af2253a27b7f0cadcd', '52f74394178186af2253a27b7f0cadcd', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('60c588f0e0fcf68e14860cf224c557de', '33e232eb34adb3df7a8916dfcb2b1f2f', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('74cd3101edd66d0f85d0f18fabcb3a1c', '8bd120a6fe32118dbcf560d717fedda2', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('7539e68f34859b98fb13d73a421d6bb9', '7539e68f34859b98fb13d73a421d6bb9', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('790bbd63fb50ea7f6d8c278859e8ef93', '659a3a9e672af9bcaa875a09e4a96322', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('7d9017f155a75852c51e09f12c642735', 'a141e191cb89e0e97a5288ae59960bdc', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('7dd202796dbe23acc957bf3d7d5b4f2e', 'c16a87d755094a2d85717aa2ef228028', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('9de2382b38429c0cf6bc779eb4f3f1a5', 'f47ec85a3c19e7f3880e9b7e8b98e0f1', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('b0390838dd8fc793f8ab69346065ef2a', 'b0390838dd8fc793f8ab69346065ef2a', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('b36f1cccc4b0e384c203de9f9b7f2786', '928c115e9665b553885e27371966096e', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('d4f1f94b334d648fffe071f5401fac6b', '83253ed66030cefc5dba876dcb6b0fea', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('d9d00bf9e1a1ee654de3f6af4ba91813', 'd9d00bf9e1a1ee654de3f6af4ba91813', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('e4ce9fdf5eefebfb26e35a5a91e8861d', 'e4ce9fdf5eefebfb26e35a5a91e8861d', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('f9e342259530ed32d1c03439636b59d6', '18f5e3d26f8274a586939bae49eb02c8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_version_rel (series_rel_id, series_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('fdab7a98bfd1b942236e3b1324d5cfea', '7a59daa316ccddbb9c84d97192b37394', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_series_version_rel;
