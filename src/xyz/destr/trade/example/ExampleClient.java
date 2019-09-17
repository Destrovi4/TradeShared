package xyz.destr.trade.example;

import java.io.IOException;

import xyz.destr.trade.network.DefaultClientConnection;
import xyz.destr.trade.resource.ResourceType;
import xyz.destr.trade.user.UserController;
import xyz.destr.trade.user.UserHandler;
import xyz.destr.trade.user.info.ResourceInventoryInfo;
import xyz.destr.trade.user.info.ResourceMarketInfo;

public class ExampleClient implements UserController {

	public static void main(String[] args) throws IOException {
		DefaultClientConnection.init(args);
		DefaultClientConnection.start(new ExampleClient());
	}
	
	@Override
	public void onUserEntry(UserHandler userHandler) {
		System.out.println("userUUID " + userHandler.getUserUUID());
		
	}

	@Override
	public void onResourceMarketInfo(UserHandler userHandler, ResourceMarketInfo rmi) {
		System.out.println("res\tamount\tsell\tbuy");
		for(int i = 0; i < rmi.amount.length; i++) {
			ResourceType rt = ResourceType.values[i];
			System.out.printf("%s\t%d\t%d\t%d\n", rt.name(), rmi.getAmount(rt), rmi.getSellingPrice(rt), rmi.getPurchasePrice(rt));
		}
		System.out.println();
	}

	@Override
	public void onResourceInventoryInfo(UserHandler userHandler, ResourceInventoryInfo rii) {
		for(int i = 0; i < rii.amount.length; i++) {
			ResourceType rt = ResourceType.values[i];
			System.out.printf("%s %d\n", rt.name(), rii.getAmount(rt));
		}
	}

}
