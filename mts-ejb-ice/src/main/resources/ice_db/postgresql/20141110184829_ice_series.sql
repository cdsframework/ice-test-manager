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

-- // ice_series
-- Migration SQL that makes the change goes here.

create table ice_series (
            series_id varchar(32) primary key,
            name varchar(256) not null,
            code varchar(256) not null,
            group_id varchar(32) not null,
            notes varchar(4000),
            last_mod_datetime timestamp not null,
            last_mod_id varchar(32) not null,
            create_datetime timestamp not null,
            create_id varchar(32) not null);

alter table ice_series add constraint ice_sergs2cdsvggs foreign key (group_id) references ICE_VACCINE_GROUP (group_id);

INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('18f5e3d26f8274a586939bae49eb02c8', 'Hib 4-Dose Series', '9808d21f26aa9303abb790c5de6dfa81', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Hib4DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('33e232eb34adb3df7a8916dfcb2b1f2f', 'HepA 2-Dose Child/Adult Series', 'da79a9a2186d87cd236c1d6a9fd86e28', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'HepA2DoseChildAdultSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('4bd268a88de2a777eb2e891e642b7948', 'HPV 3-Dose Series', 'a97422fac07fed6db5cf2f95ea141e1c', 'ICE, based on the pink book not specifying recommended ages for doses 2 and 3, does not y a recommended routine age to doses 2 and 3. Once the series is initiated, ICE recommends subsequent doses based on the recommended interval.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'HPV3DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('4ec29271e05178b3a2047c0d29db659a', 'Influenza 2-Dose Default Series', '51785397ea25f92c2349b7b47e363a62', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Influenza2DoseDefaultSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('529cfa2cca9b51278537b45ce8d335a8', 'HepA Adult 3-Dose Series', 'da79a9a2186d87cd236c1d6a9fd86e28', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'HepAAdult3DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('52f74394178186af2253a27b7f0cadcd', 'Rotavirus 3-Dose Series', '2cc45eefd7d0135e9ed5e4dcb81cd2d1', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Rotavirus3DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('5396a0ac694a89333a0f45f92c56df0c', 'MMR 2-Dose Series', '6248e15c5d8a94c05c54f8efac5b3e62', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'MMR2DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('659a3a9e672af9bcaa875a09e4a96322', 'PPSV 1-Dose Series', 'd031730eeb92154f78bc4f4c88827b45', 'If an invalid shot is given, you have to wait 5y-4d before a valid shot actually can be given.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'PPSV1DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('7539e68f34859b98fb13d73a421d6bb9', 'Influenza 2-Dose Series', '51785397ea25f92c2349b7b47e363a62', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Influenza2DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('7a59daa316ccddbb9c84d97192b37394', 'Varicella 2-Dose Series', '4192deec1e4503a7df2e74a4b7289f0b', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Varicella2DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('83253ed66030cefc5dba876dcb6b0fea', 'PCV 4-Dose Series', 'cee1aace28bc115685dbbfd1536c7307', 'CDC CDSi rules and test cases evaluate CVX 100 (PCV 7) as invalid (i.e., not allowable) if given as target dose 4. The ICE Workgroup could not find any official guidance that suggests CVX 100 is not acceptable as dose 4, but rather found guidance that the series cannot be completed with PCV 7 only (once the PCV 13 vaccine became available). ICE allows PCV 7 for dose 4, but follows the PCV 13 rule, when icable, that recommends a dose of PCV 13. 
    
', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'PCV4DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('8bd120a6fe32118dbcf560d717fedda2', 'Influenza 1-Dose Series', '51785397ea25f92c2349b7b47e363a62', 'If an invalid shot is given, you have to wait 28d before a valid shot actually can be given.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Influenza1DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('928c115e9665b553885e27371966096e', 'HepB Child/Adult Series', '28f2cf85fc083a569eb46a69671fe8b0', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'HepBChildAdultSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('a141e191cb89e0e97a5288ae59960bdc', 'H1N1 2-Dose Series', '514c2a0658db7732b4fd1fb9cde3833e', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'H1N12DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('b0390838dd8fc793f8ab69346065ef2a', 'Polio 4-Dose Series', '8faa56ba12d371ff805a9967c31c0fb5', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Polio4DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('c16a87d755094a2d85717aa2ef228028', 'H1N1 1-Dose Series', '514c2a0658db7732b4fd1fb9cde3833e', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'H1N11DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('d9d00bf9e1a1ee654de3f6af4ba91813', 'Rotavirus 2-Dose Series', '2cc45eefd7d0135e9ed5e4dcb81cd2d1', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'Rotavirus2DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('e4608e3c95c6365c4ebd145f752a2626', 'Hib OMP Series', '9808d21f26aa9303abb790c5de6dfa81', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'HibOMPSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('e4ce9fdf5eefebfb26e35a5a91e8861d', 'MCV4 2-Dose Series', '1ababe4ac2985098e7d4af77a9415a3d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'MCV42DoseSeries');
INSERT INTO ice_series (series_id, name, group_id, notes, last_mod_datetime, last_mod_id, create_datetime, create_id, code) 
    VALUES ('f47ec85a3c19e7f3880e9b7e8b98e0f1', 'HepB Newborn Series', '28f2cf85fc083a569eb46a69671fe8b0', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'HepBNewbornSeries');

-- //@UNDO
-- SQL to undo the change goes here.

drop table ice_series;
