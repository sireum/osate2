package org.osate.ge.internal.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.osate.ge.graphics.Point;
import org.osate.ge.internal.diagram.runtime.AgeDiagram;
import org.osate.ge.internal.diagram.runtime.DiagramElement;
import org.osate.ge.internal.ui.util.UiUtil;

public class ArrangeInCircleHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final List<DiagramElement> selectedDiagramElements = AgeHandlerUtil.getSelectedDiagramElements();
		final AgeDiagram diagram = UiUtil.getDiagram(selectedDiagramElements);
		if (diagram == null) {
			throw new RuntimeException("Unable to get diagram");
		}

		diagram.modify("Arrange in Circle", m -> {
			// Calculate and sets the position of the selected shapes into a radial layout.
			final List<Double> angles = new ArrayList<>();
			final double padding = 5;
			double radius = 0;
			double sizeOfShapes = 0;
			double circumference = -1;

			// Find appropriate radius for shapes
			while((sizeOfShapes > circumference) || (Double.isNaN(sizeOfShapes))) {
				sizeOfShapes = 0;
				angles.clear();
				radius++;
				circumference = 2*Math.PI*radius;

				for(final DiagramElement e1 : selectedDiagramElements) {
					// Get largest dimension of shape
					final double shapeDimension = ((e1.getWidth() > e1.getHeight() ? e1.getWidth() : e1.getHeight())
							+ padding) / 2;

					// Calculate diagonal of shape
					final double shapeSize = Math.sqrt((Math.pow(shapeDimension, 2) + Math.pow(shapeDimension, 2)));

					// Calculate angle for shape diagonal using Law of Cosines and double it
					final double angle = Math.acos((Math.pow(radius, 2) + Math.pow(radius, 2) - Math.pow(shapeSize, 2))
							/ (2 * radius * radius)) * 2;

					angles.add(angle);
					sizeOfShapes += (circumference * (angle/(2*Math.PI)));
				}
			}

			final Point center = getCenter(selectedDiagramElements, radius);
			double placementAngle = 0;
			int i = 0;
			for(final DiagramElement e2 : selectedDiagramElements) {
				double spacingAngle = angles.get(i++);

				// Center of placement
				placementAngle += spacingAngle/2;

				// Calculate where to place shape
				final long x = Math.round(center.x + radius * Math.cos(placementAngle) - e2.getWidth()/2);
				final long y = Math.round(center.y + radius * Math.sin(placementAngle) - e2.getHeight()/2);

				// End of placement
				placementAngle += spacingAngle/2;

				m.setPosition(e2, new Point((int)x, (int)y));
			}
		});

		return null;
	}

	private static Point getCenter(final List<DiagramElement> diagramElements, final double radius) {
		final int boarderPadding = 10;
		double xMin = Double.MAX_VALUE, xMax = Double.MIN_VALUE, yMin = Double.MAX_VALUE, yMax = Double.MIN_VALUE;
		double maxWidth = Double.MIN_VALUE, maxHeight = Double.MIN_VALUE;

		// Get min and max values for x and y axis.
		// Get max dimension of shapes
		for (final DiagramElement diagramElement : diagramElements) {
			final double width = diagramElement.getWidth() / 2;
			final double height = diagramElement.getHeight() / 2;

			final double x = diagramElement.getX() + width;
			final double y = diagramElement.getY() + height;

			if (x < xMin) {
				xMin = x;
			}

			if (x > xMax) {
				xMax = x;
			}

			if (y < yMin) {
				yMin = y;
			}

			if (y > yMax) {
				yMax = y;
			}

			if (maxWidth < width) {
				maxWidth = width;
			}

			if (maxHeight < height) {
				maxHeight = height;
			}
		}

		// Calculate center point
		double xCenter = (xMin + xMax) / 2;
		double yCenter = (yMax + yMin) / 2;

		// Move center point to appropriate location if necessary
		if (xCenter - radius - maxWidth < 0) {
			xCenter += Math.abs(xCenter - radius - maxWidth) + boarderPadding;
		}

		if (yCenter - radius - maxHeight < 0) {
			yCenter += Math.abs(yCenter - radius - maxHeight) + boarderPadding;
		}

		return new Point(xCenter, yCenter);
	}
}
