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
package org.cdsframework.exceptions;

/**
 *
 * @author HLN Consulting, LLC
 */
public class IncorrectResultSizeException extends RuntimeException {

    private int expectedSize;
    private int actualSize;

    public IncorrectResultSizeException() {
    }

    public IncorrectResultSizeException(Throwable thrwbl) {
        super(thrwbl);
    }

    public IncorrectResultSizeException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public IncorrectResultSizeException(String string) {
        super(string);
    }

    public IncorrectResultSizeException(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    public IncorrectResultSizeException(int expectedSize, int actualSize) {
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

    public IncorrectResultSizeException(String msg, int expectedSize) {
        super(msg);
        this.expectedSize = expectedSize;
    }

    public IncorrectResultSizeException(String msg, int expectedSize, Throwable ex) {
        super(msg);
        this.expectedSize = expectedSize;
        initCause(ex);
    }

    public IncorrectResultSizeException(String msg, int expectedSize, int actualSize) {
        super(msg);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

    public IncorrectResultSizeException(String msg, int expectedSize, int actualSize, Throwable ex) {
        super(msg);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
        initCause(ex);
    }

    public int getExpectedSize() {
        return expectedSize;
    }

    public int getActualSize() {
        return actualSize;
    }

}
