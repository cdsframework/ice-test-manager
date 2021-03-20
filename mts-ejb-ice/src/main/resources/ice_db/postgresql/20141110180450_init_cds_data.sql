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

-- // init_cds_data
-- Migration SQL that makes the change goes here.

-- business/scope

INSERT INTO cds_business_scope (business_scope_id, scoping_entity_id, business_id, status, description, last_mod_datetime, last_mod_id, create_datetime, create_id)
    VALUES ('51c05495139a1c0c7b31c338c8510856', 'org.nyc.cir', 'ICE', 'ACTIVE', 'Default ICE Profile', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- version

INSERT INTO cds_version (version_id, NAME, business_scope_id, version, status, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f89c7a73aff266756922d437e0ad8044', 'Default ICE Schedule', '51c05495139a1c0c7b31c338c8510856', '1.0.0', 'ACTIVE', 'Default ICE Schedule', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- code systems

INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e0bc41f880dc19d217656508b6cf9908', '2.16.840.1.113883.12.292', 'CVX', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e6b7f6483524ff91e2feae09fe4b1619', '2.16.840.1.113883.3.795.12.100.1', 'ICE Vaccine Group', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7f626b079d08d67d9dcfe835b72467a7', '2.16.840.1.113883.3.795.12.100.2', 'ICE Evaluation', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('26bb57f1188231aef8d95c48c9599225', '2.16.840.1.113883.3.795.12.100.3', 'ICE Evaluation Reason', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('424a3e1027d09abe37401b287db19dfa', '2.16.840.1.113883.3.795.12.100.4', 'Immunization Recommendation Focus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1a4b4d5cd30c24a5188bce0a54a4d122', '2.16.840.1.113883.3.795.12.100.5', 'ICE Recommendation', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fd199749f7ea8aa3acd7bc1ee733ab13', '2.16.840.1.113883.3.795.12.100.6', 'ICE Recommendation Reason', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('edd269b4b909d500d3b9f4973684c1d4', '2.16.840.1.113883.3.795.12.100.8', 'ICE Immunity Reason', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('77e021a72dc45592e7351331bf363f5d', '2.16.840.1.113883.3.795.12.100.9', 'ICE Immunity', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code_system (code_system_id, oid, name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8a74891e484ecf4a221d15d973427416', '2.16.840.1.113883.3.795.12.100.20', 'ICE Disease', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

--codes

-- codes for cvx
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10320d22a2631f2c5a07ee7734b80528', 'e0bc41f880dc19d217656508b6cf9908', '94', 'MMR-Varicella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1063d02b120f84c7d2e30b098404284d', 'e0bc41f880dc19d217656508b6cf9908', '118', 'HPV, bivalent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('115011c96e54f5e1cc044f76f69e77b2', 'e0bc41f880dc19d217656508b6cf9908', '119', 'rotavirus, monovalent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('127a169c5621fb76483f32714314ae9b', 'e0bc41f880dc19d217656508b6cf9908', '39', 'Japanese Encephalitis', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('16b7163c2814a310a9d392c23d76e143', 'e0bc41f880dc19d217656508b6cf9908', '07', 'mumps', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('16e24f863c0ebfc9324a2571508cea6a', 'e0bc41f880dc19d217656508b6cf9908', '19', 'BCG', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1a211cb73bc3b484d1e0852b18500a1e', 'e0bc41f880dc19d217656508b6cf9908', '106', 'DTaP, 5 pertussis antigens', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1a72a3652ad621ef6c142028bc827e1b', 'e0bc41f880dc19d217656508b6cf9908', '05', 'measles', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b3b5e305c967a32e20a9a447bad47e5', 'e0bc41f880dc19d217656508b6cf9908', '128', 'H1N1-09 NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1d6f24415002fc1ed3f31282eb3db7bb', 'e0bc41f880dc19d217656508b6cf9908', '137', 'HPV NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e55b9c7feed0f3d920f9a6523a69631', 'e0bc41f880dc19d217656508b6cf9908', '30', 'HBIG (Hepatitis B Immune Globulin)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2363716213b0f3a8e85243c976f3e7df', 'e0bc41f880dc19d217656508b6cf9908', '138', 'Td, not adsorbed', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2491447191b3f5a7fc25b83cb9c8bfa3', 'e0bc41f880dc19d217656508b6cf9908', '36', 'VZIG', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('26b5eadb78914bfa390dc8b1d7d7a37c', 'e0bc41f880dc19d217656508b6cf9908', '10', 'IPV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2a355af61f5612e3698f4b7a63c4775e', 'e0bc41f880dc19d217656508b6cf9908', '129', 'Japanese Encephalitis, NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2b2a73cb2fa719859549739222459af6', 'e0bc41f880dc19d217656508b6cf9908', '34', 'RIG', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2b5514e33f18fba4853f4f3717dc10b6', 'e0bc41f880dc19d217656508b6cf9908', '66', 'Lyme disease', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2fb18cfe6dc2701f7260e660ed0ee5c3', 'e0bc41f880dc19d217656508b6cf9908', '89', 'polio NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('31676df5fcd3d2f0dcbb7c4540096254', 'e0bc41f880dc19d217656508b6cf9908', '156', 'Rho (D) IG NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('34ce1c28ba3d41ee57762841cddf1360', 'e0bc41f880dc19d217656508b6cf9908', '24', 'anthrax', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('363f183aa6055d052fd7900e338cf559', 'e0bc41f880dc19d217656508b6cf9908', '32', 'meningococcal MPSV4', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('36fea054a6b78d2a10139b43480b1083', 'e0bc41f880dc19d217656508b6cf9908', '33', 'pneumococcal polysaccharide ', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('37faef5213ff09e3ef4906c32ef40e0b', 'e0bc41f880dc19d217656508b6cf9908', '28', 'DT, pediatric', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('397ed5fa6dbb10de74a309ed7c4216d8', 'e0bc41f880dc19d217656508b6cf9908', '82', 'Adenovirus, NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('39d17b6e86a657837bb1b2dfe11bfdb6', 'e0bc41f880dc19d217656508b6cf9908', '47', 'Hib (HbOC)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3fff865fe68daee74d69c5e0c7750d0e', 'e0bc41f880dc19d217656508b6cf9908', '149', 'influenza, live, intranasal, quadrivalent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('43041e87d8e921484eee385ac621cf9a', 'e0bc41f880dc19d217656508b6cf9908', '144', 'influenza, intradermal, preservative-free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('43c1aefb6b54e4f04bb00c87b206195e', 'e0bc41f880dc19d217656508b6cf9908', '02', 'OPV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45948d8e4691ce3a232ac1849308b9ed', 'e0bc41f880dc19d217656508b6cf9908', '91', 'Typhoid, NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45e87d2f7abe2edee107277fdb754006', 'e0bc41f880dc19d217656508b6cf9908', '157', 'Rho (D) IG IM', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('462cc4cb26baef19bbd57b4989b4fb40', 'e0bc41f880dc19d217656508b6cf9908', '29', 'CMVIG IV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('466ad98e153d516745ee566f6a685073', 'e0bc41f880dc19d217656508b6cf9908', '41', 'typhoid, parenteral', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('47fe49b03811a40755c47cd4b74ccdfa', 'e0bc41f880dc19d217656508b6cf9908', '09', 'Td, adult, absorbed', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4b4288d0fe3dc9bb7ed45ee727d1d88d', 'e0bc41f880dc19d217656508b6cf9908', '26', 'cholera', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5178e23c337a26a1ce17a4a73f8bf97b', 'e0bc41f880dc19d217656508b6cf9908', '48', 'Hib (PRP-T)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('52ba1d7a1ccfe5be84f6d1a9b5e608a3', 'e0bc41f880dc19d217656508b6cf9908', '151', 'influenza nasal, unspecified formulation', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5346f65f8d180de8781cdaaa5ffaff33', 'e0bc41f880dc19d217656508b6cf9908', '111', 'influenza, live, intranasal', CURRENT_TIMESTAMP, '8d8a9330a4f6e9f5af131df5c5ec6c7d', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('53f358d0b6e07c53d9a118c39a4182db', 'e0bc41f880dc19d217656508b6cf9908', '43', 'Hep B, adult', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('59e02e084e71ebc4cf13c0a748e4f47a', 'e0bc41f880dc19d217656508b6cf9908', '17', 'Hib NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5b04394f57e2cd25a8f995ff5d4d0854', 'e0bc41f880dc19d217656508b6cf9908', '21', 'varicella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6334689712dcc4ebf302c90968682853', 'e0bc41f880dc19d217656508b6cf9908', '49', 'Hib (PRP-OMP)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('66564dceb24841699e60b14e9b1d7b2b', 'e0bc41f880dc19d217656508b6cf9908', '55', 'Adenovirus, type 7', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6699c4d58521edb9c091072e976bc67d', 'e0bc41f880dc19d217656508b6cf9908', '114', 'meningococcal MCV4P', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('68a94ed7b016986a40f6551b3f7a9cbf', 'e0bc41f880dc19d217656508b6cf9908', '85', 'Hep A NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('696564f7f1fc0533d4f5dd85ed05cfea', 'e0bc41f880dc19d217656508b6cf9908', '15', 'influenza, split ', CURRENT_TIMESTAMP, '8d8a9330a4f6e9f5af131df5c5ec6c7d', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('699e36db6ca1ea07c9c4c2ee86053f96', 'e0bc41f880dc19d217656508b6cf9908', '153', 'Influenza, injectable, MDCK, (ccIIV3), trivalent, preservative free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('69bef82831628da319d3910067b9ef5c', 'e0bc41f880dc19d217656508b6cf9908', '25', 'typhoid, live, oral', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6a93b7b5eb793339480570defbe7fe14', 'e0bc41f880dc19d217656508b6cf9908', '141', 'influenza, injectable', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6b1fd318ceda97a08812b2a71ba80a67', 'e0bc41f880dc19d217656508b6cf9908', '46', 'Hib (PRP-D)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6c7949af1810cd1183895825be4b8ce1', 'e0bc41f880dc19d217656508b6cf9908', '126', 'H1N1-09, preservative-free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d3e2c6f7b0de8a9785bea8df68f0c47', 'e0bc41f880dc19d217656508b6cf9908', '122', 'rotavirus NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d5c6d027b55ff047b5e12e16d634f8e', 'e0bc41f880dc19d217656508b6cf9908', '50', 'DTaP-Hib', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d78bb9f5eff5cbfa9d0b2343ba91360', 'e0bc41f880dc19d217656508b6cf9908', '158', 'Influenza-IIV4, IM (>3yrs)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6e89da7570445099ccaca472a4510dbd', 'e0bc41f880dc19d217656508b6cf9908', '116', 'rotavirus, pentavalent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6ed89869ac0ace491bec4526dec327f8', 'e0bc41f880dc19d217656508b6cf9908', '01', 'DTP', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('71a48f0d94463afe5ff7a3aed4e3ee2e', 'e0bc41f880dc19d217656508b6cf9908', '42', 'Hep B, adolescent/high risk infant', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('72992266a4c25a5e6e5f132a9ede2680', 'e0bc41f880dc19d217656508b6cf9908', '52', 'Hep A, adult', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('72f4c00c91390f7a5df4a0d6fc492894', 'e0bc41f880dc19d217656508b6cf9908', '16', 'influenza, whole', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('74b3cbcaf3fbd8b5245706bd1501abfb', 'e0bc41f880dc19d217656508b6cf9908', '86', 'IG intramuscular', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b0195669cf49dbbacac7d82f3c70042', 'e0bc41f880dc19d217656508b6cf9908', '06', 'rubella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7c597a96f55a91f4357cb2121437af7a', 'e0bc41f880dc19d217656508b6cf9908', '54', 'Adenovirus, type 4', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7d3d80b067c1fa9390eb3b50f20075d9', 'e0bc41f880dc19d217656508b6cf9908', '100', 'pneumococcal conjugate PCV 7', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7d4f1b8fe4efbdb39cbd17398f54f29a', 'e0bc41f880dc19d217656508b6cf9908', '79', 'Vaccinia Immune Globulin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7d536a7617f76beda66bdbcf8b206878', 'e0bc41f880dc19d217656508b6cf9908', '159', 'Rho (D) IG IV or IM', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7e1471be78ff8ead9772f37fd6aac6cf', 'e0bc41f880dc19d217656508b6cf9908', '125', 'H1N1-09, nasal', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7e7eeda75d1ea89921cab7d86b66c447', 'e0bc41f880dc19d217656508b6cf9908', '44', 'Hep B, dialysis', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7fee2edcf1b3e44a94a16059bef44072', 'e0bc41f880dc19d217656508b6cf9908', '148', 'meningococcal C/Y-HIB PRP', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('807930df85c91a3ebe707f09b9c8c609', 'e0bc41f880dc19d217656508b6cf9908', '135', 'influenza, high dose ', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('84082e62128fabcd5a5364f78de9b631', 'e0bc41f880dc19d217656508b6cf9908', '152', 'pneumococcal conjugate NOS ', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87c5a8aa610515972ae3d2aa814e1b77', 'e0bc41f880dc19d217656508b6cf9908', '142', 'Tetanus toxoid, not adsorbed', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87db1f2fe083ec4ad63b869e22119d19', 'e0bc41f880dc19d217656508b6cf9908', '87', 'IG intravenous', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('886903982dada4949ab8488698671819', 'e0bc41f880dc19d217656508b6cf9908', '110', 'DTaP-Hep B-IPV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('89d9d0aa5f1e35db59a2734281bf3a63', 'e0bc41f880dc19d217656508b6cf9908', '45', 'Hep B NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8e3f621a6368e6d294211b84b09da1b8', 'e0bc41f880dc19d217656508b6cf9908', '113', 'Td, adult, preservative free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('93edb35732b676bc57f99858da3e11f7', 'e0bc41f880dc19d217656508b6cf9908', '23', 'plague', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('940719422398be2efc9ae08751bf417b', 'e0bc41f880dc19d217656508b6cf9908', '133', 'pneumococcal conjugate PCV 13', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('993ea3ebe57ee235d2affb1173e40388', 'e0bc41f880dc19d217656508b6cf9908', '161', 'Influenza-IIV4,IM Presrv-free (6-35mos)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('99acdd2bcedffac9e0205217da0357fa', 'e0bc41f880dc19d217656508b6cf9908', '11', 'pertussis', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9bb88b1daa2dafd48c265a9e3a7bc878', 'e0bc41f880dc19d217656508b6cf9908', '150', 'Influenza, injectable, quadrivalent (IIV4), preservative free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9c47e492cb267923614e4827fa3d6d2b', 'e0bc41f880dc19d217656508b6cf9908', '140', 'influenza, injectable, preservative-free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9cbb3c5ac11c3bc229e682ab99afc723', 'e0bc41f880dc19d217656508b6cf9908', '37', 'yellow fever', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9d8e6e80e7805d2de2594a5f11fd7fc7', 'e0bc41f880dc19d217656508b6cf9908', '109', 'pneumococcal NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9f4c6120a5c2be073a0519cb6f9b9d2f', 'e0bc41f880dc19d217656508b6cf9908', '31', 'Hep A, pediatric, NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9fcd623a2f22c928d8a8c01774c3ab92', 'e0bc41f880dc19d217656508b6cf9908', '40', 'rabies, intradermal injection', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a059147de03843241d61b1bf55cf7310', 'e0bc41f880dc19d217656508b6cf9908', '18', 'Rabies-intramuscular injection', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a0e226a6a44b1eafb30b6cb4ccef45c6', 'e0bc41f880dc19d217656508b6cf9908', '38', 'rubella/mumps', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a2cfcee1cefe42eab42e2318645d9f33', 'e0bc41f880dc19d217656508b6cf9908', '103', 'meningococcal C conjugate', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a58a28918894428bdefab782a7f1c215', 'e0bc41f880dc19d217656508b6cf9908', '08', 'Hep B, adolescent or pediatric', CURRENT_TIMESTAMP, '8d8a9330a4f6e9f5af131df5c5ec6c7d', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a94856e326cc369c5a5ebb1e53fd76e5', 'e0bc41f880dc19d217656508b6cf9908', '139', 'Td, adult NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a9527a3639c6a6c5a04d2728b7fc42c0', 'e0bc41f880dc19d217656508b6cf9908', '62', 'HPV, quadrivalent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('acf262fa464441cd22704f6f70839150', 'e0bc41f880dc19d217656508b6cf9908', '84', 'Hep A, ped/adol, 3-dose', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b6f1d7bd6d6e5fc6db728c48678967c5', 'e0bc41f880dc19d217656508b6cf9908', '166', 'Influenza-IIV4, ID Presrv-free(18-64yrs)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b6ffa4b8bc0847078f9be7209b5df876', 'e0bc41f880dc19d217656508b6cf9908', '130', 'DTaP-IPV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b787e25b2be97d5d1a8b230fee619326', 'e0bc41f880dc19d217656508b6cf9908', '71', 'RSV-IGIV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bb59ba73a1072418faaa463ee5f472dc', 'e0bc41f880dc19d217656508b6cf9908', '20', 'DTaP', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bd1b49f5a89652d6db5c587594136f42', 'e0bc41f880dc19d217656508b6cf9908', '155', 'influenza, recombinant, inj., preservative-free', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'amoniz');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('be777d762c0f14a4bf003e0a8a9d0fc6', 'e0bc41f880dc19d217656508b6cf9908', '120', 'DTaP-Hib-IPV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bee2f502a3d076e64a219e335957803f', 'e0bc41f880dc19d217656508b6cf9908', '51', 'Hib-Hep B', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c328a8db8b9fc54945997aaee0fbeb81', 'e0bc41f880dc19d217656508b6cf9908', '101', 'Typhoid ViCPs', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c955ffa4dc71edfe7b4f831ac3729c7d', 'e0bc41f880dc19d217656508b6cf9908', '127', 'H1N1-09, injectable', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cc422a1c3027b283a6caa0c7ef17e551', 'e0bc41f880dc19d217656508b6cf9908', '831', 'Meningococcal C & Y', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'amoniz');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d1fff1abfe5eab959f304de38f10d43c', 'e0bc41f880dc19d217656508b6cf9908', '147', 'meningococcal MCV4 NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d355695d931880b31ff86595b44f8985', 'e0bc41f880dc19d217656508b6cf9908', '165', 'HPV (HPV9-Gardasil 9)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d8d690813be570868cbfe9cdd63d9187', 'e0bc41f880dc19d217656508b6cf9908', '102', 'DTP-Hib-Hep B', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dbb90d607a346452e82c6a526f6dd727', 'e0bc41f880dc19d217656508b6cf9908', '115', 'Tdap', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dbfd3987a6a6b4b21e7e13ae5db1f787', 'e0bc41f880dc19d217656508b6cf9908', '121', 'zoster', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc738d07d4271415f8ea752c7c976ee5', 'e0bc41f880dc19d217656508b6cf9908', '14', 'IG NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dcb01536cd425956165af9d374c2baa8', 'e0bc41f880dc19d217656508b6cf9908', '136', 'meningococcal MCV4O', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e14d0cbe1a1d56c2ec14f94dfffb4fa8', 'e0bc41f880dc19d217656508b6cf9908', '12', 'diphtheria antitoxin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e3da750a24577410a9794edcb2f406b6', 'e0bc41f880dc19d217656508b6cf9908', '22', 'DTP-Hib', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e3f3ab4bb0643e385b69f33b34c01cf8', 'e0bc41f880dc19d217656508b6cf9908', '104', 'Hep A-Hep B', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e5d15051f018680a81ba7a0bb2f2e0dc', 'e0bc41f880dc19d217656508b6cf9908', '108', 'meningococcal NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e750f036712c026da038386ea237c67a', 'e0bc41f880dc19d217656508b6cf9908', '93', 'RSV-MAb (SYNAGIS)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e776fd17cf35191c5d729425f2b010ac', 'e0bc41f880dc19d217656508b6cf9908', '143', 'Adenovirus type 4 and 7', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e989ce64cfea38bc3fcb4e179ef6056d', 'e0bc41f880dc19d217656508b6cf9908', '35', 'tetanus toxoid, adsorbed', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ed444e8a59047e86b28e89e335b21bfb', 'e0bc41f880dc19d217656508b6cf9908', '03', 'MMR', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ee71f0da3bf130dfc532ce6dc309fca0', 'e0bc41f880dc19d217656508b6cf9908', '13', 'tetanus immune globulin (TIG)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f43885acb83bafd76ebec01b6f53f6cf', 'e0bc41f880dc19d217656508b6cf9908', '88', 'influenza NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f715a2ea209729e852117d0a305799e1', 'e0bc41f880dc19d217656508b6cf9908', '04', 'measles/rubella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f8ef6fc7a9f39e0f6e8a56aeda960db3', 'e0bc41f880dc19d217656508b6cf9908', '83', 'Hep A, ped/adol, 2-dose', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f9e3be777817ba68958c4fa7a3d89b90', 'e0bc41f880dc19d217656508b6cf9908', '107', 'DTaP NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fb284f06c8a73851707a9610789c8381', 'e0bc41f880dc19d217656508b6cf9908', '74', 'rotavirus, tetravalent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fe168be1731ef6bd3cb028766d49ff2c', 'e0bc41f880dc19d217656508b6cf9908', '27', 'botulinum antitoxin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- codes for ice groups
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e30a4dfba31908875df46e6a39c87ff', 'e6b7f6483524ff91e2feae09fe4b1619', '300', 'Hib', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('310518a288bb55558f9e5a614528e551', 'e6b7f6483524ff91e2feae09fe4b1619', '500', 'MMR', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('46b67611ecfde554df6e9fa2a03c0c44', 'e6b7f6483524ff91e2feae09fe4b1619', '100', 'HepB', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6081ee6ced25174c1dcee698a270be7e', 'e6b7f6483524ff91e2feae09fe4b1619', '800', 'Influenza', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6efadfb4961c7e87a0be9bc0b6ed47a0', 'e6b7f6483524ff91e2feae09fe4b1619', '400', 'Polio', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7564ba9956b5e1231317f0d35c0c0562', 'e6b7f6483524ff91e2feae09fe4b1619', '830', 'Meningococcal', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('89c6de3ec6e652b0ea9adb32c51b3b2d', 'e6b7f6483524ff91e2feae09fe4b1619', '999', 'Other', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8b71825560392a83ebb8e4da645e31f9', 'e6b7f6483524ff91e2feae09fe4b1619', '600', 'Varicella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8e7c271a449d8a0b43712eea56609953', 'e6b7f6483524ff91e2feae09fe4b1619', '200', 'DTP', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a0d81acc5ecff41b343d937031a2c3c7', 'e6b7f6483524ff91e2feae09fe4b1619', '700', 'Pneumococcal Conjugate', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a1bbadd1e2579d63fd83a3505c4e0c9c', 'e6b7f6483524ff91e2feae09fe4b1619', '720', 'Pneumococcal Polysaccharide', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a56086c8f7ea4be65a9872d610932f62', 'e6b7f6483524ff91e2feae09fe4b1619', '820', 'Rotavirus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ae074b7462f600e8f8f5b9e8972ff0aa', 'e6b7f6483524ff91e2feae09fe4b1619', '810', 'HepA', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d61dfbba9507faf0201b746027a614b1', 'e6b7f6483524ff91e2feae09fe4b1619', '840', 'Human Papillomavirus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f4028a479943d4f9d665b375c3e42c50', 'e6b7f6483524ff91e2feae09fe4b1619', '998', 'Multi-Component', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('faced2f118d43da024aee72e3fff7b99', 'e6b7f6483524ff91e2feae09fe4b1619', '890', 'H1N1 Influenza', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- codes for ice evaluation
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('11ab2b689d659f49b018e542ee6c0e84', '7f626b079d08d67d9dcfe835b72467a7', 'ACCEPTED', 'Accepted Immunization', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b9a3825ff53af790e6132e0c766e7035', '7f626b079d08d67d9dcfe835b72467a7', 'IGNORE', 'Ignore Immunization', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d46a55919980fa73aa4e2c42cf01916d', '7f626b079d08d67d9dcfe835b72467a7', 'INVALID', 'Invalid Immunization', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f773fab3de9c467d37130b2e8e02a7f1', '7f626b079d08d67d9dcfe835b72467a7', 'VALID', 'Valid Immunization', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- codes for ice eval reason
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2a828fb57c6c4d1fffffec2192f7005b', '26bb57f1188231aef8d95c48c9599225', 'BELOW_MINIMUM_INTERVAL', 'Below Minimum Interval', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('348c52d04be7d556becb9b8c1ee70c6e', '26bb57f1188231aef8d95c48c9599225', 'PROOF_OF_IMMUNITY', 'Proof of Immunity', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3b7d98e61499166ddf42d41423e0f526', '26bb57f1188231aef8d95c48c9599225', 'OUTSIDE_FLU_VAC_SEASON', 'Administered Outside of Influenza Vaccine Season', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('41964f474cb4bf4e938400dfcc024fe0', '26bb57f1188231aef8d95c48c9599225', 'BELOW_MINIMUM_AGE_SERIES', 'Below Minimum Age for Series', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('432a8991bf57233311c467e1911450b1', '26bb57f1188231aef8d95c48c9599225', 'WAITING_FOR_EVALUATION', 'Waiting for Evaluation', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45db24fb5667367fc155438ff495fe26', '26bb57f1188231aef8d95c48c9599225', 'WRONG_GENDER', 'Wrong Gender', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4d830b901901edc01e62571ad471acd6', '26bb57f1188231aef8d95c48c9599225', 'VACCINE_NOT_MEMBER_OF_SERIES', 'Vaccine Not Member of Series', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('57213fca1f901a6adf708be9e3407209', '26bb57f1188231aef8d95c48c9599225', 'INSUFFICIENT_ANTIGEN', 'Insufficient Antigen', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6f3e3b0276346b11af311e6477be233f', '26bb57f1188231aef8d95c48c9599225', 'INVALID_AGE', 'Invalid Age', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('75b8be1794967287eef76b398e70a646', '26bb57f1188231aef8d95c48c9599225', 'BELOW_REC_AGE_SERIES', 'Below Recommended Age for Series', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87413172bb85f80e9768af68b856a7b4', '26bb57f1188231aef8d95c48c9599225', 'ABOVE_MAX_AGE_VACCINE', 'Above Maximum Age for Vaccine', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('90120aea63b53909fd457036ceb8be36', '26bb57f1188231aef8d95c48c9599225', 'NOT_DEFINED', 'Not Defined', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('915680403ff70cbf8cedcdb02f166544', '26bb57f1188231aef8d95c48c9599225', 'PRIOR_TO_DOB', 'Prior to DOB', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('96d3c6f7811280e3a72e3d529889bfd7', '26bb57f1188231aef8d95c48c9599225', 'ABOVE_REC_AGE', 'Above Recommended Age', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a44d5aefe7bf02506f5703cb95fc2928', '26bb57f1188231aef8d95c48c9599225', 'ABOVE_REC_AGE_SERIES', 'Above Recommended Age for Series', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aca72fc41c7f72311cc1baa8e17d3156', '26bb57f1188231aef8d95c48c9599225', 'TOO_EARLY_LIVE_VIRUS', 'Too Early Live Virus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '8d8a9330a4f6e9f5af131df5c5ec6c7d');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b36cca7b23847708d121aed917aa11f3', '26bb57f1188231aef8d95c48c9599225', 'EXTRA_DOSE', 'Extra Dose', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b770bdcb9b9c2c94bc4f4860fdeed0a4', '26bb57f1188231aef8d95c48c9599225', 'DISEASE_DOCUMENTED', 'Disease Documented', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bc365d09ed40e231ee794bbbbd951dac', '26bb57f1188231aef8d95c48c9599225', 'BELOW_MINIMUM_AGE_VACCINE', 'Below Minimum Age for Vaccine', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bdec519c5d1ea06e5e233c03d22dea37', '26bb57f1188231aef8d95c48c9599225', 'BELOW_MIN_INTERVAL_PCV_PPSV', 'Below Minimum Interval between PCV and PPSV', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d7564f45ccf2014cab139b637eb735ba', '26bb57f1188231aef8d95c48c9599225', 'BOOSTER_ONLY', 'Booster Only', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dac424843b1e0477dcf5773d74642fd5', '26bb57f1188231aef8d95c48c9599225', 'BELOW_MINIMUM_AGE_FINAL_DOSE', 'Below Minimum Age for Final Dose', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('db6499a6c18228d447ed3fd93ecfb435', '26bb57f1188231aef8d95c48c9599225', 'DUPLICATE_SAME_DAY', 'Duplicate Same Day', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ddc29f45341ad9ef169a4e1e72730617', '26bb57f1188231aef8d95c48c9599225', 'NOT_LICENSED_FOR_MALES', 'Not Licensed for Males', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e0b81a627724eb67ce2f8c186aa1a829', '26bb57f1188231aef8d95c48c9599225', 'D_AND_T_INVALID/P_VALID', 'Diphtheria and tetanus invalid due to minimum interval violation, pertussis valid.', CURRENT_TIMESTAMP, 'regina', CURRENT_TIMESTAMP, 'regina');

-- codes for rec focus
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('339688fd9dfd407972b29566fea12be1', '424a3e1027d09abe37401b287db19dfa', '200', 'Immunization Recommendation Focus (DTaP)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3f672cb947025afcc839b0a588ef4e26', '424a3e1027d09abe37401b287db19dfa', '100', 'Immunization Recommendation Focus (Hep B)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('53fd9cca7a7fc58575a80b6333c98bde', '424a3e1027d09abe37401b287db19dfa', '300', 'Immunization Recommendation Focus (Hib)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('62c26b149bb51c23797a5c06f263acf4', '424a3e1027d09abe37401b287db19dfa', '400', 'Immunization Recommendation Focus (IPV)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('abbd6a12b8ab3805c6f649d4b1f8d55b', '424a3e1027d09abe37401b287db19dfa', '810', 'Immunization Recommendation Focus (Hep A)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- codes for rec
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('461cf7273598f747bf4f87be40c81856', '1a4b4d5cd30c24a5188bce0a54a4d122', 'COMPLETED', 'Series Completed', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6caad4861f355a6176d2ddcd338541b0', '1a4b4d5cd30c24a5188bce0a54a4d122', 'FUTURE_RECOMMENDED', 'Future Recommendation', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d44c6a07fdeab4eea549fce42fee8f5', '1a4b4d5cd30c24a5188bce0a54a4d122', 'RECOMMENDED', 'Recommended', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a842f4f74b14cbc3582d270ab59919bc', '1a4b4d5cd30c24a5188bce0a54a4d122', 'IGNORE', 'Ignore', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b1772f4680ea565059096f625ea283d0', '1a4b4d5cd30c24a5188bce0a54a4d122', 'NOT_RECOMMENDED', 'Not Recommended', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('edc905b5534a8414c0b6b7b5f8dae839', '1a4b4d5cd30c24a5188bce0a54a4d122', 'CONDITIONAL', 'Conditional', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- codes for rec reason
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1de59e603debc6e91575137a2194ff51', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'COMPLETE', 'Complete', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2aa66b1545223ed4d3c0fa45f5efa466', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'OUTSIDE_FLU_SEASON', 'Outside of Influenza Vaccine Season', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3868f01ac2be3842b429812dc4b54bdd', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'DUE_NOW', 'Due Now', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45513fe32b8d95cb19ebcaacf1adc547', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'HIGH_RISK', 'High Risk', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4d24f863c037e7e86cd499ff52952dc7', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'PROOF_OF_IMMUNITY', 'Proof of Immunity', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4e4e92bf69e5a547de07e6ab373f44ff', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'TOO_OLD', 'Too Old', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('767bcc8fc9f27fa6a22c038579e29c2d', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'VAC_GROUP_NO_LONGER_REC', 'Vaccine Group No Longer Recommended', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'amoniz');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7844fa10d5de2f1288df65714cc116ff', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'DISEASE_DOCUMENTED', 'Disease Documented', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b1db5ec01a127d69a6b6714b9c230e5e', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'DUE_IN_FUTURE', 'Due in Future', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc03869edd9b51837429d5e8c81be355', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'ABOVE_AGE_MAY_COMPLETE', 'Above Recommended Age But May Complete Series', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc8d62e6a007ce128ff6818d5ab39821', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'IGNORE', 'Ignore', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df606f44c96a6ae0509513b8523d59dd', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'TOO_OLD_HIGH_RISK', 'Too Old - High Risk Only', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e3ef6218f586119751ed5834be648db4', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'NOT_SPECIFIED', 'Not Specified', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e7ce26dcfded66093e91f29319a21d43', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'COMPLETE_HIGH_RISK', 'Series Complete Unless High Risk', CURRENT_TIMESTAMP, 'amoniz', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f3eaf38d6f9118d0b2168e7e6ab6efe7', 'fd199749f7ea8aa3acd7bc1ee733ab13', 'WRONG_GENDER', 'Wrong Gender', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- codes for immunity reason
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('575761dfd9f8d9a455dfb51d3919983c', 'edd269b4b909d500d3b9f4973684c1d4', 'DISEASE_DOCUMENTED', 'Disease Documented', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('715e8ee33a1a601038980748461bf7e9', 'edd269b4b909d500d3b9f4973684c1d4', 'PROOF_OF_IMMUNITY', 'Proof of Immunity', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- code for immunity
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('527c3ae3ea4d0283ee86e0ce6a7d6718', '77e021a72dc45592e7351331bf363f5d', 'IS_IMMUNE', 'Is Immune', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- code for disease
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1094c6e572a39a0a13a1b534251f175f', '8a74891e484ecf4a221d15d973427416', 'PERTUSSIS', 'Pertussis', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('330fbc8d4c137595f3adb54d93cc1978', '8a74891e484ecf4a221d15d973427416', 'ROTAVIRUS', 'Rotavirus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3abf1c8772c4bae57d338812cd9997e9', '8a74891e484ecf4a221d15d973427416', 'H1N1', 'H1N1 Influenza', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4f27cec9fd486d29935cc05b3ad185b4', '8a74891e484ecf4a221d15d973427416', 'INFLUENZA', 'Influenza', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('50a5fecb498c84202107718b940b9e5e', '8a74891e484ecf4a221d15d973427416', 'HPV', 'Human Papillomavirus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('50ebd184328992f2e1942a4d57abafa2', '8a74891e484ecf4a221d15d973427416', 'MEASLES', 'Measles', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c138da625d6ff08ac681db987c1f4af', '8a74891e484ecf4a221d15d973427416', 'MUMPS', 'Mumps', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('801bf6ead736240b7a238369c3005754', '8a74891e484ecf4a221d15d973427416', 'PNEUMOCOCCAL', 'Pneumococcal Pneumonia', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('81c85e43430ec651a931e5e4b8b19afd', '8a74891e484ecf4a221d15d973427416', 'VARICELLA', 'Varicella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('826a5180b6816dfe93bdeebfc3ddac49', '8a74891e484ecf4a221d15d973427416', 'TETANUS', 'Tetanus', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('83aeaca8fceb6f601c8ac5f19cc95fa0', '8a74891e484ecf4a221d15d973427416', 'POLIO', 'Acute Poliomyelitis', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8d58b13fcafa8c26fe46417a188a4eb0', '8a74891e484ecf4a221d15d973427416', 'HIB', 'Hib', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ac2cfcd44fc9a651f68fc8bb30d38238', '8a74891e484ecf4a221d15d973427416', 'HEPA', 'Hepatitis A', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b88473bbb2f9f8f524b6cb0c587b3a1a', '8a74891e484ecf4a221d15d973427416', 'MENINGOCOCCAL', 'Meningococcal', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d53eac28e4a8229cab9af40d2f5a3318', '8a74891e484ecf4a221d15d973427416', 'RUBELLA', 'Rubella', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ecd5a411d6a36beb26a66042261b5195', '8a74891e484ecf4a221d15d973427416', 'DIPHTHERIA', 'Diphtheria', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_code (code_id, code_system_id, code, display_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f3dbf7b984af74335867bfabc6482b69', '8a74891e484ecf4a221d15d973427416', 'HEPB', 'Hepatitis B', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- concepts

INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('102a2e4fd09b28228a9e44a24fe12dc3', 'ICE116', 'Rotavirus RV5 (RotaTeq, 3 dose)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10e4d96a58df7d7f743a90ad36541ac9', 'ICE150', 'influenza, injectable, quadrivalent, preservative free', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10fb5b1f93f7eba82ee23c2aec5561d9', 'ICE51', 'Hib-HepB (Comvax)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('11290f644fbf2a79d3ef3336bbfeb63e', 'ICE148', 'Mening C&Y-Hib PRP-T (Menhibrix)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('12dde5675c2a916f4f8a36a4d7cfb072', 'ICE228', 'Immunization Not Recommended (Wrong Gender)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1386388188a237e3c79939cfe9ba8006', 'ICE161', 'Influenza-IIV4,IM Presrv-free (6-35mos)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('138fd34a38afddffffccc5e0b23d048b', 'ICE122', 'Rotavirus NOS', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('13e71f70ad9630fcae7688fb37fa1389', 'ICE04', 'Measles/Rubella', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1413822a1d192f731cc2726d8c422001', 'ICE204', 'Immunization Evaluation (Hib Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('184d96ab9450466838e36975af8d5893', 'ICE05', 'Measles', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1889289e19733e8ba0ff193663bac407', 'ICE149', 'influenza, live, intranasal, quadrivalent', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('19055eb2fb7d943f3158fd48e462487e', 'ICE215', 'Immunization Recommendation Focus (DTaP)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('19d03d8091aa3f4da6d21e27d0843660', 'ICE45', 'HepB NOS', 'HepB NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('19fdf3518aea0b4d3c198509b4746295', 'ICE102', 'DTP-Hib-HepB', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b3a153dc564038b802335238fc6e2bf', 'ICE151', 'influenza nasal, unspecified formulation', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1c576ac631541b78f31463027978331b', 'ICE207', 'Valid Immunization', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1debbebcd1c494a8213ab05dd71f6d62', 'ICE03', 'MMR', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e59777d1dd3e3c4ea8c82e98639aed9', 'ICE155', 'influenza, recombinant, injectable, preservative free', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2294cfe16df746817d68315293a66aa8', 'ICE221', 'Immunization Not Recommended', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('24055466bcc6ec0fa62cc9c7c733df14', 'ICE276', 'Immunization Evaluation (Influenza Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('276423739f3aa1f032f193b0e8071be3', 'ICE01', 'DTP', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('27e528b13c8f5a59814117f0433da63c', 'ICE205', 'Immunization Evaluation (Hep A Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2be84f067521b33aa6e317f3415481d5', 'ICE30', 'HBIG (Hepatitis B Immune Globulin)', 'HBIG (Hepatitis B Immune Globulin)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2bf18017335250b555a8c3c6875f35b3', 'ICE158', 'Influenza-IIV4, IM (>3yrs)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2cb07905b5ccd9ac4f208f5bfb326e7d', 'ICE209', 'Immunization Accepted (Extra Dose)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2de4f72f1078230a474e31ff4e51ef35', 'ICE74', 'Rotavirus', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2fbe722c84f17892db0c660d40f2770c', 'ICE993', 'Disease Immunity Focus (Rubella)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3043187179ad37048a30a2dbbd6f1ad4', 'ICE44', 'HepB-dialysis', 'HepB-dialysis', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3080e94aa5bf59923bfdca9f72fe43e1', 'ICE223', 'Immunization Recommended (Due Now)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('32d5d1da56bf18c9a42450cbe1a77ea3', 'ICE273', 'Immunization Evaluation (HPV Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('336d08336003fc6656e1820666989895', 'ICE983', 'Disease Immunity Focus (Pertussis)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('35c28d47509f9e405ab749f9e032e2af', 'ICE16', 'influenza, whole', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('39c89978fd27b14a2403e2fe3673fc64', 'ICE111', 'influenza, live, intranasal', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('39cb978436a76ee52eac5d7a7bd6358f', 'ICE203', 'Immunization Evaluation (DTaP Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3f322f7e86a997179bdbd69c92ef6f6c', 'ICE1000', 'Proof of Immunity', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3f5b8c2360690eefeb33888f1834c75b', 'ICE985', 'Disease Immunity Focus (Influenza)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40b8efb0eb48b5d2bdeb47979512b64a', 'ICE85', 'HepA NOS', 'HepA NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('44fcfff24874a1809090867faa7cb694', 'ICE222', 'Immunization Recommended in Future', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('46a524937c6b47beb74e67977ab44aef', 'ICE984', 'Disease Immunity Focus (Meningococcal)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('46c3979a44a0577094bb66b348820427', 'ICE84', 'HepA pediatric/adolescent (3 dose)', 'HepA pediatric/adolescent (3 dose)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('492d826422926c69c47a88296b24c5bf', 'ICE202', 'Immunization Evaluation (Hep B Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('49b289efc9a44e8cee2a7d1ff9f6d319', 'ICE986', 'Disease Immunity Focus (HPV)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4de9953e65c12555d0d944e9ef544a25', 'ICE137', 'HPV NOS', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4ed70dfd50f032187d63bdbd6b6bfced', 'ICE38', 'Mumps/Rubella', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('504eed2afd40f3c8f6e9d805a59f3226', 'ICE06', 'Rubella', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('50c4a3779817b6aaedc7e1442d9c73f2', 'ICE127', 'Novel influenza-H1N1-09', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('51d5c41d19bf08b6201a5667a23b9f25', 'ICE280', 'Immunization Not Recommended (Not Specified)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5511651bd1110b4df4fbd90445667214', 'ICE21', 'Varicella', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5878d8e9ccc5bcb469d8862843c4fc29', 'ICE213', 'Immunization Invalid (Below Minimum Age)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('58ddc48d2d7a8a57a71275111863b20b', 'ICE49', 'Hib-PRP-OMP (PedvaxHIB)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5b08a0ba2e2bdd259f137a57b6cc83d4', 'ICE996', 'Disease Immunity Focus (HepA)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c4debcc6268bea71cc5d8ce5a79d399', 'ICE46', 'Hib-PRP-D (ProHIBIT)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5dbf4c241ea421019081812746c9126a', 'ICE152', 'Pneumococcal Conjugate NOS', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('61ee7ba7d89ec0995bdaa8c0665e53f9', 'ICE999', 'Disease Immunity Reason', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6208b41d8ad96940da4bf03ab5834e67', 'ICE225', 'Immunization Recommended in Future (Due In future)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('62a0af669acc1645c877de1f999c288e', 'ICE227', 'Immunization Not Recommended (Too Old)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6727a5cea85cfdb69844929db864d723', 'ICE218', 'Immunization Recommendation Focus (IPV)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('672c794bcf04406c28635f0c90904c43', 'ICE275', 'Immunization Evaluation (PPSV Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('677737d6c0c21c9cd8d6f927b7abd8e9', 'ICE47', 'Hib-HbOC (HibTITER)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6a1ae4b797d662b5b93ae1772a1e2b22', 'ICE141', 'influenza, seasonal, injectable', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6c7bebb4409d458b185c729bde62b711', 'ICE17', 'Hib NOS', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d5c5e09607d1f1072ceb0962f94f046', 'ICE135', 'influenza, high dose, seasonal', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6fdcddfe9d36d73e2e6664dc873e9815', 'ICE62', 'HPV Quadrivalent (Gardasil)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7469b4142956af6f9ae273ec7f564096', 'ICE88', 'influenza, unspecified formulation', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7a46c784275cefba9f603a377598ea24', 'ICE50', 'DTAP/Hib', 'DTAP/Hib', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7a77007c21f94e7349cc9c6d39ee8e45', 'ICE271', 'Immunization Evaluation (Varicella Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7a8ef07a731cf9a67a21aec0cb5ecf61', 'ICE278', 'Immunization Evaluation (Meningococcal Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7aa441fab874ac4de8e10449a6be361d', 'ICE831', 'Meningococcal C/Y', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b471a502ec26419b439b90ff26f756a', 'ICE128', 'Novel Influenza-H1N1-09, all formulations', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7e932a8c23ad4eec37aebf9ae1e2e101', 'ICE987', 'Disease Immunity Focus (H1N1)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('809d23aad49bd6b968027de55f72f40c', 'ICE230', 'Immunization Not Recommended (Proof of Immunity)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('80f34c1160abdfe335b87ff5eaaa972e', 'ICE219', 'Immunization Recommended', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('850090943836db29602c1e1fb7df5d2f', 'ICE989', 'Disease Immunity Focus (Polio)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8750f40889cb260882e190ea585228d6', 'ICE982', 'Disease Immunity Focus (Pneumococcal)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87a047178bbf376a8f700265dae17e2d', 'ICE206', 'Immunization Evaluation (Polio Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('89acab12939ce5cb6db325f559c77ec4', 'ICE211', 'Immunization Invalid (Prior to DOB)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8d0cbbbbdc7ea7b16c02cabe3c1d6c03', 'ICE220', 'Immunization Conditionally Recommended', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8e251c50bd38bb981132b959abb9daf3', 'ICE166', 'Influenza-IIV4, ID Presrv-free(18-64yrs)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8e39694d8fe12457edcda500f74e902d', 'ICE991', 'Disease Immunity Focus (Rotavirus)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('907aa9b3bf51feb6f01562f3fc88527d', 'ICE212', 'Immunization Invalid (Below Minimum Interval)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('94a9dcb9d3143bb7ec9f2918b5b99628', 'ICE208', 'Invalid Immunization', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9810e88f4c5b296d1991036ff3b95de8', 'ICE272', 'Immunization Evaluation (Rotavirus Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9dd4191d394145466537445cefe022da', 'ICE214', 'Immunization Recommendation Focus (Hep B)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a025f7e22ad9d7bc5df8438b67f66712', 'ICE118', 'HPV Bivalent (Cervarix)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a0394184f5f772394491eba2fdd2cd6d', 'ICE43', 'HepB adult >= 20yrs', 'HepB adult >= 20yrs', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a2ac7864a367aebef380e4af98dbd10e', 'ICE126', 'Novel influenza-H1N1-09, preservative-free', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a4f6af06dba794d3dfaa3b3051535641', 'ICE998', 'Disease Immunity Documented', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a5b18dc53cdb88393d7b7ab475653254', 'ICE994', 'Disease Immunity Focus (Mumps)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a7107045db775d88f6424726c017fb67', 'ICE140', 'influenza, seasonal, injectable, preservative free', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a80d7ce2e56e4ed1a21531658da7063d', 'ICE20', 'DTaP', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('acd1ae4fb14d3b2d6ac36e15f8cacbb7', 'ICE120', 'DTaP-Hib (PRP-T)-IPV', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('af74a21c4ba14bfd3a0924849dfddf24', 'ICE210', 'Immunization Invalid (Insufficient Antigen)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('afe13f163e3eda0853767f00c02aaafa', 'ICE42', 'HepB high risk infant', 'HepB high risk infant', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b0b54156779438ccee7deb6fc2a169ee', 'ICE94', 'MMR-Varicella', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b0fd342b9f5b6e47e4a3cc41b03b10d8', 'ICE133', 'Pneumococcal Conjugate', 'Pneumococcal Conjugate', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b95c5786746d7549dbe55f9e86e52eaa', 'ICE224', 'Immunization Conditionally Recommended (High Risk)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bba55374f7e04808262140c633cbda08', 'ICE201', 'Immunization-vaccination management', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bf5265afc7dba531b351010e3617af3c', 'ICE22', 'DTP-Hib (Tetramune; OmniHib-DTP)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bfc390292f863e801c9854ca248e2c0c', 'ICE165', 'HPV (HPV9-Gardasil 9)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c10a2862e338ccecab4c9567d26c3192', 'ICE15', 'influenza, split', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c21af61d49d2c59ad2fd96dfa1fc8147', 'ICE216', 'Immunization Recommendation Focus (Hib)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c299b5a35cc0c15c33b8d79d867f066b', 'ICE07', 'Mumps', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c470f1b18d7e423673daa5c51e528cce', 'ICE48', 'Hib-PRP-T (ActHIB, Hiberix)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c503f873a4b062a72509385156f08e5d', 'ICE270', 'Immunization Evaluation (MMR Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c6eadd032e897c62fc6cef8939336ce5', 'ICE274', 'Immunization Evaluation (PCV Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c9244d3cd3167420cc720b68749f18e6', 'ICE119', 'Rotavirus RV1 (Rotarix, 2 dose)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ca726c47e1a6aecc572877b2cdbe2d8b', 'ICE31', 'HepA pediatric NOS', 'HepA pediatric NOS', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cb85a7cb27878b365ad4e7ecb97856d4', 'ICE226', 'Immunization Not Recommended (Complete)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d09f820290275497949a2dc4b63e2cb9', 'ICE995', 'Disease Immunity Focus (Measles)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d21031b91c51d50177dfb897a59cb3e8', 'ICE992', 'Disease Immunity Focus (Varicella)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d3b8cb4755e5368e7c2cfe1714a87523', 'ICE109', 'Pneumococcal Conjugate NOS', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d7840c1215b97a29d9e0dfaa727f6fde', 'ICE153', 'influenza, injectable, MDCK, preservative free', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dab138bb7a47efb1fffca51b0ae3965d', 'ICE08', 'HepB peds <20yrs', 'HepB peds <20yrs', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('db3d31988458af71e7b83fa2dba19064', 'ICE144', 'influenza, seasonal, intradermal, preservative free', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc0a46d2b7763a6b34c1a6764d4b2950', 'ICE33', 'Pneumococcal Polysaccharide 23 Valent', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dd734acceb82d02eda3dddaf5f5722df', 'ICE100', 'Pneumococcal Conjugate 7 valent (PCV 7)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e10591982a5a9530c49e4989ce749af6', 'ICE988', 'Disease Immunity Focus (Diphtheria)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e23434e456e04fc97c5f73d883e09e86', 'ICE83', 'HepA peds/adol 2 dose', 'HepA peds/adol 2 dose', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e2b4ceac589c3fb1b28b722ce5571685', 'ICE104', 'HepA-HepB (Twinrix)', 'HepA-HepB (Twinrix)', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e2d298da31b2d03859f7898c42efcd21', 'ICE110', 'HepB peds <20yrs', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e4e29339f120d6e5a9a060d9446d2a9d', 'ICE990', 'Disease Immunity Focus (Hib)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e4f440e3affc483e736e85c8bd55462e', 'ICE229', 'Immunization Not Recommended (Disease Documented)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e538bf1f347073c8473cb02a4ce3b0f4', 'ICEDIPOI', 'Disease Immunity Source - Proof of Immunity', 'Disease Immunity Source - Proof of Immunity', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e5d0ee72c6eb914b702d283bde516b9e', 'ICE997', 'Disease Immunity Focus (HepB)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ed1bf058913d4806d4c7aa8fc774a87b', 'ICE981', 'Disease Immunity Focus (Tetanus)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eed02d9091aadceb30018f7ad4c45ab6', 'ICE217', 'Immunization Recommendation Focus (Hep A)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ef351c7232c4c51c9e217106302d846f', 'ICE190', 'Accepted Immunization', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('efa957475fbd88fe9b2ef107a57e27fa', 'ICE52', 'HepA adult', 'HepA adult', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f3273c9cadb1e052c534f3c046160c28', 'ICE277', 'Immunization Evaluation (H1N1 Vaccine Group)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f39c3892f2d78ea510f5a86d4db154d8', 'ICEDIDD', 'Disease Immunity Source - Disease Documented', 'Disease Immunity Source - Disease Documented', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fd1b1bb426800d08c5a33f2728eb13c1', 'ICE250', 'Immunization Invalid (Invalid Age)', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO opencds_concept (code_id, code, display_name, description, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ff166a5f3b060f15b138afd5d87cd198', 'ICE125', 'Novel Influenza-H1N1-09, nasal', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- concept mappings

INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('12b9c1202793d3cd489230258c65711b', 'dc0a46d2b7763a6b34c1a6764d4b2950', '36fea054a6b78d2a10139b43480b1083', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('1419adef166c5bc266b2e9fe3b6d0559', 'e2b4ceac589c3fb1b28b722ce5571685', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('14be81d0bb617e1995a22465cb6c04c7', 'c9244d3cd3167420cc720b68749f18e6', '115011c96e54f5e1cc044f76f69e77b2', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('162422cab276535d109041deabc8ccc5', '1debbebcd1c494a8213ab05dd71f6d62', 'ed444e8a59047e86b28e89e335b21bfb', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('1774622d3458e2fc90bfb7a72d9e8e22', '1386388188a237e3c79939cfe9ba8006', '993ea3ebe57ee235d2affb1173e40388', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('19401306bc69477b186593c97a97417c', 'e4e29339f120d6e5a9a060d9446d2a9d', 'b7c0ee3e3f7b6f85799fbaf15493f8dd', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('1dafe50722c6868328a4105413c11537', '4ed70dfd50f032187d63bdbd6b6bfced', 'a0e226a6a44b1eafb30b6cb4ccef45c6', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('1ecd78d566b64b31e84c0bb1aa335dee', 'b95c5786746d7549dbe55f9e86e52eaa', '45513fe32b8d95cb19ebcaacf1adc547', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('1f431f1dca8236cf70dc1c4741d823ea', 'bf5265afc7dba531b351010e3617af3c', 'e3da750a24577410a9794edcb2f406b6', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('2183f4173860c95af4b05dfec4d85569', '2be84f067521b33aa6e317f3415481d5', '1e55b9c7feed0f3d920f9a6523a69631', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('22bcf582c4512e36bd35aaa0dac3a9b6', '24055466bcc6ec0fa62cc9c7c733df14', '6081ee6ced25174c1dcee698a270be7e', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('22f519e1b449b514f5eefdeaadb0b086', '2fbe722c84f17892db0c660d40f2770c', '9d92df621a474bfa8a2d1b312002dcc5', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('246650f52f073bd35f6f49ab1c51ba18', 'c470f1b18d7e423673daa5c51e528cce', '5178e23c337a26a1ce17a4a73f8bf97b', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('24e2dbf4d1b058e8b410b6270834406c', '10fb5b1f93f7eba82ee23c2aec5561d9', 'bee2f502a3d076e64a219e335957803f', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('260998e054122342ee5d6fdabe381003', 'a5b18dc53cdb88393d7b7ab475653254', '5c138da625d6ff08ac681db987c1f4af', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('27a88d7f20b4da478dba6826424853eb', '61ee7ba7d89ec0995bdaa8c0665e53f9', '527c3ae3ea4d0283ee86e0ce6a7d6718', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('292d56d1e45e20297b97d845ad07300a', 'b0fd342b9f5b6e47e4a3cc41b03b10d8', '940719422398be2efc9ae08751bf417b', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('2b6c4b47da2cfdfaf72d3726ac151b48', 'ed1bf058913d4806d4c7aa8fc774a87b', 'db3fa0b93c44bdb68365c09e377770a3', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('2b74f8409a74a912ecc4185d71f56a8f', 'd7840c1215b97a29d9e0dfaa727f6fde', '699e36db6ca1ea07c9c4c2ee86053f96', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('2d67180d92ffadff34843f7d47002543', 'e23434e456e04fc97c5f73d883e09e86', 'f8ef6fc7a9f39e0f6e8a56aeda960db3', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('2ec78c702b17a89ffb62fc19a93781ed', 'e538bf1f347073c8473cb02a4ce3b0f4', '715e8ee33a1a601038980748461bf7e9', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3280a1a22edf1233300bb96660ee8a9b', '3f5b8c2360690eefeb33888f1834c75b', '56b47c5fc851f3a32ff594281990b5e6', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('32a176e0d2c3954aa1051e6a83b37bfc', 'cb85a7cb27878b365ad4e7ecb97856d4', '1de59e603debc6e91575137a2194ff51', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('35ff4dd35be6afe144c11af4bff44072', 'e4e29339f120d6e5a9a060d9446d2a9d', '8d58b13fcafa8c26fe46417a188a4eb0', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3752a83ccabea2277e3fec7251791951', 'b0b54156779438ccee7deb6fc2a169ee', '10320d22a2631f2c5a07ee7734b80528', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('382640723ff0df786122f1edeaf0fd17', '80f34c1160abdfe335b87ff5eaaa972e', '6d44c6a07fdeab4eea549fce42fee8f5', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('389562bf849c7f4e4d2ec02312658116', '12dde5675c2a916f4f8a36a4d7cfb072', 'f3eaf38d6f9118d0b2168e7e6ab6efe7', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3c2c7bd4b7b5346522acab9edcb85b41', '39cb978436a76ee52eac5d7a7bd6358f', '8e7c271a449d8a0b43712eea56609953', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3dadf100811cfbd8cecfaab688496d9f', '336d08336003fc6656e1820666989895', '1094c6e572a39a0a13a1b534251f175f', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3defc0ab7cd96a12d65a8d0132a6722d', 'a5b18dc53cdb88393d7b7ab475653254', 'f5373142f662ddc367bfee9244d279d2', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3e40025b6088476bec2c5717e589d39f', 'c299b5a35cc0c15c33b8d79d867f066b', '16b7163c2814a310a9d392c23d76e143', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('3f8cb5045eb3699463bcf1122ec21d30', '1413822a1d192f731cc2726d8c422001', '1e30a4dfba31908875df46e6a39c87ff', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('423a95771c9618ac6e4c518876b083c5', '7b471a502ec26419b439b90ff26f756a', '1b3b5e305c967a32e20a9a447bad47e5', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('4568f79d1dfabd17900aa030e86b7bd4', '4de9953e65c12555d0d944e9ef544a25', '1d6f24415002fc1ed3f31282eb3db7bb', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('482460e4cbf336d79f0d75ab11ce0115', '2fbe722c84f17892db0c660d40f2770c', 'd53eac28e4a8229cab9af40d2f5a3318', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('48b7b710de290dcbfd9270de8ae6f0fb', '50c4a3779817b6aaedc7e1442d9c73f2', 'c955ffa4dc71edfe7b4f831ac3729c7d', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('490fe1f2b6d1cb614a140ca97f77f71d', 'c10a2862e338ccecab4c9567d26c3192', '696564f7f1fc0533d4f5dd85ed05cfea', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('4a59e96028baa5624c3b22c13e71f591', 'dab138bb7a47efb1fffca51b0ae3965d', 'a58a28918894428bdefab782a7f1c215', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('4a7aa2deee1c1ba5f557ae597ccdecb6', '8750f40889cb260882e190ea585228d6', '78c90abce2b98b431963f8fd9d9df353', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('4b05776a66218f81a6114c79476c92d1', '276423739f3aa1f032f193b0e8071be3', '6ed89869ac0ace491bec4526dec327f8', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('4b65594df9edf39ced8871d689def766', '6727a5cea85cfdb69844929db864d723', '62c26b149bb51c23797a5c06f263acf4', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('4fd412a158987b3480c9fb4ba1302ea1', '8e39694d8fe12457edcda500f74e902d', '330fbc8d4c137595f3adb54d93cc1978', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('519c6a8fb9432413d0f16aaee9213dc8', 'a2ac7864a367aebef380e4af98dbd10e', '6c7949af1810cd1183895825be4b8ce1', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('51c8ecc6cfa87f8cebc2efc08c3841f2', 'c6eadd032e897c62fc6cef8939336ce5', 'a0d81acc5ecff41b343d937031a2c3c7', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('5308067e8df7157dc071ae8337ee7355', 'af74a21c4ba14bfd3a0924849dfddf24', '57213fca1f901a6adf708be9e3407209', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('532d7193eaeeff0548f6e28a79557617', '5b08a0ba2e2bdd259f137a57b6cc83d4', '59b23f3bd35da1477e29b2464ed5c525', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('56a3de2df029940338396552ebfe8ebd', '672c794bcf04406c28635f0c90904c43', 'a1bbadd1e2579d63fd83a3505c4e0c9c', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('5a62b922d11b3990c58e9d72cf1e6ef4', '49b289efc9a44e8cee2a7d1ff9f6d319', '1373d79652d8ae8027785a4ce670c0a7', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('5e2ffc632785f2f1717e0a4c189aa442', '5878d8e9ccc5bcb469d8862843c4fc29', '41964f474cb4bf4e938400dfcc024fe0', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('609583461ff3381fb99cbc2c83f3f643', '5dbf4c241ea421019081812746c9126a', '84082e62128fabcd5a5364f78de9b631', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('62ef08e9b95854447407165ac9f9247f', 'd09f820290275497949a2dc4b63e2cb9', 'c97adc04eccb8a88c3e3aa29289ff35d', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('65b369723e838fede7bde32479862e3f', 'd21031b91c51d50177dfb897a59cb3e8', '5d5271debc5f5605cc16adca2600bbaa', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('67a7630158f33c9c66aa5c279750025c', '7a8ef07a731cf9a67a21aec0cb5ecf61', '7564ba9956b5e1231317f0d35c0c0562', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('67b558cb926a67846f34cc29d13f9773', '8750f40889cb260882e190ea585228d6', '801bf6ead736240b7a238369c3005754', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('68618c537c4eef5da28e889377afc57d', '6a1ae4b797d662b5b93ae1772a1e2b22', '6a93b7b5eb793339480570defbe7fe14', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('68b3351ca149498049dce1b6125e0cf4', '87a047178bbf376a8f700265dae17e2d', '6efadfb4961c7e87a0be9bc0b6ed47a0', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('6a00204fbd706cfda1402190b434f1ad', '9810e88f4c5b296d1991036ff3b95de8', 'a56086c8f7ea4be65a9872d610932f62', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('6a2315f2a2a17d009921ea1173430217', 'c21af61d49d2c59ad2fd96dfa1fc8147', '53fd9cca7a7fc58575a80b6333c98bde', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('6a8a53354f4a2f4f2a76b78a3fb6154b', '19fdf3518aea0b4d3c198509b4746295', 'd8d690813be570868cbfe9cdd63d9187', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('6adfcfb879df0a30443c68c7bb7fd917', 'bfc390292f863e801c9854ca248e2c0c', 'd355695d931880b31ff86595b44f8985', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('6e1fd191222833fcb4868f6e545e7a86', '1e59777d1dd3e3c4ea8c82e98639aed9', 'bd1b49f5a89652d6db5c587594136f42', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('6ef7d48df740f1f2411a2d3d925c2ec5', '9dd4191d394145466537445cefe022da', '3f672cb947025afcc839b0a588ef4e26', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('72a24793d87954e69cd465b2c9c68185', '7aa441fab874ac4de8e10449a6be361d', 'cc422a1c3027b283a6caa0c7ef17e551', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('7467163ea68f2a2b92025e4912539d57', '5511651bd1110b4df4fbd90445667214', '5b04394f57e2cd25a8f995ff5d4d0854', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('766f18935a783c213558c1f66bd7ffae', 'e23434e456e04fc97c5f73d883e09e86', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('7cc3d32d847cfa57fcd7439a2818a5c6', '102a2e4fd09b28228a9e44a24fe12dc3', '6e89da7570445099ccaca472a4510dbd', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('7de3f1dbc7b999b86d3a73a8fe65c17d', '19d03d8091aa3f4da6d21e27d0843660', '89d9d0aa5f1e35db59a2734281bf3a63', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('813d4c97ca2149c8dd62d4ace4a7ffbb', '184d96ab9450466838e36975af8d5893', '1a72a3652ad621ef6c142028bc827e1b', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('86316a22b347f081a15b9209346995d3', '62a0af669acc1645c877de1f999c288e', '4e4e92bf69e5a547de07e6ab373f44ff', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('894a624e230ec75e44de5233307b3bb1', '504eed2afd40f3c8f6e9d805a59f3226', '7b0195669cf49dbbacac7d82f3c70042', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('8ab17384854c1987b62da8c1cd9b62f0', '6fdcddfe9d36d73e2e6664dc873e9815', 'a9527a3639c6a6c5a04d2728b7fc42c0', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('8aefeb99bd86c56c7722ccae667614d3', '8e251c50bd38bb981132b959abb9daf3', 'b6f1d7bd6d6e5fc6db728c48678967c5', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('901104e66189c4b6b209140659187082', '3f5b8c2360690eefeb33888f1834c75b', '4f27cec9fd486d29935cc05b3ad185b4', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('91646227518e0271143b47eada9af2c9', 'f39c3892f2d78ea510f5a86d4db154d8', '575761dfd9f8d9a455dfb51d3919983c', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('95901c7c6fd920c9af4c5ac64b74cced', '19055eb2fb7d943f3158fd48e462487e', '339688fd9dfd407972b29566fea12be1', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('95f673a323c0d17d86d82253e1617143', '336d08336003fc6656e1820666989895', 'c5afe2a5f824a9305ccb51c9231b036c', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('962a2ba8cdffbef02802d356a7c5161f', '6d5c5e09607d1f1072ceb0962f94f046', '807930df85c91a3ebe707f09b9c8c609', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('97509e9b5590dca27fc3a3278b272636', 'dab138bb7a47efb1fffca51b0ae3965d', '886903982dada4949ab8488698671819', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('97f41eb1df248b5b1541a96ebc43caec', 'afe13f163e3eda0853767f00c02aaafa', '71a48f0d94463afe5ff7a3aed4e3ee2e', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('9a7a843ad23244325a90cbab46c2a06a', 'd3b8cb4755e5368e7c2cfe1714a87523', '9d8e6e80e7805d2de2594a5f11fd7fc7', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('9be72bec2204eb2676d6708fe79935d4', '809d23aad49bd6b968027de55f72f40c', '4d24f863c037e7e86cd499ff52952dc7', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('9cb3b27c2a72dc227cce3391d8504df8', '492d826422926c69c47a88296b24c5bf', '46b67611ecfde554df6e9fa2a03c0c44', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('9d8b90fe4bce17cf3148f9893efe0619', 'db3d31988458af71e7b83fa2dba19064', '43041e87d8e921484eee385ac621cf9a', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('9f4231456fb12950d4343c3fd4b11340', '10e4d96a58df7d7f743a90ad36541ac9', '9bb88b1daa2dafd48c265a9e3a7bc878', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('9fdec702925c593c6094659c507eadd6', 'dd734acceb82d02eda3dddaf5f5722df', '7d3d80b067c1fa9390eb3b50f20075d9', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('a5a91c4f47a4f13a3f218f71d5b2703c', 'e5d0ee72c6eb914b702d283bde516b9e', '7c2c9ca01cbd1148dd8e381dc062cd21', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('a83a1a986341d5ab67378559f883d133', '40b8efb0eb48b5d2bdeb47979512b64a', '68a94ed7b016986a40f6551b3f7a9cbf', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('a9897d2f3604fdb0ec8ed40cc3fb7c57', 'ef351c7232c4c51c9e217106302d846f', '11ab2b689d659f49b018e542ee6c0e84', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('ae97e6b84488c2da6de1ba67ac41a3e7', '8d0cbbbbdc7ea7b16c02cabe3c1d6c03', 'edc905b5534a8414c0b6b7b5f8dae839', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b0a9a3463da4f1d6219ba2f52508a193', '3080e94aa5bf59923bfdca9f72fe43e1', '3868f01ac2be3842b429812dc4b54bdd', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b1307f9acbca8ac37face185e948af9c', '7e932a8c23ad4eec37aebf9ae1e2e101', '812f35323238c662608f9f47904bcc56', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b17efc852ec95a1234e07c243dc98371', 'e10591982a5a9530c49e4989ce749af6', 'd82844df4fbf6cfa55dc91dbe47b3e9b', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b1893e2e3b71fe538008084461d6850b', 'a0394184f5f772394491eba2fdd2cd6d', '53f358d0b6e07c53d9a118c39a4182db', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b3cef58236a565f4749b6414792b07dc', '138fd34a38afddffffccc5e0b23d048b', '6d3e2c6f7b0de8a9785bea8df68f0c47', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b400385b9eaab191ccb4fc2b3e3af512', 'e2b4ceac589c3fb1b28b722ce5571685', '89d9d0aa5f1e35db59a2734281bf3a63', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b4ec7223c211a9a74ab1fc605c1bbf92', 'e10591982a5a9530c49e4989ce749af6', 'ecd5a411d6a36beb26a66042261b5195', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b627c514fe7d91a7a8b3108879896649', '35c28d47509f9e405ab749f9e032e2af', '72f4c00c91390f7a5df4a0d6fc492894', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('b6d93ca543d9c27fcf82bdf7a487af81', '27e528b13c8f5a59814117f0433da63c', 'ae074b7462f600e8f8f5b9e8972ff0aa', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('bd21b0a0588e0f8160a0cb3fbc8f7268', 'acd1ae4fb14d3b2d6ac36e15f8cacbb7', 'be777d762c0f14a4bf003e0a8a9d0fc6', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('be1d3ae2f1e765676d2ab540fd4175b7', 'f3273c9cadb1e052c534f3c046160c28', 'faced2f118d43da024aee72e3fff7b99', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('bf0b81f89f29831a8b8d7e6726175e51', '7a77007c21f94e7349cc9c6d39ee8e45', '8b71825560392a83ebb8e4da645e31f9', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c13f6fad59fe54ab165f6c1740c2932d', '44fcfff24874a1809090867faa7cb694', '6caad4861f355a6176d2ddcd338541b0', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c169e574732ec66bd8825898fe14fb9f', 'e2d298da31b2d03859f7898c42efcd21', '886903982dada4949ab8488698671819', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c17a1eeb8890ff311fa1e0526066649a', '46c3979a44a0577094bb66b348820427', 'acf262fa464441cd22704f6f70839150', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c2806105b75353f958facf50401d5700', '7e932a8c23ad4eec37aebf9ae1e2e101', '3abf1c8772c4bae57d338812cd9997e9', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c3c0e9b0ea654559bfc46bd48e0bd6b7', '46a524937c6b47beb74e67977ab44aef', 'b88473bbb2f9f8f524b6cb0c587b3a1a', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c3dd2981b6322d87ddcf7191c27b2345', '89acab12939ce5cb6db325f559c77ec4', '915680403ff70cbf8cedcdb02f166544', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c6a1355d85e41eefe46d8b0b21790b7c', 'efa957475fbd88fe9b2ef107a57e27fa', '72992266a4c25a5e6e5f132a9ede2680', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c7691b8a124e22634fc05b1fd8f34402', 'dab138bb7a47efb1fffca51b0ae3965d', 'bee2f502a3d076e64a219e335957803f', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c93276bbbe06db77f57efb764d78d76c', '51d5c41d19bf08b6201a5667a23b9f25', 'e3ef6218f586119751ed5834be648db4', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c948c88e5000fa7ff091fa61a64f25fe', '850090943836db29602c1e1fb7df5d2f', 'db8028ae56f91975c57f026ed9a9c321', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('c962d33f1274b2c86e4fe0efaa281fcd', '907aa9b3bf51feb6f01562f3fc88527d', '2a828fb57c6c4d1fffffec2192f7005b', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('ca4c487679c1bed32fd60de9458b10e6', '49b289efc9a44e8cee2a7d1ff9f6d319', '50a5fecb498c84202107718b940b9e5e', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('cc16ae52fd419687776ea0dc7a191463', 'ca726c47e1a6aecc572877b2cdbe2d8b', '9f4c6120a5c2be073a0519cb6f9b9d2f', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('cc3c375189a0b44b83f45a40b15e31b9', '8e39694d8fe12457edcda500f74e902d', '3062ef2e4282ab57d8a5b0113d0bf267', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('cd67228a90bdf651ae47fe103b73f251', 'a4f6af06dba794d3dfaa3b3051535641', '575761dfd9f8d9a455dfb51d3919983c', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('cf1ea0eab3ce52d87a5316fa535f512f', '1b3a153dc564038b802335238fc6e2bf', '52ba1d7a1ccfe5be84f6d1a9b5e608a3', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('cf96ebe82a5c23baf10b32753c81bf7d', '2cb07905b5ccd9ac4f208f5bfb326e7d', 'b36cca7b23847708d121aed917aa11f3', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d01b1d93ec362c0591b460591b34495a', 'a80d7ce2e56e4ed1a21531658da7063d', 'bb59ba73a1072418faaa463ee5f472dc', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d083c6463d197dee1c17a4e8c8db6abf', 'ed1bf058913d4806d4c7aa8fc774a87b', '826a5180b6816dfe93bdeebfc3ddac49', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d2d65dd1eb59db6adc805e69204b5770', '7469b4142956af6f9ae273ec7f564096', 'f43885acb83bafd76ebec01b6f53f6cf', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d35606d24e25775391dcd7288a573aa3', 'c503f873a4b062a72509385156f08e5d', '310518a288bb55558f9e5a614528e551', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d388c8ea5234250c6503484c7b6873d1', '2de4f72f1078230a474e31ff4e51ef35', 'fb284f06c8a73851707a9610789c8381', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d49d3e5270b747656b73b9d9e80545dc', 'eed02d9091aadceb30018f7ad4c45ab6', 'abbd6a12b8ab3805c6f649d4b1f8d55b', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d4dd3c9ed36e5c609ad2c6e9de306aa9', 'ff166a5f3b060f15b138afd5d87cd198', '7e1471be78ff8ead9772f37fd6aac6cf', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d5469954491fb0b226f75c26c286c627', '3043187179ad37048a30a2dbbd6f1ad4', '7e7eeda75d1ea89921cab7d86b66c447', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d741268b7514e74a35af365c9bd88912', 'd09f820290275497949a2dc4b63e2cb9', '50ebd184328992f2e1942a4d57abafa2', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('d9308c94a56d09dea95f111689b582c3', '677737d6c0c21c9cd8d6f927b7abd8e9', '39d17b6e86a657837bb1b2dfe11bfdb6', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('dc44227ee9c376f6463c31894216d4a8', 'a025f7e22ad9d7bc5df8438b67f66712', '1063d02b120f84c7d2e30b098404284d', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('de72d6132da5a0a9cf8874ddabbce085', '7a46c784275cefba9f603a377598ea24', '6d5c6d027b55ff047b5e12e16d634f8e', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('def72bf9b5e3792d3a433e93954b9895', 'dab138bb7a47efb1fffca51b0ae3965d', 'e3f3ab4bb0643e385b69f33b34c01cf8', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('df7696018b696712eb2184f6fa569898', '46a524937c6b47beb74e67977ab44aef', 'bf56c457d473e84a6c38847c61e6a4fd', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e007c5bbf42823ec9b9cecf64e76559d', 'd21031b91c51d50177dfb897a59cb3e8', '81c85e43430ec651a931e5e4b8b19afd', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e0e58e99f980bdc56f3c57b79dac3617', '13e71f70ad9630fcae7688fb37fa1389', 'f715a2ea209729e852117d0a305799e1', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e578cb84f9a8666e28c7fb5e08dc8fe3', 'a7107045db775d88f6424726c017fb67', '9c47e492cb267923614e4827fa3d6d2b', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e5e3488f8d0eca53e6b882a34ad7e417', '5b08a0ba2e2bdd259f137a57b6cc83d4', 'ac2cfcd44fc9a651f68fc8bb30d38238', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e651aaa1d0572ed541cd75a58796721b', '94a9dcb9d3143bb7ec9f2918b5b99628', 'd46a55919980fa73aa4e2c42cf01916d', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e78da89985e7ddf1a7bda50a6e065811', '6c7bebb4409d458b185c729bde62b711', '59e02e084e71ebc4cf13c0a748e4f47a', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e79413e348bbe95891d8ad3fce06b9fb', '2bf18017335250b555a8c3c6875f35b3', '6d78bb9f5eff5cbfa9d0b2343ba91360', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e7d39680449a66ccc83694e589d201e4', '2294cfe16df746817d68315293a66aa8', 'b1772f4680ea565059096f625ea283d0', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e8c45177c0108c54aee4be19eefa9020', '58ddc48d2d7a8a57a71275111863b20b', '6334689712dcc4ebf302c90968682853', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('e9e004cd8cf91b0f31c31119af3fd616', '32d5d1da56bf18c9a42450cbe1a77ea3', 'd61dfbba9507faf0201b746027a614b1', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('ea7fc5e184a26514fbe17ae33735b4b3', '11290f644fbf2a79d3ef3336bbfeb63e', '7fee2edcf1b3e44a94a16059bef44072', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('ef6da1999f1e94cf9f877976668dabbb', '5c4debcc6268bea71cc5d8ce5a79d399', '6b1fd318ceda97a08812b2a71ba80a67', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('efde92b935c83a8e5751550d842ff88e', '3f322f7e86a997179bdbd69c92ef6f6c', '715e8ee33a1a601038980748461bf7e9', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('f1e315eb3b0d3710a07d702d1fee5550', '1889289e19733e8ba0ff193663bac407', '3fff865fe68daee74d69c5e0c7750d0e', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('f3e553ca854328db410fb00a33708b52', 'e5d0ee72c6eb914b702d283bde516b9e', 'f3dbf7b984af74335867bfabc6482b69', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('f4381096e7e272c78bffb8da7632c6a6', 'e4f440e3affc483e736e85c8bd55462e', '7844fa10d5de2f1288df65714cc116ff', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('f6d78c4181dbd7e2aa1ef70a8ca2204c', '850090943836db29602c1e1fb7df5d2f', '83aeaca8fceb6f601c8ac5f19cc95fa0', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('fa644afed979fd61f922c63ace4b3496', '39c89978fd27b14a2403e2fe3673fc64', '5346f65f8d180de8781cdaaa5ffaff33', 'cdb46802ca1e8385a2175f566a47b20d', NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('fafa5c50c715ebc6dd9f8c64386c72f3', '6208b41d8ad96940da4bf03ab5834e67', 'b1db5ec01a127d69a6b6714b9c230e5e', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');
INSERT INTO opencds_concept_rel (relationship_id, concept_code_id, cds_code_id, determination_method, specification_notes, last_mod_datetime, last_mod_id, create_datetime, create_id, mapping_type) 
    VALUES ('fe9619444c6472fc3c2d6a22b22385db', '1c576ac631541b78f31463027978331b', 'f773fab3de9c467d37130b2e8e02a7f1', 'cdb46802ca1e8385a2175f566a47b20d', 'Created manually.', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin', 'CODE');

-- value sets
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1a31291daeb4d048d068dd0299d100b2', 'ICE_REC_REASON_FUTURE_RECOMMENDED', 'ICE Recommendation Reason Value Set: Future Recommended', '7.1.1.1', NULL, '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2068fdb3e52b1c2015fdfd774223247b', 'ICD9_IMM_DIS', 'ICD9 Immunization Disease Value Set', '1.1.1.1', 'Sub-set of ICD9 Diagnosis codes specific to immunizations', '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d58eb602db0cfb2fa0a098615fe661f', 'ICE_REC_REASON_RECOMMENDED', 'ICE Recommendation Reason Value Set: Recommended', '4.1.1.1', NULL, '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7f89f59ebc4bad23b4c21533d7c09256', 'ICE_REC_REASON_NOT_RECOMMENDED', 'ICE Recommendation Reason Value Set: Not Recommended', '6.1.1.1', NULL, '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('845fc0475958c1889342bd8f19edd51e', 'ICE_EVAL_REASON_INVALID', 'ICE Evaluation Reason Value Set: Invalid', '2.1.1.1', NULL, '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c2cc3827bee46d612f009a8ef470f238', 'ICE_EVAL_REASON_ACCEPTED', 'ICE Evaluation Reason Value Set: Accepted', '3.1.1.1', NULL, '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set (value_set_id, code, name, oid, description, version, version_description, version_effective_date, version_expiration_date, version_status, value_set_type, source, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb679ebcd8d519bd5633d61672ff7c56', 'ICE_REC_REASON_CONDITIONAL', 'ICE Recommendation Reason Value Set: Conditional', '5.1.1.1', NULL, '1.0', NULL, NULL, NULL, 'Active', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- value set mappings

INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10d276655b44d45fcd10d2d1f1ccf140', '2068fdb3e52b1c2015fdfd774223247b', 'db8028ae56f91975c57f026ed9a9c321', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('113ff081836c6698dda136e341a479ad', 'eb679ebcd8d519bd5633d61672ff7c56', '45513fe32b8d95cb19ebcaacf1adc547', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b82d942786aeb5e7ad2d11faddd40a4', '2068fdb3e52b1c2015fdfd774223247b', 'd82844df4fbf6cfa55dc91dbe47b3e9b', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1cdf9d4f936f7abdfee828f6f9bfc63c', '845fc0475958c1889342bd8f19edd51e', '57213fca1f901a6adf708be9e3407209', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('218d287340bb668ce5e805e94a94a2f5', '7f89f59ebc4bad23b4c21533d7c09256', 'e7ce26dcfded66093e91f29319a21d43', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('22016b8bd15a7114758a8cbc69018120', '845fc0475958c1889342bd8f19edd51e', '41964f474cb4bf4e938400dfcc024fe0', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('274ea057ad607c6ca9aaabefdb3ef155', '2068fdb3e52b1c2015fdfd774223247b', '812f35323238c662608f9f47904bcc56', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('348cd21d98e885d53fda25a9631d1400', '2068fdb3e52b1c2015fdfd774223247b', '3062ef2e4282ab57d8a5b0113d0bf267', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3959afc6560d15742c321db2829ce8db', '2068fdb3e52b1c2015fdfd774223247b', 'c5afe2a5f824a9305ccb51c9231b036c', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3bc62b6db7f94e73e6c2506f1aec5e03', '2068fdb3e52b1c2015fdfd774223247b', '56b47c5fc851f3a32ff594281990b5e6', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c9ecbcf1f879765d2b218785d5cfab3', '7f89f59ebc4bad23b4c21533d7c09256', '4d24f863c037e7e86cd499ff52952dc7', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3fba395f95203a684ece7cf9a6af7445', '7f89f59ebc4bad23b4c21533d7c09256', 'df606f44c96a6ae0509513b8523d59dd', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40a51896a0df836fed1c73cee820748a', 'c2cc3827bee46d612f009a8ef470f238', 'b770bdcb9b9c2c94bc4f4860fdeed0a4', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('43c68dc421224c42874232d7d531626c', '845fc0475958c1889342bd8f19edd51e', 'bc365d09ed40e231ee794bbbbd951dac', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('467525eb66a1d94d92ccc66c13562965', '1a31291daeb4d048d068dd0299d100b2', 'b1db5ec01a127d69a6b6714b9c230e5e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('48633ba124634046266eb23b32d73acb', '7f89f59ebc4bad23b4c21533d7c09256', '4e4e92bf69e5a547de07e6ab373f44ff', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4c5c872ba97478365cb25125b974cc56', '7f89f59ebc4bad23b4c21533d7c09256', '1de59e603debc6e91575137a2194ff51', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('54487c2d759e804cb1cfb693f08d8068', '2068fdb3e52b1c2015fdfd774223247b', '9d92df621a474bfa8a2d1b312002dcc5', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('54e84e11eb373cbd50404e9563720155', '2068fdb3e52b1c2015fdfd774223247b', '5d5271debc5f5605cc16adca2600bbaa', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('591a2b001fda360354a69b551e463a34', '845fc0475958c1889342bd8f19edd51e', 'bdec519c5d1ea06e5e233c03d22dea37', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5f7eb15269315984b4d0483a3b260048', '845fc0475958c1889342bd8f19edd51e', 'd7564f45ccf2014cab139b637eb735ba', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('61b584b3bbfc8b9616572faf5cb34e3d', 'c2cc3827bee46d612f009a8ef470f238', '75b8be1794967287eef76b398e70a646', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6398a250594bb7e23c5c80701d9c33c9', '7f89f59ebc4bad23b4c21533d7c09256', '7844fa10d5de2f1288df65714cc116ff', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6994aae374f063f7d542d0cca636866d', '845fc0475958c1889342bd8f19edd51e', 'dac424843b1e0477dcf5773d74642fd5', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('70cf0a4fa09f3aaf1fd6fb0497cd22c6', '845fc0475958c1889342bd8f19edd51e', 'db6499a6c18228d447ed3fd93ecfb435', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7a3e47c0748299a5af31d6c755716371', '2068fdb3e52b1c2015fdfd774223247b', '78c90abce2b98b431963f8fd9d9df353', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8cb786ee454e8bb2c8fadf42c111d730', '6d58eb602db0cfb2fa0a098615fe661f', '3868f01ac2be3842b429812dc4b54bdd', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8ce4f0fcc208e5f6fc2df006dde6af97', '2068fdb3e52b1c2015fdfd774223247b', '1373d79652d8ae8027785a4ce670c0a7', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8f56b9bafabd945133f3c8a8476d5160', '2068fdb3e52b1c2015fdfd774223247b', 'db3fa0b93c44bdb68365c09e377770a3', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8f906c0ef4f6ed265ae13af55410b4c0', 'c2cc3827bee46d612f009a8ef470f238', 'a44d5aefe7bf02506f5703cb95fc2928', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9836e51faa1b7f9cd6078834c56084ac', '2068fdb3e52b1c2015fdfd774223247b', '59b23f3bd35da1477e29b2464ed5c525', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9afb24ac365ef39ccda1de93cb1b3412', '7f89f59ebc4bad23b4c21533d7c09256', '767bcc8fc9f27fa6a22c038579e29c2d', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a1c47e342a8b69bdc59050ee725fdea5', 'c2cc3827bee46d612f009a8ef470f238', '96d3c6f7811280e3a72e3d529889bfd7', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b10fb286435b98a55a97ac2b84aa500c', 'c2cc3827bee46d612f009a8ef470f238', '348c52d04be7d556becb9b8c1ee70c6e', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b7d9959711c3e7343912db4b7c6cbecb', '2068fdb3e52b1c2015fdfd774223247b', 'bf56c457d473e84a6c38847c61e6a4fd', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bee4778b47daf975e07cca55b7518660', '2068fdb3e52b1c2015fdfd774223247b', 'b7c0ee3e3f7b6f85799fbaf15493f8dd', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c1e39f6665febd72b523ec86e166596b', '845fc0475958c1889342bd8f19edd51e', '2a828fb57c6c4d1fffffec2192f7005b', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ce34911dabb0201692768059613a53ab', '845fc0475958c1889342bd8f19edd51e', 'ddc29f45341ad9ef169a4e1e72730617', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d8956530b27ada2015a4b01430977e0a', '2068fdb3e52b1c2015fdfd774223247b', 'f5373142f662ddc367bfee9244d279d2', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df53f32ca480051ebc6cf1f6cdc2da9a', 'c2cc3827bee46d612f009a8ef470f238', 'b36cca7b23847708d121aed917aa11f3', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e259dd3aeed6d2781a3a9b90b028f51b', '845fc0475958c1889342bd8f19edd51e', 'aca72fc41c7f72311cc1baa8e17d3156', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb69e30f96e42df9462e00e21a45cd29', '845fc0475958c1889342bd8f19edd51e', '915680403ff70cbf8cedcdb02f166544', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f35eedcbd59f2d26ccd8e272bc4d8ff1', '2068fdb3e52b1c2015fdfd774223247b', 'c97adc04eccb8a88c3e3aa29289ff35d', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fa779dd6d01d142ebbc026fe28536a48', '2068fdb3e52b1c2015fdfd774223247b', '7c2c9ca01cbd1148dd8e381dc062cd21', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO value_set_cds_code_rel (value_set_cds_code_rel_id, value_set_id, code_id, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fe13ba85af0eea705141c787fa821872', '845fc0475958c1889342bd8f19edd51e', '3b7d98e61499166ddf42d41423e0f526', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- version/cdm rel

INSERT INTO cds_version_cdm_rel (relationship_id, version_id, determination_method, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87f30478b7ecf473d27fc34144972e81', 'f89c7a73aff266756922d437e0ad8044', 'cdb46802ca1e8385a2175f566a47b20d', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- cds lists

INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'ICE_IMM_REC_REASON', 'ICE Immunization Recommendation Reason List: All', NULL, 'fd199749f7ea8aa3acd7bc1ee733ab13', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2b9926a4d682759612a1bc99645479a3', 'CONCEPT', 'ICE_IMM_DISEASE_DIAG_CONCEPT', 'ICE Immunization Disease Diagnosis Concept List', NULL, '734d727366047ef1b6292d310d36b657', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'ICE_IMM_VACCINE_GROUP', 'ICE Immunization Vaccine Group List', NULL, 'e6b7f6483524ff91e2feae09fe4b1619', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', 'ICE_IMM_REC_REASON_NOT_RECOMMENDED', 'ICE Immunization Recommendation Reason List: Not Recommended', NULL, NULL, '7f89f59ebc4bad23b4c21533d7c09256', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5b6db361819bf41a7701670290eb4e02', 'VALUE_SET', 'ICE_IMM_REC_REASON_FUTURE_RECOMMENDED', 'ICE Immunization Recommendation Reason List: Future Recommended', NULL, NULL, '1a31291daeb4d048d068dd0299d100b2', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c352b28cc1325255763af9b1e35e695', 'CODE_SYSTEM', 'ICE_IMM_GENDER', 'ICE Immunization Gender List', NULL, '2b4c99853b45d76f572ef1c6ac339901', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', 'ICE_IMM_EVAL_REASON_ACCEPTED', 'ICE Immunization Evaluation Reason List: Accepted', NULL, NULL, 'c2cc3827bee46d612f009a8ef470f238', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', 'ICE_IMM_DISEASE_DIAG', 'ICE Immunization Disease Diagnosis List', NULL, NULL, '2068fdb3e52b1c2015fdfd774223247b', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'ICE_IMM_EVAL_REASON', 'ICE Immunization Evaluation Reason List: All', NULL, '26bb57f1188231aef8d95c48c9599225', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('964ebcab9f4a7fdbe79e23652da5f2b9', 'VALUE_SET', 'ICE_IMM_REC_REASON_RECOMMENDED', 'ICE Immunization Recommendation Reason List: Recommended', NULL, NULL, '6d58eb602db0cfb2fa0a098615fe661f', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ace2e9241136ed041306bfcd5a5dde74', 'CODE_SYSTEM', 'ICE_IMM_IMMUNITY_REASON', 'ICE Immunization Immunity Reason List', NULL, 'edd269b4b909d500d3b9f4973684c1d4', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b2e4a5ab93360c4833e44ff32d328812', 'VALUE_SET', 'ICE_IMM_REC_REASON_CONDITIONAL', 'ICE Immunization Recommendation Reason List: Conditional', NULL, NULL, 'eb679ebcd8d519bd5633d61672ff7c56', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'ICE_IMM_VACCINE', 'ICE Immunization Vaccine List', NULL, 'e0bc41f880dc19d217656508b6cf9908', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', 'ICE_IMM_REC', 'ICE Immunization Recommendation List', NULL, '1a4b4d5cd30c24a5188bce0a54a4d122', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'ICE_IMM_MANUFACTURER', 'ICE Immunization Manufacturer List', NULL, 'f47e38fdcc2340ae7282963bf26c26ca', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', 'ICE_IMM_DISEASE', 'ICE Immunization Disease List', NULL, '8a74891e484ecf4a221d15d973427416', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', 'ICE_IMM_EVAL_REASON_INVALID', 'ICE Immunization Evaluation Reason List: Invalid', NULL, NULL, '845fc0475958c1889342bd8f19edd51e', NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fde739ed9a6d3d03ceec9ba7eb6f3bac', 'CODE_SYSTEM', 'ICE_IMM_EVAL', 'ICE Immunization Evaluation List', NULL, '7f626b079d08d67d9dcfe835b72467a7', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- for rule mgr

INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'cdsCode_Evaluation_Interpretation', 'ICE Evaluation Reason', 'evaluation_interpretation', '26bb57f1188231aef8d95c48c9599225', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'cdsCode_Recommendation_Interpretation', 'ICE Recommendation Reason', NULL, 'fd199749f7ea8aa3acd7bc1ee733ab13', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ed79c68e936e07f417febb04f8db3b0', 'AD_HOC', 'SeriesCompletionStatus', 'Series Completion Status', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', 'openCdsCode_Vaccine', 'ICE Vaccines', NULL, 'e0bc41f880dc19d217656508b6cf9908', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'cdsCode_Vaccine_Group', 'ICE Vaccine Group', NULL, 'e6b7f6483524ff91e2feae09fe4b1619', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9941592a447c86c33ec44e1b31aac680', 'AD_HOC', 'ComparisonOp', 'Comparison Operator', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', 'cdsCode_Recommendation_Value', 'ICE Recommendation', NULL, '1a4b4d5cd30c24a5188bce0a54a4d122', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', 'openCdsCode_Disease', 'Concept-Based ICD9CM Diagnosis', NULL, '734d727366047ef1b6292d310d36b657', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b208313be4ef365418ed390bf6b34928', 'AD_HOC', 'DurationType', 'Duration Type', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c57137ec9eda557528cebad5448855af', 'AD_HOC', 'MedicalUnits', 'Medical Units', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7', CURRENT_TIMESTAMP, '1bf9f5d620698688aa0e0e18627fe4e7');
INSERT INTO cds_list (list_id, list_type, code, name, description, code_system_id, value_set_id, opencds_concept_type_id, enum_class_name, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ea34a3f720d5aa1fd1f202b5e324002c', 'AD_HOC', 'ArithmeticOperator', 'ArithmeticOperator', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- cds list version rel
INSERT INTO cds_list_version_rel (version_rel_id, list_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('4341e6b16eca41eab5a288ae1094a67c', '2ed79c68e936e07f417febb04f8db3b0', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO cds_list_version_rel (version_rel_id, list_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('88cbd19ace529617c95629856a147b20', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO cds_list_version_rel (version_rel_id, list_id, version_id, LAST_MOD_ID, LAST_MOD_DATETIME, CREATE_ID, CREATE_DATETIME) 
    VALUES ('d917b3ee6bc5a3d89b01f5f45f9dcd34', 'a9f897d898e6ca4f959585f039d37b17', 'f89c7a73aff266756922d437e0ad8044', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

-- cds list items

-- list items for ICE_IMM_MANUFACTURER
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('153d1976a990c6cf196fbcc34ea85a60', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '559dd4757cf81d6ebedfa2a9e422f54a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('15c7e6649b73ebede942deff58e1e477', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '622e7cfb8f4ef0db1d0642aeb4269141', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('173645324d2d34dbfac33d7dea64e526', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'f03d3788311fccd25ab0ca66266f9cf2', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('17c1beeb46932861fb2da271fb080dc8', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '934d0cfea0f1aaecfc223db69eab7269', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1ac73d675ca809ed9d04df605e42fcd3', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'f16d261a693fa3a5c238389cbd23e37c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b99eb0b370b581a82b73cc94f50a964', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '4e3a992ce8d9fa1fd5fab146fa69deb5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1da9339955b52759eb8168557b479093', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '6feb6a5a53fca35bd36ada75f4b9e033', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1f775f58d12cb18a88e6649957c39a09', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'cb6e24f9eecd0d0dab17cda29367c8c4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2056559e187676523050140cf160f495', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '57b78a337597c7a5dffdce9992a5b366', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('29b5bd8fbec71bdb937f8ce8f6f30b56', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '4594c45798ffce2948fc6e1ed7b3325d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2b935454cf1080704be2abb51316a7c6', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'ddd1db303a67ca1d75aba7778d952a4a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('35aa0634895a9dc23be19135842385bc', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '45d6166082d23a5f35db2927a136a892', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('35aab328e1a66163d8dbc92aac0a0005', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '21e1fa8e8288d90ef82ba31ad0ec2533', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3ee972ae9df67e82d6a25bc6e9a4582b', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'fc8a55ee5700bfbc8e1eee575178c6c4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('415a5c13b3f56d0b90e86c89e6a6572f', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'cfad7f2189f4a1ec8c63bc542496ed5c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('42352f50c03fba8e4bf9c97a62fca76e', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '1863880b6e4d58172fa038d810f75201', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('42762786ffe88eb93f3df66c5e9ffabd', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '5817965582c6e366187b12e553c6d6f1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4c4dc55d495bead8a9a1bebb2c933402', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'bdfa6fde7c6515a22b5e8aef560b6c09', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4e1dfca227f2bb2ef94bb924f074fc39', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '265cc59f91b05ffbe55a9c57271849d5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('549f91ec1d47e752c36b4e8fbe449186', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '566ea92b696b41ef5758f6a57cea5b63', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5eeae9548107772762c9d718e20515bb', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'e4cdc40cfb9c81a85e6b491d7e2eb248', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('619211ea89a1e5183d1a64a633ed4e6d', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'd72bcf21e8db61fd22d13be1f021552f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6702f723536a8cc98aeb9c01561693b9', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '1b890d6818b9f23c9dbbf29c901b0f73', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6c7d1ee987e7d1f36a8193154ca7755f', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '55f10e16d432551f5821a3fce1840ce3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6cc3129a22eb39e5a5525415d5fabe3c', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '1d113dd3bd637c421d93bfd8f3bd0243', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('71f33939c029e73c9799d6ba37de899f', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '326e0520c7c1b8b2c450b5a533d825e3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8022a2d542d53527a198e471aa1e1a7e', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '26452fcc28764cea33ce19e480816d2f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('823f5b26773bf23e1c5451f427e91850', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '2d2b0877eb6b0ab936c599ee4d0c4574', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('83e7a19f449d346cc50b60d6c3aa058a', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'bb22bebfd85a4a74f9568fa16dc86d0c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('85fc0260983bac8dea7dafd23d2e34bb', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'd7d9df3f4f02dc3f42a5e881c380f5e7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('87cf5a2fa935a60839504398cf00fe0a', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'ca3784a1363da37ad6e8dc2ce5ce15ac', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8d5fe0dac4f2d80b38f23d129abd9383', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'a231424793fb7e0a93021fc7cf94b123', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('93c54d9d78da445aee608bff5c18e2f9', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'c475283ebe0f1529aecac0873818aa4d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9a4897e0d77631a2935371f5d08e11b0', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '542e68d2591d45bf218391a6b078ae49', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9a65869ae60bf2d6f50fc91c73e6be7c', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '9a9e927bac5275ae674f11dd32d09899', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a87828e3526363210fef72cdf199a1a5', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '2f90b2f66a0f3d29c0d09742f9adb601', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aca16641169c0f9628e5cafb46323b93', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '863470d5d1438e785c0dc9169d0c50a7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ad9b6679901690b868fa6cac8670a5bc', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '554b5bc5a57660f597fe2c3c97777b57', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ada32d492e7b8f49df5babc8231afa48', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'b7f0837c2bdb4e63a41b4c3adfc4f88f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b07cf9e052657427f2a682978b6ca351', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'b83685fbd105cf36f52cd5a6e0c92247', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b2e74ffe75492761e31d6d9055a9c9a4', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '25b59120536c4d04c871c1727db4c758', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b68dde5c9d3f7953c657540ce53c0738', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '7d7d16fedcbb25ab3fb71e57a3f4f415', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bad0851a7bcc08057816cb56c67b04c7', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '844afd15b6ef4324922cad6ade5539c2', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bd82bb4446c8818d4f15a2534df9bc08', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '6890bb034b190c88fa4a1d1daaa00c17', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('beb1940b89879f2efdf622b23fadda28', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'e5df3350dfbd4a7e4460e60991afecad', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bfdc35c254c02bfbb33f9e87b8657596', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'a3459b38bce442e97125ff443d56e2bf', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c8ff9732e04bb2622ef6c4ccc0fb554a', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '292a973b75c6220022ef4f06a0557ce0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c911af2a430d0005740463aaf39ce715', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'c405d476923a452aa22890344191f72a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cc8ff76d3a5d1a647cff545e6427c2ea', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '750e6fd83c2c810aadf54a861014e76f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ce88bf3ea0610fae378ee561c49a7545', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '984ed662151c4caecc8ec37b500d30c5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d57db6ae301be6afd32c49f79db4b5d8', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '9c1c156b68e760f3bec41040987a709d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d5a39ccb314cb0be2427670a9226f526', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'fbef283d3517c0fc5f0c0ff3c3610db5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d88892081a9e83d3263b2342339ee2d0', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'ccef73dd406263932fa0c2142a1acae0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc8e60d67cbe58135c99b3ea8f5a7065', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'b8eef8f1b7dea1a0a75ea2333bc5f34e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ddb9c9482b4506da2ac100478cf7d1c1', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '3dc8da0f5d053e86abf1c6a8a2044459', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df1e70502889a87322440607d2d87248', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '95ca6cb8ef592c65629023761a27eee0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e8bae513cec800898b85fa3972b91708', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '616e896fe1abce494fe3d9b0f7cef3c9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ea5d123c649062a9774e57d76c29ee29', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '84c63729d55bfe718beb7e0057a9abc8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f43c25ef4addd6a9ead6298224058353', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'd57a0126c34b7eae93a98b9860f91efa', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f6289236be51bf4a45e9752654ef1eb1', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', '97a1827fe6ac177cb2e5b70f424c805a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('face6350f84af52af2a8c2f420b643b2', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'b8135ca5554fe22439d60b72e4d90cb9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fedc65c94ca013f4d5183015da031ce2', 'c4f1b2f570ad49b6fdcb26012762722c', 'CODE_SYSTEM', 'b338ec12576d06b8e7ac6fb87f2aa044', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for ICE_IMM_EVAL
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('492c874f2f4bcc672fad7c15aea07f11', 'fde739ed9a6d3d03ceec9ba7eb6f3bac', 'CODE_SYSTEM', 'd46a55919980fa73aa4e2c42cf01916d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6f3c4cbf35dd84992e278960ff407a0b', 'fde739ed9a6d3d03ceec9ba7eb6f3bac', 'CODE_SYSTEM', '11ab2b689d659f49b018e542ee6c0e84', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7c361b548e8ccc7c804b9789641044a3', 'fde739ed9a6d3d03ceec9ba7eb6f3bac', 'CODE_SYSTEM', 'f773fab3de9c467d37130b2e8e02a7f1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c956b2b5470ea8ae25d0b532f7ce8793', 'fde739ed9a6d3d03ceec9ba7eb6f3bac', 'CODE_SYSTEM', 'b9a3825ff53af790e6132e0c766e7035', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_EVAL_REASON_INVALID
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10ad4a31d11067cc8c4de81137c1791a', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, 'c1e39f6665febd72b523ec86e166596b', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3419fee6551479a76868f042d72f6598', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '5f7eb15269315984b4d0483a3b260048', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4a6a9845a31382f018dc000cb6a4ca91', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '591a2b001fda360354a69b551e463a34', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6368ed55e8b01fc59f404b0c4920408d', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, 'eb69e30f96e42df9462e00e21a45cd29', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('72811e6156f18f62375dc5be4783d134', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, 'fe13ba85af0eea705141c787fa821872', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7486cb92a0c902d8117837608475452f', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, 'ce34911dabb0201692768059613a53ab', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('88e7491a3f3f6b5b840d0e4319da8079', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '1cdf9d4f936f7abdfee828f6f9bfc63c', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b761ec1c6c35af7d69d21f20bc7ed2aa', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '6994aae374f063f7d542d0cca636866d', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('de9be4a2e4972f53c57d7ed4e00f0928', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '70cf0a4fa09f3aaf1fd6fb0497cd22c6', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ef871a4a447169de723876bfd76b9e22', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '43c68dc421224c42874232d7d531626c', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f356238b70435709227656b4425e8273', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, '22016b8bd15a7114758a8cbc69018120', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ff9a94c95f8ca43d5f75ca4ee956e9f0', 'df69b29a58b6bd282e6e7b4cb9aee14b', 'VALUE_SET', NULL, NULL, 'e259dd3aeed6d2781a3a9b90b028f51b', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_DISEASE
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('18630296ff76be2f10df08f9d21f3d7c', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '83aeaca8fceb6f601c8ac5f19cc95fa0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('24886b267727b6913d124f59f8a20660', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '4f27cec9fd486d29935cc05b3ad185b4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2bfaa8f8ed2793424e980ce9598d53c3', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '8d58b13fcafa8c26fe46417a188a4eb0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('38105b4bd9c64a80a34016171745640a', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '826a5180b6816dfe93bdeebfc3ddac49', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3e35726d53d6c1843f2e68195a602132', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', 'f3dbf7b984af74335867bfabc6482b69', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('405ac59e377bce756e170212880bf49b', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', 'ecd5a411d6a36beb26a66042261b5195', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4a5acc5e414c6c80f9d709f649968a42', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '81c85e43430ec651a931e5e4b8b19afd', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('58145233a231d78ae3988baf36bc55ed', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '801bf6ead736240b7a238369c3005754', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('764a0e2b308d93bc803195337f71f9f5', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '3abf1c8772c4bae57d338812cd9997e9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('82f471f2d106b22dade2d1359bb2f543', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', 'ac2cfcd44fc9a651f68fc8bb30d38238', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('892442d0b318eed052926763e73ca9f3', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '330fbc8d4c137595f3adb54d93cc1978', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a46cd45edb3a271cd95cbf3ad6e7435f', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '1094c6e572a39a0a13a1b534251f175f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aea17ef0362ceb517bb28aeecc1f7b44', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '50ebd184328992f2e1942a4d57abafa2', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b163db6e4cf4b208214cd0980086d7b6', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '5c138da625d6ff08ac681db987c1f4af', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e921fd8e159c29061ecae6573f3488fe', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', '50a5fecb498c84202107718b940b9e5e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f8b14a2b206255cf21e51a614f6220b4', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', 'd53eac28e4a8229cab9af40d2f5a3318', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f92251eb16fd694dc9df4e9c97dc1546', 'd96e716f172d240b58fcc92abfcd60ca', 'CODE_SYSTEM', 'b88473bbb2f9f8f524b6cb0c587b3a1a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_REC
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('117f560a621020ea9cb010bad60275e8', 'bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', '6caad4861f355a6176d2ddcd338541b0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ff844167418d75d965c302123e403cc', 'bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', '6d44c6a07fdeab4eea549fce42fee8f5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3848b94dbc4713329f953d04f9cd918e', 'bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', 'edc905b5534a8414c0b6b7b5f8dae839', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('501a3eb361fd815db2cb82a8b03f1060', 'bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', 'b1772f4680ea565059096f625ea283d0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6c1b4ba7da5e805529561a5319041bfa', 'bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', 'a842f4f74b14cbc3582d270ab59919bc', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e42507502cce40efbcaec331358ec8cb', 'bb36656bd805d127b31d4e58b64dfad4', 'CODE_SYSTEM', '461cf7273598f747bf4f87be40c81856', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_VACCINE
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1472bd30c1589cabb358c676bc3b17f8', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '47fe49b03811a40755c47cd4b74ccdfa', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('14f8ea70f4231e378e01253593512047', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '45e87d2f7abe2edee107277fdb754006', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('155c99a92c0a66d5bef84dedce53a8a8', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6ed89869ac0ace491bec4526dec327f8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1a47bef7e89c84fe4c2d797fa1986ae6', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '5346f65f8d180de8781cdaaa5ffaff33', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1be47cd8b3643e21cbe382431853c388', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '5b04394f57e2cd25a8f995ff5d4d0854', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1d05746add565e0f775e6b7aa8087c99', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '10320d22a2631f2c5a07ee7734b80528', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('23549ec85a3effd4ef34fde8f6fc3ff8', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '59e02e084e71ebc4cf13c0a748e4f47a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('269b17334312e0e6d160d8f061f9ddda', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '886903982dada4949ab8488698671819', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('29711d8674607d213e38fcbd34d4f803', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '2a355af61f5612e3698f4b7a63c4775e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2982254dfc840ca541380f431732a88d', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e989ce64cfea38bc3fcb4e179ef6056d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2ae8b62ff4ae3f1986307b795922ba9c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '462cc4cb26baef19bbd57b4989b4fb40', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2c2570a7eaab66f5504e6227145c6a9a', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7d4f1b8fe4efbdb39cbd17398f54f29a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2d9895541fa080a7fd258af48e7ea9a5', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7d3d80b067c1fa9390eb3b50f20075d9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2f0ad7e99cb0f33de0b245b68dd643a6', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '699e36db6ca1ea07c9c4c2ee86053f96', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('30a35ced3f6b5d511083a60382fca626', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '68a94ed7b016986a40f6551b3f7a9cbf', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('316cdd54df6edc0097f738bf37de2e29', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '99acdd2bcedffac9e0205217da0357fa', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3398d091eba99db29445e936fd5db0c1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '39d17b6e86a657837bb1b2dfe11bfdb6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('339a9a48a3326b9fa82069ae54d1104a', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'b787e25b2be97d5d1a8b230fee619326', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('341e3e1f4c86766e9fdcc1496fe09560', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9cbb3c5ac11c3bc229e682ab99afc723', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('363894a077fd27c3658a3b8f6db396e4', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'a94856e326cc369c5a5ebb1e53fd76e5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3e2553cea198d1fcf29b1b11c48564f6', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '127a169c5621fb76483f32714314ae9b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40871182b83d0343265316667809ba7c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6699c4d58521edb9c091072e976bc67d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4249160d44388287ac8ec1b6f0c481c1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '2b2a73cb2fa719859549739222459af6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('427727fa3b518ec9da2ab55cf8b183a0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '2363716213b0f3a8e85243c976f3e7df', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('42b4e64729ddb48e33d967c36dbd6aa1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'b6f1d7bd6d6e5fc6db728c48678967c5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('438b23d2f6290872cde104e89e5fa39f', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'c955ffa4dc71edfe7b4f831ac3729c7d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4404502fb30adc60fb45140998836748', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6d5c6d027b55ff047b5e12e16d634f8e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('44389dee28c072e89498c0216e5f5cec', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e3da750a24577410a9794edcb2f406b6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4596b90de072c0d8f352d5b8225ba79c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'b6ffa4b8bc0847078f9be7209b5df876', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('47b59c78b817b8d4c2626787a182efd2', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '2b5514e33f18fba4853f4f3717dc10b6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('495b8fd7d0cf4979a71fc815327e8367', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7fee2edcf1b3e44a94a16059bef44072', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('539167e6b6e184f755b00677d6c7aae1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6d78bb9f5eff5cbfa9d0b2343ba91360', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('56023168fda246c991896c0430490374', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'bb59ba73a1072418faaa463ee5f472dc', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5aa3bd44375f54e4d095d9eff64e2e6d', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '5178e23c337a26a1ce17a4a73f8bf97b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5ae0a2e38a64a9fdec6284682b987ced', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '940719422398be2efc9ae08751bf417b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5b3df812124265119ed6f05e10f4a524', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '72992266a4c25a5e6e5f132a9ede2680', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5bd50c4f12725d4590321d15dcdbe5e0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'a0e226a6a44b1eafb30b6cb4ccef45c6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c2028d176f2565d208b163f6584c7b0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'd8d690813be570868cbfe9cdd63d9187', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c623f4b6ab7f73f21c3cc9ee41b64b7', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '397ed5fa6dbb10de74a309ed7c4216d8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5e6cec6c63be4479ec65b885b26aca9c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e776fd17cf35191c5d729425f2b010ac', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('60a0e2fc3a4c63bc62ef9bc7dfdd800f', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'dbb90d607a346452e82c6a526f6dd727', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('627bab1ff7d38af4a87772354932dfa4', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'f43885acb83bafd76ebec01b6f53f6cf', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('651d6a1e33f2a7a582b716177a40c820', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '69bef82831628da319d3910067b9ef5c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6702c0da2e81ad2ac507083cfdba1bc5', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'f8ef6fc7a9f39e0f6e8a56aeda960db3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('69ad4601518a5b0bf46876189f426d29', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'dc738d07d4271415f8ea752c7c976ee5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6c7b13c43646acbbc95d9079bcc25c03', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e14d0cbe1a1d56c2ec14f94dfffb4fa8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6e34d56cf25fe3e91aef7c306fddc819', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '66564dceb24841699e60b14e9b1d7b2b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('758818ebf44140f123fc3f362b26f016', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'f715a2ea209729e852117d0a305799e1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7620155684fae46fc03ba824d2cb7587', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'bd1b49f5a89652d6db5c587594136f42', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7646c61de3448706b817cd00a403037c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '89d9d0aa5f1e35db59a2734281bf3a63', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('76918651b8cedfeefa12f846a4a2ba3b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'f9e3be777817ba68958c4fa7a3d89b90', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('80551d3daae0b456f2f88ac479dcff87', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'acf262fa464441cd22704f6f70839150', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('80c7ab50ae776cc12296c6caef822c69', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6e89da7570445099ccaca472a4510dbd', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8114b2d64ad87b68a5a27dd08be15b92', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'a2cfcee1cefe42eab42e2318645d9f33', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('81cb9ab82df9be83a371d6cc5ed726df', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '87c5a8aa610515972ae3d2aa814e1b77', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('848598a8718445f967073b2b3f8892af', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'a059147de03843241d61b1bf55cf7310', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('853d4bf5258f87ad4001665d9f3eded7', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e5d15051f018680a81ba7a0bb2f2e0dc', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('863174251e651eb0dd077f932fd17710', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '34ce1c28ba3d41ee57762841cddf1360', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8798d3da2f0ded42df5ded8d40a64ae1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'dbfd3987a6a6b4b21e7e13ae5db1f787', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('885acad72c4b99794d9d8fea9d9f3e3c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'fe168be1731ef6bd3cb028766d49ff2c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8d48b92275741da6bcf3869c494f95a6', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'bee2f502a3d076e64a219e335957803f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8d505169362b7550519c8e25c4c842dd', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '2fb18cfe6dc2701f7260e660ed0ee5c3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8f767d622874cc3098d6698c1ad206e0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '2491447191b3f5a7fc25b83cb9c8bfa3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('938bf6b55537bb4a9c381c31adab897e', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '1d6f24415002fc1ed3f31282eb3db7bb', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('950c4140fbe1658482b5bafa33b81db8', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7b0195669cf49dbbacac7d82f3c70042', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('97a622dff7eb77541ec9bf70fa44ce08', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6c7949af1810cd1183895825be4b8ce1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9e876ee782ceee87d5efc6f2273d5461', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '4b4288d0fe3dc9bb7ed45ee727d1d88d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9eaea1a93cfe7c9c455ff089d23195fa', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '36fea054a6b78d2a10139b43480b1083', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9f5869eefc0901df5411ae0cbbb78f2f', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7d536a7617f76beda66bdbcf8b206878', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9fbf01bdcdee1f2652df1bed3a8b354c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'a58a28918894428bdefab782a7f1c215', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a4a111c1a541fd84da1f707e3f0ff62d', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6334689712dcc4ebf302c90968682853', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a790ee3d66b750586062860f50c7d478', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'ed444e8a59047e86b28e89e335b21bfb', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a8b5864b1c8a6495daeee9ed6860b73a', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '26b5eadb78914bfa390dc8b1d7d7a37c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a8cc0b6f3ef290f36b3ded46d58fffae', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'c328a8db8b9fc54945997aaee0fbeb81', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aa05a5eb288e32b69511c570422d35d7', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '37faef5213ff09e3ef4906c32ef40e0b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ab02f07e1f8dc296561da91126d46d1c', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7e1471be78ff8ead9772f37fd6aac6cf', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aba402ddde2035734a119b72a4f90236', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '363f183aa6055d052fd7900e338cf559', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('af1b4fc2d26f4970574c3428dc6973c5', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '16e24f863c0ebfc9324a2571508cea6a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('af5ffddf9eb6b526c9057392d1ee7952', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '807930df85c91a3ebe707f09b9c8c609', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b0508c16f12ca94444a33acccb2ba387', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '45948d8e4691ce3a232ac1849308b9ed', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b4104d01df3f47d9fbb197142db0955a', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '16b7163c2814a310a9d392c23d76e143', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b45112f5bfcaa3d1d116f096dc907aa0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9d8e6e80e7805d2de2594a5f11fd7fc7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b49c8875fca0980ac60b5aae2874c923', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9f4c6120a5c2be073a0519cb6f9b9d2f', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b709127a71352b86158d04c5edb03a80', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6d3e2c6f7b0de8a9785bea8df68f0c47', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b8fd7314145e8c5374ceb17135cb054d', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'ee71f0da3bf130dfc532ce6dc309fca0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b92b2ad8a734683df8d9e22ffa0031af', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6b1fd318ceda97a08812b2a71ba80a67', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bd31fe4e4836c70f9db312381207db74', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '52ba1d7a1ccfe5be84f6d1a9b5e608a3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('be3c4273c158636b5d4ce0c9d05acd64', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '1a211cb73bc3b484d1e0852b18500a1e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c245eff4710b854c75d6831c65e8233b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '93edb35732b676bc57f99858da3e11f7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c94214f439d261aceb576655e9067ee7', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9fcd623a2f22c928d8a8c01774c3ab92', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cfb6a6ec385911c21e6940517aa6f188', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'be777d762c0f14a4bf003e0a8a9d0fc6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d0d6130651e8c33bac11c9cad68f8890', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '71a48f0d94463afe5ff7a3aed4e3ee2e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d2ff8a6bb6abf9611adc5fd859989b70', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '1a72a3652ad621ef6c142028bc827e1b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d3754809d89c558aa4bb311e9c332a82', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'fb284f06c8a73851707a9610789c8381', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d60be827759f71856f7632f279d92da8', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e3f3ab4bb0643e385b69f33b34c01cf8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d7347c58d1b6bf562d01f4e1623f115d', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'dcb01536cd425956165af9d374c2baa8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d75774853fae8459a8f0bc776890495b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '1e55b9c7feed0f3d920f9a6523a69631', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d955f85c1424580aa9642b16ec6dccb0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '993ea3ebe57ee235d2affb1173e40388', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dba84cac5c7cc5f3f14a1966539a46b0', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '43041e87d8e921484eee385ac621cf9a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dbfbe5247ed18cee63604a0a53341b11', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '72f4c00c91390f7a5df4a0d6fc492894', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc17838fbc3a9748de2d76a11b327426', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '31676df5fcd3d2f0dcbb7c4540096254', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ddea41e35fc4771ad42b51a1f3cd5cb1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'd1fff1abfe5eab959f304de38f10d43c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e1af3a62cc97d84263197c2c51f6e74b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '115011c96e54f5e1cc044f76f69e77b2', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e281e2f7e8a93db671cc14d82da233e1', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '43c1aefb6b54e4f04bb00c87b206195e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e29f0df8ab19df47475ae504be7a2a54', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7c597a96f55a91f4357cb2121437af7a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e3398a0e86f04ce4318c05c38ab3832b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '87db1f2fe083ec4ad63b869e22119d19', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e400cc3ad739e32343aa42969062b687', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '1b3b5e305c967a32e20a9a447bad47e5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e40d98011d3c0f8063953bca104ad059', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '3fff865fe68daee74d69c5e0c7750d0e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e6a4476e61620d264730cd6dbdbb4dc9', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'cc422a1c3027b283a6caa0c7ef17e551', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e6eda4834113b178cc952fd8421de349', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '466ad98e153d516745ee566f6a685073', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ec5b650ec6c78415f68a769a593edabf', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'd355695d931880b31ff86595b44f8985', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ece97d32eda02bf1f023f627cf045d20', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '6a93b7b5eb793339480570defbe7fe14', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ee8d8dc7747c687e91110ad87025d3de', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9bb88b1daa2dafd48c265a9e3a7bc878', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f007d53c55e5b1b09c6089a8455ab63d', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '84082e62128fabcd5a5364f78de9b631', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f174a52c7f661f9c7cb17e46f974c55b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '696564f7f1fc0533d4f5dd85ed05cfea', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f5572a38e465a52ba8ec836ef33bbc11', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '9c47e492cb267923614e4827fa3d6d2b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f616518eb4f814ef8505e6acf4858a6b', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '74b3cbcaf3fbd8b5245706bd1501abfb', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f723ccd1e9eca366b09d3e3139cf0596', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '7e7eeda75d1ea89921cab7d86b66c447', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f8c0b71d7eb49304bedb8fb315f6d7b7', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'a9527a3639c6a6c5a04d2728b7fc42c0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fcc0b12615020bdd3065c47465063d67', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '53f358d0b6e07c53d9a118c39a4182db', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fe0540bd3e3c1fdd161441843cc03d75', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', 'e750f036712c026da038386ea237c67a', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ffd0c9a12173dbae27f67cb01e4ede47', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '1063d02b120f84c7d2e30b098404284d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fffe45febdc1fb6676fca6d35a49d069', 'ba0dedafb9d3b020351e9623d87d8d30', 'CODE_SYSTEM', '8e3f621a6368e6d294211b84b09da1b8', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_REC_REASON_CONDITIONAL
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb85e5208430c09ba91e5023d893b7aa', 'b2e4a5ab93360c4833e44ff32d328812', 'VALUE_SET', NULL, NULL, '113ff081836c6698dda136e341a479ad', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_IMMUNITY_REASON
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('75b69c028b4fd64487f4ebbfb0df2545', 'ace2e9241136ed041306bfcd5a5dde74', 'CODE_SYSTEM', '715e8ee33a1a601038980748461bf7e9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1538ce6ad19ba3bc7f0fb2e183164a90', 'ace2e9241136ed041306bfcd5a5dde74', 'CODE_SYSTEM', '575761dfd9f8d9a455dfb51d3919983c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for ICE_IMM_REC_REASON_RECOMMENDED
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('19973e6f9dfc8e317eb70e9fb639191a', '964ebcab9f4a7fdbe79e23652da5f2b9', 'VALUE_SET', NULL, NULL, '8cb786ee454e8bb2c8fadf42c111d730', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_EVAL_REASON
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('148ac0e9a8e65820be2725cd18f6b161', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '96d3c6f7811280e3a72e3d529889bfd7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('19101cfdb26fa84641d5c5da57eb3a38', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'db6499a6c18228d447ed3fd93ecfb435', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b5d4806763dfb3757778bd84d6324ce', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '75b8be1794967287eef76b398e70a646', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1f2a7ac130894899ba69703949e0a8a3', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '41964f474cb4bf4e938400dfcc024fe0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('291bdcf459b493f52d7502c9b9833768', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '45db24fb5667367fc155438ff495fe26', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2a693168f5c9ab0295d2ffbea61db525', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'b36cca7b23847708d121aed917aa11f3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2dcb9c9d558a6be4d052c33b741106f3', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '4d830b901901edc01e62571ad471acd6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c2660e713fc23e72887a1aa94125342', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'aca72fc41c7f72311cc1baa8e17d3156', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3ea92cb06b8d8be667a447349741015d', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'dac424843b1e0477dcf5773d74642fd5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4e452a12620a8d5572d17cc9d870c64f', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'b770bdcb9b9c2c94bc4f4860fdeed0a4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4f6fb65c644f76fceb6971bc65e0ddfe', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'd7564f45ccf2014cab139b637eb735ba', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('544af63f66ee39daaf09cd31b807ce15', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '87413172bb85f80e9768af68b856a7b4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c66bc28f132251ac07abed97eab67e8', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'a44d5aefe7bf02506f5703cb95fc2928', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7c0434091f5dd9213bb754798122d3f5', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'bdec519c5d1ea06e5e233c03d22dea37', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('861a5ca912e0b0fc8c4e81f93b7967c5', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'bc365d09ed40e231ee794bbbbd951dac', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8aa6d96b3e9e10d6e60b080e1938a8d3', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '348c52d04be7d556becb9b8c1ee70c6e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a00e95eb9e473fd94cf5e95856a0c439', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '90120aea63b53909fd457036ceb8be36', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a14734cc2b422e8356ea29b9f6b57eec', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '2a828fb57c6c4d1fffffec2192f7005b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('af04f85b1a47e734688a14b8a2e44bdc', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '915680403ff70cbf8cedcdb02f166544', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ce564954b5cd64f698d0913b807f41fe', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '57213fca1f901a6adf708be9e3407209', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d366ed4ba03f8ca36e938b06c6e374fe', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '432a8991bf57233311c467e1911450b1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dc6e722e2c1308db3ec58c01d5fedc46', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', '3b7d98e61499166ddf42d41423e0f526', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df195704e2dcce0f2f88b607593e8c84', '94c758ebbd53c5216c0005edb0936be4', 'CODE_SYSTEM', 'ddc29f45341ad9ef169a4e1e72730617', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_DISEASE_DIAG
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('15fa9bc06e05edee3f28347acdf87d24', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, 'bee4778b47daf975e07cca55b7518660', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('223ed2f147f05d2ed85b0b99baa7732d', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '7a3e47c0748299a5af31d6c755716371', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2c40ae0491f665fb4f1bc9a6c60d9d57', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, 'b7d9959711c3e7343912db4b7c6cbecb', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c3dfc7a712537c13343d0324697fa03', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '54e84e11eb373cbd50404e9563720155', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('458f99ae6db6e260fe0482ec38193ff8', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '274ea057ad607c6ca9aaabefdb3ef155', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('49e99d91635667741189df2c69bcb913', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '3bc62b6db7f94e73e6c2506f1aec5e03', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('59403f55f42030531e84505986855964', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, 'f35eedcbd59f2d26ccd8e272bc4d8ff1', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c12d7359dc691fe10987e215cacac57', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '54487c2d759e804cb1cfb693f08d8068', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('629ad6dd71cef6e14aabd15eb06a9614', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '8ce4f0fcc208e5f6fc2df006dde6af97', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('63aee5c7d736881afb9025fc8616237e', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '3959afc6560d15742c321db2829ce8db', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7c524f51d25db9416bbf5d58f2c6f30a', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '348cd21d98e885d53fda25a9631d1400', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9d088ad0bdce1cce77dd4dd1a6930f50', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, 'd8956530b27ada2015a4b01430977e0a', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bbe3a50bb6fa7c3394c1bff4c4ec6205', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '1b82d942786aeb5e7ad2d11faddd40a4', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d6a1a2c419e6fdb5770108852a482c3f', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, 'fa779dd6d01d142ebbc026fe28536a48', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e1fc8debe2d6e0f5eb5e7ac85ae03109', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '10d276655b44d45fcd10d2d1f1ccf140', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e2586438fdb1f67b993fb47e4da6e293', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '9836e51faa1b7f9cd6078834c56084ac', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fe75577a1c63e97edba385fe7f454c11', '8fad9415c2aa61b1d3e50df9d6a7a669', 'VALUE_SET', NULL, NULL, '8f56b9bafabd945133f3c8a8476d5160', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for ICE_IMM_EVAL_REASON_ACCEPTED
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3520418d69842fef53e32d12806ba7fd', '7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', NULL, NULL, 'b10fb286435b98a55a97ac2b84aa500c', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9632710eaebf7407a9635fc0e1815f7a', '7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', NULL, NULL, 'a1c47e342a8b69bdc59050ee725fdea5', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a398ca0d5f2af10f5db9e3df34cb34d4', '7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', NULL, NULL, '40a51896a0df836fed1c73cee820748a', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b55d7afe1f968e2f6db20072559904aa', '7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', NULL, NULL, '61b584b3bbfc8b9616572faf5cb34e3d', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ba8df3f56516d4935e63b56153839713', '7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', NULL, NULL, '8f906c0ef4f6ed265ae13af55410b4c0', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f64bb167744722b9e1ac366a63966058', '7ff13e695e1e176d3a1c06dbdc9a1da0', 'VALUE_SET', NULL, NULL, 'df53f32ca480051ebc6cf1f6cdc2da9a', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for ICE_IMM_GENDER
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6e5b6c3a921ba7d0622e7242d676e546', '5c352b28cc1325255763af9b1e35e695', 'CODE_SYSTEM', '537030c4d4a618263258a7d819b71b16', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ea5704ca73d6025e95c186a386745926', '5c352b28cc1325255763af9b1e35e695', 'CODE_SYSTEM', '161a3206589043b77094ef410e707121', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f1e1590654158f42f655ad77f823f4fe', '5c352b28cc1325255763af9b1e35e695', 'CODE_SYSTEM', 'a3e55f26912c2759407e01d34373ff50', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_REC_REASON_FUTURE_RECOMMENDED
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f1d942cfc8f3b32a1e8f09f1a1f08b8e', '5b6db361819bf41a7701670290eb4e02', 'VALUE_SET', NULL, NULL, '467525eb66a1d94d92ccc66c13562965', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_REC_REASON_NOT_RECOMMENDED
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4203c7f41a9b1452218d6760eccfe3f7', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '48633ba124634046266eb23b32d73acb', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('59fb0918a3421c6494b2ce0fdad55a31', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '3fba395f95203a684ece7cf9a6af7445', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6be093bb5cfe66c520c58a6d23d204e4', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '9afb24ac365ef39ccda1de93cb1b3412', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9300ca588b73e5ebcebf155722adddf4', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '4c5c872ba97478365cb25125b974cc56', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9e697c7372c916ebd8f8a50323031e00', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '6398a250594bb7e23c5c80701d9c33c9', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9f520ccaa3b44fb445c0bb801d725fee', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '3c9ecbcf1f879765d2b218785d5cfab3', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e2bd37384f4428f479a9dd556090fb8d', '543eb87dc88e0bd6da6d9b7b14fab39f', 'VALUE_SET', NULL, NULL, '218d287340bb668ce5e805e94a94a2f5', NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_VACCINE_GROUP
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1d721962f9e8c0ec341efa369890eee1', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '8b71825560392a83ebb8e4da645e31f9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('351c08646ae11880a2f7c3a8d4acd691', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'd61dfbba9507faf0201b746027a614b1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('37a82f634d2936828791ddbe244fd3d3', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '310518a288bb55558f9e5a614528e551', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('42a4e8c6d50b3e33883e7003ecca803f', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '7564ba9956b5e1231317f0d35c0c0562', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5c0c4e83e415f731d4f6ef75e28176e8', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'f4028a479943d4f9d665b375c3e42c50', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('618b1735db2ad1f543473ede81bffebb', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'faced2f118d43da024aee72e3fff7b99', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8b15036de881899bd41c855599e0e625', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'ae074b7462f600e8f8f5b9e8972ff0aa', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('995ba809c74d2cfbdae4d876cd8a2d08', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '1e30a4dfba31908875df46e6a39c87ff', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9abaeb48285a7acb2a06ece990b619be', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'a56086c8f7ea4be65a9872d610932f62', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a414edaeebbadbb77746dd5cf42ff212', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '6081ee6ced25174c1dcee698a270be7e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ca15d655ea8b37ddc8ece393f1fc6acb', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'a1bbadd1e2579d63fd83a3505c4e0c9c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dbea1765ec8fc3414319b9ac4a991947', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '89c6de3ec6e652b0ea9adb32c51b3b2d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('df602c24e8b1e9eaa0a9b56bbda142d5', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '6efadfb4961c7e87a0be9bc0b6ed47a0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ed504b441a96899a4f68c4309ec61753', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '46b67611ecfde554df6e9fa2a03c0c44', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fcc5d5c27659e31031d98df1ed489c76', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', '8e7c271a449d8a0b43712eea56609953', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fe536b5260250c510ea8079b99496e83', '50bfd24b91cd89dbf3fdbe6f2b638800', 'CODE_SYSTEM', 'a0d81acc5ecff41b343d937031a2c3c7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_DISEASE_DIAG_CONCEPT
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('36db26bfea17ce168bdc711053900ab3', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '5b08a0ba2e2bdd259f137a57b6cc83d4', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3caf6fae22e52353c86f3a04d9a14caa', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'ed1bf058913d4806d4c7aa8fc774a87b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3e766bb4abf2101e51cc317b1f4be32a', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '49b289efc9a44e8cee2a7d1ff9f6d319', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4fbadee6781569be7fd3a16d55b280c1', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '850090943836db29602c1e1fb7df5d2f', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('59c3c2dddbf5f6e955cc7b8df286380b', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'a5b18dc53cdb88393d7b7ab475653254', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('626366b9d8885d8b1ce98d791cfebc8d', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '46a524937c6b47beb74e67977ab44aef', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('638b5242a4d7d3dce6f2d361ceff2dcf', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'd21031b91c51d50177dfb897a59cb3e8', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6ed6f2a02b01d1df2eeee7fae6d6356e', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'd09f820290275497949a2dc4b63e2cb9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b70d27c6f4b7dac293364a69f90b6a1', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'e5d0ee72c6eb914b702d283bde516b9e', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8dacb6fb159f230023e2d853666cf73a', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '8e39694d8fe12457edcda500f74e902d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b4858cced4865074559309cb8a40bbd4', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '8750f40889cb260882e190ea585228d6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d95491a468e5cf217c53d3ab7a95de3a', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'e10591982a5a9530c49e4989ce749af6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e4d79b78966a27ade0930a6529592ef5', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '336d08336003fc6656e1820666989895', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ea1e1aa9608193284fab31064305588f', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, 'e4e29339f120d6e5a9a060d9446d2a9d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ee07b8ddd29d044fb83ba7c6225946c1', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '7e932a8c23ad4eec37aebf9ae1e2e101', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f477272aeac22c46ed9951599ab7de9c', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '2fbe722c84f17892db0c660d40f2770c', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fd24704861e8af518b3b57c68a882c5e', '2b9926a4d682759612a1bc99645479a3', 'CONCEPT', NULL, '3f5b8c2360690eefeb33888f1834c75b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ICE_IMM_REC_REASON
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2a1974832ce06d937abe4b08b20529ae', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '4e4e92bf69e5a547de07e6ab373f44ff', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('510b5ccbd0df16f772701cf99ec62cb9', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '2aa66b1545223ed4d3c0fa45f5efa466', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('56368e059da850c3831153ccc6722315', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '3868f01ac2be3842b429812dc4b54bdd', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('584af73f16d9178ba8271784a8543e01', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'dc03869edd9b51837429d5e8c81be355', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5a8871e125599672290a88a144e62dda', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '4d24f863c037e7e86cd499ff52952dc7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5ef543ae2f74301882d21a40a84e6103', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'dc8d62e6a007ce128ff6818d5ab39821', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('62f999f4b5781cd0848a353da519ca94', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'f3eaf38d6f9118d0b2168e7e6ab6efe7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b58e0d1e8aa1beee446c66690cf74ae', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '7844fa10d5de2f1288df65714cc116ff', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('95780066d80eba5f381ad71d27276db0', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'e3ef6218f586119751ed5834be648db4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('99d1d59a93ec4ea2d1dc4634f8e65256', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '45513fe32b8d95cb19ebcaacf1adc547', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9d494d7cddcf9c57f6da1d84fa7db54f', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '767bcc8fc9f27fa6a22c038579e29c2d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d0e4fd9ea5edc1387385d6386652d3a4', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'e7ce26dcfded66093e91f29319a21d43', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb888f21c5b65cb6854c6cb2e63c568f', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'df606f44c96a6ae0509513b8523d59dd', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ed7d6cfd1da17ab4d607f8a506e53e99', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', 'b1db5ec01a127d69a6b6714b9c230e5e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ffb7c504104ac9b4539bdb1185f38b01', '20da8f23dddb94fbb57b3980c55a393a', 'CODE_SYSTEM', '1de59e603debc6e91575137a2194ff51', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for ArithmeticOperator
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('55982ad8ee0c14f8ee9c7d4de1fbc81a', 'ea34a3f720d5aa1fd1f202b5e324002c', 'AD_HOC', NULL, NULL, NULL, NULL, '-', 'Subtract', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('815157c9eb1ceb0810028a3378f1aec2', 'ea34a3f720d5aa1fd1f202b5e324002c', 'AD_HOC', NULL, NULL, NULL, NULL, '+', 'Add', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for MedicalUnits
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1b6f3501920036852e2e6bb8ee899374', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'kg', 'kg', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2a4aaade42e89b3dcecb28ab6ce84d0d', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'L', 'L', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('387de303204efa60b69b26b97ae5cffc', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'dose', 'doses', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3fbced20981ee3a227a01e2069ae56b9', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'mg/dL', 'mg/dL', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('449f297b6ce70e0fc672816caa6fca36', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'EIA Units', 'EIA Units', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45bd5632f1886a24c8e7b5c33497d8d2', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'g', 'g', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('49eada0c3c1669ce2fd84087ead551fd', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'U', 'Units', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6506a1f8a2aeebb5ace5ac043920abd6', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'mU', 'MicroUnit', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6999ad57fbff7d478c93c604f3004086', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, '%', 'percent', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a1da070c91fdd1eac6d5b3fc164ed4a4', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'dL', 'dL', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a5eb1af2ca870b7aad4491a5b521e551', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'volume %', 'volume %', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b53d299d949b543b8438a6341d41250d', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'g/dL', 'g/dL', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bfc7efb64c649930a8a18a0e878e26bf', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, '% of total', '% of total', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e72aeace8e2f88d5f88e2e1eea5c29f6', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'ppm', 'ppm', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('efdd174b2f4e6e84db3af5a7fffccd26', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'mole', 'moles', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f42c9b691e06ac7cc7484ee68c1c9b85', 'c57137ec9eda557528cebad5448855af', 'AD_HOC', NULL, NULL, NULL, NULL, 'cm', 'cm', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for DurationType
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('78f2a57bd0f2e92ea7cedfee8ff6de2c', 'b208313be4ef365418ed390bf6b34928', 'AD_HOC', NULL, NULL, NULL, NULL, 'DurationType.Days', 'Days', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('81cfd0d513e4320f18976035ee8e3de1', 'b208313be4ef365418ed390bf6b34928', 'AD_HOC', NULL, NULL, NULL, NULL, 'DurationType.Weeks', 'Weeks', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('aea87f642f76c3f3d6d7c4a1e3704bb5', 'b208313be4ef365418ed390bf6b34928', 'AD_HOC', NULL, NULL, NULL, NULL, 'DurationType.Months', 'Months', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b3926c87913c6a986eee287f392f4f30', 'b208313be4ef365418ed390bf6b34928', 'AD_HOC', NULL, NULL, NULL, NULL, 'DurationType.Years', 'Years', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for openCdsCode_Disease
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e774cfe6659861ae636d79aa2fff51d', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'e5d0ee72c6eb914b702d283bde516b9e', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40f2cd8abe9554b15f9133a2b78a2b7f', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'e10591982a5a9530c49e4989ce749af6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4fd673876f369cf2a9c05d12166f21cb', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'ed1bf058913d4806d4c7aa8fc774a87b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('58002d6c4805c3aafecfadc39209c644', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'e4e29339f120d6e5a9a060d9446d2a9d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('760e02f2097ce0a8326069fffc75767b', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '5b08a0ba2e2bdd259f137a57b6cc83d4', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('778a500078683812d8d3499063ff4a81', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '46a524937c6b47beb74e67977ab44aef', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('86f28774727235c0a76838bcf8911b9d', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'd09f820290275497949a2dc4b63e2cb9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('89bbc47c44394fefe5bce5abfc9afff5', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '8750f40889cb260882e190ea585228d6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('928c7945fadfa7939132787e00ef79bd', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '7e932a8c23ad4eec37aebf9ae1e2e101', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a1edee6b6bc89087502b85d01ed7e560', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '2fbe722c84f17892db0c660d40f2770c', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a5650746a4d83dcf4df5702411ed6c7f', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '336d08336003fc6656e1820666989895', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bbd7c8acfca759cae35f900c1b8f0485', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'd21031b91c51d50177dfb897a59cb3e8', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('caea177343fb33ef64eb62fd6aa227ce', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '49b289efc9a44e8cee2a7d1ff9f6d319', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d72bdaeca8f745b99d47caf2dd7663d2', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '8e39694d8fe12457edcda500f74e902d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e5b7260588d1a277ddfa84428aa6f951', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '3f5b8c2360690eefeb33888f1834c75b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('eb05409a9b0d1cb89bb6ba23ed1ad1e4', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, 'a5b18dc53cdb88393d7b7ab475653254', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f0c5d7cccf0e6be864cbdd175779f7a3', 'a9f897d898e6ca4f959585f039d37b17', 'CONCEPT', NULL, '850090943836db29602c1e1fb7df5d2f', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for cdsCode_Recommendation_Value
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('159b71d8e608f7a7e6e9e81f39048719', '9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', 'a842f4f74b14cbc3582d270ab59919bc', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1abb2fdbefa239ad592f07a4d8297d0a', '9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', 'b1772f4680ea565059096f625ea283d0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1ae9a4bb935e59e0404d0afdd2769518', '9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', '461cf7273598f747bf4f87be40c81856', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8667cf9f8a78500ef5efb2cafa0c90cf', '9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', '6d44c6a07fdeab4eea549fce42fee8f5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c57ea837158c6a4398c448ed81f35686', '9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', '6caad4861f355a6176d2ddcd338541b0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c6c15edc13c41ffdda8477946d7530fa', '9ae9944e3d28f157e1562b095f2b0fd3', 'CODE_SYSTEM', 'edc905b5534a8414c0b6b7b5f8dae839', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for ComparisonOp
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('904605649228cb9b75f9c51477374bab', '9941592a447c86c33ec44e1b31aac680', 'AD_HOC', NULL, NULL, NULL, NULL, '>=', '>=', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9d61f8d356d0f66aae942026738e5b6e', '9941592a447c86c33ec44e1b31aac680', 'AD_HOC', NULL, NULL, NULL, NULL, '==', '=', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ba9243eb1f706c338a4b3da97583b289', '9941592a447c86c33ec44e1b31aac680', 'AD_HOC', NULL, NULL, NULL, NULL, '<=', '<=', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e7b319803800fad011cba462dbf398e4', '9941592a447c86c33ec44e1b31aac680', 'AD_HOC', NULL, NULL, NULL, NULL, '>', '>', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fafaf9e9bc83935f99aef6a45b1ba3c6', '9941592a447c86c33ec44e1b31aac680', 'AD_HOC', NULL, NULL, NULL, NULL, '<', '<', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for cdsCode_Vaccine_Group
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('113c72fcddb8b02ed4419e12ab6f42d2', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'faced2f118d43da024aee72e3fff7b99', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('31439d3a2e7eae93e1b83cbbbe7d71fd', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '8e7c271a449d8a0b43712eea56609953', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('334e4e159d2324b5e684d5d6ad3f84ca', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '8b71825560392a83ebb8e4da645e31f9', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3a95c31014e3eb71f4f3d8e8e4822176', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'd61dfbba9507faf0201b746027a614b1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('62b338557da05d23358c98842fcd4e04', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '6efadfb4961c7e87a0be9bc0b6ed47a0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('62d7a1a0462b05d896de5ed6035ab6c5', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '1e30a4dfba31908875df46e6a39c87ff', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7b91479241319a85f2e3f2c0e498f8f6', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '46b67611ecfde554df6e9fa2a03c0c44', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8064b2279144f0954179f08f935ae7ea', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'ae074b7462f600e8f8f5b9e8972ff0aa', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('86b2f65dcd2c8b0f592acefc666706e4', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '7564ba9956b5e1231317f0d35c0c0562', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('96584d8d679fc6a3a12413ebec8f6a76', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'a1bbadd1e2579d63fd83a3505c4e0c9c', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('982119d5b0c00817f98423f3e83cfc61', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'a56086c8f7ea4be65a9872d610932f62', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9e51ff5e455eb3fab7347ea9e31c72df', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'a0d81acc5ecff41b343d937031a2c3c7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('acdbc428ad815aaa5fef4bdab872ac24', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '89c6de3ec6e652b0ea9adb32c51b3b2d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c16f84d53377e3df8b0cf231e97ebe29', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '310518a288bb55558f9e5a614528e551', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e7877a4fd69e0cc2151e5fa4be950dde', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', 'f4028a479943d4f9d665b375c3e42c50', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ec0a268adf711ed682d66bcbaca5545a', '8f593a564630184bcc25b6f6aa31cc82', 'CODE_SYSTEM', '6081ee6ced25174c1dcee698a270be7e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- list items for openCdsCode_Vaccine
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('107cac4aa97292b227d4fa0bf9c1c526', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '39c89978fd27b14a2403e2fe3673fc64', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('116bc9b05ff37f04a5860268c3bf5c44', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'd7840c1215b97a29d9e0dfaa727f6fde', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1226fdbfd72c995e3eabf5b1fbfa07e6', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'a0394184f5f772394491eba2fdd2cd6d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1d0a137dc3f321c8fa7ab97743d8e751', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '276423739f3aa1f032f193b0e8071be3', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e1699379d831100a720f9738859c4b6', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '7a46c784275cefba9f603a377598ea24', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e39fde03cf8b497515169609bdd97ea', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '8e251c50bd38bb981132b959abb9daf3', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('1e771afd80de3b187b60dd0dff7ad244', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '3043187179ad37048a30a2dbbd6f1ad4', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('21df00a9296481074b7630619304c740', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'ff166a5f3b060f15b138afd5d87cd198', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('237c2906ac3040683a191c6db9ebc52e', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'c299b5a35cc0c15c33b8d79d867f066b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('280948d9d38b7bc41bdc71e951e7dcb8', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '6fdcddfe9d36d73e2e6664dc873e9815', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('290837142291f4626314fceca307d8c2', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '1b3a153dc564038b802335238fc6e2bf', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('32dbebcc644a9ce12b84aa424aeb17e9', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '504eed2afd40f3c8f6e9d805a59f3226', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('33a4a27dc2d7ca29f8f0ebc42cf12438', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '10e4d96a58df7d7f743a90ad36541ac9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('362f1ac88bdf51d4123ff880678d820f', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'b0fd342b9f5b6e47e4a3cc41b03b10d8', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('37d388c66a122ada2d34186227319815', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '13e71f70ad9630fcae7688fb37fa1389', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c99bdd265ea2b1b5850fdee37c69e08', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '46c3979a44a0577094bb66b348820427', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3dcd842de26de0095a26384c61cde3ea', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '1debbebcd1c494a8213ab05dd71f6d62', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3eded470a07068ec4ee394b0a6e9713a', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '58ddc48d2d7a8a57a71275111863b20b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3f684d3aaad6f7d2a1d1493f3a97e32b', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'a2ac7864a367aebef380e4af98dbd10e', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('40f279541d335517e25b241ba4a154ef', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '4ed70dfd50f032187d63bdbd6b6bfced', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('45697c55c27c7c39912116853fec7e26', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'e2d298da31b2d03859f7898c42efcd21', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4cbb54debaa43017cdfac4b1a117dd61', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'efa957475fbd88fe9b2ef107a57e27fa', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('4e268d0313621ac9499d728ef4340cfb', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '1e59777d1dd3e3c4ea8c82e98639aed9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('51addbfbbd335106a23320fd51e6ed51', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '10fb5b1f93f7eba82ee23c2aec5561d9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('5644a9a741f04b01b5ab2dc5fdc85dca', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'e23434e456e04fc97c5f73d883e09e86', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('575445d1cebb83c0e2f5c85252f1edf9', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '6a1ae4b797d662b5b93ae1772a1e2b22', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('68e6bf0e24d589eff30d6a43017b79ab', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'c9244d3cd3167420cc720b68749f18e6', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('69960ccfc58dcb9bb94edb93a6650ba6', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '4de9953e65c12555d0d944e9ef544a25', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('69b1b9ca3227c3995b8e07f45938a2e6', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '5c4debcc6268bea71cc5d8ce5a79d399', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('69dce124d3351f244311c3dc391204be', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '2bf18017335250b555a8c3c6875f35b3', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6fd31068b7c52eb34b09a2a07bb43f67', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'e2b4ceac589c3fb1b28b722ce5571685', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7f60124b5c33235ca05291a5b5178070', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '5dbf4c241ea421019081812746c9126a', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8ab782fa950443bbdb8b4df35e96e73b', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '2de4f72f1078230a474e31ff4e51ef35', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8cf7330d461b3356fb28a624562059a6', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '5511651bd1110b4df4fbd90445667214', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('95984efde5a23effc770e6de983435e5', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'dc0a46d2b7763a6b34c1a6764d4b2950', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('95ae0f85dec425f3203e43cec7ee37b8', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'bf5265afc7dba531b351010e3617af3c', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('98d071c57a95be2bd3464678e3240383', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '2be84f067521b33aa6e317f3415481d5', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('9dd607fa6fb596db2762450f8183ee29', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '19d03d8091aa3f4da6d21e27d0843660', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a3717d21e737294edb604623af22aa7c', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'acd1ae4fb14d3b2d6ac36e15f8cacbb7', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('a6aeccd4255edd9afd51dca92ad596ee', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'c470f1b18d7e423673daa5c51e528cce', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('abb747628e2f013e3def3c77605e184c', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '50c4a3779817b6aaedc7e1442d9c73f2', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b262724f9e8c4f39ddb15d6f86b7144b', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '7aa441fab874ac4de8e10449a6be361d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b4412ec15a789c0653a686d94d4beb26', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '40b8efb0eb48b5d2bdeb47979512b64a', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b76f1d5597605b88186e86f2a28f9cb7', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'dd734acceb82d02eda3dddaf5f5722df', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b7ac4cc5b66c23fd79ba8fc8e5cfd953', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '1889289e19733e8ba0ff193663bac407', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b9a538afadecea14b1d9f927b5f96207', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '35c28d47509f9e405ab749f9e032e2af', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('bf90fc7c3715fc5c9ce0b2ee12f26772', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '19fdf3518aea0b4d3c198509b4746295', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c128431981e8953f943b498b792b653d', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'ca726c47e1a6aecc572877b2cdbe2d8b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c96c14bde808027a67814c3d1c3b9935', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'a025f7e22ad9d7bc5df8438b67f66712', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c97972e56ba0f0035f7b6567dcdb01bc', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '6d5c5e09607d1f1072ceb0962f94f046', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ca06915fee923b6f9ba893ba7e8ac857', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'd3b8cb4755e5368e7c2cfe1714a87523', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d1718874de4b2582d1f0c64ba32b9c54', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'db3d31988458af71e7b83fa2dba19064', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d4491de155bd2474bda3e770dcecbff0', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'b0b54156779438ccee7deb6fc2a169ee', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('dac765ed65f3e7b2c3e1a451256b5115', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '138fd34a38afddffffccc5e0b23d048b', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('db440c6a628fffc94d0feb22d8c7a541', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'afe13f163e3eda0853767f00c02aaafa', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('de5ae80b7325c09b82cd1b2830fa50ee', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'a80d7ce2e56e4ed1a21531658da7063d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e04999cd1233dc7e76c68c36eb710ce6', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '184d96ab9450466838e36975af8d5893', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e3ec0af3bec827c4113ced0e9636a024', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '7469b4142956af6f9ae273ec7f564096', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e3fde64ef5f795015dd1c626bb5a9247', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '7b471a502ec26419b439b90ff26f756a', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e541f96894f9b3a01f65e48510588469', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '677737d6c0c21c9cd8d6f927b7abd8e9', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e88801f5d6aadad3c1e9a5a68f0a51c1', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '102a2e4fd09b28228a9e44a24fe12dc3', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e9b4e4b89f97c5e2f9b3cb35efcb586c', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'bfc390292f863e801c9854ca248e2c0c', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ea26d688a44c55962a3591eeff450527', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'dab138bb7a47efb1fffca51b0ae3965d', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f1fc9e14270e6a3c4d9b569aae22db8d', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '1386388188a237e3c79939cfe9ba8006', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'INTERNAL', CURRENT_TIMESTAMP, 'INTERNAL');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f48236b9b3b0620a7568bb7fa21a71d0', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'a7107045db775d88f6424726c017fb67', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f61b3df632f015d77121d4e3baeedbff', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '11290f644fbf2a79d3ef3336bbfeb63e', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fd0641a478f5d5da198d39d9eeebac58', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, 'c10a2862e338ccecab4c9567d26c3192', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fdbd99e3e0ac4345ac62b65cfa6d4145', '8e9ac2f0995b9e3deabe65d7b1abaf36', 'CONCEPT', NULL, '6c7bebb4409d458b185c729bde62b711', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for SeriesCompletionStatus
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6a259837d007feebada9150a2c2689aa', '2ed79c68e936e07f417febb04f8db3b0', 'AD_HOC', NULL, NULL, NULL, NULL, 'true', 'Complete', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d775ce6e93dd399ca279ec956a45c8d8', '2ed79c68e936e07f417febb04f8db3b0', 'AD_HOC', NULL, NULL, NULL, NULL, 'false', 'Not Complete', CURRENT_TIMESTAMP, 'daryl', CURRENT_TIMESTAMP, 'daryl');

-- list items for cdsCode_Recommendation_Interpretation
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('10b2f08de93b5641be5a533024c72bd7', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '3868f01ac2be3842b429812dc4b54bdd', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('12a56cd7bd59e8301637856fb222ca71', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'e7ce26dcfded66093e91f29319a21d43', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('13efa3817a0fc46b4b96b9c0a81b0114', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '7844fa10d5de2f1288df65714cc116ff', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('254d07c068d582d09f65252644962012', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'b1db5ec01a127d69a6b6714b9c230e5e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('25bc796a98867c4af9ead404ebd44088', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'df606f44c96a6ae0509513b8523d59dd', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2935669724679b23aecc0fcfeb2d3f06', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '4d24f863c037e7e86cd499ff52952dc7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2a31b39b15131a55299a7ede51bdb59b', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '4e4e92bf69e5a547de07e6ab373f44ff', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6cd9575a2d249ada1eea3d5e932f6d81', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'dc03869edd9b51837429d5e8c81be355', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7afc612ddd5fb2edfeaa4c9c41221a77', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'dc8d62e6a007ce128ff6818d5ab39821', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('99a3f67bc96dd0f1c1228acefb137692', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'e3ef6218f586119751ed5834be648db4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('add3848262976bf0eff6a082fd3d1bbd', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '767bcc8fc9f27fa6a22c038579e29c2d', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('c92f3591f3bda8591c5a1d5cdfcfce8a', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', 'f3eaf38d6f9118d0b2168e7e6ab6efe7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d4b43cb245659d797555ab9e718ca476', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '45513fe32b8d95cb19ebcaacf1adc547', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('e10dd9bc42410226c10f8a5232c95ade', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '1de59e603debc6e91575137a2194ff51', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('edee96839f5f3a7214a41a07db7215fa', '23fab00c9c8a58c457d56cd734155ec5', 'CODE_SYSTEM', '2aa66b1545223ed4d3c0fa45f5efa466', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- list items for cdsCode_Evaluation_Interpretation
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('16ce7337f373d47d78dcbb04f0856a64', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '4d830b901901edc01e62571ad471acd6', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('25de508feed58a4ff34185cc1c498880', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '41964f474cb4bf4e938400dfcc024fe0', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('279d91ec97e41594af31f63d51fb2c5e', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'bc365d09ed40e231ee794bbbbd951dac', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('2cc78c9dd4fdf563efc1febfc30a8f5b', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'a44d5aefe7bf02506f5703cb95fc2928', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3c4d16d1538d7c18f0201471951de6a3', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '348c52d04be7d556becb9b8c1ee70c6e', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('3fb6bc3f2d17617bdfff156ea406b772', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'aca72fc41c7f72311cc1baa8e17d3156', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('411e49dab00c7b148d1f60f1d66becc6', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '90120aea63b53909fd457036ceb8be36', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('42801e5825578f12359243bd803f29c5', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'b36cca7b23847708d121aed917aa11f3', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('57db17536c31833abd7cd607fdc503f8', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '87413172bb85f80e9768af68b856a7b4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('592ddd0b3f39c6eb1b8cc894b58c57fb', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '432a8991bf57233311c467e1911450b1', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('61f17c5d424fd1bed3d228fbc4ab69b3', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'ddc29f45341ad9ef169a4e1e72730617', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('68fb9b8b9fd478464931553b841368a1', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '2a828fb57c6c4d1fffffec2192f7005b', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('6d8dd53fe93ef54fbfb71cc3ca7b6691', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '57213fca1f901a6adf708be9e3407209', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('7715d51f16e42c437530725f5798e774', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '3b7d98e61499166ddf42d41423e0f526', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('8bdf026b2909674132c1cd24b4dfe2f6', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '75b8be1794967287eef76b398e70a646', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('961e08922492ae227ff088e330b9a6a6', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'dac424843b1e0477dcf5773d74642fd5', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('b2316fef55107fbc81a0b80a79b52b52', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'db6499a6c18228d447ed3fd93ecfb435', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('cea8fe6c5770a0a0a5704f697e0b3fcd', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '96d3c6f7811280e3a72e3d529889bfd7', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d01b4c5e51291d0da197b7a1a82e98d7', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '45db24fb5667367fc155438ff495fe26', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('d5bc4758de63cb0f67b08ca787c98c90', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'd7564f45ccf2014cab139b637eb735ba', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('ec6c8d6a37f463a288ed3bc2cc8ccbfa', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'b770bdcb9b9c2c94bc4f4860fdeed0a4', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('f3a3b6e4266c2a8b581cdf4542a8246b', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', '915680403ff70cbf8cedcdb02f166544', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO cds_list_item (item_id, list_id, item_type, code_id, opencds_code_id, value_set_cds_code_rel_id, opencds_concept_rel_id, ad_hoc_id, ad_hoc_label, last_mod_datetime, last_mod_id, create_datetime, create_id) 
    VALUES ('fdf7af2ae63a6648bfd0c1e5bc96eb61', '1f305bc0568822e2608f22be9d1bac9c', 'CODE_SYSTEM', 'bdec519c5d1ea06e5e233c03d22dea37', NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

-- //@UNDO
-- SQL to undo the change goes here.
