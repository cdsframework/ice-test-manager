/**
 * The MTS support cds project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
 * details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
 * Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
 * limitation, and warranty) complete irrevocable access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HLN Consulting, LLC
 */
public enum State {

    ALABAMA("Alabama", "AL"),
    ALASKA("Alaska", "AK"),
    AMERICAN_SAMOA("American Samoa", "AS"),
    ARIZONA("Arizona", "AZ"),
    ARKANSAS("Arkansas", "AR"),
    CALIFORNIA("California", "CA"),
    COLORADO("Colorado", "CO"),
    CONNECTICUT("Connecticut", "CT"),
    DELAWARE("Delaware", "DE"),
    DISTRICT_OF_COLUMBIA("District of Columbia", "DC"),
    FLORIDA("Florida", "FL"),
    GEORGIA("Georgia", "GA"),
    GUAM("Guam", "GU"),
    HAWAII("Hawaii", "HI"),
    IDAHO("Idaho", "ID"),
    ILLINOIS("Illinois", "IL"),
    INDIANA("Indiana", "IN"),
    IOWA("Iowa", "IA"),
    KANSAS("Kansas", "KS"),
    KENTUCKY("Kentucky", "KY"),
    LOUISIANA("Louisiana", "LA"),
    MAINE("Maine", "ME"),
    MARYLAND("Maryland", "MD"),
    MASSACHUSETTS("Massachusetts", "MA"),
    MICHIGAN("Michigan", "MI"),
    MINNESOTA("Minnesota", "MN"),
    MISSISSIPPI("Mississippi", "MS"),
    MISSOURI("Missouri", "MO"),
    MONTANA("Montana", "MT"),
    NEBRASKA("Nebraska", "NE"),
    NEVADA("Nevada", "NV"),
    NEW_HAMPSHIRE("New Hampshire", "NH"),
    NEW_JERSEY("New Jersey", "NJ"),
    NEW_MEXICO("New Mexico", "NM"),
    NEW_YORK("New York", "NY"),
    NORTH_CAROLINA("North Carolina", "NC"),
    NORTH_DAKOTA("North Dakota", "ND"),
    OHIO("Ohio", "OH"),
    OKLAHOMA("Oklahoma", "OK"),
    OREGON("Oregon", "OR"),
    PALAU("Palau", "PW"),
    PENNSYLVANIA("Pennsylvania", "PA"),
    PUERTO_RICO("Puerto Rico", "PR"),
    RHODE_ISLAND("Rhode Island", "RI"),
    SOUTH_CAROLINA("South Carolina", "SC"),
    SOUTH_DAKOTA("South Dakota", "SD"),
    TENNESSEE("Tennessee", "TN"),
    TEXAS("Texas", "TX"),
    UTAH("Utah", "UT"),
    VERMONT("Vermont", "VT"),
    VIRGIN_ISLANDS("Virgin Islands", "VI"),
    VIRGINIA("Virginia", "VA"),
    WASHINGTON("Washington", "WA"),
    WEST_VIRGINIA("West Virginia", "WV"),
    WISCONSIN("Wisconsin", "WI"),
    WYOMING("Wyoming", "WY");

    /**
     * The state's label.
     */
    private final String label;

    /**
     * The state's abbreviation.
     */
    private final String abbreviation;

    /**
     * The set of states addressed by abbreviations.
     */
    public static final Map<String, State> STATES_BY_ABBR = new HashMap<String, State>();

    /* static initializer */
    static {
        for (State state : values()) {
            STATES_BY_ABBR.put(state.getAbbreviation(), state);
        }
    }

    /**
     * Constructs a new state.
     *
     * @param name the state's label.
     * @param abbreviation the state's abbreviation.
     */
    State(String label, String abbreviation) {
        this.label = label;
        this.abbreviation = abbreviation;
    }

    /**
     * Returns the state's abbreviation.
     *
     * @return the state's abbreviation.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Gets the enum constant with the specified abbreviation.
     *
     * @param abbr the state's abbreviation.
     * @return the enum constant with the specified abbreviation.
     */
    public static State valueOfAbbreviation(final String abbr) {
        final State state = STATES_BY_ABBR.get(abbr);
        if (state != null) {
            return state;
        } else {
            throw new IllegalArgumentException("State was null!");
        }
    }

    public static State valueOfName(final String name) {
        final String enumName = name.toUpperCase().replaceAll(" ", "_");
        try {
            return valueOf(enumName);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("No valueOf: " + name);
        }
    }

    public static State valueOfNoException(final String name) {
        State result = null;
        if (name != null) {
            final String enumName = name.toUpperCase().replaceAll(" ", "_");
            for (State item : State.values()) {
                if (item.name().equals(enumName) || item.getLabel().equals(name) || item.getAbbreviation().equals(name)) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    public String getLabel() {
        return label;
    }

}
