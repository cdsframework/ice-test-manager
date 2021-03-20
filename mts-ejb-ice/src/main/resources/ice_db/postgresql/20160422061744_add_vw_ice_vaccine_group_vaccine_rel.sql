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

-- // add vw_ice_vaccine_group_vaccine_rel
-- Migration SQL that makes the change goes here.

create view vw_ice_vaccine_group_vaccine_rel as
    select distinct
                cc1.code_id as vaccine_code_id,
                cc1.code as vaccine_code,
                cc1.display_name as vaccine_code_name,
                cc2.code_id as vaccine_group_code_id,
                cc2.code as vaccine_group_code,
                cc2.display_name as vaccine_group_code_name,
                ivg.group_id as group_id,
                iv.vaccine_id as vaccine_id
            from
                ice_vaccine_group ivg,
                ice_vaccine_group_version_rel ivgvr,
                ice_vaccine_group_disease_rel ivgdr,
                ice_vaccine iv,
                ice_vaccine_version_rel ivvr,
                ice_vaccine_component_rel ivcr,
                ice_vaccine_component ivc,
                ice_vaccine_disease_rel ivdr,
                cds_code cc1,
                cds_code cc2
            where
                ivg.GROUP_ID = ivgvr.GROUP_ID and
                ivvr.VERSION_ID = ivgvr.VERSION_ID and
                ivvr.VACCINE_ID = iv.VACCINE_ID and
                ivgdr.GROUP_ID = ivg.GROUP_ID and
                ivdr.COMPONENT_ID = ivc.COMPONENT_ID and
                ivc.COMPONENT_ID = ivcr.COMPONENT_ID and
                ivcr.VACCINE_ID = iv.VACCINE_ID and
                ivgdr.DISEASE_CODE_ID = ivdr.DISEASE_CODE_ID and
                cc1.CODE_ID = iv.VACCINE_CODE_ID and
                cc2.CODE_ID = ivg.VACCINE_GROUP_ID;

-- //@UNDO
-- SQL to undo the change goes here.

drop view vw_ice_vaccine_group_vaccine_rel;