package org.osate.ge.ui.editor;

import org.eclipse.ui.actions.RetargetAction;

public class DecreaseNestingDepthRetargetAction extends RetargetAction implements ComponentImplementationItem {
	public DecreaseNestingDepthRetargetAction() {
		super(DecreaseNestingDepthAction.ID, "Decrease Nesting Depth");
		setHoverImageDescriptor(DecreaseNestingDepthAction.IMAGE_DESCRIPTOR);
	}
}