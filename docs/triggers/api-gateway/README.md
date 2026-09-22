# API Gateway/ELB Function Events

This will configure the lambda to be executed via the API/ELB and setup
the lambda to execute a local events to run/test locally.

## Installation
Copy the following information in to the lambda:

- __events__:
Copy the `apigw-event.json` and `elb-event.json` in to the __local/events__ folder.
- __infrastructure__:
Add the context from the files in to the __parameters.yaml__ and 
__cf-lambdatemplate.yaml__ files.
- __infrastructure/LambdaFunction__:
Add the following to the _LambdaFunction/Environment/Variables_ section:
```
`CONTEXT_PATH: !Ref 'ContextPath'`
```
- __pom.xml__:
Add the context from the __pom.xml__ in to the __pom.xml__
- __run__:
Copy the `Run Locally` file in to the __.run__ folder.
- __src__:
Copy the class files to the __src__ folder. This includes source and test 
files.
- __src/PropertyKeys__:
Add the additional property key to the __PropertyKeys__;
```
public static final PropertyKeyString CONTEXT_PATH = new PropertyKeyString("CONTEXT_PATH", "");
```

Once installed, add the `APIGatewayExampleService` to the `Handler` service list.

## AzureDevOps Variables
Add the following variables to your variable groups.

- __ContextPath__:
This is your service path.
- __DalAlbHttpsListenerArnSsmExtra__: Set this to the following to use the EXTRA ALB: _/$(Domain)/infra/dal/$(DxLShortEnvironmentName)$(DxLEnvironmentFlavour)/alb-extra-https-listener_
- __DalAlbSecurityGroupSsmExtra__: Set this to the following to use the EXTRA ALB: _/$(Domain)/infra/dal/$(DxLShortEnvironmentName)$(DxLEnvironmentFlavour)/alb-extra-sg-id_
- __HealthCheckPath__: Set this to: _actuator/health_
- __HealthCheckIntervalSeconds__: Set this to: _300_
- __HealthCheckTimeoutSeconds__: Set this to: _5_
- __HealthyThresholdCount__: Set this to: _2_
- __UnhealthyThresholdCount__: Set this to: _3_
- __Priority__: Set this to a unique value for the ALB.

## Running / Testing
Once the above steps are complete;
- you can run/test locally by executing a new 
configuration run command, which will run the `apigw-event.json` or
`elb-event.json` in the __events__ folder.
- you can run the test class `APIGatewayExampleServiceTest`
- you can deploy it and check that this runs by calling your lambda using
https://{{dxl.url}}/{{context-path}}