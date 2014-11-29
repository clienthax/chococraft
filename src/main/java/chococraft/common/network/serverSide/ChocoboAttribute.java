package chococraft.common.network.serverSide;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.network.PacketHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboAttribute implements IMessage {

	public int entityID;
	public String chocoboName;//update to uuid in future TODO
	public boolean hideName;
	public boolean isFollowing;
	public boolean isWander;
	public int dimensionId;

	public ChocoboAttribute() {}

	public ChocoboAttribute(EntityAnimalChocobo chocobo) {
		this.entityID = chocobo.getEntityId();
		this.chocoboName = chocobo.getName();
		this.hideName = chocobo.isHidename();
		this.isFollowing = chocobo.isFollowing();
		this.isWander = chocobo.isWander();
		this.dimensionId = chocobo.worldObj.provider.getDimensionId();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.entityID);
		ByteBufUtils.writeUTF8String(buffer, this.chocoboName);
		buffer.writeBoolean(this.hideName);
		buffer.writeBoolean(this.isFollowing);
		buffer.writeBoolean(this.isWander);
		buffer.writeInt(this.dimensionId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityID = buffer.readInt();
		this.chocoboName = ByteBufUtils.readUTF8String(buffer);
		this.hideName = buffer.readBoolean();
		this.isFollowing = buffer.readBoolean();
		this.isWander = buffer.readBoolean();
		this.dimensionId = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<ChocoboAttribute, IMessage> {

		@Override
		public IMessage onMessage(ChocoboAttribute message, MessageContext ctx) {
			EntityAnimalChocobo chocobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionId);
			if(chocobo != null)
			{
				chocobo.setHidename(message.hideName);
				chocobo.setFollowing(message.isFollowing);
				chocobo.setWander(message.isWander);
				chocobo.setName(message.chocoboName);
			}
			return null;
		}
	}
}
