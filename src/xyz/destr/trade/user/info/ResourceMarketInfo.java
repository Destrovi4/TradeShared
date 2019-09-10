package xyz.destr.trade.user.info;

import xyz.destr.trade.resource.ResourceType;

public class ResourceMarketInfo implements UserInfo {
	private static final long serialVersionUID = -4491611244274329319L;
	public final long[] resources = new long[ResourceType.values.length];
	public final long[] purchasePrice = new long[ResourceType.values.length];
	public final long[] sellingPrice = new long[ResourceType.values.length];
}
