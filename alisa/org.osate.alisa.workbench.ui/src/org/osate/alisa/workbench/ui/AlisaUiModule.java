/*
 * generated by Xtext
 */
package org.osate.alisa.workbench.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Use this class to register components to be used within the IDE.
 */
public class AlisaUiModule extends org.osate.alisa.workbench.ui.AbstractAlisaUiModule {
	public AlisaUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public Class<? extends org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper> bindIHyperlinkHelper() {
		return org.osate.alisa.common.ui.util.AlisaHyperLinkHelper.class;
	}
}