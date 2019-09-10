package xyz.destr.trade.network;

import java.io.Serializable;
import java.util.UUID;

public class HandshakeResponse implements NetPacket, Serializable {
	private static final long serialVersionUID = 5586684994368730948L;
	public UUID userUUID;
	public boolean success;
	
	@Override
	public boolean closeConnection() {
		return !success;
	}
}
