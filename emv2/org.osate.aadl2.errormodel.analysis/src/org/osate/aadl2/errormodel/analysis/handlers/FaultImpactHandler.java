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
package org.osate.aadl2.errormodel.analysis.handlers;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osate.aadl2.Element;
import org.osate.aadl2.errormodel.analysis.PropagateErrorSources;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.ui.handlers.AaxlReadOnlyHandlerAsJob;

public final class FaultImpactHandler extends AaxlReadOnlyHandlerAsJob {
	@Override
	protected String getMarkerType() {
		return "org.osate.analysis.errormodel.FaultImpactMarker";
	}

	@Override
	protected String getActionName() {
		return "Fault impact analysis";
	}

	@Override
	public void doAaxlAction(IProgressMonitor monitor, Element obj) {
		monitor.beginTask("Fault impact analysis", IProgressMonitor.UNKNOWN);

		// Get the system instance (if any)
		SystemInstance si;
		if (obj instanceof InstanceObject) {
			si = ((InstanceObject) obj).getSystemInstance();
		} else {
			return;
		}

		/*
		 * Create a new model statistics analysis object and run it over the
		 * declarative model. If an instance model exists, run it over that too.
		 */
		PropagateErrorSources faultimpact = new PropagateErrorSources("FaultImpact", si); // optional third parameter maxLevel
		faultimpact.reportImpactHeading();
		faultimpact.reportTableHeading();
		Collection<ComponentInstance> cilist = faultimpact.getSubcomponents();
		for (ComponentInstance componentInstance : cilist) {
			if (componentInstance != null && !(componentInstance instanceof SystemInstance)) {
				// OsateDebug.osateDebug("start flow for ci="+componentInstance.getName());
				faultimpact.startErrorFlows(componentInstance);
			}
		}
		for (ComponentInstance componentInstance : cilist) {
			if (componentInstance != null) {
				// OsateDebug.osateDebug("start flow for ci="+componentInstance.getName());
				faultimpact.startConnectionSourceFlows(componentInstance);
			}
		}
		faultimpact.startExternalFlows(si);
		faultimpact.saveReport();

		monitor.done();
	}
}