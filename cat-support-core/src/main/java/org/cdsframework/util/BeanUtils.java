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
package org.cdsframework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.base.CatBasePlugin;
import org.cdsframework.base.MessageArgCallback;

/**
 *
 * @author HLN Consulting, LLC
 */
public class BeanUtils {

    final private static LogUtils logger = LogUtils.getLogger(BeanUtils.class);

    public static BeanManager getBeanManager() throws NamingException {
        InitialContext initialContext = new InitialContext();
        return (BeanManager) initialContext.lookup("java:comp/BeanManager");
    }

    public static <T> T getBean(Class<T> beanClass) throws NamingException {
        BeanManager beanManager = getBeanManager();
        Bean<T> bean = (Bean<T>) beanManager.getBeans(beanClass).iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, beanClass, ctx);
    }

    public static Map<Class<? extends BaseDTO>, MessageArgCallback> getPluginMessageArgCallbackMap() throws NamingException {
        final String METHODNAME = "getPluginMessageArgCallbackMap ";
        long start = System.nanoTime();
        Map<Class<? extends BaseDTO>, MessageArgCallback> messageArgCallbackMap = new HashMap<Class<? extends BaseDTO>, MessageArgCallback>();
        BeanManager beanManager = getBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(CatBasePlugin.class);
        for (Bean bean : beans) {
            logger.info("found plugin bean: ", bean.getBeanClass());
            CreationalContext<CatBasePlugin> ctx = beanManager.createCreationalContext(bean);
            CatBasePlugin plugin = (CatBasePlugin) beanManager.getReference(bean, CatBasePlugin.class, ctx);
            Map<Class<? extends BaseDTO>, MessageArgCallback> pluginMessageArgCallbackMap = plugin.getMessageArgCallbackMap();
            logger.info(METHODNAME, "loading up plugin message arg callbacks for ", plugin.getClass(), "; count: ", pluginMessageArgCallbackMap.size());
            messageArgCallbackMap.putAll(pluginMessageArgCallbackMap);
            ctx.release();
        }
        logger.info(METHODNAME, "finished loading up plugin message arg callbacks - total count: ", messageArgCallbackMap.size());
        logger.logDuration("Plugin initialization ", start);
        return messageArgCallbackMap;
    }
}
