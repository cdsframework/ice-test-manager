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

-- // ice_season_version_rel
-- Migration SQL that makes the change goes here.

CREATE TABLE ice_season_version_rel (
        season_rel_id VARCHAR(32) primary key,
        season_id VARCHAR(32) NOT NULL,
        version_id VARCHAR(32) NOT NULL,
        LAST_MOD_ID VARCHAR(32) NOT NULL,
        LAST_MOD_DATETIME timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CREATE_ID VARCHAR(32) NOT NULL,
        CREATE_DATETIME timestamp NOT NULL);

alter table ice_season_version_rel add constraint fk_iseasvrsid_2_issid foreign key (season_id) references ice_season (season_id);

alter table ice_season_version_rel add constraint fk_iseasvrvid_2_cvvid foreign key (version_id) references cds_version (version_id);

alter table ice_season_version_rel add constraint un_iseasvrvid_2_vid unique (season_id, version_id);

INSERT INTO ice_season_version_rel (season_rel_id, season_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('43f46d429e80322a8f8fa251fd4557b0', '2d2c642d26b89e8685c4ce9bf082d3d8', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_season_version_rel (season_rel_id, season_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('900f3f78d36878497dde5c87a9bbb867', '65fb40fcc79d7045755916faeb5e2662', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_season_version_rel (season_rel_id, season_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('9f6cd375431ccf9d6b1c356b3d0c6157', 'e8ed7a294b0dd1906d0df68f1649b3f5', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO ice_season_version_rel (season_rel_id, season_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('e1982119cc08d1ff1f8f650caf6ed393', 'ca689767c72a9c07c79514d7aef74624', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_season_version_rel;
