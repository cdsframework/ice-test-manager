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

-- cds db
drop table ice_test_recommendation;
drop table ice_test_proposal;
drop table ice_test_immunity;
drop table ice_test_evaluation;
drop table ice_test_event_component;
drop table ice_test_event;
drop table ice_test_vaccine_group_rel;
drop table ice_test;
drop table ice_test_group;
drop table ice_test_suite;
drop table ice_series_version_rel;
drop table ice_series_season_rel;
drop table ice_series_dose_vaccine_rel;
drop table ice_series_dose_interval;
drop table ice_series_dose;
drop table ice_series;
drop table ice_season_version_rel;
drop table ice_season;
drop table ice_vaccine_version_rel;
drop table ice_vaccine_group_version_rel;
drop table ice_vaccine_group_disease_rel;
drop table ice_vaccine_conflict_rel;
drop table ice_vaccine_component_rel;
drop table ice_vaccine;
drop table ice_vaccine_disease_rel;
drop table ice_vaccine_component;
drop table ice_vaccine_group;
drop table ice_disease_vers_concept_rel;
drop table ice_disease;
drop table ice_changelog;

-- mts db

drop table ice_mts_changelog;