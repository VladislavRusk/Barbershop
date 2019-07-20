import java.net.*;
import java.io.*;

public class GetServer {
	InetAddress ip;
 //   ObjectOutputStream toServer; // serializes them
 //   ObjectInputStream fromServer; // deserializes data and objects
	
	
	/* socket: endpoint of communcication between two machines
	 * 		Needs IP and Port
	 * ObjectOutPutStream: serializes objects
	 * getOutputStream() returns output stream
	 * ObjectInputStream: deserializes objects
	 * getInputStream() returns input stream 
	 */
	GetServer() {
	}
// CUSTOMER INTERFACE CLASSES YOU CAN USE	
	
	public giveCustomerInfo(CustomerInfo info) {
		this.sendInfo(new Packet(
	}
	
	/*
		@return will return customerinfo class if found and null if no account
	*/	
	
	public CustomerInfo getCustomerInfo(String userName) {
		Packet packet = new Packet((new CustomerInfo(userName)),RequestEnum.request.getData);
		packet = this.getInfo(packet);
		return packet.getCustomer();
	}
	
	/*
		@return will return customerinfo class if found and null if no account
		(COMPARES PASSWORD)
	*/
	
	public CustomerInfo getCustomerInfo(String userName, String password) {
		return this.findCustomerInfo(username,password);
	}	
	
	public CustomerInfo getCustomerInfo(CustomerInfo info) {
		Packet packet = new Packet(info,RequestEnum.request.getData);
		packet = this.getInfo(packet);
		return packet.getCustomer();
	}	
	
	/*
		@return returns true if created account false if account already exists
	*/
	public boolean createCustomerInfoAccount(CustomerInfo info) {
		if (this.findCustomerInfo(info.getUserName(),info.getPassword()) == null) {
			this.sendInfo(new Packet(info,RequestEnum.Request.giveData));
			return true;
		} else {
			System.out.println("ACCOUNT EXISTS");
		}		
		return false;
	}	
	
// BARBERSHOP INTERFACE CLASSES
	public BarberShopInfo getBarberShopInfo(String userName) {
		Packet packet = new Packet((new BarberShopInfo(userName)),RequestEnum.request.getData);
		packet = this.getInfo(packet);
		return packet.getBarberShop();
	}	
	
	/*
		@return returns account if it found it based on username or password
		Will Return null if it doesnt find account
	*/
	public BarberShopInfo getBarberShopInfo(String userName, String password) {
		return this.findBarberShopInfo(username,password);
	}	
	
	public BarberShopInfo getBarberShopInfo(BarberShopInfo info) {
		Packet packet = new Packet(info,RequestEnum.request.getData);
		packet = this.getInfo(packet);
		return packet.getBarberShop();
	}
	
	
	/*
		@return returns true if created account false if account already exists
	*/	
	public boolean createBarberShopAccount(BarberShopInfo info) {
		if (this.findBarberShopInfo(info.getUserName(),info.getPassword()) == null) {
			this.sendInfo(new Packet(info,RequestEnum.Request.giveData));
			return true;
		} else {
			System.out.println("ACCOUNT EXISTS");
		}		
		return false;
	}	
	
	

// DONT WORRY ABOUT THESE CLASSES

	private CustomerInfo findCustomerInfo(String userName, String password) {
		Packet packet = new Packet((new CustomerInfo(userName,password)),RequestEnum.request.findData);
		return packet.getCustomer();
	}	
	
	private BarberShopInfo findBarberShopInfo(String userName, String password) {
		Packet packet = new Packet((new CustomerInfo(userName,password)),RequestEnum.request.findData);
		return packet.getCustomer();
	}
	
	/* returns true of connected returns false otherwise
	 * 
	 */
	private Socket connectToServer() {
		Socket socket;
		try {
			this.ip = InetAddress.getLocalHost();
			socket = new Socket(ip.getHostName(),4000);
			return socket;		
		} catch(Exception e) {
			System.out.println("Couldn't Connect");
		}		
		return null;
	}
	
	private void sendInfo(Packet info) {
		try {
			Socket socket = this.connectToServer();
			
			// serializes some object and sends
			// getOutputStream returns an output stream from socket
			ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(info); // writes state of object
			toServer.close();
		} catch (Exception e) {
			System.out.println("Didnt send info");
		}
	}
		
	private Packet getInfo(Packet info) {
		Packet packet = null;
		try {
			this.sendInfo(info);
			// serializes some object and sends
			// getOutputStream returns an output stream from socket
			Socket socket = new Socket(ip.getHostName(),4001);
			ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
			packet = (Packet)fromServer.readObject(); // writes state of object
			fromServer.close();
		} catch (Exception e) {
			System.out.println("Didnt send info2");
		}		
		return packet;
	}
	
}

