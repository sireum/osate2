/*
 * <copyright>
 * Copyright  2012 by Carnegie Mellon University, all rights reserved.
 *
 * Use of the Open Source AADL Tool Environment (OSATE) is subject to the terms of the license set forth
 * at http://www.eclipse.org/org/documents/epl-v10.html.
 *
 * NO WARRANTY
 *
 * ANY INFORMATION, MATERIALS, SERVICES, INTELLECTUAL PROPERTY OR OTHER PROPERTY OR RIGHTS GRANTED OR PROVIDED BY
 * CARNEGIE MELLON UNIVERSITY PURSUANT TO THIS LICENSE (HEREINAFTER THE "DELIVERABLES") ARE ON AN "AS-IS" BASIS.
 * CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED AS TO ANY MATTER INCLUDING,
 * BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABILITY, INFORMATIONAL CONTENT,
 * NONINFRINGEMENT, OR ERROR-FREE OPERATION. CARNEGIE MELLON UNIVERSITY SHALL NOT BE LIABLE FOR INDIRECT, SPECIAL OR
 * CONSEQUENTIAL DAMAGES, SUCH AS LOSS OF PROFITS OR INABILITY TO USE SAID INTELLECTUAL PROPERTY, UNDER THIS LICENSE,
 * REGARDLESS OF WHETHER SUCH PARTY WAS AWARE OF THE POSSIBILITY OF SUCH DAMAGES. LICENSEE AGREES THAT IT WILL NOT
 * MAKE ANY WARRANTY ON BEHALF OF CARNEGIE MELLON UNIVERSITY, EXPRESS OR IMPLIED, TO ANY PERSON CONCERNING THE
 * APPLICATION OF OR THE RESULTS TO BE OBTAINED WITH THE DELIVERABLES UNDER THIS LICENSE.
 *
 * Licensee hereby agrees to defend, indemnify, and hold harmless Carnegie Mellon University, its trustees, officers,
 * employees, and agents from all claims or demands made against them (and any related losses, expenses, or
 * attorney's fees) arising out of, or relating to Licensee's and/or its sub licensees' negligent use or willful
 * misuse of or negligent conduct or willful misconduct regarding the Software, facilities, or other rights or
 * assistance granted by Carnegie Mellon University under this License, including, but not limited to, any claims of
 * product liability, personal injury, death, damage to property, or violation of any laws or regulations.
 *
 * Carnegie Mellon Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 * </copyright>
 */
package org.osate.aadl2.errormodel.analysis;

import java.io.ObjectInputStream.GetField;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.Connection;
import org.osate.aadl2.Feature;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;
import org.osate.aadl2.instance.ConnectionReference;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.instance.FlowSpecificationInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.aadl2.modelsupport.WriteToFile;
import org.osate.aadl2.util.Aadl2InstanceUtil;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorBehaviorState;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorBehaviorStateOrTypeSet;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorEvent;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorFlow;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorPath;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorPropagation;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorPropagations;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorSink;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorSource;
import org.osate.xtext.aadl2.errormodel.errorModel.TypeMappingSet;
import org.osate.xtext.aadl2.errormodel.errorModel.TypeSet;
import org.osate.xtext.aadl2.errormodel.errorModel.TypeToken;
import org.osate.xtext.aadl2.errormodel.util.EM2TypeSetUtil;
import org.osate.xtext.aadl2.errormodel.util.EM2Util;

/**
 * @author phf
 */
public class TraverseErrorFlows {
	protected WriteToFile report ;
	protected AnalysisModel faultModel ;
	protected EList<EObject> visited ;
	protected int maxLevel = 5;

	public TraverseErrorFlows(String reportType, ComponentInstance root){
		report = new WriteToFile(reportType, root);
		faultModel = new AnalysisModel(root);
		visited = new UniqueEList<EObject>();
		
	}

