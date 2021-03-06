package com.dubture.twig.ui;

import java.io.IOException;

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.dubture.twig.core.log.Logger;
import com.dubture.twig.ui.editor.configuration.TwigContributionContextTypeRegistry;
import com.dubture.twig.ui.editor.templates.TwigTemplateContextType;
import com.dubture.twig.ui.preferences.PreferenceConstants;
import com.dubture.twig.ui.viewsupport.ImagesOnFileSystemRegistry;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class TwigUICorePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dubture.twig.ui"; //$NON-NLS-1$

	// The shared instance
	private static TwigUICorePlugin plugin;
	
	
	protected ContextTypeRegistry contentTypeRegistry = null;
	protected TemplateStore templateStore = null;

	private ImageDescriptorRegistry fImageDescriptorRegistry;
	
	private ImagesOnFileSystemRegistry fImagesOnFSRegistry;	
	
	
	/**
	 * The constructor
	 */
	public TwigUICorePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TwigUICorePlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptorRegistry getImageDescriptorRegistry() {
		return getDefault().internalGetImageDescriptorRegistry();
	}
	
	private synchronized ImageDescriptorRegistry internalGetImageDescriptorRegistry() {
		if (fImageDescriptorRegistry == null)
			fImageDescriptorRegistry = new ImageDescriptorRegistry();
		return fImageDescriptorRegistry;
	}

	public ContextTypeRegistry getTemplateContextRegistry() {
		
		if (contentTypeRegistry == null) {
			ContributionContextTypeRegistry registry = new TwigContributionContextTypeRegistry();
			
			registry.addContextType(TwigTemplateContextType.TWIG_CONTEXT_TYPE_ID);
			
			contentTypeRegistry = registry;
		}

		return contentTypeRegistry;
		
	}

	public TemplateStore getTemplateStore() {
		
		if (templateStore == null) {
			
			templateStore = new PHPTemplateStore(getTemplateContextRegistry(), getPreferenceStore(), PreferenceConstants.TEMPLATES_KEY);

			try {
				templateStore.load();
				
			} catch (IOException e) {

				Logger.logException(e);
			}
		}
		return templateStore;

		
	}
	
	public ImagesOnFileSystemRegistry getImagesOnFSRegistry() {
		if (fImagesOnFSRegistry == null) {
			fImagesOnFSRegistry = new ImagesOnFileSystemRegistry();
		}
		return fImagesOnFSRegistry;
	}
	
}