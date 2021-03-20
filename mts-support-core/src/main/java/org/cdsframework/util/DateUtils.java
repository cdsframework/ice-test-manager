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

import java.util.*;
import java.text.*;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.StringUtils;
import org.cdsframework.exceptions.PastDateException;

/**
 * Provides a class to store commonly used date utility functions.
 *
 * @author HLN Consulting, LLC
 */
public class DateUtils {

    /**
     * The default date mask.
     */
    public static final String DATEINMASK = "MM/dd/yyyy";
    /**
     * Another default datetime mask.
     */
    public static final String MODDATEMASK = "yyyyMMddHHmmss";

    public static final String ISO8601 = "yyyyMMddHHmmssz";
    public static final String DATETIMEFORMAT = "yyyy-MM-dd-hh-mm-ss a";
    
    public static final String ISO8601_UTC_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String ISO8601_DATE_FORMAT = "yyyyMMdd";
    public static final String ISO8601_DATETIME_FORMAT = ISO8601;
    /**
     * Returns the current date.
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * Returns the year of a date.
     *
     * @param pDate
     * @return
     */
    public static int getYear(java.util.Date pDate) {
        if (pDate == null) {
            throw new IllegalArgumentException("getYear(): date may not be null.");
        }
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(pDate);
        return gCal.get(Calendar.YEAR);
    }

    /**
     * Returns the month of a date.
     *
     * @param pDate
     * @return
     */
    public static int getMonth(java.util.Date pDate) {
        if (pDate == null) {
            throw new IllegalArgumentException("getMonth(): date may not be null.");
        }
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(pDate);
        return (gCal.get(Calendar.MONTH) + 1);
    }

    /**
     * Returns the day of a date.
     *
     * @param pDate
     * @return
     */
    public static int getDay(java.util.Date pDate) {
        if (pDate == null) {
            throw new IllegalArgumentException("getDay(): date may not be null.");
        }
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(pDate);
        return gCal.get(Calendar.DATE);
    }

    /**
     * Returns the last date of a month.
     *
     * @param p_in_date
     * @return
     */
    public static java.util.Date getDateLastOfMonth(java.util.Date p_in_date) {
        if (p_in_date == null) {
            return null;
        }
        int yr = p_in_date.getYear();
        int mn = p_in_date.getMonth();
        int dy = getLastDayOfMonth(yr + 1900, mn + 1);
        return new java.util.Date(yr, mn, dy);
    }

    /**
     * Returns the integer of the last day of a month.
     *
     * @param p_year
     * @param p_month
     * @return
     */
    public static int getLastDayOfMonth(int p_year, int p_month) {
        int currYear = getYear(new java.util.Date());
        if (p_year < (currYear - 120) || p_year > (currYear + 50)) {
            throw new IllegalArgumentException("invalid year '" + p_year + "'");
        }
        if (p_month < 1 || p_month > 12) {
            throw new IllegalArgumentException("invalid month '" + p_month + "'");
        }

        Calendar c = new GregorianCalendar(p_year, p_month - 1, 1);
        c.setLenient(false);
        //int d = c.get(Calendar.DATE);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        int lastday = c.get(Calendar.DATE);
        return lastday;
    }

    /**
     * Returns a formatted date.
     *
     * @param p_date
     * @return
     */
    public static String getFormattedDate(java.util.Date p_date) {
        return getFormattedDate(p_date, "yyyy-MM-dd");
    }

    /**
     * Returns a default formatted date.
     *
     * @param p_date
     * @return
     */
    public static String getFormattedDateInMask(java.util.Date p_date) {
        return getFormattedDate(p_date, DATEINMASK);
    }

    /**
     * Returns a default formatted datetime.
     *
     * @param p_date
     * @return
     */
    public static String getFormattedModDateMask(java.util.Date p_date) {
        return getFormattedDate(p_date, MODDATEMASK);
    }

