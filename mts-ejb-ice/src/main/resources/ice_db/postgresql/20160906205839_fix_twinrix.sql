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

-- // fix twinrix
-- Migration SQL that makes the change goes here.

update ice_vaccine_component_rel set component_id = 
    coalesce((select component_id from ice_vaccine_component where code_id = (
        select code_id from cds_code where code_system_id = (
            select code_system_id from cds_code_system where oid = '2.16.840.1.113883.12.292') and code = '83')), component_id)
where component_id = (
    select component_id from ice_vaccine_component where code_id = (
        select code_id from cds_code where code_system_id = (
            select code_system_id from cds_code_system where oid = '2.16.840.1.113883.12.292') and code = '84'))
and vaccine_id = (select vaccine_id from ice_vaccine where vaccine_code_id = (select code_id from cds_code where code_system_id = (
            select code_system_id from cds_code_system where oid = '2.16.840.1.113883.12.292') and code = '104'));

-- //@UNDO
-- SQL to undo the change goes here.
