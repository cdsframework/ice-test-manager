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

-- // ice schema cascade deletes
-- Migration SQL that makes the change goes here.

alter table ice_disease_vers_concept_rel drop constraint fk_idvcrdid2iddid;
alter table ice_disease_vers_concept_rel add constraint fk_idvcrdid2iddid foreign key (disease_id) references ice_disease (disease_id) on delete cascade;
alter table ice_vaccine_disease_rel drop constraint fk_ivdrcid2ivccid;
alter table ice_vaccine_disease_rel add constraint fk_ivdrcid2ivccid foreign key (component_id) references ice_vaccine_component (component_id) on delete cascade;
alter table ice_vaccine_group_disease_rel drop constraint fk_ivgdrvgid2ivgvgid;
alter table ice_vaccine_group_disease_rel add constraint fk_ivgdrvgid2ivgvgid foreign key (group_id) references ice_vaccine_group (group_id) on delete cascade;
alter table ice_vaccine_group_version_rel drop constraint fk_ivgvrvgid2ivvid4;
alter table ice_vaccine_group_version_rel add constraint fk_ivgvrvgid2ivvid4 foreign key (group_id) references ice_vaccine_group (group_id) on delete cascade;
alter table ice_vaccine_component_rel drop constraint fk_ivcrvid2ivvid;
alter table ice_vaccine_component_rel add constraint fk_ivcrvid2ivvid foreign key (vaccine_id) references ice_vaccine (vaccine_id) on delete cascade;
alter table ice_vaccine_version_rel drop constraint fk_ivvrvid2ivvid4;
alter table ice_vaccine_version_rel add constraint fk_ivvrvid2ivvid4 foreign key (vaccine_id) references ice_vaccine (vaccine_id) on delete cascade;
alter table ice_test_group drop constraint tcg2tcs;
alter table ice_test_group add constraint tcg2tcs foreign key (suite_id) references ice_test_suite (suite_id) on delete cascade;
alter table ice_test drop constraint tc2tcs_sid;
alter table ice_test add constraint tc2tcs_sid foreign key (group_id) references ice_test_group (group_id) on delete cascade;
alter table ice_test_event drop constraint ce2tc_tcid;
alter table ice_test_event add constraint ce2tc_tcid foreign key (test_id) references ice_test (test_id) on delete cascade;
alter table ice_test_event_component drop constraint cec2ce_eid;
alter table ice_test_event_component add constraint cec2ce_eid foreign key (event_id) references ice_test_event (event_id) on delete cascade;
alter table ice_test_evaluation drop constraint ce2cec_ecid;
alter table ice_test_evaluation add constraint ce2cec_ecid foreign key (event_component_id) references ice_test_event_component (event_component_id) on delete cascade;
alter table ice_test_immunity drop constraint ci2tc_tcid;
alter table ice_test_immunity add constraint ci2tc_tcid foreign key (test_id) references ice_test (test_id) on delete cascade;
alter table ice_test_proposal drop constraint cp2tc_tcid;
alter table ice_test_proposal add constraint cp2tc_tcid foreign key (test_id) references ice_test (test_id) on delete cascade;
alter table ice_test_recommendation drop constraint fk_iprid2irrid;
alter table ice_test_recommendation add constraint fk_iprid2irrid foreign key (proposal_id) references ice_test_proposal (proposal_id) on delete cascade;
alter table ice_test_vaccine_group_rel drop constraint fk_itvgrtid2ittid;
alter table ice_test_vaccine_group_rel add constraint fk_itvgrtid2ittid foreign key (test_id) references ice_test (test_id) on delete cascade;
alter table ice_season_version_rel drop constraint fk_iseasvrsid_2_issid;
alter table ice_season_version_rel add constraint fk_iseasvrsid_2_issid foreign key (season_id) references ice_season (season_id) on delete cascade;
alter table ice_series_dose drop constraint fk_isdsid2issid;
alter table ice_series_dose add constraint fk_isdsid2issid foreign key (series_id) references ice_series (series_id) on delete cascade;
alter table ice_series_dose_interval drop constraint fk_isdi_sid2issid;
alter table ice_series_dose_interval add constraint fk_isdi_sid2issid foreign key (series_id) references ice_series (series_id) on delete cascade;
alter table ice_series_dose_vaccine_rel drop constraint fk_isdvrdid2isddid;
alter table ice_series_dose_vaccine_rel add constraint fk_isdvrdid2isddid foreign key (dose_id) references ice_series_dose (dose_id) on delete cascade;
alter table ice_series_season_rel drop constraint fk_isseasvrsid_2_issid;
alter table ice_series_season_rel add constraint fk_isseasvrsid_2_issid foreign key (series_id) references ice_series (series_id) on delete cascade;
alter table ice_series_version_rel drop constraint fk_isvrsid_2_issid;
alter table ice_series_version_rel add constraint fk_isvrsid_2_issid foreign key (series_id) references ice_series (series_id) on delete cascade;

