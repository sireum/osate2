/*******************************************************************************
 * Copyright (C) 2013 University of Alabama in Huntsville (UAH)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * The US Government has unlimited rights in this work in accordance with W31P4Q-10-D-0092 DO 0073.
 *******************************************************************************/
package org.osate.ge.diagrams.common.patterns;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.osate.aadl2.Aadl2Factory;
import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.AbstractFeature;
import org.osate.aadl2.AccessSpecification;
import org.osate.aadl2.ArrayableElement;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Context;
import org.osate.aadl2.DeviceImplementation;
import org.osate.aadl2.DirectedFeature;
import org.osate.aadl2.DirectionType;
import org.osate.aadl2.Element;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.Feature;
import org.osate.aadl2.FeatureGroup;
import org.osate.aadl2.FeatureGroupType;
import org.osate.aadl2.FeaturePrototypeActual;
import org.osate.aadl2.FeaturePrototypeBinding;
import org.osate.aadl2.FlowSpecification;
import org.osate.aadl2.InternalFeature;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.PortSpecification;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessorFeature;
import org.osate.aadl2.PrototypeBinding;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.ThreadGroupImplementation;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.VirtualProcessorImplementation;
import org.osate.aadl2.modelsupport.util.ResolvePrototypeUtil;
import org.osate.ge.diagrams.common.AadlElementWrapper;
import org.osate.ge.diagrams.common.AgeImageProvider;
import org.osate.ge.diagrams.common.Categorized;
import org.osate.ge.services.AadlArrayService;
import org.osate.ge.services.AadlFeatureService;
import org.osate.ge.services.AadlModificationService;
import org.osate.ge.services.AnchorService;
import org.osate.ge.services.BusinessObjectResolutionService;
import org.osate.ge.services.ConnectionService;
import org.osate.ge.services.DiagramModificationService;
import org.osate.ge.services.GraphicsAlgorithmCreationService;
import org.osate.ge.services.GraphicsAlgorithmManipulationService;
import org.osate.ge.services.HighlightingService;
import org.osate.ge.services.LayoutService;
import org.osate.ge.services.NamingService;
import org.osate.ge.services.PropertyService;
import org.osate.ge.services.PrototypeService;
import org.osate.ge.services.RefactoringService;
import org.osate.ge.services.ShapeCreationService;
import org.osate.ge.services.ShapeService;
import org.osate.ge.services.UserInputService;
import org.osate.ge.services.GhostingService;
import org.osate.ge.services.AadlModificationService.AbstractModifier;
import org.osate.ge.util.StringUtil;

/**
 * Pattern for controlling Feature shapes
 * Note: With the exception of child feature shapes, child shapes are recreated during updates so they should not be referenced.
 * @author philip.alldredge
 */
public class FeaturePattern extends AgeLeafShapePattern implements Categorized {
	private static final String featureShapeName = "feature";
	private static final String labelShapeName = "label";	
	private static final String annotationShapeName = "annotation";
	public static final String innerConnectorAnchorName = "innerConnector";
	public static final String outerConnectorAnchorName = "outerConnector";
	public static final String flowSpecificationAnchorName = "flowSpecification";
	private static LinkedHashMap<EClass, String> featureTypeToMethodNameMap = new LinkedHashMap<EClass, String>();
	private static final int featureGroupSymbolWidth = 30;
	private static final int labelPadding = 5;
	private static final int annotationPadding = 5;
	private final AnchorService anchorUtil;
	private final GhostingService ghostingService;
	private final PropertyService propertyUtil;
	private final GraphicsAlgorithmManipulationService graphicsAlgorithmUtil;
	private final ShapeService shapeService;
	private final GraphicsAlgorithmCreationService graphicsAlgorithmCreator;
	private final AadlFeatureService featureService;
	private final PrototypeService prototypeService;
	private final UserInputService userInputService;
	private final LayoutService layoutService;
	private final AadlModificationService modificationService;
	private final NamingService namingService;
	private final DiagramModificationService diagramModService;
	private final BusinessObjectResolutionService bor;
	private final ShapeCreationService shapeCreationService;
	private final HighlightingService highlightingService;
	private final RefactoringService refactoringService;
	private final AadlArrayService arrayService;
	private final ConnectionService connectionService;
	private final EClass featureType;
	
