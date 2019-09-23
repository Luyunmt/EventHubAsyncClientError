
import java.io.IOException;
import java.util.List;
import com.azure.core.amqp.exception.AmqpException;
import com.azure.identity.credential.DefaultAzureCredential;
import com.azure.identity.credential.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubAsyncClient;
import com.azure.messaging.eventhubs.EventHubAsyncProducer;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
		String host = "{eventhub namespace}.servicebus.windows.net";		
		String eventHubName = "<< NAME OF THE EVENT HUB >>";
		EventHubAsyncClient eventHubAsyncClient = new EventHubClientBuilder()
				.credential(host, eventHubName, credential)
				.buildAsyncClient();
		List<String> firstPartition = eventHubAsyncClient.getPartitionIds().collectList().block();
		System.out.println(firstPartition);
		EventHubAsyncProducer producer = eventHubAsyncClient.createProducer();
		EventData data = new EventData("Hello world!".getBytes(UTF_8));
		producer.send(data).subscribe(
		(ignored) -> System.out.println("Event sent."),
		error -> {
			System.err.println("There was an error sending the event: " + error.toString());
			if (error instanceof AmqpException) {
				AmqpException amqpException = (AmqpException) error;
				System.err.println(String.format("Is send operation retriable? %s. Error condition: %s",
					amqpException.isTransient(), amqpException.getErrorCondition()));
			}
		}, () -> {
			try {
				producer.close();
			} catch (IOException e) {
				System.err.println("Error encountered while closing producer: " + e.toString());
			}
			eventHubAsyncClient.close();
		});	
   }
}

 