	public TraverseErrorFlows(String reportType, EObject root, int maxLevel){
		report = new WriteToFile(reportType, root);
		visited = new UniqueEList<EObject>();
		this.maxLevel = maxLevel;
	}
	
	public void setMaxDepth(int maxLevel){
		this.maxLevel = maxLevel;
	}
	
	public void addText(String text){
		if (report != null)
			report.addOutput(text);
	}
	
	public int getMaxLevel(){
		return this.maxLevel;
	}
	
	public void addTextNewline(String text){
		if (report != null)
			report.addOutputNewline(text);
	}
	
	public void addNewline(){
		if (report != null)
			report.addOutputNewline("");
	}
	
	public void saveReport(){
		report.saveToFile();
	}

	
	public void reportHeading( ){
		report.addOutput("Component, Initial Failure Mode, 1st Level Effect");
		for (int i = 2; i <= this.maxLevel; i++) {
			report.addOutput(", Failure Mode, "+Integer.toString(i)+(i==2?"nd":"th")+" Level Effect");
		}
		report.addOutputNewline(", Severity");	
	}
	
	
	/**
	 * Put an entry into the report based on the prefix, entryText and any postfix processing based on level
	 * @param entryText String assumed to provide any comma before each entry
	 * @param curLevel last level reported
	 */
	public void reportEntry(String entryText,int curLevel){
		report.addOutput(entryText);
		for (int i = curLevel; i < maxLevel; i++) {
			report.addOutput(", , ");
		}
		report.addOutputNewline(", "+"Severe");	
	}

	/**
	 * traverse error flow if the component instance is an error source
	 * @param ci component instance
	 */
	public void startErrorFlows(ComponentInstance ci){
		ErrorPropagations eps = EM2Util.getContainingErrorPropagations(ci.getComponentClassifier());
		if (eps == null) return;
		EList<ErrorSource> eslist = EM2Util.getErrorSources(eps);
		String componentText = generateItemText(ci);
//		int dot = componentText.lastIndexOf(".");
//		String newstr = componentText.substring(dot);
		for (ErrorSource errorSource : eslist) {
			ErrorPropagation ep = errorSource.getOutgoing();
			TypeSet ts = errorSource.getTypeTokenConstraint();
			if (ts == null) ep.getTypeSet();
			 ErrorBehaviorStateOrTypeSet fmr = errorSource.getFailureModeReference();
			 if (fmr instanceof ErrorBehaviorState){
				 // XXX TODO how about the other case
			ErrorBehaviorState failureMode = (ErrorBehaviorState) fmr;
			EList<TypeToken> result = EM2TypeSetUtil.generateAllTypeTokens(ts);
			for (TypeToken typeToken : result) {
				String failuremodeText = generateFailureModeText(failureMode!=null?failureMode:typeToken);
				Feature f = ep.getFeature();
				// we also have observables, error propagations with kind, and not error propagations
				if (f != null){
					FeatureInstance fi = ci.findFeatureInstance(f);
					// Call on process to attach the typeToken to the outgoing feature
					setToken(fi,typeToken);
					EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getOutgoingConnection(ci,fi);
					for (ConnectionInstance connectionInstance : connilist) {
						traceErrorFlows(fi,connectionInstance,2,componentText+", "+failuremodeText+", "+generateEffectText(fi, ep));
					}
				}
			}
			 }
		}
	}
	
