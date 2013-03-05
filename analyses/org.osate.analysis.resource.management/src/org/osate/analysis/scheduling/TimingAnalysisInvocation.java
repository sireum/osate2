/** to invoke the schedulability analysis
 * created by Jun Li, Mar. 22, 1999.
 */

package org.osate.analysis.scheduling;

import org.osate.aadl2.ComponentCategory;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.modelsupport.WriteToFile;
import org.osate.aadl2.modelsupport.errorreporting.AnalysisErrorReporterManager;



public class TimingAnalysisInvocation {
	
	/**
	 * This method drives scheduling analysis for a particular processor
	 * The RuntimeProcessWalker remembers the name and does analysis
	 * @param processor
	 */
	public static boolean timingSchedulabilityAnalysis(
			final AnalysisErrorReporterManager errMgr, WriteToFile csvlog,
			final ComponentInstance processor) {
		if (processor.getCategory() != ComponentCategory.PROCESSOR)
			return false;
		RuntimeProcessWalker walker = new RuntimeProcessWalker( errMgr,csvlog);
		walker.cleanProcessHolder() ;
		walker.setCurrentProcessor(processor);
		walker.initWalker();
		walker.componentsSortByPeriod();
		walker.assignPriority();
		boolean result = walker.timingSchedualabilityAnalysis();
		return result;
	}
	
	
}