    /**
     * Returns a date string suitable for a logger.
     *
     * @return
     */
    public static String getLogDate() {
        return getFormattedDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * Returns a formatted date based on a mask passed in.
     *
     * @param p_date
     * @param p_fmt_string
     * @return
     */
    public static String getFormattedDate(java.util.Date p_date, String p_fmt_string) {
        String ret = "";
        if (p_date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(p_fmt_string); //yyyyMMdd");
            formatter.setLenient(false);
            ret = formatter.format(p_date);
        }
        return ret;
    }

    public static String getDateAsPresentOrPast(java.util.Date p_date) {
        String formattedDate = "";
        if (p_date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a"); //yyyyMMdd");
            formatter.setLenient(false);
            formattedDate = formatter.format(p_date);

            Calendar presentOrPastCalendar = Calendar.getInstance();
            formattedDate = formattedDate.replace(DateUtils.getFormattedDate(presentOrPastCalendar.getTime(), "MM/dd/yyyy"), "Today");

            if (!formattedDate.contains("Today")) {
                presentOrPastCalendar.add(Calendar.DATE, -1);
                formattedDate = formattedDate.replace(DateUtils.getFormattedDate(presentOrPastCalendar.getTime(), "MM/dd/yyyy"), "Yesterday");
            }
        }
        return formattedDate;
    }

    /**
     * Returns the last mod datetime.
     *
     * @param timeStamp
     * @return
     */
    public static java.util.Date getLastModDateTime(java.sql.Timestamp timeStamp) {
        java.util.Date dt = null;
        if (timeStamp != null) {
            dt = new java.util.Date(timeStamp.getTime());
        }
        return dt;
    }

