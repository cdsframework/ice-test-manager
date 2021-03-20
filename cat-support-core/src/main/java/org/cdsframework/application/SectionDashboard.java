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
package org.cdsframework.application;

import org.cdsframework.util.LogUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@Named
@RequestScoped
public class SectionDashboard implements Serializable {

    private LogUtils logger = LogUtils.getLogger(SectionDashboard.class);
    private DashboardModel dashboardModel = new DefaultDashboardModel();
    private DashboardColumn column1 = new DefaultDashboardColumn();
    private DashboardColumn column2 = new DefaultDashboardColumn();

    @PostConstruct
    protected void initialize() {
        final String METHODNAME = "initialize ";
        logger.logBegin(METHODNAME);
        try {
            column1.addWidget("module1");
            column1.addWidget("module3");
            column1.addWidget("module5");
            column1.addWidget("module7");
            column1.addWidget("module9");
            column2.addWidget("module2");
            column2.addWidget("module4");
            column2.addWidget("module6");
            column2.addWidget("module8");
            column2.addWidget("module10");
            dashboardModel.addColumn(column1);
            dashboardModel.addColumn(column2);
        } finally {
            logger.logEnd(METHODNAME);
        }
    }

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public void handleDashboardReorder(DashboardReorderEvent event) {
    }
}