	/**
	 * Populate the map that contains the feature type to create method name mapping
	 */
	static {
		final Aadl2Package p = Aadl2Factory.eINSTANCE.getAadl2Package();
		
		// Regular Features
		featureTypeToMethodNameMap.put(p.getAbstractFeature(), "createOwnedAbstractFeature");
		featureTypeToMethodNameMap.put(p.getBusAccess(), "createOwnedBusAccess");
		featureTypeToMethodNameMap.put(p.getDataAccess(), "createOwnedDataAccess");
		featureTypeToMethodNameMap.put(p.getDataPort(), "createOwnedDataPort");
		featureTypeToMethodNameMap.put(p.getEventDataPort(), "createOwnedEventDataPort");
		featureTypeToMethodNameMap.put(p.getEventPort(), "createOwnedEventPort");
		featureTypeToMethodNameMap.put(p.getFeatureGroup(), "createOwnedFeatureGroup");
		featureTypeToMethodNameMap.put(p.getParameter(), "createOwnedParameter");
		featureTypeToMethodNameMap.put(p.getSubprogramAccess(), "createOwnedSubprogramAccess");
		featureTypeToMethodNameMap.put(p.getSubprogramGroupAccess(), "createOwnedSubprogramGroupAccess");
		
		// Internal Features
		featureTypeToMethodNameMap.put(p.getEventSource(), "createOwnedEventSource");
		featureTypeToMethodNameMap.put(p.getEventDataSource(), "createOwnedEventDataSource");
		
		// Processor Features
		featureTypeToMethodNameMap.put(p.getSubprogramProxy(), "createOwnedSubprogramProxy");
		featureTypeToMethodNameMap.put(p.getPortProxy(), "createOwnedPortProxy");
	}
	
	public static Collection<EClass> getFeatureTypes() {
		return featureTypeToMethodNameMap.keySet();
	}
	
	@Inject
	public FeaturePattern(final AnchorService anchorUtil, final GhostingService ghostingService, 
			final PropertyService propertyUtil, final GraphicsAlgorithmManipulationService graphicsAlgorithmUtil,
			final ShapeService shapeService, final GraphicsAlgorithmCreationService graphicsAlgorithmCreator, 
			final AadlFeatureService featureService, final PrototypeService prototypeService, final UserInputService userInputService, 
			final LayoutService layoutService, final AadlModificationService modificationService, final NamingService namingService,
			final DiagramModificationService diagramModService, final BusinessObjectResolutionService bor, final ShapeCreationService shapeCreationService,
			final HighlightingService highlightingService, final RefactoringService refactoringService, final AadlArrayService arrayService,
			final ConnectionService connectionService, final @Named("Feature Type") EClass featureType) {
		super(anchorUtil, ghostingService);
		this.anchorUtil = anchorUtil;
		this.ghostingService = ghostingService;
		this.propertyUtil = propertyUtil;
		this.graphicsAlgorithmUtil = graphicsAlgorithmUtil;
		this.shapeService = shapeService;
		this.graphicsAlgorithmCreator = graphicsAlgorithmCreator;
		this.featureService = featureService;
		this.prototypeService = prototypeService;
		this.userInputService = userInputService;
		this.layoutService = layoutService;
		this.modificationService = modificationService;
		this.namingService = namingService;
		this.diagramModService = diagramModService;
		this.bor = bor;
		this.shapeCreationService = shapeCreationService;
		this.highlightingService = highlightingService;
		this.refactoringService = refactoringService;
		this.arrayService = arrayService;
		this.connectionService = connectionService;
		this.featureType = featureType;
	}

	@Override
	public boolean isMainBusinessObjectApplicable(final Object mainBusinessObject) {
		return featureType.isInstance(AadlElementWrapper.unwrap(mainBusinessObject));
	}

