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

-- // ice_test_vaccine_group_vaccine
-- Migration SQL that makes the change goes here.

create view vw_ice_test_vaccine_group_vaccine as 
select distinct iv.*, it.test_id, itg.group_id, its.suite_id, cc.code, cc.display_name from ice_test it
join ice_test_group itg on itg.group_id = it.group_id
join ice_test_suite its on its.suite_id = itg.suite_id
join ice_test_vaccine_group_rel itvgr on itvgr.test_id = it.test_id
join ice_vaccine_group ivg on ivg.group_id = itvgr.group_id
join ice_disease_vers_concept_rel idvcr on idvcr.version_id = its.version_id
join ice_vaccine_group_disease_rel ivgdr on ivgdr.group_id = ivg.group_id
join ice_disease id on id.disease_id = ivgdr.disease_code_id
join ice_vaccine_disease_rel ivdr on ivdr.disease_code_id = id.disease_id
join ice_vaccine_component ivc on ivc.component_id = ivdr.component_id
join ice_vaccine_component_rel ivcr on ivcr.component_id = ivc.component_id
join ice_vaccine iv on iv.vaccine_id = ivcr.vaccine_id
join cds_code cc on cc.code_id = iv.vaccine_code_id ;

-- //@UNDO
-- SQL to undo the change goes here.

drop view vw_ice_test_vaccine_group_vaccine;
