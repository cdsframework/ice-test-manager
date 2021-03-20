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
package org.cdsframework.util.support;

import org.cdsframework.util.support.FastByteArrayInputStream;
import java.io.OutputStream;
import java.io.InputStream;

/**
 *
 * @author HLN Consulting, LLC
 */
public class FastByteArrayOutputStream extends OutputStream {
    /**
     * Buffer and size
     */
    protected byte[] buf = null;
    protected int size = 0;

    /**
     * Constructs a stream with buffer capacity size 5K
     */
    public FastByteArrayOutputStream() {
        this(5 * 1024);
    }

    /**
     * Constructs a stream with the given initial size
     */
    public FastByteArrayOutputStream(int initSize) {
        this.size = 0;
        this.buf = new byte[initSize];
    }

    /**
     * Ensures that we have a large enough buffer for the given size.
     */
    private void verifyBufferSize(int sz) {
        if (sz > buf.length) {
            byte[] old = buf;
            buf = new byte[Math.max(sz, 2 * buf.length )];
            System.arraycopy(old, 0, buf, 0, old.length);
            old = null;
        }
    }

    public int getSize() {
        return size;
    }

    /**
     * Returns the byte array containing the written data. Note that this
     * array will almost always be larger than the amount of data actually
     * written.
     */
    public byte[] getByteArray() {
        return buf;
    }

    public final void write(byte b[]) {
        verifyBufferSize(size + b.length);
        System.arraycopy(b, 0, buf, size, b.length);
        size += b.length;
    }

    public final void write(byte b[], int off, int len) {
        verifyBufferSize(size + len);
        System.arraycopy(b, off, buf, size, len);
        size += len;
    }

    public final void write(int b) {
        verifyBufferSize(size + 1);
        buf[size++] = (byte) b;
    }

    public void reset() {
        size = 0;
    }

    /**
     * Returns a ByteArrayInputStream for reading back the written data
     * @return
     */
    public InputStream getInputStream() {
        return new FastByteArrayInputStream(buf, size);
    }

}
