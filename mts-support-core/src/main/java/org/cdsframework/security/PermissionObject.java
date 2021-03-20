/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
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
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.security;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import org.cdsframework.annotation.Table;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.util.DTOUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
public class PermissionObject implements Serializable {

    private static final long serialVersionUID = 7256209197228293290L;

    private final String label;
    private final Class<? extends BaseDTO> dtoClass;
    private final String dtoTableName;
    private final boolean ddlDefined;

    public PermissionObject(String label, Class<? extends BaseDTO> dtoClass) {
        this.label = label;
        this.dtoClass = dtoClass;
        InputStream resourceAsStream = null;
        Table dtoTable = DTOUtils.getDtoTable(dtoClass);
        if (dtoTable != null) {
            dtoTableName = dtoTable.name().toUpperCase();
            ClassLoader classLoader = getClass().getClassLoader();
            if (classLoader != null) {
                resourceAsStream = classLoader.getResourceAsStream(String.format("/tables/%s.xml", dtoTableName.toLowerCase()));
            } else {
                System.out.println("WARN  PermissionObject - classloader was null");
            }
        } else {
            if (!Modifier.isAbstract(dtoClass.getModifiers())) {
                System.out.println("WARN  PermissionObject - dtoTable was null: " + dtoClass.getCanonicalName());
            }
            dtoTableName = null;
        }
        if (resourceAsStream != null) {
            ddlDefined = true;
        } else {
            ddlDefined = false;
        }
    }

    public boolean isDdlDefined() {
        return ddlDefined;
    }

    public String getDtoTableName() {
        return dtoTableName;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return dtoClass.getCanonicalName();
    }

    public Class getDtoClass() {
        return dtoClass;
    }
}
