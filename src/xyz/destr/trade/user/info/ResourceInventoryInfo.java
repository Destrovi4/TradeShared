package xyz.destr.trade.user.info;

import xyz.destr.trade.resource.ResourceType;

public class ResourceInventoryInfo implements UserInfo {
	private static final long serialVersionUID = -5946594202470880660L;
	public final long[] resources = new long[ResourceType.values.length];
}
