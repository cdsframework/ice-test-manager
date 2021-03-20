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

-- // ice_season
-- Migration SQL that makes the change goes here.

create table ice_season (
            season_id varchar(32) primary key,
            name varchar(256) not null,
            code varchar(256) not null,
            end_date date,
            start_date date,
            default_season boolean not null default false,
            default_start_mon_day varchar(5),
            default_stop_mon_day varchar(5),
            group_id varchar(32) not null,
            notes varchar(4000),
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_season add constraint ice_seasgs2cdsvggs foreign key (group_id) references ICE_VACCINE_GROUP (group_id);

INSERT INTO ice_season (season_id, name, code, end_date, start_date, default_season, default_start_mon_day, default_stop_mon_day, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2d2c642d26b89e8685c4ce9bf082d3d8', '2009 H1N1 Season', '2009H1N1Season', '2010-06-30', '2009-10-01', false, NULL, NULL, '514c2a0658db7732b4fd1fb9cde3833e', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_season (season_id, name, code, end_date, start_date, default_season, default_start_mon_day, default_stop_mon_day, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('65fb40fcc79d7045755916faeb5e2662', '2012-2013 Influenza Season', '20122013InfluenzaSeason', '2013-06-30', '2012-08-01', false, NULL, NULL, '51785397ea25f92c2349b7b47e363a62', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_season (season_id, name, code, end_date, start_date, default_season, default_start_mon_day, default_stop_mon_day, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ca689767c72a9c07c79514d7aef74624', '2013-2014 Influenza Season', '20132014InfluenzaSeason', '2014-06-30', '2013-08-01', false, NULL, NULL, '51785397ea25f92c2349b7b47e363a62', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO ice_season (season_id, name, code, end_date, start_date, default_season, default_start_mon_day, default_stop_mon_day, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e8ed7a294b0dd1906d0df68f1649b3f5', 'Default Influenza Season', 'DefaultInfluenzaSeason', NULL, NULL, true, '08-01', '06-30', '51785397ea25f92c2349b7b47e363a62', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_season;
