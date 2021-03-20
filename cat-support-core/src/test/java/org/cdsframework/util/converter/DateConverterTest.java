/**
 * CAT Core support plugin project.
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
package org.cdsframework.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HLN Consulting, LLC
 */
public class DateConverterTest {

    public DateConverterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAsObject method, of class DateConverter.
     */
    @Test
    public void testGetAsObject() {
        System.out.println("getAsObject");
        FacesContext context = null;
        UIComponent component = null;
        String inputDateString = "12/08/2015";
        DateConverter instance = new DateConverter();
        Object expResult = new DateTime(2015, 12, 8, 0, 0, 0, 0).toDate();
        Object result = instance.getAsObject(context, component, inputDateString);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAsString method, of class DateConverter.
     */
    @Test
    public void testGetAsString() {
        System.out.println("getAsString");
        FacesContext context = null;
        UIComponent component = null;
        Object value = new DateTime(2015, 12, 8, 0, 0, 0, 0).toDate();
        DateConverter instance = new DateConverter();
        String expResult = "12/08/2015";
        String result = instance.getAsString(context, component, value);
        assertEquals(expResult, result);
    }
}
