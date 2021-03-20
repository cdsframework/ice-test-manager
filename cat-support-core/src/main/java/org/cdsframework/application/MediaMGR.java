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

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.cdsframework.util.LogUtils;
import org.cdsframework.util.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author HLN Consulting, LLC
 */
@Named
@ApplicationScoped
public class MediaMGR implements Serializable {
    private static final long serialVersionUID = -6981350283829142594L;
    private final static LogUtils logger = LogUtils.getLogger(MediaMGR.class);
    
    private Map<String, StreamedContent> streamedContentMap = new ConcurrentHashMap<String,StreamedContent>();
    
    public String registerStreamedContent(StreamedContent streamedContent) {
        final String METHODNAME = "registerStreamedContent ";
       
        String key = StringUtils.getHashId();
        streamedContentMap.put(key, streamedContent);
//        logger.info(METHODNAME, "streamedContent=", streamedContent);
        return key;
   }
   
   public StreamedContent getStreamedContent() throws IOException {
       final String METHODNAME = "getStreamedContent ";

       FacesContext context = FacesContext.getCurrentInstance();
       StreamedContent streamedContent = null;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
//            logger.info(METHODNAME, "creating default streamedContent");
            streamedContent = new DefaultStreamedContent();
        }
        else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            logger.info(METHODNAME, "id=", id);

            streamedContent = streamedContentMap.get(id);
//            logger.info(METHODNAME, "returning StreamedContent=", streamedContent);
            streamedContentMap.remove(id);
        }
        return streamedContent;
    }    
    
}
