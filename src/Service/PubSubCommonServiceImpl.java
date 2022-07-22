package Service;

import Consumer.Consumer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Model.Message;


public class PubSubCommonServiceImpl implements PubSubCommonService {
	Map<Consumer, MessagingQueue> messagingQueueMap;
	Map<String, Set<Consumer>> suscribersMap;
	int queueSize;

	public PubSubCommonServiceImpl(int size){
		messagingQueueMap = new HashMap<>();
		suscribersMap = new HashMap<>();
		queueSize = size;
	}

	@Override
	public boolean addToMessageQueue(Message message) {
		boolean isSuccess =true;
		if(isSuccess){
			String topic = message.getTopic();
			if(suscribersMap.containsKey(topic)){
				Set<Consumer> consumers = suscribersMap.get(topic);
				for(Consumer consumer : consumers){
					isSuccess = consumer.addMessageToList(message);
					if(!isSuccess){
						addMessageToConsumerQueue(message, consumer);
					}
				}
			}
		}
		return isSuccess;
	}

	private void addMessageToConsumerQueue(Message message, Consumer consumer) {
		MessagingQueue messagingQueue = null;
		if(messagingQueueMap.containsKey(consumer)){
			messagingQueue = messagingQueueMap.get(consumer);
			messagingQueue.addMessage(message);
			messagingQueueMap.put(consumer, messagingQueue);
		}else{
			messagingQueue = new MessagingQueue(queueSize);
			messagingQueue.addMessage(message);
			messagingQueueMap.put(consumer, messagingQueue);
		}
	}

	@Override
	public void addSubscriber(String topic, Consumer consumer) {
		Set<Consumer> consumers = null;
		if(suscribersMap.containsKey(topic)){
			consumers = suscribersMap.get(topic);
			consumers.add(consumer);
			suscribersMap.put(topic, consumers);
		}else{
			consumers = new HashSet<>();
			consumers.add(consumer);
			suscribersMap.put(topic, consumers);
		}
	}

	@Override
	public void removeSubscriber(String topic, Consumer consumer) {
		if(suscribersMap.containsKey(topic)){
			Set<Consumer> consumers = suscribersMap.get(topic);
			consumers.remove(consumer);
			suscribersMap.put(topic, consumers);
		}
	}

	@Override
	public void downloadMessages(Consumer consumer) {
		if(messagingQueueMap.containsKey(consumer)){
			System.out.println("Downloading messages for consumer with name="+consumer.getName());
			MessagingQueue messagingQueue = messagingQueueMap.get(consumer);
			while(!messagingQueue.isEmpty()){
				Message message = messagingQueue.getFront();
				System.out.println("New message is "+message.payload);
				consumer.addMessageToList(message);
				messagingQueue.removeMessage();
			}
		}
	}

}