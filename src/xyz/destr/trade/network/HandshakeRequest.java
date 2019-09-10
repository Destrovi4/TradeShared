package xyz.destr.trade.network;

import java.io.Serializable;
import java.util.UUID;

public class HandshakeRequest implements NetPacket, Serializable {
	private static final long serialVersionUID = 7106889557667952436L;
	public UUID userUUID;
}
