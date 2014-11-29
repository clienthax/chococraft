package chococraft.common.network.clientSide;

import chococraft.common.config.ChocoboConfig;
import chococraft.common.ModChocoCraft;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboLocalSetupUpdate implements IMessage {//TODO - is this even needed..

	public boolean hungerEnabled;

	public ChocoboLocalSetupUpdate() {
		this.hungerEnabled = ModChocoCraft.hungerEnabled;
	}


	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeBoolean(this.hungerEnabled);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.hungerEnabled = buffer.readBoolean();
	}

	public static class Handler implements IMessageHandler<ChocoboLocalSetupUpdate, IMessage> {

		@Override
		public IMessage onMessage(ChocoboLocalSetupUpdate message, MessageContext ctx) {
			ModChocoCraft.isRemoteClient = false;
			ChocoboConfig.readConfigFilePreInit();
			return null;
		}
	}
}