-- //@UNDO
-- SQL to undo the change goes here.

alter table ice_disease_vers_concept_rel drop constraint fk_idvcrdid2iddid;
alter table ice_disease_vers_concept_rel add constraint fk_idvcrdid2iddid foreign key (disease_id) references ice_disease (disease_id);
alter table ice_vaccine_disease_rel drop constraint fk_ivdrcid2ivccid;
alter table ice_vaccine_disease_rel add constraint fk_ivdrcid2ivccid foreign key (component_id) references ice_vaccine_component (component_id);
alter table ice_vaccine_group_disease_rel drop constraint fk_ivgdrvgid2ivgvgid;
alter table ice_vaccine_group_disease_rel add constraint fk_ivgdrvgid2ivgvgid foreign key (group_id) references ice_vaccine_group (group_id);
alter table ice_vaccine_group_version_rel drop constraint fk_ivgvrvgid2ivvid4;
alter table ice_vaccine_group_version_rel add constraint fk_ivgvrvgid2ivvid4 foreign key (group_id) references ice_vaccine_group (group_id);
alter table ice_vaccine_component_rel drop constraint fk_ivcrvid2ivvid;
alter table ice_vaccine_component_rel add constraint fk_ivcrvid2ivvid foreign key (vaccine_id) references ice_vaccine (vaccine_id);
alter table ice_vaccine_version_rel drop constraint fk_ivvrvid2ivvid4;
alter table ice_vaccine_version_rel add constraint fk_ivvrvid2ivvid4 foreign key (vaccine_id) references ice_vaccine (vaccine_id);
alter table ice_test_group drop constraint tcg2tcs;
alter table ice_test_group add constraint tcg2tcs foreign key (suite_id) references ice_test_suite (suite_id);
alter table ice_test drop constraint tc2tcs_sid;
alter table ice_test add constraint tc2tcs_sid foreign key (group_id) references ice_test_group (group_id);
alter table ice_test_event drop constraint ce2tc_tcid;
alter table ice_test_event add constraint ce2tc_tcid foreign key (test_id) references ice_test (test_id);
alter table ice_test_event_component drop constraint cec2ce_eid;
alter table ice_test_event_component add constraint cec2ce_eid foreign key (event_id) references ice_test_event (event_id);
alter table ice_test_evaluation drop constraint ce2cec_ecid;
alter table ice_test_evaluation add constraint ce2cec_ecid foreign key (event_component_id) references ice_test_event_component (event_component_id);
alter table ice_test_immunity drop constraint ci2tc_tcid;
alter table ice_test_immunity add constraint ci2tc_tcid foreign key (test_id) references ice_test (test_id);
alter table ice_test_proposal drop constraint cp2tc_tcid;
alter table ice_test_proposal add constraint cp2tc_tcid foreign key (test_id) references ice_test (test_id);
alter table ice_test_recommendation drop constraint fk_iprid2irrid;
alter table ice_test_recommendation add constraint fk_iprid2irrid foreign key (proposal_id) references ice_test_proposal (proposal_id);
alter table ice_test_vaccine_group_rel drop constraint fk_itvgrtid2ittid;
alter table ice_test_vaccine_group_rel add constraint fk_itvgrtid2ittid foreign key (test_id) references ice_test (test_id);
alter table ice_season_version_rel drop constraint fk_iseasvrsid_2_issid;
alter table ice_season_version_rel add constraint fk_iseasvrsid_2_issid foreign key (season_id) references ice_season (season_id);
alter table ice_series_dose drop constraint fk_isdsid2issid;
alter table ice_series_dose add constraint fk_isdsid2issid foreign key (series_id) references ice_series (series_id);
alter table ice_series_dose_interval drop constraint fk_isdi_sid2issid;
alter table ice_series_dose_interval add constraint fk_isdi_sid2issid foreign key (series_id) references ice_series (series_id);
alter table ice_series_dose_vaccine_rel drop constraint fk_isdvrdid2isddid;
alter table ice_series_dose_vaccine_rel add constraint fk_isdvrdid2isddid foreign key (dose_id) references ice_series_dose (dose_id);
alter table ice_series_season_rel drop constraint fk_isseasvrsid_2_issid;
alter table ice_series_season_rel add constraint fk_isseasvrsid_2_issid foreign key (series_id) references ice_series (series_id);
alter table ice_series_version_rel drop constraint fk_isvrsid_2_issid;
alter table ice_series_version_rel add constraint fk_isvrsid_2_issid foreign key (series_id) references ice_series (series_id);