	/**
	 * get the text to be used for the item (Component or feature)
	 * that is the source of a failure mode
	 * @param ci component instance
	 * @return String
	 */
	public String generateItemText(InstanceObject io){
		if (io instanceof FeatureInstance){
			FeatureInstance fi = (FeatureInstance)io;
			ComponentInstance ci = fi.getContainingComponentInstance();
			return (/*ci.getCategory().name()+" "+*/ci.getQualifiedName()+"."+fi.getName());
		} else if (io instanceof ComponentInstance){
			ComponentInstance ci = (ComponentInstance)io;
			return (/*ci.getCategory().name()+" "+*/ci.getQualifiedName());
		} else if (io instanceof ConnectionInstance){
			ConnectionInstance ci = (ConnectionInstance)io;
			EList<ConnectionReference> list = ci.getConnectionReferences();
			ConnectionReference connref = list.isEmpty()?null:list.get(0);
			String res ;
			if (connref != null){
				Connection conn = connref.getConnection();
				res = conn.getName()+ " : "+EM2Util.getPrintName(getToken(ci.getDestination()));
			} else {
				res = generateItemText(ci.getSource())+"-"+ 						
						"->"+generateItemText(ci.getDestination());
			}
			return (generateItemText(ci.getSource())+"-"+
			EM2Util.getPrintName(getToken(ci.getDestination()))+
			"->"+generateItemText(ci.getDestination()));
		} else {
			return (io.getName());
		}
	}
	
	/**
	 * get the text for the failure mode
	 * @param io Error State or Type token
	 * @return String
	 */
	public String generateFailureModeText(EObject io){
		if (io instanceof ErrorBehaviorState){
			ErrorBehaviorState ev = (ErrorBehaviorState)io;
			return ev.getName();
		} else if (io instanceof TypeToken){
			return EM2Util.getPrintName((TypeToken) io);
		} else if (io instanceof TypeSet){
			return EM2Util.getTableName((TypeSet)io);
		} else {
			return "NoError";
		}
	}
	
	protected void setToken(InstanceObject io, TypeToken token){
		ErrorModelState st = (ErrorModelState) ErrorModelStateAdapterFactory.INSTANCE.adapt(io, ErrorModelState.class);
		if (st != null) st.setToken(token);
	}
	
	protected TypeToken getToken(InstanceObject io){
		ErrorModelState st = (ErrorModelState) ErrorModelStateAdapterFactory.INSTANCE.adapt(io, ErrorModelState.class);
		return st == null?null:st.getToken();
	}
	
	/**
	 * report on io object with optional error propagation.
	 * report on attached ErrorModelState if present
	 * note: error propagation ep can be null.
	 * @param io Instance Object
	 * @param ep Error Propagation
	 */
	public String generateEffectText(InstanceObject io, ErrorPropagation ep){
		TypeToken tu = getToken(io);
		if (tu != null){
			return(generateItemText(io)+":"+
					EM2Util.getPrintName(tu));
		}
		if (ep != null){
			return(generateItemText(io)+
					(ep.getTypeSet()!=null?":"+EM2Util.getTableName(ep.getTypeSet()):""));
		} else {
			return(generateItemText(io));
		}
	}
	
	/**
	 *  figure out the target typetoken based on the source and type mappings
	 * Path can be a connection instance, a flow spec instance, or an error flow
	 * @param path connection instance, flow spec instance, error flow
	 * @param targetfi feature instance
	 * @return true if to continue traversing, false if to prune traversal (not follow outgoing paths)
	 */
	protected void processToken(TypeToken sourceToken,EObject path,FeatureInstance targetfi){
		if (path instanceof ConnectionInstance){
			if (sourceToken != null){
				// TODO lookup type transformations for connections and use them to determine target type
				setToken(targetfi,sourceToken);
			}
		} else if (path instanceof ErrorPath){
			ErrorPath epath = (ErrorPath)path;
			// map the token
			TypeToken ttup = epath.getTargetToken();
			if (ttup == null){
				// map token via tms
				TypeMappingSet tms = epath.getTypeMappingSet();
				if (tms != null){
					ttup = EM2TypeSetUtil.mapTypeToken(sourceToken, tms);
				}
			}
			setToken(targetfi,ttup==null?sourceToken:ttup);
		} else if (path instanceof FlowSpecificationInstance){
			setToken(targetfi,sourceToken);
		}
		return ;
	}
	
