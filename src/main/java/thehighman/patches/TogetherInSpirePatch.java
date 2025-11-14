package thehighman.patches;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import thehighman.powers.ChapadoPower;
import thehighman.powers.LaricaPower;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

@SpirePatch(
        clz = ChapadoPower.class,
        method = "stackPower",
        optional = true,
        requiredModId = "spireTogether"
)
public class TogetherInSpirePatch {

    private static boolean isHostOrSinglePlayer() {
        try {
            Class<?> modClass = Class.forName("spireTogether.SpireTogetherMod");
            Field isConnectedField = modClass.getField("isConnected");
            boolean isConnected = isConnectedField.getBoolean(null);
            if (!isConnected) {
                return true;
            }
            Class<?> p2pClass = Class.forName("spireTogether.network.P2P.P2PManager");
            Class<?> playerClass = Class.forName("spireTogether.network.P2P.P2PPlayer");
            Class<?> locationClass = Class.forName("spireTogether.network.objects.rooms.NetworkLocation");
            Method getSelfMethod = p2pClass.getMethod("GetSelf");
            Method getAllPlayersMethod = p2pClass.getMethod("GetAllPlayers", boolean.class);
            Field idField = playerClass.getField("id");
            Field locationField = playerClass.getField("location");
            // O método .equals() para comparar dois objetos NetworkLocation
            Method locationEqualsMethod = locationClass.getMethod("equals", Object.class);
            Object selfPlayer = getSelfMethod.invoke(null);
            if (selfPlayer == null) return false;
            Integer selfId = (Integer) idField.get(selfPlayer);
            Object selfLocation = locationField.get(selfPlayer);
            if (selfId == null || selfLocation == null) return false;
            Object iteratorObj = getAllPlayersMethod.invoke(null, true);
            Iterator<?> playerIterator = (Iterator<?>) iteratorObj;
            Integer lowestIdInRoom = null;
            while (playerIterator.hasNext()) {
                Object currentPlayer = playerIterator.next();
                Object currentLocation = locationField.get(currentPlayer);
                if (currentLocation == null) continue;
                boolean inSameRoom = (Boolean) locationEqualsMethod.invoke(currentLocation, selfLocation);
                if (inSameRoom) {
                    Integer currentId = (Integer) idField.get(currentPlayer);
                    if (lowestIdInRoom == null || (currentId != null && currentId < lowestIdInRoom)) {
                        lowestIdInRoom = currentId;
                    }
                }
            }
            return selfId.equals(lowestIdInRoom);
        } catch (Exception e) {
            return true;
        }
    }

    @SpirePrefixPatch
    public static SpireReturn<Void> multiplayerSafetyCheck(ChapadoPower inst, int stackAmount) {
        inst.setStackFontScale();
        inst.amount += stackAmount;
        if (inst.amount >= 20) {
            if (isHostOrSinglePlayer()) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(inst.owner, inst.owner, new LaricaPower(inst.owner, 1), 1));
            }
            inst.amount = 5;
        }
        inst.updateDescription();
        return SpireReturn.Return();
    }
}