    /**
     * Returns a date parsed from a string with a particular format.
     *
     * @param p_input
     * @param p_fmt_string
     * @param validate - NOT USED used throws exception if date is 100 years old
     * @return
     */
    public static java.util.Date parseDateFromString(String p_input, String p_fmt_string, boolean validate) {
        if (StringUtils.isEmpty(p_input)) {
            throw new IllegalArgumentException("Input string may not be empty");
        }
        if (StringUtils.isEmpty(p_input)) {
            throw new IllegalArgumentException("Date Format string may not be empty");
        }

        // Count the number for forward slashes "/" in input string
        int counter = 0;
        for (int i = -1; (i = p_input.indexOf("/", i + 1)) != -1; counter++) {
        }

        // If input string contains forward slashes "/"
        if (counter > 0) {
            // make sure there are only 2 forward slashes "/"
            if (counter != 2) {
                throw new IllegalArgumentException("Incorrect date format: " + p_input);
            }
        }

        java.util.Date ret = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(p_fmt_string);
            sdf.setLenient(false);
            ret = sdf.parse(p_input);
            if (validate) {
                Calendar calendar = new GregorianCalendar();
                calendar.add(Calendar.YEAR, -100);
                Calendar tmp = new GregorianCalendar();
                tmp.setTime(ret);
                if (tmp.get(Calendar.YEAR) < calendar.get(Calendar.YEAR)) {
                    throw new IllegalArgumentException("");
                }
            }
        } catch (IllegalArgumentException e) {
            e.initCause(new PastDateException("'" + p_input + "' Too far in the past"));
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to convert '" + p_input + "' into a date using format '" + p_fmt_string + "'.");
        }
        return ret;
    }

    /**
     * Returns a date parsed from a string with a particular format.
     *
     * @param p_input
     * @param p_fmt_string
     * @return
     */
    public static java.util.Date parseDateFromString(String p_input, String p_fmt_string) {
        // Date cant be over 100 year old
        return parseDateFromString(p_input, p_fmt_string, false);
    }

    /**
     * Returns a boolean if a string is a date.
     *
     * @param p_input
     * @param p_fmt_string
     * @return
     */
    public static final boolean isDate(String p_input, String p_fmt_string) {
        if (StringUtils.isEmpty(p_input)) {
            throw new IllegalArgumentException("Date Format string may not be empty");
        }

        boolean ret;  // until proven True
        try {
            java.util.Date tmp = parseDateFromString(p_input, p_fmt_string);
            ret = (tmp != null && p_input.equals(getFormattedDate(tmp, p_fmt_string)));
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    /**
     * Returns a boolean if a valid 10 digit date is passed with a wildcard...
     *
     * @param p_input
     * @return
     */
    public static boolean isValidTenCharDateWithWildcards(String p_input) {
        if (p_input == null || p_input.trim().length() != 10) {
            return false;
        }
        String trimmedInput = p_input.trim();
        char[] cDate = trimmedInput.toCharArray();
        boolean bValid = (Character.isDigit(cDate[0]) || cDate[0] == '*')
                && (Character.isDigit(cDate[1]) || cDate[1] == '*')
                && (cDate[2] == '/')
                && (Character.isDigit(cDate[3]) || cDate[3] == '*')
                && (Character.isDigit(cDate[4]) || cDate[4] == '*')
                && (cDate[5] == '/')
                && (Character.isDigit(cDate[6]) || cDate[6] == '*')
                && (Character.isDigit(cDate[7]) || cDate[7] == '*')
                && (Character.isDigit(cDate[8]) || cDate[8] == '*')
                && (Character.isDigit(cDate[9]) || cDate[9] == '*');
        //if(bValid && trimmedInput.startsWith("0*"))
        //    bValid = false;
        return bValid;
    }

    /**
     * Returns the difference in days between two dates.
     *
     * @param earlierDate
     * @param laterDate
     * @return
     */
    public static long getDaysDifference(String earlierDate, String laterDate) {
        String laterDateDD;
        long diff = getMillisecondDifference(laterDate, earlierDate);

        if (diff > 0 && diff < 86400000) // 86400000 is one day
        {
            // If get here we're within one days millisconds, so check the day to see if they are different
            String earlierDateDD = (earlierDate.substring(3, 5));

            if (null == laterDate) // then use today
            {
                java.util.Date dt = new java.util.Date();
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String sDt = df.format(dt);
                laterDateDD = (sDt.substring(3, 5));
            } else {
                laterDateDD = (laterDate.substring(3, 5));
            }

            if (earlierDateDD.equals(laterDateDD)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            /* If get here we're at least one days diff
             */

            long differenceInDays = diff / (24 * 60 * 60 * 1000);
            return differenceInDays;
        }
    }

    /**
     * Returns the milliseconds difference between two dates.
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getMillisecondDifference(String date1, String date2) {
        //System.out.println("StringHelper.getMillisecondDifference -- date1 = " + date1+ " - date2 = " + date2);
        Date dtChildDOB = new Date(date1);
        Date dtToday;

        if (null != date2) {
            dtToday = new Date(date2);  // set to date passed
        } else {
            dtToday = new Date();   // set to today
        }
        //System.out.println("StringHelper.getMillisecondDifference -- date1 = " + date1+ " - dtToday = " + dtToday);

        Calendar calChildDOB = Calendar.getInstance();
        Calendar calToday = Calendar.getInstance();

        // different date might have different offset
        calChildDOB.setTime(dtChildDOB);

        //long ldtChildDOB = dtChildDOB.getTime();
        calToday.setTime(dtToday);

        //long ldtToday = dtToday.getTime();
        long diffMillis = calChildDOB.getTimeInMillis() - calToday.getTimeInMillis();

        //System.out.println("StringHelper.getMillisecondDifference -- Date1 = " + calChildDOB.getTimeInMillis()+ " - Date2 = " + calToday.getTimeInMillis());
        //System.out.println("StringHelper.getMillisecondDifference -- difference in milliseconds=" + diffMillis);
        return diffMillis;
    }

    /**
     * Returns the difference in days between two dates.
     *
     * @param earlierDate
     * @param laterDate
     * @return
     */
    public static long getDaysDifference(Date earlierDate, Date laterDate) {
        long differenceInDays;

        // Get the difference in milliseconds
        long differenceInMilliSeconds = getMillisecondDifference(laterDate, earlierDate);
        // 86400000 is one day
        if (differenceInMilliSeconds > 0 && differenceInMilliSeconds < 86400000) {
            if (earlierDate.compareTo(laterDate) == 0) {
                differenceInDays = 0;
            } else {
                differenceInDays = 1;
            }
        } else {
            differenceInDays = differenceInMilliSeconds / (24 * 60 * 60 * 1000);
        }
        return differenceInDays;

    }

    /**
     * Returns the milliseconds difference between two dates.
     *
     * @param laterDate
     * @param earlierDate
     * @return
     */
    public static long getMillisecondDifference(Date laterDate, Date earlierDate) {
        Calendar calLaterDate = Calendar.getInstance();
        Calendar calEarlierDate = Calendar.getInstance();
        calLaterDate.setTime(laterDate);
        calEarlierDate.setTime(earlierDate);
        return (calLaterDate.getTimeInMillis() - calEarlierDate.getTimeInMillis());
    }

    /**
     * Returns the difference in months between two dates.
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int calcDateDiffMonths(java.util.Date date1, java.util.Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("input dates may not be null");
        }

        int nAgeMonths = 0;
        GregorianCalendar cal1 = new GregorianCalendar();  // the earlier date
        GregorianCalendar cal2 = new GregorianCalendar();  // the later date
        if (date1.after(date2)) {
            cal1.setTime(date2);
            cal2.setTime(date1);
        } else {
            cal1.setTime(date1);
            cal2.setTime(date2);
        }
        // increment one month until we've gone too far, then back up by one.
        while (cal1.before(cal2)) {
            cal1.add(Calendar.MONTH, 1);
            nAgeMonths++;
        }
        if (nAgeMonths > 0) {
            nAgeMonths--;
        }

        return nAgeMonths;
    }

    /**
     * Returns the difference in seconds between two dates.
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getElapsedSeconds(java.util.Date startTime, java.util.Date endTime) {
        long elapsedMillis = Math.abs(endTime.getTime() - startTime.getTime());
        float fSecs = elapsedMillis / 1000;
        long nSecs = (long) Math.floor(fSecs);
        return nSecs;
    }

    public static Date getTruncatedDate(Date inputDate) {
        Date truncatedDate = inputDate;
        if (inputDate != null) {
            truncatedDate = DateUtils.parseDateFromString(DateUtils.getFormattedDate(inputDate, "yyyy-MM-dd"), "yyyy-MM-dd");
        }
        return truncatedDate;
    }

    // Truncate the milliseconds
    public static Date getTruncatedDateTime(Date inputDateTime) {
        Date truncatedDateTime = inputDateTime;
        if (inputDateTime != null) {
            truncatedDateTime = DateUtils.parseDateFromString(DateUtils.getFormattedDate(inputDateTime, DATETIMEFORMAT), DATETIMEFORMAT);
        }
        return truncatedDateTime;

    }

    /**
     * Remove the time element from a java date object.
     * 
     * @param date
     * @return 
     */
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

//    public static Date convertDateTime(Date inputDateTime) {
//        Date convertedDateTime = inputDateTime;
//        if (inputDateTime != null) {
//            convertedDateTime = DateUtils.parseDateFromString(DateUtils.getFormattedDate(inputDateTime, "yyyy-MM-dd-hh-mm-ss a"), "yyyy-MM-dd-hh-mm-ss a");
//        }
//        return convertedDateTime;
//    }

    public static Integer getDateDiffDaysInteger(Date dateA, Date dateB) {
        int days = 0;
        if (dateA != null && dateB != null && !dateA.after(dateB) && !dateA.equals(dateB)) {
            days = getUnitsBetweenDates(dateA, dateB, Calendar.DAY_OF_MONTH, 0, 0);
        }
        return days;
    }

    public static String getDateDiffDays(Date dateA, Date dateB) {
        return String.format("%s days", getDateDiffDaysInteger(dateA, dateB));
    }

    public static String getDateDiffCombo(Date dateA, Date dateB) {
        String result;
        dateA = removeTime(dateA);
        dateB = removeTime(dateB);
        String dateDiffYMD = getDateDiffYMDNoEx(dateA, dateB);
        String dateDiffDays = getDateDiffDays(dateA, dateB);
        if (dateDiffYMD == null || dateDiffYMD.trim().isEmpty()) {
            result = dateDiffDays;
        } else {
            result = String.format("%s (%s)", dateDiffYMD, dateDiffDays);
        }
        return result;
    }

    public static String getDateDiffYMDNoEx(Date dateA, Date dateB) {
        String dateDiffYMD;
        if (dateA == null || dateB == null) {
            return "Null date found.";
        }
        if (dateA.after(dateB)) {
            return "Error - First date cannot proceed second date.";
        }
        try {
            dateDiffYMD = getDateDiffYMD(dateA, dateB);
        } catch (Exception e) {
            dateDiffYMD = "Error - " + e.getMessage();
//            System.out.println(dateA + " - before  - " + dateB);
        }
        return dateDiffYMD;
    }

    public static String getDateDiffDaysNoEx(Date dateA, Date dateB) {
        String dateDiffDays;
        if (dateA == null || dateB == null) {
            return "Null date found.";
        }
        if (dateA.after(dateB)) {
            return "Error - First date cannot proceed second date.";
        }
        try {
            dateDiffDays = getDateDiffDays(dateA, dateB);
        } catch (Exception e) {
            dateDiffDays = "Error - " + e.getMessage();
//            System.out.println(dateA + " - before  - " + dateB);
        }
        return dateDiffDays;
    }

    public static String getDateDiffYMD(Date dateA, Date dateB) {
        return getDateDiffYMD(dateA, dateB, false, false);
    }

    public static String getDateDiffYMD(Date dateA, Date dateB, boolean terse, boolean performEomChecks) {
        int years = 0;
        int months = 0;
        int days = 0;
        if (dateA != null && dateB != null) {
            years = getUnitsBetweenDates(dateA, dateB, Calendar.YEAR, 0, 0);
            months = getUnitsBetweenDates(dateA, dateB, Calendar.MONTH, years, 0);
            days = getUnitsBetweenDates(dateA, dateB, Calendar.DAY_OF_MONTH, years, months);
//            System.out.println("performEomChecks: " + performEomChecks);
            if (performEomChecks) {
                boolean eom5DayRule = checkEndOfMonthOffset(dateA, dateB, years, months + 1, -5);
                if (eom5DayRule) {
                    months++;
                    days = -5;
//                    System.out.println("performEomChecks - hit 5day rule: " + months + "m " + days + "d");
                }
                boolean eom4DayRule = checkEndOfMonthOffset(dateA, dateB, years, months + 1, -4);
                if (eom4DayRule) {
                    months++;
                    days = -4;
//                    System.out.println("performEomChecks - hit 4day rule: " + months + "m " + days + "d");
                }
                boolean eom3DayRule = checkEndOfMonthOffset(dateA, dateB, years, months + 1, -3);
                if (eom3DayRule) {
                    months++;
                    days = -3;
//                    System.out.println("performEomChecks - hit 3day rule: " + months + "m " + days + "d");
                }
                boolean eom1DayRule = checkEndOfMonthOffset(dateA, dateB, years, months + 1, -1);
                if (eom1DayRule) {
                    months++;
                    days = -1;
//                    System.out.println("performEomChecks - hit 1day rule: " + months + "m " + days + "d");
                }
            }
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (years > 0 || years < 0) {
            stringBuilder.append(years).append(terse ? "y " : " years ");
        }
        if (months > 0 || months < 0) {
            stringBuilder.append(months).append(terse ? "m " : " months ");
        }
        if (days > 0 || days < 0) {
            stringBuilder.append(days).append(terse ? "d " : " days ");
        }
        return stringBuilder.toString().trim();
    }

    public static boolean checkEndOfMonthOffset(Date dateA, Date dateB, int years, int months, int days) {
        boolean result = false;

        StringBuilder stringBuilder = new StringBuilder();
        if (years > 0) {
            stringBuilder.append(years).append("y ");
        }
        if (months > 0) {
            stringBuilder.append(months).append("m ");
        }
        stringBuilder.append(days).append("d ");

        Date incrementDateFromString = incrementDateFromString(dateA, stringBuilder.toString(), false);
//        System.out.println("incrementDateFromString - adding " + stringBuilder.toString() + " to " + dateA + " = " + incrementDateFromString);
        if (dateB.equals(incrementDateFromString)) {
            result = true;
        }
        return result;
    }

    public static int getUnitsBetweenDates(Date dateA, Date dateB, int unit, int years, int months) {
        int result = 0;
//        if (dateA.after(dateB)) {
//            throw new IllegalArgumentException("First date cannot proceed second date.");
//        }
        Calendar calendarA = Calendar.getInstance();
        Calendar calendarB = Calendar.getInstance();
        int appendValue = 1;
        if (dateA.after(dateB)) {
            appendValue = -1;
            calendarA.setTime(dateB);
            calendarB.setTime(dateA);
        } else {
            calendarA.setTime(dateA);
            calendarB.setTime(dateB);
        }
        calendarA.add(Calendar.YEAR, years);
        calendarA.add(Calendar.MONTH, months);
        while (calendarA.before(calendarB) || calendarA.equals(calendarB)) {
            calendarA.add(unit, 1);
            if (calendarA.before(calendarB) || calendarA.equals(calendarB)) {
                result += appendValue;
            }
        }
        return result;
    }

    public static Date incrementDateFromString(Date baseDate, String incrementString, boolean backtrack) {
        final String METHODNAME = "incrementDateFromString ";
        baseDate = removeTime(baseDate);
        int years = 0;
        int months = 0;
        int weeks = 0;
        int days = 0;
        int factor = 1;
        if (backtrack) {
            factor = -1;
        }
        String[] incrementItems;
        Date result = null;
        try {
            if (incrementString != null) {
                incrementItems = incrementString.toLowerCase().split("\\s+");
                if (incrementItems.length == 0) {
                    throw new IllegalArgumentException("A proper date increment string was not supplied: " + incrementString);
                }
            } else {
                throw new IllegalArgumentException("The date increment string was null!");
            }
            boolean foundValue = false;
            for (String item : incrementItems) {
                if (!"d".equals(item) && item.contains("d")) {
                    foundValue = true;
                    String daysString = item.split("d")[0];
                    try {
                        days += Integer.parseInt(daysString);
                    } finally {
                    }
                } else if (!"y".equals(item) && item.contains("y")) {
                    foundValue = true;
                    String yearsString = item.split("y")[0];
                    try {
                        years += Integer.parseInt(yearsString);
                    } finally {
                    }
                } else if (!"m".equals(item) && item.contains("m")) {
                    foundValue = true;
                    String monthsString = item.split("m")[0];
                    try {
                        months += Integer.parseInt(monthsString);
                    } finally {
                    }
                } else if (!"w".equals(item) && item.contains("w")) {
                    foundValue = true;
                    String weeksString = item.split("w")[0];
                    try {
                        weeks += Integer.parseInt(weeksString);
                    } finally {
                    }
                }
            }
            if (!foundValue || (years == 0 && months == 0 && days == 0 && weeks == 0)) {
                throw new IllegalArgumentException("A proper date increment string was not supplied: foundValue - "
                        + foundValue + " - years - " + years + " - months - " + months + " - days - " + days
                        + " - weeks - " + weeks + " - incrementString - " + incrementString);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("A proper date increment string was not supplied: " + incrementString);
        }
        Calendar calendar = Calendar.getInstance();
        if (baseDate != null) {
            calendar.setTime(baseDate);
//            System.out.println("incrementing base time: " + baseDate);
            calendar.add(Calendar.YEAR, factor * years);
//            System.out.println("incrementing years: " + years + " = " + calendar.getTime());
            calendar.add(Calendar.MONTH, factor * months);
//            System.out.println("incrementing months: " + months + " = " + calendar.getTime());
            calendar.add(Calendar.WEEK_OF_YEAR, factor * weeks);
//            System.out.println("incrementing weeks: " + weeks + " = " + calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, factor * days);
//            System.out.println("incrementing days: " + days + " = " + calendar.getTime());
            result = calendar.getTime();
        } else {
            throw new IllegalArgumentException("The base date was null!");
        }
        return result;
    }

    public static Date getDateInTimeZone(Date date, String timeZoneId) {
        Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(timeZoneId));
        mbCal.setTimeInMillis(date.getTime());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static boolean isFutureDate(Date date) {
        return (date != null && getTruncatedDate(date).after(getTruncatedDate(new Date())));
    }

    public static boolean isDateAfter(Date date1, Date date2) {
        return (date1 != null && date2 != null && getTruncatedDate(date1).after(getTruncatedDate(date2)));
    }

    public static boolean isDateBefore(Date date1, Date date2) {
        return (date1 != null && date2 != null && getTruncatedDate(date1).before(getTruncatedDate(date2)));
    }

    public static boolean isDateEqual(Date date1, Date date2) {
        return (date1 != null && date2 != null && getTruncatedDate(date1).equals(getTruncatedDate(date2)));
    }
    
    public static boolean isDateOver120Years(Date date) {
        return isDateOverAgeInYears(date, 120);
    }

    public static boolean isDateOverAgeInYears(Date date, int ageInYears) {
        return isDateOverAgeInYears(date, new Date(), ageInYears);
    }

    public static boolean isDateOverAgeInYears(Date date1, Date date2, int ageInYears) {
        return date1 != null && date2 != null && getUnitsBetweenDates(getTruncatedDate(date1), getTruncatedDate(date2), Calendar.YEAR, 0, 0) > ageInYears;
    }

    public static Date parseISODateFormat(String dateString) throws ParseException {
        Date result;
        SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        if (dateString == null || dateString.trim().isEmpty()) {
            result = null;
        } else {
            result = formatter.parse(dateString);
        }
        return result;
    }

    public static Date parseUTCDatetimeFormat(String dateString) throws ParseException {
        Date result;
        SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_UTC_DATETIME);
        if (dateString == null || dateString.trim().isEmpty()) {
            result = null;
        } else {
            result = formatter.parse(dateString);
        }
        return result;
    }    
    
    public static Date parseISODatetimeFormat(String dateString) throws ParseException {
        Date result;
        SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_DATETIME_FORMAT);
        if (dateString == null || dateString.trim().isEmpty()) {
            result = null;
        } else {
            result = formatter.parse(dateString);
        }
        return result;
    }    

    public static String getISODateFormat(Date date) {
        String result;
        if (date == null) {
            result = "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            result = formatter.format(date);
        }
        return result;
    }

    public static String getISODatetimeFormat(Date date) {
        String result;
        if (date == null) {
            result = "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            result = formatter.format(date);
        }
        return result;
    }

    public static Date parseDate(String s) {
        if (s == null) {
            return null;
        }
        return DatatypeConverter.parseDate(s).getTime();
    }

    public static String printDate(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(dt);
        return DatatypeConverter.printDate(cal);
    }

    public static Date parseDateTime(String s) {
        if (s == null) {
            return null;
        }
        return DatatypeConverter.parseDateTime(s).getTime();
    }

    public static String printDateTime(Date dt) {
        if (dt == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(dt);
    }
    
    public static String getDateAsYYYYMMDD(String dateToFormat) {
        String formattedDate = null;
        boolean success = false;
        String month = null;
        String day = null;
        String year = null;
        
        if (dateToFormat != null) {
            String[] dateParts = dateToFormat.split("/");
            if (dateParts.length == 3) {
                Integer dayNo = null;
                Integer monthNo = null;
                Integer yearNo = null;
                // Only asterisk or numbers are acceptable
                month = dateParts[0];
                if (!month.equals("**")) {
                    // Check to ensure its 1-12
                    try {
                        // Single asterisk's exists
                        int singleAsteriskPos = month.indexOf("*");
                        if (singleAsteriskPos == 0) {
                            // first position
                            monthNo = ObjectUtils.objectToInteger(month.substring(singleAsteriskPos + 1));
                        }
                        else if (singleAsteriskPos > 0) {
                            // second position
                            monthNo = ObjectUtils.objectToInteger(month.substring(0, singleAsteriskPos));
                        }
                        else {
                            monthNo = ObjectUtils.objectToInteger(month);
                        }
                        if (monthNo != null && (monthNo >= 1 && monthNo <= 12)) {
                            success = true;
                            month = StringUtils.leftPad(month, 2, "0");
                        }
                    }
                    catch (NumberFormatException e) {
                        success = NumberUtils.isDigit(month, true);
                    }
                }
                else {
                    success = true;
                }
                if (success) {
                    year = dateParts[2];
                    if (!year.equals("****")) {
                        try {
                            // Check to ensure its a valid year
                            yearNo = ObjectUtils.objectToInteger(year);
                            if (yearNo != null && year.length() == 4) {
                                success = true;
                            }
                            else {
                                success = false;
                            }
                        }
                        catch (NumberFormatException e) {
                            success = NumberUtils.isDigit(year, true);
                        }
                        
                    }
                    else {
                        success = true;
                    }
                }                
                if (success) {
                    day = dateParts[1];
                    if (!day.equals("**")) {
                        // Check to ensure its a valid day, add month dependancy
                        try {
                            dayNo = ObjectUtils.objectToInteger(day);
                            // Default day count
                            int dayCount = 31;

                            // Handle 30 calendar day months
                            if (monthNo != null) {
                                if (monthNo == 4 || monthNo == 6 || monthNo == 9 || monthNo == 11) {
                                    dayCount = 30;
                                }
                                // february
                                else if (monthNo == 2) {
                                    // default
                                    dayCount = 28;
                                    // Leap year ?
                                    if (yearNo != null) {
                                        GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
                                        if (gregorianCalendar.isLeapYear(yearNo)) {
                                            dayCount = 29;
                                        }
                                    }
                                }
                            }
                            
                            // Check the day count
                            if (dayNo != null && (dayNo >= 1 && dayNo <= dayCount)) {
                                success = true;
                                day = StringUtils.leftPad(day, 2, "0");
                                
                            }
                            else {
                                success = false;
                            }
                        }
                        catch (NumberFormatException e) {
                            success = NumberUtils.isDigit(day, true);
                        }
                    }
                    else {
                        success = true;
                    }
                }
            }
        }
        if (success) {
            month = month.replace("*", "_");
            day = day.replace("*", "_");
            year = year.replace("*", "_");
            formattedDate = year + month + day;
        }
        else {
            throw new IllegalArgumentException("Invalid Date");
        }
        
        return formattedDate;
    }    

    public static String getTimeFromDate(Date date, String timeFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.format(date.getTime());
    }
    public static String getTimeFromDate(Date date) {
        return getTimeFromDate(date, "HH:mm:ss");
    }
    public static boolean isDateStartOfDay(Date date) {
        boolean startOfDay = true;
        if (date != null) {
            startOfDay = getTimeFromDate(date).equals("00:00:00");
        }
        return startOfDay;
    }
    
}
