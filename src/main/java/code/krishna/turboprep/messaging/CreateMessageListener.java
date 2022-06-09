package code.krishna.turboprep.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;

import code.krishna.turboprep.domain.Item;

@Component
public class CreateMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println("Hii I received a message");
		try {
			Item itmMsg = (Item) SerializationUtils.deserialize(message.getBody());
			System.out.println("New item of Id : " + itmMsg.getId() + " & name: " + itmMsg.getName() + " created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
