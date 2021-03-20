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

-- // add views back
-- Migration SQL that makes the change goes here.

create view vw_ice_test_proposal as
select *,
       (SELECT string_agg(cc.display_name, ', ')
        FROM ice_test_recommendation itr
        join cds_code cc on cc.code_id = itr.rec_interpret_id
        where itp.proposal_id = itr.proposal_id) reasons
from ice_test_proposal itp;

create view vw_ice_vaccine_series as
select iv.*, cc.code, cc.display_name, s.series_id from ice_vaccine iv
    join cds_code cc on cc.code_id = iv.vaccine_code_id
    join ice_vaccine_version_rel ivvr on ivvr.vaccine_id = iv.vaccine_id
    join ice_vaccine_component_rel ivcr on ivcr.vaccine_id = iv.vaccine_id
    join ice_vaccine_component ivc on ivc.component_id = ivcr.component_id
    join ice_vaccine_disease_rel ivdr on ivdr.component_id = ivc.component_id
    join ice_vaccine_group_disease_rel ivgdr on ivgdr.disease_code_id = ivdr.disease_code_id
    join ice_vaccine_group ivg on ivg.group_id = ivgdr.group_id
    join ice_vaccine_group_version_rel ivgvr on ivgvr.group_id = ivg.group_id and ivgvr.version_id = ivvr.version_id
    join ice_series_version_rel isvr on isvr.version_id = ivgvr.version_id
    join ice_series s on s.series_id = isvr.series_id and s.group_id = ivg.group_id;

create view vw_ice_vaccine_group_test as
select distinct ivg.*, cc.code, cc.display_name, it.test_id
    from ice_vaccine_group ivg
    join cds_code cc on cc.code_id = ivg.vaccine_group_id
    join ice_vaccine_group_version_rel ivgvr on ivgvr.group_id = ivg.group_id
    join ice_test_suite its on its.version_id = ivgvr.version_id
    join ice_test_group itg on itg.suite_id = its.suite_id
    join ice_test it on it.group_id = itg.group_id
    join ice_test_vaccine_group_rel itvgr on itvgr.test_id = it.test_id and itvgr.group_id = ivg.group_id;

create view vw_ice_test_event as
select *,
       (select count(*) from ice_test_event_component itec
        where itec.event_id = ite.event_id) comp_count,
       (SELECT string_agg(cc.display_name, ', ')
        FROM ice_test_event_component itec2
        join cds_code cc on cc.code_id = itec2.eval_value_id
        where ite.event_id = itec2.event_id) comp_status
from ice_test_event ite;

create view vw_ice_test_event_component as
select *,
       (SELECT string_agg(cc.display_name, ', ')
        FROM ice_test_evaluation ite
        join cds_code cc on cc.code_id = ite.eval_interpret_id
        where ite.event_component_id = itec.event_component_id) eval_reason
from ice_test_event_component itec;

create view vw_ice_test_vac_vac_group as
select distinct ivg.*, cc.code, cc.display_name, it.test_id, iv.vaccine_id
    from ice_vaccine_group ivg
    join cds_code cc on cc.code_id = ivg.vaccine_group_id
    join ice_vaccine_group_version_rel ivgvr on ivgvr.group_id = ivg.group_id
    join ice_test_suite its on its.version_id = ivgvr.version_id
    join ice_test_group itg on itg.suite_id = its.suite_id
    join ice_test it on it.group_id = itg.group_id
    join ice_vaccine_group_disease_rel ivgdr on ivgdr.group_id = ivg.group_id
    join ice_disease id on id.disease_id = ivgdr.disease_code_id
    join ice_disease_vers_concept_rel idvcr on idvcr.disease_id = id.disease_id and idvcr.version_id = its.version_id
    join ice_vaccine_disease_rel ivdr on ivdr.disease_code_id = id.disease_id
    join ice_vaccine_component ivc on ivc.component_id = ivdr.component_id
    join ice_vaccine_component_rel ivcr on ivcr.component_id = ivc.component_id
    join ice_vaccine iv on iv.vaccine_id = ivcr.vaccine_id;

