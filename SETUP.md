# dal-demo-lambda setup

# A Guide to setting and configuring a new lambda

This repository was generated for `dal-demo-lambda` and is ready for development.

The owning team is `platform-team` and the service lifecycle is `experimental`.




## Setup Steps

1. Access [Teams Service](https://dx-team-services.vf-cep.engineering.vodafone.com/9f2fb151-e7b7-4b2e-ab9c-2c17d1db61d7) and select `Create a UK Service` under `Services`. Enter the service name, team name, choose `lambda` as the service type, select `Java` as the language, select appropriate platform (e.g., dal), and create the service. You will be redirected to a page with URLs for your new lambda repository and its build & release pipelines.

2. Clone your new repository to your local machine using `git clone`.

3. No interactive installation script is required. Repository names, Java packages, handler classes, infrastructure, and ownership files were generated from the workflow inputs.

4. Open the project in IntelliJ IDEA. Verify that the project loads and compiles successfully. Ensure you can run the generated `DemoLambdaHandlerTest` JUnit test.

5. Before running the build pipeline for the first time, manually create a SonarQube project for your service by following the [SonarQube setup guide](https://dev.azure.com/vfuk-digital/Digital/_wiki/wikis/Digital%20X.wiki/30417/SonarQube-Enterprise-FAQ).

6. Configure variable groups for your new project, starting with the `dev1` environment. Make sure all required variables (`Memory`, `Timeout`, `ReservedConcurrentExecutions`, `CronScheduleFlag`, `Priority` etc.,) are set, and verify that existing values are correct for your service and environment. After confirming the service works in `dev1`, repeat this process for other environments.

7. Moment of truth.... Commit changes!

If this has worked, then it will cause the project to be built in the pipelines (Compile and JUnit Tested)
and then once passed, deployed to `dev1` where it will also run unittests.

You should now enable build policies on the master branch to add minimum reviewers, build policies and Sonarqube quality check.

## Once all this is confirmed...

1. You can test your Lambda from AWS Management console and see the output in Cloudwatch logs or Datadog.
Here is the link to [AWS](https://eu-west-1.console.aws.amazon.com/lambda)

 - Use this template to invoke your lambda manually:
   
   ```json
   {
   "id": "cdc73f9d-aea9-11e3-9d5a-835b769c0d9c",
   "detail-type": "Scheduled Event",
   "source": "aws.events",
   "account": "123456789012",
   "time": "1970-01-01T00:00:00Z",
   "region": "{region}",
   "resources": [
   "arn:{partition}:events:{region}:123456789012:rule/my-schedule"
   ],
   "detail": {}
   }
   ```
   
2. Update the other environment variable groups; 

3. Update the documentation files; add links, info, etc

4. Delete this file and the install files!
