
import java.util.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.elasticmapreduce.*;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsResult;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;

public class SubmitSparkJobsOnEMR {
	
	public static void main(String[] args) {
	
	    AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
	    AmazonElasticMapReduce emr = new AmazonElasticMapReduceClient(credentials);
	 
	    AddJobFlowStepsRequest req = new AddJobFlowStepsRequest()
	    		                     .withJobFlowId("j-1K48XXXXXXHCB");

	    List<StepConfig> stepConfigs = new ArrayList<StepConfig>();
			
	    HadoopJarStepConfig sparkStepConf = new HadoopJarStepConfig()
				.withJar("command-runner.jar")
				.withArgs("spark-submit","--executor-memory","1g",
						"--class","org.apache.spark.examples.SparkPi",
						"/usr/lib/spark/lib/spark-examples.jar","10");			
			
	    StepConfig sparkStep = new StepConfig()
				.withName("Spark Step")
				.withActionOnFailure("CONTINUE")
				.withHadoopJarStep(sparkStepConf);

	    stepConfigs.add(sparkStep);
	    req.withSteps(stepConfigs);
	    AddJobFlowStepsResult result = emr.addJobFlowSteps(req);
	    System.out.println(result.getStepIds());
	}
}
