# Cron / Schedule Function Events

This will configure the lambda to be executed on a scheduled time and setup
the lambda to execute a local event to run/test locally.

## Installation
Copy the following information in to the lambda:

- __events__:
Copy the `cron-schedule-event.json` in to the __local/events__ folder.
- __infrastructure__:
Add the context from the files in to the __parameters.yaml__ and 
__cf-lambdatemplate.yaml__ files.
- __run__:
Copy the `Run Locally` file in to the __.run__ folder.
- __src__:
Copy the class files to the __src__ folder. This includes source and test 
files.

Once installed, add the `CronJobExampleService` to the `Handler` service list.

## AzureDevOps Variables
Add the following variables to your variable groups.

- __CronScheduleFlag__:
Enabled/Disables the CRON Job
- __CronSchedule__:
Schedule to run the CRON Job. i.e cron(0/10 * * * MON-FRI *).
See https://docs.aws.amazon.com/AmazonCloudWatch/latest/events/ScheduledEvents.html 
or https://docs.aws.amazon.com/eventbridge/latest/userguide/eb-cron-expressions.html

## Running / Testing
Once the above steps are complete;
- you can run/test locally by executing a new 
configuration run command, which will run the `cron-schedule-event.json` in the
__events__ folder.
- you can run the test class `CronJobExampleServiceTest`
- you can deploy it and check that this runs as per the schedule.
  __cron(0/10 * * * MON-FRI *)__ will run every 10 mins MON-FRI. 
__(Please disable this once tested as it will keep running!)__