package xyz.destr.trade.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;

import xyz.destr.trade.user.UserController;
import xyz.destr.trade.user.UserHandler;

public class DefaultClientConnection {
	
	protected static String address = "127.0.0.1";
	protected static int port = DefaultNetworkPropertys.PORT;
	protected static boolean load = false;
	protected static boolean save = false;
		
	public static void init(String[] args) {
		int index = 0;
		while(index < args.length) {
			switch(args[index++]) {
			case "address":
				address = args[index++];
			break;
			case "port":
				port = Integer.parseInt(args[index++]);
			break;
			case "save":
				save = Boolean.parseBoolean(args[index++]);
			break;
			case "load":
				load = Boolean.parseBoolean(args[index++]);
			break;
			}
		}
	}
	
	protected static UUID loadUUID() {
		try {
			File uuidFile = new File("uuid");
			if(uuidFile.exists()) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(uuidFile));
				try {
					return (UUID)(ois.readObject());
				} finally {
					ois.close();
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected static void saveUUID(UUID uuid) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("uuid"));
			try {
				oos.writeObject(uuid);
			} finally {
				oos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ClientConnection start(UserController userController) throws IOException {
		ClientConnection clientConnection = new ClientConnection();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(address, port);		
		clientConnection.start(inetSocketAddress, load ? loadUUID() : null, save ? new UserController() {
			
			@Override
			public void onUserEntry(UserHandler userHandler) {
				clientConnection.setUserController(userController);
				saveUUID(userHandler.getUserUUID());
				userController.onUserEntry(userHandler);
			}
			
		} : userController);
		return clientConnection;
	}
}