	/**
	 * report on io object with optional error propagation.
	 * report on attached ErrorModelState if present
	 * note: error propagation ep can be null.
	 * @param io Instance Object
	 * @param ep Error Propagation
	 */
	public void report(InstanceObject io, ErrorPropagation ep){
	}
	
	/**
	 * process component instance ci, which is the destination of path.
	 * Path can be a connection instance
	 * @param source Source instance object of path
	 * @param path connection instance or flow instance
	 * @param destination component instance
	 * @return true if to continue traversing, false if to prune traversal (not follow outgoing paths)
	 */
	protected boolean process(InstanceObject source,EObject path, ComponentInstance destination){
		return true;
	}
	
	/**
	 * traverse through the destination of the connection instance 
	 * @param conni
	 */
	protected void traceErrorFlows(InstanceObject source,ConnectionInstance conni, int depth, String entryText){
		ComponentInstance ci = faultModel.getContainingComponentInstance(conni.getDestination());
		InstanceObject desti = Aadl2InstanceUtil.getDestEndPointInstance(ci,conni);
//		if (!visited.add(desti)){
//			// this should be only when visited with the same token
//			reportEntry(entryText, depth);
//			return;
//		}
		String myText=", "+generateItemText(conni);
		// we go to the end of the connection instance, not an enclosing component that may have an error model abstraction
		ErrorPropagations eps = EM2Util.getContainingErrorPropagations(ci.getComponentClassifier());
		if (eps != null){
			ErrorFlow ef=EM2Util.findErrorFlow(eps, desti);
			if (ef instanceof ErrorSink){
				// process should have returned false, but for safety we check again
				String maskText = ", "+generateItemText(desti)+": "+EM2Util.getPrintName(getToken(source))+" Masked";
				reportEntry(entryText+myText+maskText, depth);
				return;
			} else if (ef instanceof ErrorPath){ // error path
				ErrorPropagation outp = ((ErrorPath)ef).getOutgoing();
				FeatureInstance outfi = ci.findFeatureInstance(outp.getFeature());
				processToken(getToken(source),ef,outfi);
					EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getOutgoingConnection(ci,outfi);
					for (ConnectionInstance connectionInstance : connilist) {
						traceErrorFlows(outfi,connectionInstance,depth+1,entryText+myText+", "+generateEffectText(outfi, outp));
					}
				return;
			} 
		}
		// no error flows: 
		// try flows or propagate to all outgoing connections
		if (desti instanceof FeatureInstance){
			FeatureInstance infi = ((FeatureInstance)desti);
			EList<FlowSpecificationInstance> flowlist = ci.getFlowSpecifications();
			if (!flowlist.isEmpty()){
				for (FlowSpecificationInstance flowSpecificationInstance : flowlist) {
					if (flowSpecificationInstance.getSource() == infi){
						processToken(getToken(infi),flowSpecificationInstance,infi);
						FeatureInstance outp = flowSpecificationInstance.getDestination();
						if (outp != null){
							// assumes connection instance does not start inside
							EList<ConnectionInstance> outconnlist = outp.getSrcConnectionInstances();
							for (ConnectionInstance connectionInstance : outconnlist) {
								ErrorPropagation ep = EM2Util.getOutgoingErrorPropagation(infi);
								traceErrorFlows(outp,connectionInstance,depth+1,entryText+myText+generateEffectText(infi, ep));
							}
						}
					}
				}
				return;
			}
		}
		// now all outgoing connections since we did not find flows
		EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getOutgoingConnections(ci);
		for (ConnectionInstance connectionInstance : connilist) {
//			ConnectionInstanceEnd srcci = connectionInstance.getSource();
			ConnectionInstanceEnd srcci = (ConnectionInstanceEnd)Aadl2InstanceUtil.getSrcEndPointInstance(ci, conni);
			// this may be a feature instance of a contained component instance
			// we may want to find the feature of the component we are starting out from
			if (srcci instanceof FeatureInstance){
				processToken(getToken(srcci),null,(FeatureInstance)srcci);
					ErrorPropagation ep = EM2Util.getOutgoingErrorPropagation((FeatureInstance)srcci);
					traceErrorFlows(srcci,connectionInstance,depth+1,entryText+myText+generateEffectText(srcci, ep));
			} else {
				// component instance
				process(desti,null,(ComponentInstance)srcci);
			}
		}
	}

