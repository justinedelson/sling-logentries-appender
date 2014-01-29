/*
 * #%L
 * ACS AEM Commons Bundle
 * %%
 * Copyright (C) 2013 Adobe
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.adobe.acs.logging.logentries.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

import ch.qos.logback.core.Appender;

import com.logentries.logback.LogentriesAppender;

@Component(metatype = true, policy = ConfigurationPolicy.REQUIRE, label = "Logentries.com Appender",
        description = "Logback appender to send messages to Logentries.com")
public class LogentriesLogger {

    @Property(label = "Token", description = "Logger token issues by Logentries.com")
    private static final String PROP_TOKEN = "token";

    private ServiceRegistration appenderRegistration;
    private LogentriesAppender appender;

    @Activate
    protected void activate(ComponentContext ctx) {
        String token = PropertiesUtil.toString(ctx.getProperties().get(PROP_TOKEN), null);
        if (token == null) {
            throw new IllegalArgumentException("Must provide a token");
        }

        BundleContext bundleContext = ctx.getBundleContext();

        appender = new LogentriesAppender();

        appender.setToken(token);
        appender.setFacility("USER");
        appender.setSsl(false);

        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put("loggers", new String[] { "ROOT" });
        appenderRegistration = bundleContext.registerService(Appender.class.getName(), appender, props);
    }

    @Deactivate
    protected void deactivate() {
        if (appender != null) {
            appender.stop();
            appender = null;
        }

        if (appenderRegistration != null) {
            appenderRegistration.unregister();
            appenderRegistration = null;
        }
    }

}
