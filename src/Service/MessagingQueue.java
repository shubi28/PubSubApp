package Service;

import Model.Message;
import Model.Node;


public class MessagingQueue {
	Node front;
	Node rear;
	int size;
	int capacity;

	MessagingQueue(int capacity){
		this.capacity=capacity;
		this.size=0;
		this.front = null;
		this.rear = null;
	}

	boolean isEmpty(){
		return (size==0);
	}

	boolean isFull(){
		return (size==capacity);
	}

	Message getFront(){
		return (isEmpty())?null: this.front.message;
	}

	boolean addMessage(Message msg){
		if(isFull()){
			return false;
		}
		Node temp = new Node(msg);
		if(front==null){
			front = temp;
		}else{
			rear.next=temp;
		}
		size++;
		rear=temp;
		return true;
	}

	boolean removeMessage(){
		if(isEmpty()){
			return false;
		}
		Node temp = this.front;
		if(front== rear){
			front = null;
			rear = null;
		}else{
			front = front.next;
		}
		size--;
		return true;
	}

	void setSize(int size){
		if(size>capacity)
			this.capacity = size;
	}
}