	/**
	 * traverse through the source of the connection instance 
	 * @param conni
	 */
	protected void traceReverseErrorFlows(InstanceObject dest,ConnectionInstance conni, int depth, String entryText){
		ComponentInstance ci = faultModel.getContainingComponentInstance(conni.getSource());
		InstanceObject srci = Aadl2InstanceUtil.getSrcEndPointInstance(ci,conni);
//		if (!visited.add(desti)){
//			// this should be only when visited with the same token
//			reportEntry(entryText, depth);
//			return;
//		}
		String myText=", "+generateItemText(conni);
		// we go to the end of the connection instance, not an enclosing component that may have an error model abstraction
		ErrorPropagations eps = EM2Util.getContainingErrorPropagations(ci.getComponentClassifier());
		if (eps != null){
			ErrorFlow ef=EM2Util.findReverseErrorFlow(eps, srci);
			if (ef instanceof ErrorSource){
				// process should have returned false, but for safety we check again
				String maskText = ", "+generateItemText(srci)+": "+EM2Util.getPrintName(getToken(dest))+" Source";
				reportEntry(entryText+myText+maskText, depth);
				return;
			} else if (ef instanceof ErrorPath){ // error path
				ErrorPropagation inp = ((ErrorPath)ef).getIncoming();
				FeatureInstance infi = ci.findFeatureInstance(inp.getFeature());
				processToken(getToken(dest),ef,infi);
					EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getIncomingConnection(ci,infi);
					for (ConnectionInstance connectionInstance : connilist) {
						traceErrorFlows(infi,connectionInstance,depth+1,entryText+myText+", "+generateEffectText(infi, inp));
					}
				return;
			} 
		}
		// no error flows: 
		// try flows or propagate to all outgoing connections
		if (srci instanceof FeatureInstance){
			FeatureInstance infi = ((FeatureInstance)srci);
			EList<FlowSpecificationInstance> flowlist = ci.getFlowSpecifications();
			if (!flowlist.isEmpty()){
				for (FlowSpecificationInstance flowSpecificationInstance : flowlist) {
					if (flowSpecificationInstance.getSource() == infi){
						processToken(getToken(infi),flowSpecificationInstance,infi);
						FeatureInstance outp = flowSpecificationInstance.getDestination();
						if (outp != null){
							// assumes connection instance does not start inside
							EList<ConnectionInstance> outconnlist = outp.getSrcConnectionInstances();
							for (ConnectionInstance connectionInstance : outconnlist) {
								ErrorPropagation ep = EM2Util.getOutgoingErrorPropagation(infi);
								traceErrorFlows(outp,connectionInstance,depth+1,entryText+myText+generateEffectText(infi, ep));
							}
						}
					}
				}
				return;
			}
		}
		// now all outgoing connections since we did not find flows
		EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getOutgoingConnections(ci);
		for (ConnectionInstance connectionInstance : connilist) {
//			ConnectionInstanceEnd srcci = connectionInstance.getSource();
			ConnectionInstanceEnd srcci = (ConnectionInstanceEnd)Aadl2InstanceUtil.getSrcEndPointInstance(ci, conni);
			// this may be a feature instance of a contained component instance
			// we may want to find the feature of the component we are starting out from
			if (srcci instanceof FeatureInstance){
				processToken(getToken(srcci),null,(FeatureInstance)srcci);
					ErrorPropagation ep = EM2Util.getOutgoingErrorPropagation((FeatureInstance)srcci);
					traceErrorFlows(srcci,connectionInstance,depth+1,entryText+myText+generateEffectText(srcci, ep));
			} else {
				// component instance
				process(srci,null,(ComponentInstance)srcci);
			}
		}
	}
	
//	/**
//	 * traverse through the destination of the connection instance 
//	 * @param conni
//	 */
//	protected void traceBackErrorFlows(ConnectionInstance conni, int depth){
//		int old = this.depth;
//		this.depth = depth;
//		ConnectionInstanceEnd incie = conni.getSource();
//		ComponentInstance ci = incie instanceof ComponentInstance? (ComponentInstance)incie: incie.getContainingComponentInstance();
//		if (!visited.add(incie)){
//			report(incie,"Visited before: ");
//		}
//		// we go to the end of the connection instance, not an enclosing component that may have an error model abstraction
//		ErrorPropagations eps = EM2Util.getComponentErrorPropagations(ci.getComponentClassifier());
//		if (incie instanceof FeatureInstance){
//			if (!process(conni,(FeatureInstance)incie)) {this.depth = old; return;}
//		} else {
//			// component instance
//			if (!process(conni,(ComponentInstance)incie)) {this.depth = old; return;}
//		}
//		if (eps != null){
//			ErrorFlow ef=EM2Util.findReverseErrorFlow(eps, incie);
//			if (ef instanceof ErrorSource){
//				// process should have returned false, but for safety we check again
//				this.depth = old; return;
//			} else if (ef instanceof ErrorPath){ // error path
//				ErrorPropagation outp = ((ErrorPath)ef).getIncoming();
//				FeatureInstance fi = ci.findFeatureInstance(outp.getFeature());
//				process(ef,fi);
//				EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getIncomingConnection(ci,fi);
//				for (ConnectionInstance connectionInstance : connilist) {
//					traceErrorFlows(connectionInstance,depth+1);
//				}
//				this.depth = old; return;
//			} 
//		}
//		// no error flows: 
//		// try flows or propagate to all incoming connections
//		if (incie instanceof FeatureInstance){
//			FeatureInstance fi = ((FeatureInstance)incie);
//			EList<FlowSpecificationInstance> flowlist = ci.getFlowSpecifications();
//			if (!flowlist.isEmpty()){
//				for (FlowSpecificationInstance flowSpecificationInstance : flowlist) {
//					if (flowSpecificationInstance.getDestination() == fi){
//						if (process(flowSpecificationInstance,fi)){
//							FeatureInstance outp = flowSpecificationInstance.getSource();
//							if (outp != null){
//								// assumes connection instance does not start inside
//								EList<ConnectionInstance> outconnlist = outp.getDstConnectionInstances();
//								for (ConnectionInstance connectionInstance : outconnlist) {
//									traceErrorFlows(connectionInstance,depth+1);
//								}
//							}
//						}
//					}
//				}
//				this.depth = old; return;
//			}
//		}
//		// now all incoming connections since we did not find flows
//		EList<ConnectionInstance> connilist = Aadl2InstanceUtil.getIncomingConnections(ci);
//		for (ConnectionInstance connectionInstance : connilist) {
//			ConnectionInstanceEnd destci = connectionInstance.getDestination();
////			ConnectionInstanceEnd destci = (ConnectionInstanceEnd)Aadl2InstanceUtil.getDestEndPointInstance(ci, conni);
//			// this may be a feature instance of a contained component instance
//			// we may want to find the feature of the component we are starting out from
//			if (destci instanceof FeatureInstance){
//				if (!process(incie,(FeatureInstance)destci)) {this.depth = old; return;}
//			} else {
//				// component instance
//				if (!process(incie,(ComponentInstance)destci)) {this.depth = old; return;}
//			}
//			traceErrorFlows(connectionInstance,depth+1);
//		}
//	}

}

