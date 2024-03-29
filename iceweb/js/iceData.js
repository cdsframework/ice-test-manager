/**
 * ICE lookup data initialization script.
 */

var cvxCodeSystem = '2.16.840.1.113883.12.292';
var icd9CodeSystem = '2.16.840.1.113883.6.103';
var groupCodeSystem = '2.16.840.1.113883.3.795.12.100.1';

var cvxData = {
    '01': {'displayName': 'DTP', 'group': ['200'] },
    '02': {'displayName': 'OPV', 'group': ['400'] },
    '03': {'displayName': 'MMR', 'group': ['500'] },
    '04': {'displayName': 'measles/rubella', 'group': ['500'] },
    '05': {'displayName': 'measles', 'group': ['500'] },
    '06': {'displayName': 'rubella', 'group': ['500'] },
    '07': {'displayName': 'mumps', 'group': ['500'] },
    '08': {'displayName': 'Hep B, adolescent or pediatric', 'group': ['100'] },
    '09': {'displayName': 'Td, adult, absorbed', 'group': ['200']},
    '10': {'displayName': 'IPV', 'group': ['400'] },
    '11': {'displayName': 'pertussis', 'group': ['200']},
    '15': {'displayName': 'influenza, split', 'group': ['800'] },
    '16': {'displayName': 'influenza, whole', 'group': ['800'] },
    '17': {'displayName': 'Hib NOS', 'group': ['300'] },
    '20': {'displayName': 'DTaP', 'group': ['200'] },
    '21': {'displayName': 'varicella', 'group': ['600'] },
    '22': {'displayName': 'DTP-Hib', 'group': ['200', '300'] },
    '24': {'displayName': 'Anthrax vaccine', 'group': ['999'] },
    '28': {'displayName': 'DT, pediatric', 'group': ['200']},
    '31': {'displayName': 'Hep A, pediatric, NOS', 'group': ['810'] },
    '32': {'displayName': 'meningococcal MPSV4', 'group': ['830'] },
    '33': {'displayName': 'pneumococcal polysaccharide', 'group': ['750'] },
    '37': {'displayName': 'yellow fever vaccine', 'group': ['999'] },
    '38': {'displayName': 'rubella/mumps', 'group': ['500'] },
    '42': {'displayName': 'Hep B, adolescent/high risk infant', 'group': ['100'] },
    '43': {'displayName': 'Hep B, adult', 'group': ['100'] },
    '44': {'displayName': 'Hep B, dialysis', 'group': ['100'] },
    '45': {'displayName': 'Hep B NOS', 'group': ['100'] },
    '46': {'displayName': 'Hib (PRP-D)', 'group': ['300'] },
    '47': {'displayName': 'Hib (HbOC)', 'group': ['300'] },
    '48': {'displayName': 'Hib (PRP-T)', 'group': ['300'] },
    '49': {'displayName': 'Hib (PRP-OMP)', 'group': ['300'] },
    '50': {'displayName': 'DTaP-Hib', 'group': ['200', '300'] },
    '51': {'displayName': 'Hib-Hep B', 'group': ['100', '300'] },
    '52': {'displayName': 'Hep A, adult', 'group': ['810'] },
    '62': {'displayName': 'HPV, quadrivalent', 'group': ['840'] },
    '74': {'displayName': 'rotavirus, tetravalent', 'group': ['820'] },
    '75': {'displayName': 'vaccinia (smallpox) vaccine', 'group': ['860'] },
    '83': {'displayName': 'Hep A, ped/adol, 2-dose', 'group': ['810'] },
    '84': {'displayName': 'Hep A, ped/adol, 3-dose', 'group': ['810'] },
    '85': {'displayName': 'Hep A NOS', 'group': ['810'] },
    '88': {'displayName': 'influenza NOS', 'group': ['800'] },
    '89': {'displayName': 'polio NOS', 'group': ['400'] },
    '94': {'displayName': 'MMR-Varicella', 'group': ['500', '600'] },
    '100': {'displayName': 'pneumococcal conjugate PCV 7', 'group': ['750'] },
    '102': {'displayName': 'DTP-Hib-Hep B', 'group': ['200', '100', '300'] },
    '103': {'displayName': 'meningococcal C conjugate', 'group': ['830'] },
    '104': {'displayName': 'Hep A-Hep B', 'group': ['810', '100'] },
    '105': {'displayName': 'vaccinia (smallpox) vaccine, diluted', 'group': ['860'] },
    '106': {'displayName': 'DTaP, 5 pertussis antigens', 'group': ['200'] },
    '107': {'displayName': 'DTaP NOS', 'group': ['200']},
    '108': {'displayName': 'meningococcal NOS', 'group': ['830'] },
    '109': {'displayName': 'pneumococcal NOS', 'group': ['750'] },
    '110': {'displayName': 'DTaP-Hep B-IPV', 'group': ['200', '400', '100'] },
    '111': {'displayName': 'influenza, live, intranasal', 'group': ['800'] },
    '113': {'displayName': 'Td, adult, preservative free', 'group': ['200'] },
    '114': {'displayName': 'meningococcal MCV4P', 'group': ['830'] },
    '115': {'displayName': 'Tdap', 'group': ['200'] },
    '116': {'displayName': 'rotavirus, pentavalent', 'group': ['820'] },
    '118': {'displayName': 'HPV, bivalent', 'group': ['840'] },
    '119': {'displayName': 'rotavirus, monovalent', 'group': ['820'] },
    '120': {'displayName': 'DTaP-Hib-IPV', 'group': ['200', '400', '300'] },
    '121': {'displayName': 'Zoster Vaccine, Live', 'group': ['620'] },
    '122': {'displayName': 'rotavirus NOS', 'group': ['820'] },
    '125': {'displayName': 'H1N1-09, nasal', 'group': ['890'] },
    '126': {'displayName': 'H1N1-09, preservative-free', 'group': ['890'] },
    '127': {'displayName': 'H1N1-09, injectable', 'group': ['890'] },
    '128': {'displayName': 'H1N1-09 NOS', 'group': ['890'] },
    '130': {'displayName': 'DTaP-IPV', 'group': ['200', '400']},
    '133': {'displayName': 'pneumococcal conjugate PCV 13', 'group': ['750'] },
    '135': {'displayName': 'influenza, high dose', 'group': ['800'] },
    '136': {'displayName': 'meningococcal MCV4O', 'group': ['830'] },
    '137': {'displayName': 'HPV NOS', 'group': ['840'] },
    '138': {'displayName': 'Td, not adsorbed', 'group': ['200']},
    '139': {'displayName': 'Td, adult NOS', 'group': ['200']},
    '140': {'displayName': 'influenza, injectable, preservative-free', 'group': ['800'] },
    '141': {'displayName': 'influenza, injectable', 'group': ['800'] },
    '144': {'displayName': 'influenza, intradermal, preservative-free', 'group': ['800'] },
    '146': {'displayName': 'DTaP-IPV-Hib-Hep B', 'group': ['200', '400', '300', '100'] },
    '147': {'displayName': 'meningococcal MCV4 NOS', 'group': ['830'] },
    '148': {'displayName': 'meningococcal C/Y-HIB PRP', 'group': ['300', '830'] },
    '149': {'displayName': 'influenza, live, intranasal, quadrivalent', 'group': ['800'] },
    '150': {'displayName': 'Influenza, injectable, quadrivalent (IIV4), preservative free', 'group': ['800'] },
    '151': {'displayName': 'influenza nasal, unspecified formulation', 'group': ['800'] },
    '152': {'displayName': 'pneumococcal conjugate NOS', 'group': ['750'] },
    '153': {'displayName': 'Influenza, injectable, MDCK, (ccIIV3), trivalent, preservative free', 'group': ['800'] },
    '155': {'displayName': 'influenza, recombinant, inj., preservative-free', 'group': ['800'] },
    '158': {'displayName': 'Influenza-IIV4, IM (>3yrs)', 'group': ['800'] },
    '161': {'displayName': 'Influenza, IIV4, IM, Presrv-free (Ped)', 'group': ['800'] },
    '162': {'displayName': 'Meningococcal B FHbp, recombinant (Trumenba)', 'group': ['835'] },
    '163': {'displayName': 'Meningococcal B4C, OMV (Bexsero)', 'group': ['835'] },
    '165': {'displayName': 'HPV (HPV9-Gardasil 9)', 'group': ['840'] },
    '166': {'displayName': 'Influenza-IIV4, ID Presrv-free(18-64yrs)', 'group': ['800'] },
    '168': {'displayName': 'Influenza, IIV3, Adjuvanted,IM(>=65yrs)', 'group': ['800'] },
    '170': {'displayName': 'DTaP-IPV-Hib', 'group': ['200', '400', '300'] },
    '171': {'displayName': 'Influenza-ccIIV4, IM (>=4yrs)', 'group': ['800'] },
    '178': {'displayName': 'OPV bivalent', 'group': ['400'] },
    '179': {'displayName': 'OPV, monovalent, unspecified (NOS)', 'group': ['400'] },
    '182': {'displayName': 'OPV, Unspecified (NOS)', 'group': ['400'] },
    '183': {'displayName': 'yellow fever vaccine alternative formulation', 'group': ['999'] },
    '184': {'displayName': 'yellow fever vaccine, unspecified formulation', 'group': ['999'] },    
    '185': {'displayName': 'Influenza, recombinant, quadrivalent,injectable, preservative free', 'group': ['800'] },
    '186': {'displayName': 'Influenza, injectable, MDCK, quadrivalent', 'group': ['800'] },
    '187': {'displayName': 'Zoster vaccine recombinant', 'group': ['620'] },
    '188': {'displayName': 'Zoster vaccine, unspecified formulation', 'group': ['620'] },
    '189': {'displayName': 'Hep B, adjuvanted', 'group': ['100'] },
    '194': {'displayName': 'influenza, Southern Hemisphere (NOS)', 'group': ['800'] },
    '195': {'displayName': 'DT-IPV adsorbed non-US', 'group': ['200', '400'] },
    '196': {'displayName': 'Td (adult) adsorbed, preservative free, Lf unspecified', 'group': ['200'] },
    '197': {'displayName': 'influenza, high dose, quadrivalent', 'group': ['800'] },
    '200': {'displayName': 'Influenza, Southern Hemisphere, pediatric, preservative free', 'group': ['800'] },
    '201': {'displayName': 'Influenza, Southern Hemisphere, preservative free', 'group': ['800'] },
    '202': {'displayName': 'Influenza, Southern Hemisphere, quadrivalent, with preservative', 'group': ['800'] },
    '203': {'displayName': 'meningococcal MenACWY-TT', 'group': ['830'] },
    '205': {'displayName': 'Influenza, seasonal vaccine, quadrivalent, adjuvanted', 'group': ['800'] },
    '206': {'displayName': 'Vaccinia, smallpox monkeypox vaccine live, PF', 'group': ['860'] },
    '207': {'displayName': 'COVID-19, mRNA LNP-S, PF, Moderna', 'group': ['850'] },
    '208': {'displayName': 'COVID-19, mRNA LNP-S, PF, Pfizer', 'group': ['850'] },
    '210': {'displayName': 'COVID-19, vector-nr, AstraZeneca', 'group': ['850'] },
    '211': {'displayName': 'COVID-19, rS-nanoparticle, Novavax', 'group': ['850'] },
    '212': {'displayName': 'COVID-19, vector-nr, Janssen', 'group': ['850'] },
    '213': {'displayName': 'COVID-19 (NOS)', 'group': ['850'] },
    '215': {'displayName': 'Pneumococcal Conjugate PCV15', 'group': ['750'] },
    '216': {'displayName': 'Pneumococcal Conjugate PCV20', 'group': ['750'] },
    '217': {'displayName': 'COVID-19, mRNA, LNP-S, PF, 30 mcg/0.3 mL dose, tris-sucrose, Pfizer', 'group': ['850'] },
    '218': {'displayName': 'COVID-19, mRNA, LNP-S, PF, 10 mcg/0.2 mL dose, tris-sucrose, Pfizer', 'group': ['850'] },
    '219': {'displayName': 'COVID-19, mRNA, LNP-S, PF, 3 mcg/0.2 mL, tris-sucrose, Pfizer', 'group': ['850'] },
    '220': {'displayName': 'Hep B recombinant, 3-antigen, Al(OH)3', 'group': ['100'] },
    '221': {'displayName': 'COVID-19, mRNA, LNP-S, PF, 50 mcg/0.5 mL dose, Moderna', 'group': ['850'] },
    '227': {'displayName': 'COVID-19, mRNA, LNP-S, PF, pediatric 50 mcg/0.5 mL dose, Moderna', 'group': ['850'] },
    '228': {'displayName': 'COVID-19, mRNA, LNP-S, PF, pediatric 25 mcg/0.25 mL dose, Moderna', 'group': ['850'] },
    '229': {'displayName': 'COVID-19, mRNA, LNP-S, bivalent booster, PF, 50 mcg/0.5 mL or 25mcg/0.25 mL dose, Moderna', 'group': ['850'] },
    '230': {'displayName': 'COVID-19, mRNA, LNP-S, bivalent booster, PF, 10 mcg/0.2 mL, Moderna', 'group': ['850'] },
    '300': {'displayName': 'COVID-19, mRNA, LNP-S, bivalent booster, PF, 30 mcg/0.3 mL dose, Pfizer', 'group': ['850'] },
    '301': {'displayName': 'COVID-19, mRNA, LNP-S, bivalent booster, PF, 10 mcg/0.2 mL dose, Pfizer', 'group': ['850'] },
    '302': {'displayName': 'COVID-19, mRNA, LNP-S, bivalent, PF, 3 mcg/0.2 mL dose, Pfizer', 'group': ['850'] },
    '308': {'displayName': 'COVID-19, XBB.1.5, mRNA, LNP-S, PF, tris-sucrose, 6 mos-4 yrs, Pfizer', 'group': ['850'] },
    '309': {'displayName': 'COVID-19, XBB.1.5, mRNA, LNP-S, PF, tris-sucrose, 12+ yrs, Pfizer', 'group': ['850'] },
    '310': {'displayName': 'COVID-19, XBB.1.5, mRNA, LNP-S, PF, tris-sucrose, 5-11yrs, Pfizer', 'group': ['850'] },
    '311': {'displayName': 'COVID-19, XBB.1.5, mRNA, LNP-S, PF, 6 mos-11 yrs, Moderna', 'group': ['850'] },
    '312': {'displayName': 'COVID-19, XBB.1.5, mRNA, LNP-S, PF, 12+ yrs, Moderna', 'group': ['850'] },
    '313': {'displayName': 'COVID-19, XBB.1.5, subunit, rS-nanoparticle, adjuvanted, PF, 12+ yrs, Novavax', 'group': ['850'] },
    '500': {'displayName': 'COVID-19 Non-US, Product Unknown', 'group': ['850'] },
    '501': {'displayName': 'COVID-19 IV Non-US (QAZCOVID-IN)', 'group': ['850'] },
    '502': {'displayName': 'COVID-19 IV Non-US (COVAXIN)', 'group': ['850'] },
    '503': {'displayName': 'COVID-19 LAV Non-US (COVIVAC)', 'group': ['850'] },
    '504': {'displayName': 'COVID-19 VVnr Non-US (Sputnik Light)', 'group': ['850'] },
    '505': {'displayName': 'COVID-19 VVnr Non-US (Sputnik V)', 'group': ['850'] },
    '506': {'displayName': 'COVID-19 VVnr Non-US Vaccine (CanSino Biological Inc./Beijing Institute of Biotechnology)', 'group': ['850'] },
    '507': {'displayName': 'COVID-19 PS Non-US (Anhui Zhifei Longcom Biopharm + Inst of Micro, Chinese Acad of Sciences)', 'group': ['850'] },
    '508': {'displayName': 'COVID-19 PS Non-US (Jiangsu Province Centers for Disease Control and Prevention)', 'group': ['850'] },
    '509': {'displayName': 'COVID-19 PS Non-US (EpiVacCorona)', 'group': ['850'] },
    '510': {'displayName': 'COVID-19 IV Non-US (BIBP, Sinopharm)', 'group': ['850'] },
    '511': {'displayName': 'COVID-19 IV Non-US (CoronaVac, Sinovac)', 'group': ['850'] },
    '512': {'displayName': 'COVID-19 VLP Non-US Vaccine (Medicago, Covifenz)', 'group': ['850'] },
    '513': {'displayName': 'COVID-19 PS Non-US Vaccine (Anhui Zhifei Longcom, Zifivax)', 'group': ['850'] },
    '514': {'displayName': 'COVID-19 DNA Non-US Vaccine (Zydus Cadila, ZyCoV-D)', 'group': ['850'] },
    '515': {'displayName': 'COVID-19 PS Non-US Vaccine (Medigen, MVC-COV1901)', 'group': ['850'] },
    '516': {'displayName': 'COVID-19 Inactivated Non-US Vaccine (Minhai Biotechnology Co, KCONVAC)', 'group': ['850'] },
    '517': {'displayName': 'COVID-19 PS Non-US Vaccine (Biological E Limited, Corbevax)', 'group': ['850'] },
    '518': {'displayName': 'COVID-19 Inactivated, Non-US Vaccine (VLA2001, Valneva)', 'group': ['850'] },
    '519': {'displayName': 'COVID-19 mRNA, bivalent, original/Omicron BA.1, Non-US Vaccine (Spikevax Bivalent), Moderna', 'group': ['850'] },
    '520': {'displayName': 'COVID-19 mRNA, bivalent, original/Omicron BA.1, Non-US Vaccine Product, Pfizer-BioNTech', 'group': ['850'] },
    '521': {'displayName': 'COVID-19 SP, protein-based, adjuvanted (VidPrevtyn Beta), Sanofi-GSK', 'group': ['850'] }
};

