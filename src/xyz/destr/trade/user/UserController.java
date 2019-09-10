package xyz.destr.trade.user;

import java.util.UUID;

import xyz.destr.trade.user.info.ResourceInventoryInfo;
import xyz.destr.trade.user.info.ResourceMarketInfo;
import xyz.destr.trade.user.info.UserInfo;

public interface UserController {
	
	public void onUserEntry(UserHandler userHandler);
	public void onUserExit(UUID uuid);
	
	public void onResourceMarketInfo(UserHandler userHandler, ResourceMarketInfo resourceMarketInfo);
	public void onResourceInventoryInfo(UserHandler userHandler, ResourceInventoryInfo resourceInventoryInfo);
	
	public default void processUserInfo(UserHandler userHandler, UserInfo userInfo) {
		if(userInfo instanceof ResourceMarketInfo) {
			onResourceMarketInfo(userHandler, (ResourceMarketInfo)userInfo);
		} else if(userInfo instanceof ResourceInventoryInfo) {
			onResourceInventoryInfo(userHandler, (ResourceInventoryInfo)userInfo);
		}
	}
	
}
