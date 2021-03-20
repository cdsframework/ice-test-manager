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

-- // ice_series_season_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_series_season_rel (
        series_rel_id VARCHAR(32) primary key,
        series_id VARCHAR(32) NOT NULL,
        season_id VARCHAR(32) NOT NULL,
        LAST_MOD_ID VARCHAR(32) NOT NULL,
        LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CREATE_ID VARCHAR(32) NOT NULL,
        CREATE_DATETIME timestamp NOT NULL);

alter table ice_series_season_rel add constraint fk_isseasvrsid_2_issid foreign key (series_id) references ice_series (series_id);

alter table ice_series_season_rel add constraint fk_isseasrsid_2_sid foreign key (season_id) references ice_season (season_id);

alter table ice_series_season_rel add constraint un_isseas_2_sid unique (series_id, season_id);

INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('8dc3332a64ba513c1275e94563576b2c', '7539e68f34859b98fb13d73a421d6bb9', 'ca689767c72a9c07c79514d7aef74624', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('9198397085e3f087407f9e09591f333e', '7539e68f34859b98fb13d73a421d6bb9', '65fb40fcc79d7045755916faeb5e2662', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('c3c0d2d54c46226161e2445850366ebb', '8bd120a6fe32118dbcf560d717fedda2', '65fb40fcc79d7045755916faeb5e2662', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('c59e274f5c0967ab70092533986fc1c8', 'a141e191cb89e0e97a5288ae59960bdc', '2d2c642d26b89e8685c4ce9bf082d3d8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('df860716b4db95f648bee53ac9adc92f', '4ec29271e05178b3a2047c0d29db659a', 'e8ed7a294b0dd1906d0df68f1649b3f5', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('f07a43d831fa2c957e85a211990c3d3b', 'c16a87d755094a2d85717aa2ef228028', '2d2c642d26b89e8685c4ce9bf082d3d8', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_series_season_rel (series_rel_id, series_id, season_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('f1868c8689449f84d749ec3435c7ea47', '8bd120a6fe32118dbcf560d717fedda2', 'ca689767c72a9c07c79514d7aef74624', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_series_season_rel;
