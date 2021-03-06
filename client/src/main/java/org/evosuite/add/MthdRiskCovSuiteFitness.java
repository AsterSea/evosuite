package org.evosuite.add;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testsuite.AbstractTestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;

public class MthdRiskCovSuiteFitness extends TestSuiteFitnessFunction {

	private static final long serialVersionUID = -7721713060612435201L;

	@Override
	public double getFitness(AbstractTestSuiteChromosome<? extends ExecutableChromosome> individual) {
		Double fitness = Double.MAX_VALUE;
		List<ExecutionResult> results = runTestSuite(individual);
		Set<String> reachedMethods = new HashSet<String>();

		for (ExecutionResult result : results) {
			// if (result.hasTimeout() || result.hasTestException()) {
			// continue;
			// }
			// for (String mthd : result.getTrace().getCoveredMethods()) {
			//// org.evosuite.utils.LoggingUtils.getEvoLogger().info("lzw covered method:" +
			// mthd);
			// reachedMethods.add(mthd);
			// }
			reachedMethods.addAll(CoveredUtil.getCoveredMthd(result));
		}

		for (String reachedMthd : reachedMethods) {
//			org.evosuite.utils.LoggingUtils.getEvoLogger().info("lzw covered method:" + reachedMthd);
			Double dis = GlobalVar.i().getMthdDistance().getDistance(MthdFormatUtil.evo2std(reachedMthd));
			
			if (dis == 0) {
				DebugUtil.infoZero("===lzw zero fitness:" + reachedMthd);
			}
			if (dis < fitness) {
				fitness = dis;
			}
		}
		updateIndividual(this, individual, fitness);
//		org.evosuite.utils.LoggingUtils.getEvoLogger().info("lzw fitness:" + fitness);
		return fitness;
	}

}
