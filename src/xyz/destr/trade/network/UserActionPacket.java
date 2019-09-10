package xyz.destr.trade.network;

import java.io.Serializable;

import xyz.destr.trade.user.action.UserAction;

public class UserActionPacket implements NetPacket, Serializable {
	private static final long serialVersionUID = 3298517853223115624L;
	public UserAction userAction;
}
