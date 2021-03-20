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

-- // add propsalType
-- Migration SQL that makes the change goes here.

alter table ice_test_proposal add column proposal_type varchar(32);

update ice_test_proposal set proposal_type = 'VACCINE' where vaccine_id is not null;
update ice_test_proposal set proposal_type = 'VACCINE_GROUP' where proposal_type is null;
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

alter table ice_test_proposal drop column proposal_type;
drop view vw_ice_test_proposal;
create view vw_ice_test_proposal as
select *,
       (SELECT string_agg(cc.display_name, ', ')
        FROM ice_test_recommendation itr
        join cds_code cc on cc.code_id = itr.rec_interpret_id
        where itp.proposal_id = itr.proposal_id) reasons
from ice_test_proposal itp;
