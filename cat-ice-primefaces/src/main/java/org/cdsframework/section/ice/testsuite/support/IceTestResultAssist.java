/**
 * CAT ICE support plugin project.
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
package org.cdsframework.section.ice.testsuite.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.application.Mts;
import org.cdsframework.datatable.DataTableMGR;
import org.cdsframework.client.support.IceTestSuiteMGRClient;
import org.cdsframework.dto.IceTestDTO;
import org.cdsframework.dto.IceTestResultDTO;
import org.cdsframework.dto.SystemPropertyDTO;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.CatException;
import org.cdsframework.handlers.DefaultExceptionHandler;
import org.cdsframework.lookup.SystemPropertyDTOList;
import org.cdsframework.util.LogUtils;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ViewScoped
public class IceTestResultAssist implements Serializable {

    private static final long serialVersionUID = 8846167958186019437L;

    final static private LogUtils logger = LogUtils.getLogger(IceTestResultAssist.class);
    private DataTableMGR<IceTestResultDTO> dataTableMGR = new DataTableMGR<IceTestResultDTO>(new ArrayList<IceTestResultDTO>(), getClass());
    private boolean complete;
    private boolean started;
    private Integer progress = 0;
    private Map<UUID, IceTestResultDTO> completedTestMap = new HashMap<UUID, IceTestResultDTO>();
    private List<UUID> testQueue = new ArrayList<UUID>();
    @Inject
    private SystemPropertyDTOList systemPropertyDTOList;
    @Inject
    private Mts mts;
    private IceTestSuiteMGRClient iceTestSuiteMGRClient;
    private TreeTable treeTable;
    private TreeNode treeTableRoot;
    private Object foo;

    @PostConstruct
    public void postConstructor() {
        try {
            // initialize the iceTestSuiteMGRClient
            iceTestSuiteMGRClient = mts.getManager(IceTestSuiteMGRClient.class);
        } catch (Exception e) {
            DefaultExceptionHandler.handleException(e, getClass());
        }
    }

    /**
     * Returns the progress of the test run and also queues test to be run if they haven't yet been submitted.
     *
     * @return
     * @throws CatException
     */
    public Integer getProgress() throws CatException {
        started = true;
        final String METHODNAME = "getProgress ";
        int totalTests = testQueue.size();
        logger.debug(METHODNAME, "totalTests: ", totalTests);

        // if we are offline - abort
        if (iceTestSuiteMGRClient == null) {
            throw new CatException("MTS is offline.");
        }

        // don't run if there aren't any tests
        if (progress == null || totalTests == 0) {
            progress = 0;
        } else {

            // build a list of UUIDs that we don't have results for
            List<UUID> uuidNotCompleteList = new ArrayList<UUID>();
            for (UUID uuid : testQueue) {
                if (!completedTestMap.containsKey(uuid)) {
                    uuidNotCompleteList.add(uuid);
                }
            }
            // get any new completed tests
            logger.debug(METHODNAME, "asking for status on: ", uuidNotCompleteList);
            List<IceTestResultDTO> completedTests = iceTestSuiteMGRClient.getCompletedTests(uuidNotCompleteList);
            logger.debug(METHODNAME, "Got back status on: ", completedTests);

            for (IceTestResultDTO item : completedTests) {
                logger.debug(METHODNAME, "Storing completed test: ", item.getUuid().toString());
                completedTestMap.put(item.getUuid(), item);
            }

            int totalComplete = completedTestMap.size();

            if (logger.isDebugEnabled()) {
                logger.info(METHODNAME, "totalComplete: ", totalComplete);
                logger.info(METHODNAME, "totalNotComplete: ", totalTests - totalComplete);
                logger.info(METHODNAME, "totalTests: ", totalTests);
            }

            // calculate the percentage progress
            progress = (int) ((totalComplete * 1.0) / (totalTests * 1.0) * 100.0);
            if (progress > 100) {
                progress = 100;
            }
        }
        logger.debug(METHODNAME, "reported progress: ", progress);
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * Perform a dtoList update on complete plus set all the final states of the involved objects
     *
     * @throws MtsException
     */
    public void onComplete() throws MtsException {
        final String METHODNAME = "onComplete ";
        logger.info(METHODNAME, "called!");
        treeTableRoot = new DefaultTreeNode("root", null);
        logger.info(METHODNAME, "building tree table");
        for (UUID uuid : testQueue) {
            logger.info(METHODNAME, "adding test node to tree table: ", uuid);
            IceTestResultDTO iceTestResultDTO = completedTestMap.get(uuid);
            dataTableMGR.addOrUpdateDTO(iceTestResultDTO);
            DefaultTreeNode testNode = new DefaultTreeNode("test", iceTestResultDTO, treeTableRoot);
            List<String> differenceLog = iceTestResultDTO.getDifferenceLog();
            if (!differenceLog.isEmpty()) {
                testNode.setExpanded(true);
            }
            for (String difference : differenceLog) {
                logger.info(METHODNAME, "adding diff node to tree table: ", difference);
                DefaultTreeNode diffNode = new DefaultTreeNode("diff", difference, testNode);
            }
        }
        clearTestMap();
        complete = true;
    }

    /**
     * Cancel the test run.
     */
    public void cancel() {
        iceTestSuiteMGRClient.cancelTests(testQueue);
        clearAll();
        RequestContext.getCurrentInstance().execute("if (typeof PF('progressBarVar') != 'undefined' && PF('progressBarVar') != null) PF('progressBarVar').cancel();");
    }

    /**
     * Retrieve the bar chart model used for the test results chart.
     *
     * @return
     */
    public HorizontalBarChartModel getBarChartModel() {
        HorizontalBarChartModel model = new HorizontalBarChartModel();
        if (complete) {
            ChartSeries passed = new ChartSeries();
            passed.setLabel("Passed");
            passed.set("Tests", getTotalPassed());

            ChartSeries failed = new ChartSeries();
            failed.setLabel("Failed");
            failed.set("Tests", getTotalFailed());

            ChartSeries skipped = new ChartSeries();
            skipped.setLabel("Skipped");
            skipped.set("Tests", getTotalSkipped());

            model.addSeries(passed);
            model.addSeries(failed);
            model.addSeries(skipped);
            model.setStacked(true);
            model.setTitle("Stats");
            model.setSeriesColors("008000,FF0000,FFCC00");
        }
        return model;
    }

    /**
     * Set instance flags to an initial state.
     */
    public void clearTestMap() {
        completedTestMap = new HashMap<UUID, IceTestResultDTO>();
        testQueue = new ArrayList<UUID>();
        progress = 0;
        complete = false;
        started = false;
    }

    /**
     * Empty the data table.
     */
    public void clearDataTable() {
        dataTableMGR = new DataTableMGR<IceTestResultDTO>(new ArrayList<IceTestResultDTO>(), getClass());
    }

    /**
     * Reset the instance flags and empty the datatable.
     */
    public void clearAll() {
        clearTestMap();
        clearDataTable();
    }

    /**
     * Get the total tests skipped count.
     *
     * @return
     */
    public int getTotalSkipped() {
        int totalSkipped = 0;
        if (dataTableMGR != null) {
            for (IceTestResultDTO item : dataTableMGR.getDtoList()) {
                if (item != null) {
                    IceTestDTO iceTestDTO = item.getIceTestDTO();
                    if (iceTestDTO != null) {
                        if (iceTestDTO.isIgnore()) {
                            totalSkipped++;
                        }
                    }
                }
            }
        }
        return totalSkipped;
    }

    /**
     * Get the total tests failed count.
     *
     * @return
     */
    public int getTotalFailed() {
        return getTotalRun() - getTotalPassed();
    }

    /**
     * Get the total tests that passed recommendation count.
     *
     * @return
     */
    public int getTotalRecPassed() {
        int totalRecPassed = 0;
        if (dataTableMGR != null) {
            for (IceTestResultDTO item : dataTableMGR.getDtoList()) {
                if (item != null) {
                    IceTestDTO iceTestDTO = item.getIceTestDTO();
                    if (iceTestDTO != null) {
                        if (!iceTestDTO.isIgnore() && item.isRecommendationPassed()) {
                            totalRecPassed++;
                        }
                    }
                }
            }
        }
        return totalRecPassed;
    }

    /**
     * Get the total tests that passed evaluation count.
     *
     * @return
     */
    public int getTotalEvalPassed() {
        int totalEvalPassed = 0;
        if (dataTableMGR != null) {
            for (IceTestResultDTO item : dataTableMGR.getDtoList()) {
                if (item != null) {
                    IceTestDTO iceTestDTO = item.getIceTestDTO();
                    if (iceTestDTO != null) {
                        if (!iceTestDTO.isIgnore() && item.isEvaluationPassed()) {
                            totalEvalPassed++;
                        }
                    }
                }
            }
        }
        return totalEvalPassed;
    }

    /**
     * Get the total tests that overall passed count.
     *
     * @return
     */
    public int getTotalPassed() {
        int totalPassed = 0;
        if (dataTableMGR != null) {
            for (IceTestResultDTO item : dataTableMGR.getDtoList()) {
                if (item != null) {
                    IceTestDTO iceTestDTO = item.getIceTestDTO();
                    if (iceTestDTO != null) {
                        if (!iceTestDTO.isIgnore() && item.isPassed()) {
                            totalPassed++;
                        }
                    }
                }
            }
        }
        return totalPassed;
    }

    /**
     * Get the cumulative tests duration.
     *
     * @return
     */
    public double getTotalDuration() {
        double totalDuration = 0.0;
        if (dataTableMGR != null) {
            for (IceTestResultDTO item : dataTableMGR.getDtoList()) {
                if (item != null) {
                    IceTestDTO iceTestDTO = item.getIceTestDTO();
                    if (iceTestDTO != null) {
                        if (!iceTestDTO.isIgnore()) {
                            totalDuration += item.getDuration();
                        }
                    }
                }
            }
        }
        return totalDuration;
    }

    /**
     * Get the total tests run count.
     *
     * @return
     */
    public int getTotalRun() {
        int totalRun = 0;
        if (dataTableMGR != null) {
            for (IceTestResultDTO item : dataTableMGR.getDtoList()) {
                if (item != null) {
                    IceTestDTO iceTestDTO = item.getIceTestDTO();
                    if (iceTestDTO != null) {
                        if (!iceTestDTO.isIgnore()) {
                            totalRun++;
                        }
                    }
                }
            }
        }
        return totalRun;
    }

    public Integer getMultiplexingFactor() {
        SystemPropertyDTO systemPropertyDTO = systemPropertyDTOList.getByNameScope("TEST_MGR_TEST_SUBMISSION_Q_SIZE", "ice");
        Integer submitQueueSize = 0;
        if (systemPropertyDTO != null) {
            submitQueueSize = Integer.valueOf(systemPropertyDTO.getValue());
        } else {
            logger.error("TEST_MGR_TEST_SUBMISSION_Q_SIZE property query returned null - the property is probably set to MTS only!");
        }
        return submitQueueSize;
    }

    /**
     * Set the initial list of test UUIDs for a test run.
     *
     * @param uuids
     */
    public void setTestUuids(List<UUID> uuids) {
        clearAll();
        this.testQueue = uuids;
    }

    /**
     * Get the value of complete
     *
     * @return the value of complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Set the value of complete
     *
     * @param complete new value of complete
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Get the value of started
     *
     * @return the value of started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Set the value of started
     *
     * @param started new value of started
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    public DataTableMGR<IceTestResultDTO> getDataTableMGR() {
        return dataTableMGR;
    }

    public void setDataTableMGR(DataTableMGR<IceTestResultDTO> dataTableMGR) {
        clearTestMap();
        this.dataTableMGR = dataTableMGR;
    }

    public TreeTable getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(TreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public TreeNode getTreeTableRoot() {
        return treeTableRoot;
    }

    public Object getFoo() {
        return foo;
    }

    public void setFoo(Object foo) {
        this.foo = foo;
    }
}