function getCvxData() {
    var cvxCode;
    var process = false;
    for (cvxCode in cvxData) {
        if (typeof(cvxData[cvxCode]['selectName']) === 'undefined') {
            process = true;
        }
        break;
    }
    if (process) {
        for (cvxCode in cvxData) {
            cvxData[cvxCode]['selectName'] = cvxCode + ': ' + cvxData[cvxCode]['displayName'];
            cvxData[cvxCode]['code'] = cvxCode;
        }
    }
    return cvxData;
}

var icd9DiseaseData = {
    '045.9': {'displayName': 'Acute Poliomyelitis', 'group': '400', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '079.89': {'displayName': 'Coronavirus', 'group': '850', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '032.9': {'displayName': 'Diphtheria', 'group': '200', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '488.1': {'displayName': 'H1N1 Influenza', 'group': '890', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '070.1': {'displayName': 'Hepatitis A', 'group': '810', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '070.30': {'displayName': 'Hepatitis B', 'group': '100', 'conceptCode': 'PROOF_OF_IMMUNITY', 'interpretation': 'IS_IMMUNE'},
    '482.2': {'displayName': 'Hib', 'group': '300', 'conceptCode': '', 'interpretation': 'IS_IMMUNE'},
    '795.0': {'displayName': 'Human Papillomavirus', 'group': '840', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '487': {'displayName': 'Influenza', 'group': '800', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '055.9': {'displayName': 'Measles', 'group': '500', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '036.9': {'displayName': 'Meningococcal', 'group': '830', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '072.9': {'displayName': 'Mumps', 'group': '500', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '059.00': {'displayName': 'Orthopoxvirus', 'group': '860', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '033.9': {'displayName': 'Pertussis', 'group': '200', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '008.61': {'displayName': 'Rotavirus', 'group': '820', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '056.9': {'displayName': 'Rubella', 'group': '500', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '037': {'displayName': 'Tetanus', 'group': '200', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'},
    '052.9': {'displayName': 'Varicella', 'group': '600', 'conceptCode': 'DISEASE_DOCUMENTED', 'interpretation': 'IS_IMMUNE'}
};

function getDiseaseData() {
    var diseaseCode;
    var process = false;
    for (diseaseCode in icd9DiseaseData) {
        if (typeof(icd9DiseaseData[diseaseCode]['selectName']) === 'undefined') {
            process = true;
        }
        break;
    }
    if (process) {
        for (diseaseCode in icd9DiseaseData) {
            icd9DiseaseData[diseaseCode]['selectName'] = diseaseCode + ': ' + icd9DiseaseData[diseaseCode]['displayName'];
            icd9DiseaseData[diseaseCode]['code'] = diseaseCode;
        }
    }
    return icd9DiseaseData;
}

var vaccineGroups = {
    '850': {'displayName' : 'COVID-19'},
    '860': {'displayName' : 'Othopoxvirus'},
    '200': {'displayName' : 'DTP'},
    '810': {'displayName' : 'Hep A'},
    '100': {'displayName' : 'Hep B'},
    '300': {'displayName' : 'Hib'},
    '840': {'displayName' : 'Human Papillomavirus'},
    '800': {'displayName' : 'Influenza'},
    '830': {'displayName' : 'Meningococcal ACWY'},
    '835': {'displayName' : 'Meningococcal B'},
    '500': {'displayName' : 'MMR'},
    '750': {'displayName' : 'Pneumococcal'},
    '400': {'displayName' : 'Polio'},
    '820': {'displayName' : 'Rotavirus'},
    '600': {'displayName' : 'Varicella'},
    '620': {'displayName' : 'Zoster'},
    '890': {'displayName' : 'H1N1 Influenza'},
    '999': {'displayName' : 'Other'}
};

function getVaccineGroupData() {
    var groupCode;
    var cvxCode;
    var process = false;
    for (groupCode in vaccineGroups) {
        if (typeof(vaccineGroups[groupCode]['vaccineList']) === 'undefined') {
            process = true;
        }
        break;
    }
    if (process) {
        for (groupCode in vaccineGroups) {
            vaccineGroups[groupCode]['vaccineList'] = [];
            vaccineGroups[groupCode]['code'] = groupCode;
        }
        for (cvxCode in getCvxData()) {
            var groupList = cvxData[cvxCode]['group'];
            for (var i = 0; i < groupList.length; i++) {
                vaccineGroups[groupList[i]]['vaccineList'][vaccineGroups[groupList[i]]['vaccineList'].length] = cvxData[cvxCode];
            }
            cvxData[cvxCode]['selectName'] = cvxCode + ': ' + cvxData[cvxCode]['displayName'];
        }
        for (groupCode in vaccineGroups) {
            vaccineGroups[groupCode]['vaccineList'].sort(cvxCompare);
        }
    }
    return vaccineGroups;
}

/**
 * Sort cvxData objects by their integer cvx values.
 * 
 * @param {type} a
 * @param {type} b
 * @returns {Number}
 */
function cvxCompare(a,b) {
  if (parseInt(a.code) < parseInt(b.code))
     return -1;
  if (parseInt(a.code) > parseInt(b.code))
    return 1;
  return 0;
}

var defaultPatientList = '{"95f6340e-c51e-10e1-0337-7d8c66f1fb52":{"firstName":"Yogii","lastName":"Bare","gender":"M","dob":"20091130","id":"95f6340e-c51e-10e1-0337-7d8c66f1fb52","izs":[["14c5c7b8-ba82-f0f1-5651-6edff3905879","20091130","127: H1N1-09, injectable"],["58c2882d-2ccc-b344-d541-069c0092b72a","20100212","127: H1N1-09, injectable"],["0d1224e4-3a09-86b2-77a0-d92e2dc80d56","20100827","127: H1N1-09, injectable"],["f45a8fd1-b952-1e2b-8ef7-b500bcdcc5d3","20101002","88: influenza NOS"],["ae9b5ce3-57b2-b258-13cb-fea54509ee69","20120103","88: influenza NOS"],["88080581-02cf-7af8-60c0-0b09c8636596","20120126","43: Hep B, adult"],["4fb582e8-0821-6001-a9a5-25498ef66f9b","20120209","107: DTaP NOS"],["6e97e755-590a-c34a-9ddd-5230a034369e","20130103","94: MMR-Varicella"],["b089f223-a57b-141e-1f82-03de11e7da9b","20130103","03: MMR"],["d6b8a0ab-89a9-34d5-3e17-f67210814c98","",""]]}}';
