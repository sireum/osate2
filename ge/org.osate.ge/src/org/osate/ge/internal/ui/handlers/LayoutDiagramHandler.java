package org.osate.ge.internal.ui.handlers;

import java.util.Objects;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.elk.core.service.DiagramLayoutEngine;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class LayoutDiagramHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editorPart = Objects.requireNonNull(HandlerUtil.getActiveEditor(event),
				"unable to retrieve active editor");
		// TODO: Support progress bar for layout. There is a parameter ins the layout engine. Test.
		final DiagramLayoutEngine.Parameters params = new DiagramLayoutEngine.Parameters();
		DiagramLayoutEngine.invokeLayout(editorPart, null, params);

		return null;
	}

}
