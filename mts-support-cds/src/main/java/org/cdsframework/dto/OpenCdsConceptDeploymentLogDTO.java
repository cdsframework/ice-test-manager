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
package org.cdsframework.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.annotation.Entity;
import org.cdsframework.annotation.GeneratedValue;
import org.cdsframework.annotation.Id;
import org.cdsframework.annotation.JndiReference;
import org.cdsframework.annotation.Permission;
import org.cdsframework.annotation.Table;
import org.cdsframework.aspect.annotations.PropertyListener;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.enumeration.DeploymentAction;
import org.cdsframework.enumeration.DeploymentEnvironment;
import org.cdsframework.enumeration.GenerationSource;

/**
 *
 * @author sdn
 */
@Entity
@Table(databaseId = "CDS", name = "opencds_concept_deployment_log")
@JndiReference(root = "mts-ejb-cds")
@Permission(name = "Concept Deployment Log")
@XmlRootElement(name = "ConceptDeploymentLog")
public class OpenCdsConceptDeploymentLogDTO extends BaseDTO {

    private static final long serialVersionUID = -4988653947386659393L;

    public interface ByCodeId {
    }

    public interface ByRelationshipId {
    }

    @GeneratedValue(source = GenerationSource.AUTO)
    @Id
    private String logId;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = OpenCdsConceptDTO.class)
    private String codeId;
    @NotNull
    private DeploymentAction deploymentAction;
    @NotNull
    private DeploymentEnvironment deploymentEnvironment;
    @GeneratedValue(source = GenerationSource.FOREIGN_CONSTRAINT, sourceClass = OpenCdsConceptRelDTO.class)
    private String relationshipId;

    /**
     * Get the value of relationshipId
     *
     * @return the value of relationshipId
     */
    public String getRelationshipId() {
        return relationshipId;
    }

    /**
     * Set the value of relationshipId
     *
     * @param relationshipId new value of relationshipId
     */
    @PropertyListener
    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    /**
     * Get the value of deploymentEnvironment
     *
     * @return the value of deploymentEnvironment
     */
    public DeploymentEnvironment getDeploymentEnvironment() {
        return deploymentEnvironment;
    }

    /**
     * Set the value of deploymentEnvironment
     *
     * @param deploymentEnvironment new value of deploymentEnvironment
     */
    @PropertyListener
    public void setDeploymentEnvironment(DeploymentEnvironment deploymentEnvironment) {
        this.deploymentEnvironment = deploymentEnvironment;
    }

    /**
     * Get the value of deploymentAction
     *
     * @return the value of deploymentAction
     */
    public DeploymentAction getDeploymentAction() {
        return deploymentAction;
    }

    /**
     * Set the value of deploymentAction
     *
     * @param deploymentAction new value of deploymentAction
     */
    @PropertyListener
    public void setDeploymentAction(DeploymentAction deploymentAction) {
        this.deploymentAction = deploymentAction;
    }

    /**
     * Get the value of codeId
     *
     * @return the value of codeId
     */
    public String getCodeId() {
        return codeId;
    }

    /**
     * Set the value of codeId
     *
     * @param codeId new value of codeId
     */
    @PropertyListener
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    /**
     * Get the value of logId
     *
     * @return the value of logId
     */
    public String getLogId() {
        return logId;
    }

    /**
     * Set the value of logId
     *
     * @param logId new value of logId
     */
    @PropertyListener
    public void setLogId(String logId) {
        this.logId = logId;
    }

}
