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

run MM 20150326143617_create_changelog.sql

run new sql data:
    cds_code_system.sql
    cds_code.sql
    opencds_concept.sql
    opencds_concept_rel.sql
    cds_list.sql
    cds_list_item.sql

run MM 20150326144035_ice_disease.sql
run MM 20150326144070_ice_disease_vers_concept_rel.sql

run new sql data:
    ice_vaccine_component.sql
    ice_vaccine_disease_rel.sql
    ice_vaccine_group_disease_rel.sql

run:
delete from ice_vaccine_group_disease_rel where disease_code_id in (select code_id from cds_code cc where cc.code_system_id = '734d727366047ef1b6292d310d36b657');

delete from ice_vaccine_disease_rel where disease_code_id in (select code_id from cds_code cc where cc.code_system_id = '734d727366047ef1b6292d310d36b657');

alter table ice_vaccine_group_disease_rel drop constraint fk_ivgdr2cccid1;

alter table ice_vaccine_group_disease_rel add constraint fk_ivgdrdcid2iddid foreign key (disease_code_id) references ice_disease (disease_id);

alter table ice_vaccine_disease_rel drop constraint fk_ivdrdcid2cccid1;

alter table ice_vaccine_disease_rel add constraint fk_ivdrdcid2iddid foreign key (disease_code_id) references ice_disease (disease_id);


replace cds list ICE_IMM_DISEASE with code system version