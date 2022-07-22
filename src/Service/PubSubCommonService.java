package Service;

import Model.Message;
import Consumer.Consumer;

public interface PubSubCommonService {
	boolean addToMessageQueue(Message message);
	void addSubscriber(String topic, Consumer consumer);
	void removeSubscriber(String topic, Consumer consumer);
	void downloadMessages(Consumer consumer);
}