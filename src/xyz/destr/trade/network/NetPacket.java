package xyz.destr.trade.network;

public interface NetPacket {
	public default boolean closeConnection() {
		return false;
	}
}
