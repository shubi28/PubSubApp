package Consumer;

import java.util.List;
import Model.Message;

public interface Consumer {
	void register();
	void deregister();
	List<Message> getMessages();
	String getTopic();
	void addPredecessor(Consumer consumer);
	boolean addMessageToList(Message message);
	void printMessages();
	void getAllMyMessages();
	String getName();
}