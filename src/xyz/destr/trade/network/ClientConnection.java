package xyz.destr.trade.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import xyz.destr.trade.user.UserController;
import xyz.destr.trade.user.UserHandler;
import xyz.destr.trade.user.action.UserAction;

public class ClientConnection implements UserHandler {
	
	protected UUID userUUID;
	protected Socket socket = new Socket();
	protected UserController userController;
	protected BlockingQueue<NetPacket> toSend = new LinkedBlockingQueue<>();	
	
	protected Thread writeThread = new Thread() {
		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				{
					HandshakeRequest handshakeRequest = new HandshakeRequest();
					handshakeRequest.userUUID = userUUID;
					oos.writeObject(handshakeRequest);
				}
				while(!thread.isInterrupted()) {
					NetPacket netPacket = toSend.take();
					oos.writeObject(netPacket);
					if(netPacket.closeConnection()) {
						socket.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	protected Thread readThread = new Thread() {
		@Override
		public void run() {
			Thread thread = Thread.currentThread();
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				{
					HandshakeResponse handshakeResponse = (HandshakeResponse)ois.readObject();
					if(!handshakeResponse.success) {
						socket.close();
						return;
					} else {
						userUUID = handshakeResponse.userUUID;
					}
				}
				if(userController != null) {
					userController.onUserEntry(ClientConnection.this);
				}
				while(!thread.isInterrupted()) {
					NetPacket netPacket = (NetPacket)ois.readObject();
					if(netPacket.closeConnection()) {
						socket.close();
						return;
					} else {
						if(netPacket instanceof UserInfoPacket) {
							userController.processUserInfo(ClientConnection.this, ((UserInfoPacket)netPacket).userInfo);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public UUID getUserUUID() {
		return userUUID;
	}
	
	public void start(SocketAddress socketAddress, UUID userUUID, UserController userController) throws IOException {
		this.userController = userController;
		socket.connect(socketAddress);
		writeThread.start();
		readThread.start();
	}

	public void stop() {
		if(!socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		toSend.clear();
		toSend.add(null);
		try {
			writeThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			readThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void performUserAction(UserAction userAction) {
		UserActionPacket userActionPacket = new UserActionPacket();
		userActionPacket.userAction = userAction;
		toSend.add(userActionPacket);
	}
	
}
