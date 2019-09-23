# EventHubs-java-bug-whensendreceive
The client created by using host, enventhubname and Credential doesn't work.Use ConnectString to  create EventhubAsyncClient works well. 

# Reproduce this issue
- clone this repository using Git first.

- git clone https://github.com/wantedfast/EventHubs-donet-bug-whenConsumerEvents.git

- set AZURE_CLIENT_ID, AZURE_CLIENT_SECRET, and AZURE_TENANT_ID environment variables.

- set your eventhubname and host in the code. 

- In Visual Studio Code, Open-Folder your project file and run...

# StackTrace & Error Message

There was an error sending the event: java.lang.IllegalArgumentException: status-code: 400, status-description: Property 'type' in the 'application-properties' section is either missing, has the wrong type, or has an unexpected value.