	@Override
	public boolean canAdd(final IAddContext context) {
		if(isMainBusinessObjectApplicable(context.getNewObject())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean canResizeShape(final IResizeShapeContext ctx) {
		return false;
	}
	
	// Move
	@Override
	public boolean canMoveShape(final IMoveShapeContext ctx) {
		if(ctx.getPictogramElement() instanceof Shape){
			final Shape shape = (Shape)ctx.getPictogramElement();
			final ContainerShape container = shape.getContainer();
			final Object containerBo = AadlElementWrapper.unwrap(this.getBusinessObjectForPictogramElement(container));
			if(containerBo instanceof Classifier || containerBo instanceof Subcomponent || containerBo instanceof SubprogramCall) {
				return super.canMoveShape(ctx);
			}
		}

		return false;
	}

	@Override
	protected void preMoveShape(final IMoveShapeContext ctx) {
		super.preMoveShape(ctx);
		
		final Shape shape = ctx.getShape();
		final int containerWidth = shape.getContainer().getGraphicsAlgorithm().getWidth();
		final int containerHeight = shape.getContainer().getGraphicsAlgorithm().getHeight();
		final GraphicsAlgorithm shapeGa = shape.getGraphicsAlgorithm();
		final boolean isLeft = calculateIsLeft(shape.getContainer(), ctx.getX(), shapeGa.getWidth());
		final MoveShapeContext mutableContext = (MoveShapeContext)ctx;
		mutableContext.setX(isLeft ? 0 : containerWidth-shapeGa.getWidth());
		 
		// Snap to the top/bottom instead of resizing container
		if(ctx.getY() < 0) {
			mutableContext.setY(0);
		} else {
			final int bottomY = ctx.getY() + shapeGa.getHeight();
			if(bottomY > containerHeight) {
				mutableContext.setY(containerHeight - shapeGa.getHeight());
			}
		}
	}

	@Override 
	protected void postMoveShape(final IMoveShapeContext ctx) {
		super.postMoveShape(ctx);
		final ContainerShape shape = (ContainerShape)ctx.getShape();		

		// Update whether or not the shape is on the left or not
		final GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
		final boolean isLeft = calculateIsLeft(shape.getContainer(), ga.getX(), ga.getWidth());
		propertyUtil.setIsLeft(shape, isLeft);
		
		layoutAll(shape);
		updateAnchors(shape);
		
		if(layoutService.checkContainerSize((ContainerShape)ctx.getPictogramElement())) {
			getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
		}
		
		// Update connection anchors
		connectionService.updateConnectionAnchors(shape);
	}

	// Layout
	@Override
	public boolean canLayout(final ILayoutContext context) {
		return isPatternControlled(context.getPictogramElement());
	}
	
	@Override
	public boolean layout(final ILayoutContext context) {
		final ContainerShape shape = (ContainerShape)context.getPictogramElement();
		layout(shape);
		
		return false;
	}

	private void layout(final ContainerShape shape) {
		final GraphicsAlgorithm shapeGa = shape.getGraphicsAlgorithm();
		final ContainerShape featureShape = getFeatureShape(shape);
		final GraphicsAlgorithm featureGa = featureShape.getGraphicsAlgorithm();
		final NamedElement feature = (NamedElement)AadlElementWrapper.unwrap(getBusinessObjectForPictogramElement(shape));
		
		boolean wasLeftLayout = propertyUtil.getIsLeftLayout(shape);
		final boolean isLeftLayout = isLeft(shape);
		propertyUtil.setIsLeftLayout(shape, isLeftLayout);
		
		// Feature groups
		if(feature instanceof FeatureGroup) {
			if(isLeftLayout != wasLeftLayout) {
				graphicsAlgorithmUtil.mirror(featureGa);
			}
			
			if(isLeftLayout) {
				featureGa.setX(0);
				// Set Position of All Children
				for(final Shape child : featureShape.getChildren()) {
					child.getGraphicsAlgorithm().setX(featureGroupSymbolWidth);
				}
			} else {
				// Set Position of the Graphics Algorithm
				featureGa.setX(featureGa.getWidth()-featureGroupSymbolWidth);
				
				// Set Position of All Children
				for(final Shape child : featureShape.getChildren()) {
					child.getGraphicsAlgorithm().setX(featureGa.getWidth()-child.getGraphicsAlgorithm().getWidth()-featureGroupSymbolWidth);
				}
			}
		} else {
			// Features
	        if(isLeftLayout != wasLeftLayout) {
	    		graphicsAlgorithmUtil.mirror(featureGa);
	        }
		}
		
		// All
		featureGa.setX(isLeftLayout ? 0 : shapeGa.getWidth()-featureGa.getWidth());
		
		// Position the label shape
		final Shape labelShape = getLabelShape(shape);
		if(labelShape != null) {
			final GraphicsAlgorithm labelGa = labelShape.getGraphicsAlgorithm();
			labelGa.setX(isLeftLayout ? labelPadding : shapeGa.getWidth()-labelGa.getWidth()-labelPadding);
			
			// Adjust the alignment.
			for(final GraphicsAlgorithm labelChildGa : labelGa.getGraphicsAlgorithmChildren()) {
				final Text labelTxt = (Text)labelChildGa;
		        if(isLeftLayout) {
		        	labelTxt.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		        } else {
		        	labelTxt.setHorizontalAlignment(Orientation.ALIGNMENT_RIGHT);
		        }
			}    
		}	
		
		// Position the annotation shape
		final Shape annotationShape = getAnnotationShape(shape);		
		if(annotationShape != null) {
			final GraphicsAlgorithm annotationGa = annotationShape.getGraphicsAlgorithm();
			annotationGa.setX(isLeftLayout ? annotationPadding : shapeGa.getWidth()-annotationGa.getWidth()-annotationPadding);
		}
		
		// Handle positioning of the shape in cases where the shape container has a fully initialized container
		final GraphicsAlgorithm containerGa = shape.getContainer().getGraphicsAlgorithm();
		if(containerGa != null) {
			final int containerWidth = containerGa.getWidth();
			shapeGa.setX(isLeftLayout ? 0 : containerWidth-shape.getGraphicsAlgorithm().getWidth());
		}
	}
	
	private void layoutAll(final ContainerShape shape) {
		// Layout the children first
		for(final Shape child : shapeService.getNonGhostChildren(getFeatureShape(shape))) {
			layoutAll((ContainerShape)child);
		}
		
		layout(shape);
	}
	

	@Override
	protected void createGaAndInnerShapes(final ContainerShape shape, final Object bo, final int x, final int y) {
		createGaAndInnerShapes(shape, bo, x, y, 0);
	}

	/**
	 * Version of createGaAndInnerShapes that limits recursion
	 * @param shape
	 * @param bo
	 * @param x
	 * @param y
	 * @param callDepth
	 */
	private void createGaAndInnerShapes(final ContainerShape shape, final Object bo, final int x, final int y, final int callDepth) {
		ghostingService.setIsGhost(shape, false);
		
		final NamedElement feature = (NamedElement)bo;
		final IGaService gaService = Graphiti.getGaService();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();		
	
		// Remove all children except for the feature shape
		final Iterator<Shape> childIt = shape.getChildren().iterator();
		ContainerShape featureShape = getFeatureShape(shape);
		while(childIt.hasNext()) {
			if(featureShape != childIt.next()) {
				childIt.remove();
			}

		}

		// Set the graphics algorithm for the container to an invisible rectangle to set the bounds	of the child shapes
        final GraphicsAlgorithm ga = gaService.createInvisibleRectangle(shape);
        gaService.setLocation(ga, x, y);
        propertyUtil.setIsLeftLayout(shape, true);        

		// Create the feature shape if there wasn't one
        if(featureShape == null) {
        	featureShape = peCreateService.createContainerShape(shape, true);
        	gaService.createInvisibleRectangle(featureShape);
        	propertyUtil.setName(featureShape, featureShapeName);
        } else {
        	gaService.createInvisibleRectangle(featureShape);
        	ghostingService.ghostInvalidChildShapes(featureShape);
        }
        
        // Set the feature shape as an inner shape
        propertyUtil.setIsInnerShape(featureShape, true);

		if(callDepth > 2) {
			return;
		}
		
		// Determine the label text
        final String labelTxt = getLabelText(feature);        
        
		// Create label
        final Shape labelShape = peCreateService.createShape(shape, false);
        propertyUtil.setIsManuallyPositioned(labelShape, true);
        this.link(labelShape, new AadlElementWrapper(feature));
        propertyUtil.setName(labelShape, labelShapeName);        
		
		final GraphicsAlgorithm labelBackground = graphicsAlgorithmCreator.createTextBackground(labelShape);		
        final Text label = graphicsAlgorithmCreator.createLabelGraphicsAlgorithm(labelBackground, labelTxt);

        // Set the sizes        
        final IDimension labelSize = GraphitiUi.getUiLayoutService().calculateTextSize(labelTxt, label.getStyle().getFont());
        labelSize.setWidth((int)(labelSize.getWidth()*1.06));
		gaService.setLocationAndSize(label, 0, 0, labelSize.getWidth(), labelSize.getHeight());		
		gaService.setLocationAndSize(labelBackground, 0, 0, labelSize.getWidth(), labelSize.getHeight());
		
		// Special case for feature groups
		if(feature instanceof FeatureGroup) {
			final FeatureGroup fg = (FeatureGroup)feature;
			final FeatureGroupType fgt = featureService.getFeatureGroupType(shape, fg);
			int childY = 0;
			int maxChildWidth = 0;
			if(fgt == null) {
				featureShape.getChildren().clear();
			} else {				
		        // Create a set of child shapes for deletion. We will remove shapes from this list as they are updated. If a shape isn't updated, it should be turned into a ghost even though
		        // it has a valid business object associated with it.
		        final Set<Shape> featureShapeChildrenToGhost = new HashSet<Shape>();
		        featureShapeChildrenToGhost.addAll(shapeService.getNonGhostChildren(featureShape));
		        
		        // Create shapes for each of the children if the feature group is not part of a subcomponent
		        final Subcomponent sc = shapeService.getClosestBusinessObjectOfType(shape, Subcomponent.class);

		        // Create/Update shapes for the child features
				for(final Feature childFeature : featureService.getAllFeatures(fgt)) {
		        	// Determine whether the child feature should be shown
					// Show the child feature if the container is not a subcomponent or the feature is used as part of a flow specification
					boolean showChild;
					if(sc == null) {
						showChild = true;
					} else {
						showChild = false;
						if(sc.getComponentType() != null) {
			        		for(final FlowSpecification fs : sc.getComponentType().getAllFlowSpecifications()) {
			        			if((fs.getAllInEnd() != null && fs.getAllInEnd().getContext() == feature && fs.getAllInEnd().getFeature() == childFeature) ||
			        					(fs.getAllOutEnd() != null && fs.getAllOutEnd().getContext() == feature && fs.getAllOutEnd().getFeature() == childFeature)) {
			        				showChild = true;
			        				break;
			        			}		        			
			        		}
			        	}
					}

			        if(showChild) {
						ContainerShape childFeatureContainer = (ContainerShape)shapeService.getChildShapeByElementQualifiedName(featureShape, childFeature);
						
						// Get existing shape instead of always creating
						if(childFeatureContainer == null) {
				        	// Create the container shape
				        	childFeatureContainer = peCreateService.createContainerShape(featureShape, true);
				        	link(childFeatureContainer, new AadlElementWrapper(childFeature));
				        } else {
							// Remove the shape from the list of shapes to remove
							featureShapeChildrenToGhost.remove(childFeatureContainer);
				        }
						
				        createGaAndInnerShapes(childFeatureContainer, childFeature, 50, childY, callDepth + 1);
				        final GraphicsAlgorithm childFeatureGa = childFeatureContainer.getGraphicsAlgorithm();
				        childY += childFeatureGa.getHeight() + 5;
				        maxChildWidth = Math.max(maxChildWidth, childFeatureGa.getWidth());
			        }
				}
				
				// Remove children of the feature shape that were not updated
				for(final Shape featureShapeChild : featureShapeChildrenToGhost) {
					ghostingService.setIsGhost(featureShapeChild, true);
				}
			}
			
			// Create the feature group graphics algorithm
			final GraphicsAlgorithm fgGa = graphicsAlgorithmCreator.createFeatureGroupGraphicsAlgorithm(featureShape, featureGroupSymbolWidth, childY + 25);
			graphicsAlgorithmUtil.shrink(fgGa);
			final int fgWidth = maxChildWidth+featureGroupSymbolWidth;
	     	
			// CLEAN-UP: Consider changing how symbol is created to assume left like the other symbols...
			graphicsAlgorithmUtil.mirror(fgGa);					

			featureShape.getGraphicsAlgorithm().setWidth(fgWidth);
		} else {
			featureShape.getChildren().clear();
			
			// Flag to indicate whether a graphics algorithm has been created by the prototype handling code
			boolean featureGaCreated = false;
			
			// Check to see if it is a prototype feature
			if(feature instanceof AbstractFeature) {
				final AbstractFeature af = (AbstractFeature)feature;
				if(af.getFeaturePrototype() != null) {
					// Lookup the binding
					// Get the proper context (FeatureGroupType or ComponentClassifier) - May be indirectly for example from Subcomponent...
					final Element bindingContext = prototypeService.getPrototypeBindingContext(shape);
					if(bindingContext != null) {
						final PrototypeBinding binding = ResolvePrototypeUtil.resolveFeaturePrototype(af.getFeaturePrototype(), bindingContext);
						if(binding instanceof FeaturePrototypeBinding) {
							FeaturePrototypeActual actual = ((FeaturePrototypeBinding) binding).getActual();
							if(actual instanceof PortSpecification) {
								graphicsAlgorithmCreator.createPortGraphicsAlgorithm(featureShape, ((PortSpecification) actual).getCategory(), ((PortSpecification) actual).getDirection());
								featureGaCreated = true;
							} else if(actual instanceof AccessSpecification) {
								graphicsAlgorithmCreator.createAccessGraphicsAlgorithm(featureShape, ((AccessSpecification) actual).getCategory(), ((AccessSpecification) actual).getKind());
								featureGaCreated = true;
							}
						}
					}
				}
			}
			
			// Create the symbol based on the feature itself if one has not been created already
			if(!featureGaCreated) {
				// Create symbol
				graphicsAlgorithmCreator.createFeatureGraphicsAlgorithm(featureShape, feature);		
			}
		}

		// Position the feature shape		
		gaService.setLocation(featureShape.getGraphicsAlgorithm(), 0, labelSize.getHeight());  
		
		// Create annotation label
		final String annotationTxt = getAnnotationText(feature);
        final Shape annotationShape = peCreateService.createShape(shape, false);
        propertyUtil.setIsManuallyPositioned(annotationShape, true);
        this.link(annotationShape, new AadlElementWrapper(feature));
        propertyUtil.setName(annotationShape, annotationShapeName);
        final GraphicsAlgorithm annotationBackground = graphicsAlgorithmCreator.createTextBackground(annotationShape);
        final Text annotation = graphicsAlgorithmCreator.createAnnotationGraphicsAlgorithm(annotationBackground, annotationTxt);
        
        if(annotationTxt.length() != 0) {
        	final IDimension annotationSize = GraphitiUi.getUiLayoutService().calculateTextSize(annotationTxt, annotation.getStyle().getFont());
        	gaService.setLocationAndSize(annotation, 0, 0, annotationSize.getWidth(), annotationSize.getHeight());
        	gaService.setLocationAndSize(annotationBackground, 0, featureShape.getGraphicsAlgorithm().getY() + featureShape.getGraphicsAlgorithm().getHeight(), annotationSize.getWidth(), annotationSize.getHeight());
        }				
		
		// Determine whether the feature has a "context" and then highlight it
		final Element possibleContext = shapeService.getClosestBusinessObjectOfType(shape, Context.class, Classifier.class);
     	highlightingService.highlight(feature, possibleContext instanceof Context ? (Context)possibleContext : null, featureShape.getGraphicsAlgorithm());		
     	
        // Set size as appropriate
     	final int maxWidth =  Math.max(getWidth(annotationBackground)+annotationPadding, Math.max(getWidth(label)+labelPadding, getWidth(featureShape.getGraphicsAlgorithm())));
     	final int maxHeight = Math.max(getHeight(annotationBackground), Math.max(getHeight(label), getHeight(featureShape.getGraphicsAlgorithm())));
        gaService.setSize(ga, maxWidth,	maxHeight);
     		
        layoutAll(shape); // CLEAN-UP: Ideally would only layout each shape one.. This will cause it to happen multiple times        
	}
	
	@Override
	protected void updateAnchors(final ContainerShape shape) {
		super.updateAnchors(shape);

		final NamedElement feature = (NamedElement)AadlElementWrapper.unwrap(getBusinessObjectForPictogramElement(shape));		
		final GraphicsAlgorithm featureGa = getFeatureShape(shape).getGraphicsAlgorithm();
		final boolean isLeft = isLeft(shape);
		final ContainerShape featureShape = getFeatureShape(shape);
		
		final int featureWidth = feature instanceof FeatureGroup ? featureGroupSymbolWidth : featureGa.getWidth();
		final int innerX = featureGa.getX() + (isLeft ? featureWidth : featureGa.getWidth() - featureWidth);
		final int outerX = featureGa.getX() + (isLeft ? 0 : featureGa.getWidth());
		final int connectorY = featureGa.getY() + (featureGa.getHeight() / 2);
		final int flowSpecConnectorY = connectorY;
		final int offset = isLeft ? 50 : -50;

		// Create anchors
		// Feature shape anchor
		anchorUtil.createOrUpdateChopboxAnchor(featureShape, chopboxAnchorName);
	
		// Special cases for event out features
		if(feature instanceof EventPort && ((EventPort)feature).getDirection() == DirectionType.OUT) {
			anchorUtil.createOrUpdateFixPointAnchor(shape, innerConnectorAnchorName, outerX, connectorY);
		} else {
			anchorUtil.createOrUpdateFixPointAnchor(shape, innerConnectorAnchorName, innerX, connectorY);
		}
		
		if(feature instanceof EventPort && ((EventPort)feature).getDirection() == DirectionType.IN) {
			anchorUtil.createOrUpdateFixPointAnchor(shape, outerConnectorAnchorName, innerX, connectorY);
		} else {
			anchorUtil.createOrUpdateFixPointAnchor(shape, outerConnectorAnchorName, outerX, connectorY);
		}		
		
		anchorUtil.createOrUpdateFixPointAnchor(shape, flowSpecificationAnchorName, innerX + offset, flowSpecConnectorY);
		
		// Update the anchors of the children
		for(final Shape child : shapeService.getNonGhostChildren(getFeatureShape(shape))) {
			updateAnchors((ContainerShape)child);
		}
	}
	
	private ContainerShape getFeatureShape(final ContainerShape container) {
		return (ContainerShape)shapeService.getChildShapeByName(container, featureShapeName);
	}
	
	private Shape getLabelShape(final ContainerShape container) {
		return shapeService.getChildShapeByName(container, labelShapeName);
	}	

	private Shape getAnnotationShape(final ContainerShape container) {
		return shapeService.getChildShapeByName(container, annotationShapeName);
	}	
	
	private int getWidth(final GraphicsAlgorithm ga) {
		return ga.getX() + ga.getWidth();
	}
	
	private int getHeight(final GraphicsAlgorithm ga) {
		return ga.getY() + ga.getHeight();
	}
	
	public final String getAnnotationText(final NamedElement feature) {
		if(feature instanceof InternalFeature) {
			return "<internal>";
		} else if(feature instanceof ProcessorFeature) {
			return "<processor>";
		}
		
		return "";
	}
	
	private final String getFeatureName(final NamedElement feature) {
		return feature.getName() == null ? "" : feature.getName(); 
	}
	
	private final String getLabelText(final NamedElement feature) {
		String retVal = getFeatureName(feature);
		
		if(feature instanceof ArrayableElement) {
			retVal += arrayService.getDimensionUserString((ArrayableElement) feature);
		}

		return retVal;
	}
		
	
	@Override
	public IReason updateNeeded(final IUpdateContext context) {
		return Reason.createFalseReason();
	}
	
	private boolean isLeft(ContainerShape shape) {
		while(true) {
			final ContainerShape container = shape.getContainer();
			if(container == null) {
				break;
			}
			
			final Object containerBo = AadlElementWrapper.unwrap(this.getBusinessObjectForPictogramElement(container));
			if(containerBo instanceof Classifier || containerBo instanceof Subcomponent || containerBo instanceof SubprogramCall) {
				break;
			}
			shape = container;
		}

		return propertyUtil.getIsLeft(shape);
	}
	
	private boolean calculateIsLeft(final ContainerShape container, final int positionX, final int width) {
		final GraphicsAlgorithm containerGa = container.getGraphicsAlgorithm();
		
		// Handle the case that isLeft is caused before the container is initialized
		if(containerGa == null) {
			return true;
		}
		
		final int centerX = positionX + width/2;
		final boolean result = centerX < containerGa.getWidth()/2;
		return result;
	}	
	
	@Override
	public boolean canDelete(final IDeleteContext context) {
		return canEdit(context.getPictogramElement());
	}

	@Override
	public void delete(final IDeleteContext context) {
		if(!userInputService.confirmDelete(context)) {
			return;
		}
		
		final NamedElement feature = (NamedElement)bor.getBusinessObjectForPictogramElement(context.getPictogramElement());
		modificationService.modify(feature, new AbstractModifier<NamedElement, Object>() {
			@Override
			public Object modify(final Resource resource, final NamedElement feature) {
				// Just remove the feature. 
				// In the future it would be helpful to offer options for refactoring the model so that it does not cause errors.
				EcoreUtil.remove(feature);
				
				return null;
			}
		});
		
		// Clear selection
		getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer().selectPictogramElements(new PictogramElement[0]);
	}
	
	@Override
	public boolean isPaletteApplicable() {
		final Object diagramBo = bor.getBusinessObjectForPictogramElement(getDiagram());
		return isClassifierDiagram() && canOwnFeatureType((Classifier)diagramBo, featureType);
	}

	@Override
	public boolean canCreate(final ICreateContext context) {
		final Object containerBo = bor.getBusinessObjectForPictogramElement(context.getTargetContainer());

		// The container must be a Feature Group Type or a ComponentType and it must have a method to create the feature type that is controlled by this pattern
		return !(context.getTargetContainer() instanceof Diagram) && 
				(containerBo instanceof FeatureGroupType || 
						containerBo instanceof ComponentType || 
						containerBo instanceof ComponentImplementation) && 
				canOwnFeatureType((Classifier)containerBo, featureType);
	}
	
	@Override
	public String getCreateImageId() { 
		return AgeImageProvider.getImage(featureType);
	}
	
	@Override
	public String getCreateName() {
		return StringUtil.camelCaseToUser(featureType.getName());
	}
	
	@Override
	public Object[] create(final ICreateContext context) {
		// Get the classifier
		final Classifier classifier = (Classifier)bor.getBusinessObjectForPictogramElement(context.getTargetContainer());
		final NamedElement newFeature;
		if(classifier == null) {
			newFeature = null;
		} else {		
			final String newFeatureName = namingService.buildUniqueIdentifier(classifier, "new_feature");
			
			// Make the modification
			newFeature = modificationService.modify(classifier, new AbstractModifier<Classifier, NamedElement>() {
				private DiagramModificationService.Modification diagramMod;
				
				@Override
				public NamedElement modify(final Resource resource, final Classifier classifier) {
					// Handle diagram updates
		 			diagramMod = diagramModService.startModification();
		 			diagramMod.markRelatedDiagramsAsDirty(classifier);
		 			
					final NamedElement newFeature = createFeature(classifier, featureType);
					newFeature.setName(newFeatureName);
					
					if(newFeature instanceof DirectedFeature) {
						if(!(newFeature instanceof AbstractFeature || newFeature instanceof FeatureGroup)) {
							final DirectedFeature newDirectedFeature = (DirectedFeature)newFeature;
							newDirectedFeature.setIn(true);
							newDirectedFeature.setOut(true);
						}
					}
					
					if(classifier instanceof ComponentType) {
						((ComponentType) classifier).setNoFeatures(false);
					}
					
					return newFeature;
				}
				
				@Override
				public void beforeCommit(final Resource resource, final Classifier classifier, final NamedElement newFeature) {
					diagramMod.commit();
					
					final ContainerShape newShape = (ContainerShape)shapeCreationService.createShape(context.getTargetContainer(), newFeature, context.getX(), context.getY());
					
					// Set the is left property
					final GraphicsAlgorithm newShapeGa = newShape.getGraphicsAlgorithm();
					final boolean isLeft = calculateIsLeft(newShape.getContainer(), context.getX(), newShapeGa.getWidth());
					propertyUtil.setIsLeft(newShape, isLeft);
				}
			});
		}
		
		// Return the new feature if it was created
		return newFeature == null ? EMPTY : new Object[] {newFeature};		
	}

	private static Method getFeatureCreateMethod(final Classifier featureOwner, final EClass featureType) {
		// Determine the method name for the type of feature
		final String methodName = featureTypeToMethodNameMap.get(featureType);
		if(methodName == null) {
			return null;
		}
		
		// Get the method
		try {
			final Method method = featureOwner.getClass().getMethod(methodName);
			return method;
		} catch(final Exception ex) {
			return null;
		}
	}
	
	public static NamedElement createFeature(final Classifier featureOwner, final EClass featureClass) {
		try {
			return (NamedElement)getFeatureCreateMethod(featureOwner, featureClass).invoke(featureOwner);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean canOwnFeatureType(final Classifier featureOwner, final EClass featureType) {
		return getFeatureCreateMethod(featureOwner, featureType) != null &&
				(!isProcessorFeatureType(featureType) || canOwnProcessorFeatures(featureOwner));
	}
	
	private static boolean canOwnProcessorFeatures(final Object bo) {
		return bo instanceof SystemImplementation || 
				bo instanceof ProcessImplementation || 
				bo instanceof ThreadGroupImplementation || 
				bo instanceof ThreadImplementation || 
				bo instanceof DeviceImplementation || 
				bo instanceof VirtualProcessorImplementation;
	}
	
	private static boolean isProcessorFeatureType(final EClass t) {
		final Aadl2Package p = Aadl2Factory.eINSTANCE.getAadl2Package();
		return p.getProcessorFeature().isSuperTypeOf(t);
	}
	
	@Override
	public boolean stretchFieldToFitText() {
		return true;
	}
	
	@Override
	public int getEditingType() {
        return TYPE_TEXT;
    }
	
	private boolean canEdit(final PictogramElement pe) {
		if(!(pe instanceof Shape)) {
			return false;
		}
		
		final Shape featureShape = (Shape)pe;
		final Object bo = bor.getBusinessObjectForPictogramElement(featureShape);
		if(!(bo instanceof Feature || bo instanceof InternalFeature || bo instanceof ProcessorFeature)) {
			return false;
		}
		
		final NamedElement feature = (NamedElement)bo;
		final Object containerBo = bor.getBusinessObjectForPictogramElement(featureShape.getContainer());
		return (containerBo instanceof FeatureGroupType || containerBo instanceof ComponentType || containerBo instanceof ComponentImplementation) && feature.getContainingClassifier() == containerBo;
	}
 
	@Override
    public boolean canDirectEdit(final IDirectEditingContext context) {
		if(context.getPictogramElement() instanceof Shape) {
	        final Object bo = bor.getBusinessObjectForPictogramElement(context.getPictogramElement());
	        final GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
	        
	        if (bo instanceof NamedElement && ga instanceof Text) {
	        	final Shape labelShape = (Shape)context.getPictogramElement();
	        	final Shape featureShape = labelShape.getContainer();
	        	return canEdit(featureShape) && (!(bo instanceof Feature) || ((Feature)bo).getRefined() == null);
	        }
		}

        return false;
    }
	
    public String getInitialValue(final IDirectEditingContext context) {
    	final NamedElement feature = (NamedElement)bor.getBusinessObjectForPictogramElement(context.getPictogramElement());
    	return getFeatureName(feature);
    }
    
    @Override
    public String checkValueValid(final String value, final IDirectEditingContext context) {
    	return namingService.checkNameValidity((NamedElement)bor.getBusinessObjectForPictogramElement(context.getPictogramElement()), value);
    }
     
 	public void setValue(final String value, final IDirectEditingContext context) {
    	final PictogramElement pe = context.getPictogramElement();
    	final NamedElement feature = (NamedElement)bor.getBusinessObjectForPictogramElement(pe);    	
   	
    	refactoringService.renameElement(feature, value);
    }

	@Override
	public Category getCategory() {
		return Category.FEATURES;
	}
}