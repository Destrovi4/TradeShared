package xyz.destr.trade.user;

import java.util.UUID;

import xyz.destr.trade.resource.ResourceType;
import xyz.destr.trade.user.action.BuyUserAction;
import xyz.destr.trade.user.action.SellUserAction;
import xyz.destr.trade.user.action.UserAction;

public interface UserHandler {
	
	public UUID getUserUUID();
	
	public default void sellResource(ResourceType resourceType, long value) {
		SellUserAction sellUserAction = new SellUserAction();
		sellUserAction.resourceType = resourceType;
		sellUserAction.value = value;
		performUserAction(sellUserAction);
	}
	
	public default void buyResource(ResourceType resourceType, long value) {
		BuyUserAction buyUserAction = new BuyUserAction();
		buyUserAction.resourceType = resourceType;
		buyUserAction.value = value;
		performUserAction(buyUserAction);
	}
	
	public void performUserAction(UserAction userAction);
}
