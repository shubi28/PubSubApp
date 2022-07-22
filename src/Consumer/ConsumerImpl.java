package Consumer;

import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Service.PubSubCommonService;

public class ConsumerImpl implements Consumer {

	PubSubCommonService pubSubCommonService;
	String topic;
	List<Message> messageList;
	List<Consumer> predecessors;
	String name;

	public ConsumerImpl(PubSubCommonService pubSubCommonService, String topic, String name){
		this.pubSubCommonService = pubSubCommonService;
		this.topic = topic;
		this.messageList =  new ArrayList<>();
		predecessors = new ArrayList<>();
		this.name = name;
	}
	@Override
	public void register() {
		pubSubCommonService.addSubscriber(topic, this);
	}

	@Override
	public void deregister() {
		pubSubCommonService.removeSubscriber(topic, this);
	}

	@Override
	public List<Message> getMessages() {
		return messageList;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public void addPredecessor(Consumer consumer) {
		predecessors.add(consumer);
	}

	@Override
	public boolean addMessageToList(Message message) {
		boolean success=true;
		if(messageList.contains(message)){
			return true;
		}else{
			for(Consumer predecessor: predecessors){
				success = success && predecessor.addMessageToList(message);
			}
			if(success && message.getTopic().equals(this.topic)) {
				System.out.println("Message added for consumer="+this.name);
				messageList.add(message);
			}
			return success;
		}
	}

	@Override
	public void printMessages() {
		System.out.println("Printing messages for consumer with name="+name);
		for(Message message : messageList){
			System.out.println(message.payload);
		}
	}

	@Override
	public void getAllMyMessages() {
		pubSubCommonService.downloadMessages(this);
		this.printMessages();
	}

	@Override
	public String getName() {
		return name;
	}
}