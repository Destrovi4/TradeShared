package xyz.destr.trade.network;

import java.io.Serializable;

import xyz.destr.trade.user.info.UserInfo;

public class UserInfoPacket implements NetPacket, Serializable {
	private static final long serialVersionUID = 5271530322016649172L;
	public UserInfo userInfo;
}
