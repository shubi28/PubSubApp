package Model;

public class Node
{
	public Message message;
	public Node next;

	public Node(Message m){
		this.message = m;
		this.next = null;
	}
}