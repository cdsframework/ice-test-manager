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

-- // alter ice_test_proposal
-- Migration SQL that makes the change goes here.

alter table ice_test_proposal rename column offset_id to recommended_offset_id;
alter table ice_test_proposal rename column offset_type to recommended_offset_type;
alter table ice_test_proposal rename column offset_based to recommended_offset_based;
alter table ice_test_proposal rename column offset_value to recommended_offset;
alter table ice_test_proposal rename column administration_time to recommended_date;
alter table ice_test_proposal add column earliest_offset varchar(50);
alter table ice_test_proposal add column latest_offset varchar(50);
alter table ice_test_proposal add column overdue_offset varchar(50);
alter table ice_test_proposal add column earliest_date date;
alter table ice_test_proposal add column latest_date date;
alter table ice_test_proposal add column overdue_date date;
alter table ice_test_proposal add column earliest_offset_based boolean;
alter table ice_test_proposal add column latest_offset_based boolean;
alter table ice_test_proposal add column overdue_offset_based boolean;
alter table ice_test_proposal add column earliest_offset_type varchar(32);
alter table ice_test_proposal add column latest_offset_type varchar(32);
alter table ice_test_proposal add column overdue_offset_type varchar(32);
alter table ice_test_proposal add column earliest_offset_id varchar(32);
alter table ice_test_proposal add column latest_offset_id varchar(32);
alter table ice_test_proposal add column overdue_offset_id varchar(32);
alter table ice_test_proposal drop constraint fk_itposid2iteeid;
alter table ice_test_proposal add constraint fk_itprosid2iteeid foreign key (recommended_offset_id) references ice_test_event (event_id);
alter table ice_test_proposal add constraint fk_itpeosid2iteeid foreign key (earliest_offset_id) references ice_test_event (event_id);
alter table ice_test_proposal add constraint fk_itplosid2iteeid foreign key (latest_offset_id) references ice_test_event (event_id);
alter table ice_test_proposal add constraint fk_itpoosid2iteeid foreign key (overdue_offset_id) references ice_test_event (event_id);
drop view vw_ice_test_proposal;
create view vw_ice_test_proposal as
select *,
       (SELECT string_agg(cc.display_name, ', ')
        FROM ice_test_recommendation itr
        join cds_code cc on cc.code_id = itr.rec_interpret_id
        where itp.proposal_id = itr.proposal_id) reasons
from ice_test_proposal itp;

-- //@UNDO
-- SQL to undo the change goes here.

alter table ice_test_proposal rename column recommended_offset_id to offset_id;
alter table ice_test_proposal rename column recommended_offset_type to offset_type;
alter table ice_test_proposal rename column recommended_offset_based to offset_based;
alter table ice_test_proposal rename column recommended_offset to offset_value;
alter table ice_test_proposal rename column recommended_date to administration_time;
alter table ice_test_proposal drop column earliest_offset;
alter table ice_test_proposal drop column latest_offset;
alter table ice_test_proposal drop column overdue_offset;
alter table ice_test_proposal drop column earliest_date;
alter table ice_test_proposal drop column latest_date;
alter table ice_test_proposal drop column overdue_date;
alter table ice_test_proposal drop column earliest_offset_based;
alter table ice_test_proposal drop column latest_offset_based;
alter table ice_test_proposal drop column overdue_offset_based;
alter table ice_test_proposal drop column earliest_offset_type;
alter table ice_test_proposal drop column latest_offset_type;
alter table ice_test_proposal drop column overdue_offset_type;
alter table ice_test_proposal drop column earliest_offset_id;
alter table ice_test_proposal drop column latest_offset_id;
alter table ice_test_proposal drop column overdue_offset_id;
alter table ice_test_proposal drop constraint fk_itprosid2iteeid;
alter table ice_test_proposal add constraint fk_itposid2iteeid foreign key (offset_id) references ice_test_event (event_id);
alter table ice_test_proposal drop constraint fk_itpeosid2iteeid;
alter table ice_test_proposal drop constraint fk_itplosid2iteeid;
alter table ice_test_proposal drop constraint fk_itpoosid2iteeid;
drop view vw_ice_test_proposal;
create view vw_ice_test_proposal as
select *,
       (SELECT string_agg(cc.display_name, ', ')
        FROM ice_test_recommendation itr
        join cds_code cc on cc.code_id = itr.rec_interpret_id
        where itp.proposal_id = itr.proposal_id) reasons
from ice_test_proposal itp;
