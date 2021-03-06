/******************************************************************************* 
 * Copyright (c) 2013 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.tools.foundation.ui.internal;

import org.eclipse.core.runtime.Platform;
import org.jboss.tools.foundation.core.plugin.log.IPluginLog;
import org.jboss.tools.foundation.core.plugin.log.StatusFactory;
import org.jboss.tools.foundation.ui.credentials.internal.FaviconCache;
import org.jboss.tools.foundation.ui.plugin.BaseUIPlugin;
import org.jboss.tools.foundation.ui.plugin.BaseUISharedImages;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * This class is here primarily as an example implementation
 * of a proper subclass of BaseUIPlugin. Subclasses of 
 * BaseUIPlugin may need some or all of the methods listed
 * here for access to the most common resources that 
 * Activators typically expose internally to their own plugin.
 * 
 *  This class is internal and is in a private (not exported) package.
 */
public class FoundationUIPlugin extends BaseUIPlugin {
	public static final String PLUGIN_ID = "org.jboss.tools.foundation.ui"; //$NON-NLS-1$
	private static FoundationUIPlugin instance;
	private static BundleContext myContext;
	
	public FoundationUIPlugin() {
		super();
		instance = this;
	}

	public static FoundationUIPlugin getDefault() {
	    return instance;
	}

	public static BundleContext getBundleContext() {
	    return myContext;
	}

    public void start(BundleContext context) throws Exception {
        super.start(context);
        myContext = context;
        super.registerDebugOptionsListener(PLUGIN_ID, new Trace(this), context);
	}
    
	public void stop(BundleContext context) throws Exception {
		FaviconCache.cleanup();
		super.stop(context);
	}
	
	/**
	 * Gets message from plugin.properties
	 * @param key
	 * @return
	 */
	public static String getMessage(String key)	{
		return Platform.getResourceString(instance.getBundle(), key);
	}

	/**
	 * Get the IPluginLog for this plugin. This method 
	 * helps to make logging easier, for example:
	 * 
	 *     FoundationCorePlugin.pluginLog().logError(etc)
	 *  
	 * @return IPluginLog object
	 */
	public static IPluginLog pluginLog() {
		return getDefault().pluginLogInternal();
	}

	/**
	 * Get a status factory for this plugin
	 * @return status factory
	 */
	public static StatusFactory statusFactory() {
		return getDefault().statusFactoryInternal();
	}
	
	/**
	 * Create your shared images instance. Clients are expected to override this
	 */
	protected BaseUISharedImages createSharedImages() {
		return new FoundationSharedImages(getBundle());
	}

	public static final String IMAGE_GREEN_CHECK_16 = "icons/greencheck_16.png";
	private static class FoundationSharedImages extends BaseUISharedImages {
		public FoundationSharedImages(Bundle pluginBundle) {
			super(pluginBundle);
			addImage(IMAGE_GREEN_CHECK_16, IMAGE_GREEN_CHECK_16);
		}
	}}
