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
package org.cdsframework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import org.cdsframework.exceptions.MtsException;

/**
 *
 * @author HLN Consulting LLC
 */
public class ObjectUtils {

    public static byte[] serializeObject(Object object) throws IOException {
        byte[] objectBytes = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = null;
        try {
            objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(object);
            objectBytes = byteArrayOutputStream.toByteArray();
        } finally {
            objectOutput.close();
            byteArrayOutputStream.close();
        }
        return objectBytes;
    }

    public static Object deserializeObject(byte[] objectBytes) throws IOException, ClassNotFoundException {
        Object object = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes);
        ObjectInput objectInput = null;
        try {
            objectInput = new ObjectInputStream(byteArrayInputStream);
            object = objectInput.readObject();
        } finally {
            byteArrayInputStream.close();
            objectInput.close();
        }
        return object;
    }
    
    public static int nullCompare(Object source, Object target) {
        int comparedValue = 0;
        if (source == null && target == null) {
            comparedValue = 0;
        }
        else if (source != null && target == null) {
            comparedValue = 1;
        }
        else if (source == null && target != null) {
            comparedValue = -1;
        }
        return comparedValue;
    }
    
    public static boolean objectToBoolean(Object oBoolean) {
        boolean retBoolean = false;
        if (oBoolean != null) {
            if (oBoolean instanceof Boolean) {
                retBoolean = (Boolean) oBoolean;
            }
            else if (oBoolean instanceof String) {
                retBoolean = StringUtils.stringToBoolean((String) oBoolean);
            }
        }
        return retBoolean;
    }

    public static Boolean objectToBoolean(Object oBoolean, boolean returnNull) {
        Boolean retBoolean = null;
        if (!returnNull) {
            retBoolean = false;
        }        
        if (oBoolean != null) {
            if (oBoolean instanceof Boolean) {
                retBoolean = (Boolean) oBoolean;
            }
            else if (oBoolean instanceof String) {
                retBoolean = StringUtils.stringToBoolean((String) oBoolean, returnNull);
            }
            else if (oBoolean instanceof Number) {
                retBoolean = ((Number) oBoolean).shortValue() > 0;    
            }
        }
        return retBoolean;
    }
    
    public static Long objectToLong(Object oValue) {
        Long retValue = null;
        if (oValue != null) {
            if (oValue instanceof Long) {
                retValue = (Long) oValue;
            }
            else if (oValue instanceof String) {
                retValue = Long.valueOf((String) oValue);
            }
            else if (oValue instanceof BigDecimal) {
                retValue = ((BigDecimal) oValue).longValue();
                if (retValue == 0) {
                    BigDecimal origValue = (BigDecimal) oValue;
                    if (!origValue.equals(BigDecimal.valueOf(0))) {
                        throw new IllegalArgumentException("Received a BigDecimal value " + oValue + 
                                " that is larger then the precision of a Long");
                    }
                }
            }
            // Added RS Map converts Integer to Long
            else if (oValue instanceof Integer) {
                retValue = new Long((Integer) oValue);
            }
        }
        
        return retValue;
    }  
    
    public static Integer objectToInteger(Object oValue) {
        Integer retValue = null;
        if (oValue != null) {
            if (oValue instanceof Integer) {
                retValue = (Integer) oValue;
            }
            else if (oValue instanceof String) {
                retValue = Integer.valueOf((String) oValue);
                
            } else if (oValue instanceof Long) {
                // Precision
                retValue = ((Long) oValue).intValue();
                if (retValue == -1) {
                    // Check original value
                    Long origValue = (Long) oValue;
                    if (!origValue.equals(-1L)) {
                        throw new IllegalArgumentException("Received a Long value " + oValue + 
                                " that is larger then the precision of an Integer");
                    }
                }
            }
            else if (oValue instanceof BigDecimal) {
                retValue = ((BigDecimal) oValue).intValue();
                if (retValue == 0) {
                    BigDecimal origValue = (BigDecimal) oValue;
                    if (!origValue.equals(BigDecimal.valueOf(retValue))) {
                        throw new IllegalArgumentException("Received a BigDecimal value " + oValue + 
                                " that is larger then the precision of an Integer");
                    }
                }
            }
            
        }
        
        return retValue;
    }

    public static String objectToString(Object oValue) {
        String retValue = null;
        if (oValue != null) {
            if (oValue instanceof String) {
                if (!StringUtils.isEmpty((String) oValue)) {
                    retValue = ((String) oValue).trim();
                }
            }
        }
        return retValue;
    }        

    public static Date objectToDate(Object oDate) throws MtsException {
        final String METHODNAME = "objectToDate";
        Date date = null;
        if (oDate != null) {
            if (oDate instanceof Date) {
                date = (Date) oDate;
            } else if (oDate instanceof String) {
                // We have a Date String (handle 3 different formats)
                String sDate = (String) oDate;
                try {
                    int dateLength = sDate.length();
                    // ISO8601 UTC
                    if (dateLength == 28) {
                        date = DateUtils.parseUTCDatetimeFormat(sDate);
                    }
                    // ISO8601 DateTime with Zone (Timezones abbre vary in size)
                    else if (dateLength > 14 ) {
                        date = DateUtils.parseISODatetimeFormat(sDate);
                    }
                    // MM/dd/yyyy format
                    else if (dateLength == 10) {
                        date = DateUtils.parseDateFromString(sDate, DateUtils.DATEINMASK);
                    }
                    // ISON8601 Date
                    else if (dateLength == 8) {
                        date = DateUtils.parseISODateFormat(sDate);
                    }
                    else {
                        throw new MtsException(METHODNAME + "The date " + sDate + 
                            " was received as String in a format that is NOT supported, " +
                            "IS8601 format supported: ( " + DateUtils.ISO8601_UTC_DATETIME + " " +
                                DateUtils.ISO8601_DATE_FORMAT + " " + 
                                DateUtils.ISO8601_DATETIME_FORMAT + " )" );
                    }
                    
                } catch (ParseException ex) {
                    throw new MtsException("A ParseException has occurred, Message: " + ex.getMessage(), ex);
                }
            }
        }
        return date;
    }
    
    public static Float objectToFloat(Object object) {
        Float value = null;
        if (object != null) {
            if (object instanceof BigDecimal) {
                BigDecimal bigDecimal = (BigDecimal) object;
                value = bigDecimal.floatValue();
            } else if (object instanceof String) {
                value = Float.valueOf((String) object);
            } else {
                value = (Float) object;
            }
        }
        return value;
    }

    public static Double objectToDouble(Object object) {
        Double value = null;
        if (object != null) {
            if (object instanceof BigDecimal) {
                BigDecimal bigDecimal = (BigDecimal) object;
                value = bigDecimal.doubleValue();
            } else if (object instanceof Float) {
                value = ((Float) object).doubleValue();
            } else if (object instanceof String) {
                value = Double.valueOf((String) object);
            } else {
                value = (Double) object;
            }
        }
        return value;
    }
    
    public static BigDecimal objectToBigDecimal(Object object) {
        BigDecimal value = null;
        if (object != null) {
            if (object instanceof Double) {
                value = BigDecimal.valueOf((Double) object);
            } else if (object instanceof Float) {
                value = BigDecimal.valueOf(((Float) object).doubleValue());
            } else {
                value = (BigDecimal) object;
            }
        }
        return value;
    }
    
}