create view vw_ice_test_vac_comp_vac_group as
  select distinct ivg.*, cc.code, cc.display_name, it.test_id, ivc.component_id
    from ice_vaccine_group ivg
    join cds_code cc on cc.code_id = ivg.vaccine_group_id
    join ice_vaccine_group_version_rel ivgvr on ivgvr.group_id = ivg.group_id
    join ice_test_suite its on its.version_id = ivgvr.version_id
    join ice_test_group itg on itg.suite_id = its.suite_id
    join ice_test it on it.group_id = itg.group_id
    join ice_vaccine_group_disease_rel ivgdr on ivgdr.group_id = ivg.group_id
    join ice_disease id on id.disease_id = ivgdr.disease_code_id
    join ice_disease_vers_concept_rel idvcr on idvcr.disease_id = id.disease_id and idvcr.version_id = its.version_id
    join ice_vaccine_disease_rel ivdr on ivdr.disease_code_id = id.disease_id
    join ice_vaccine_component ivc on ivc.component_id = ivdr.component_id;

create view vw_ice_series as
select s.*, cc.code as cds_code, cc.display_name from
    ice_series s
    join ice_vaccine_group ivg on ivg.group_id = s.group_id
    join cds_code cc on ivg.vaccine_group_id = cc.code_id;

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

create view vw_ice_test_disease_code as 
    select cc.*, it.test_id, ccs.oid, ccs.name from ice_test it
     join ice_test_group itg on itg.group_id = it.group_id
     join ice_test_suite its on its.suite_id = itg.suite_id
     join ice_test_vaccine_group_rel itvgr on itvgr.test_id = it.test_id
     join ice_vaccine_group ivg on ivg.group_id = itvgr.group_id
     join ice_vaccine_group_disease_rel ivgdr on ivgdr.group_id = ivg.group_id
     join ice_disease id on id.disease_id = ivgdr.disease_code_id
     join ice_disease_vers_concept_rel idvcr on idvcr.disease_id = id.disease_id and idvcr.version_id = its.version_id
     join opencds_concept oc on oc.code_id = idvcr.primary_concept
     join opencds_concept_rel ocr on ocr.concept_code_id = oc.code_id
     join cds_code cc on cc.code_id = ocr.cds_code_id
     join cds_code_system ccs on ccs.code_system_id = cc.code_system_id;

create view vw_ice_vaccine_component_rel as
    select ivcr.*, cc.code, cc.display_name
    from ice_vaccine_component_rel ivcr, ice_vaccine_component ivc, cds_code cc
    where ivcr.component_id = ivc.component_id
        and ivc.code_id = cc.code_id;

create view vw_ice_vaccine as
    select iv.*, cc.code, cc.display_name
    from ice_vaccine iv, cds_code cc
    where iv.vaccine_code_id = cc.code_id;

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

create view vw_ice_vaccine_group_disease_rel as
    select ivgdr.*, cc.code, cc.display_name
    from ice_vaccine_group_disease_rel ivgdr, ice_disease id, cds_code cc
    where ivgdr.disease_code_id = id.disease_id
        and id.code_id = cc.code_id;

create view vw_ice_vaccine_group as
    select ivg.*, cc.code, cc.display_name
    from ice_vaccine_group ivg, cds_code cc
    where ivg.vaccine_group_id = cc.code_id;

create view vw_ice_test_vaccine_group_rel as
    select itvgr.*, vivg.code, vivg.display_name
    from ice_test_vaccine_group_rel itvgr, vw_ice_vaccine_group vivg
    where itvgr.group_id = vivg.group_id;

create view vw_ice_vaccine_disease_rel as
    select ivdr.*, cc.code, cc.display_name
    from ice_vaccine_disease_rel ivdr, ice_disease id, cds_code cc
    where ivdr.disease_code_id = id.disease_id
        and id.code_id = cc.code_id;

create view vw_ice_vaccine_component as
    select ivc.*, cc.code, cc.display_name
    from ice_vaccine_component ivc, cds_code cc
    where ivc.code_id = cc.code_id;

create view vw_ice_disease as select id.*, cc.code, cc.display_name from ice_disease id, cds_code cc where id.code_id = cc.code_id;

-- //@UNDO
-- SQL to undo the change goes here.


