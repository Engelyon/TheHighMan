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
            Method getSelfMethod = p2pClass.getMethod("GetSelf");
            Object selfPlayer = getSelfMethod.invoke(null);
            Method getLobbyOwnerMethod = p2pClass.getMethod("GetLobbyOwner");
            Object lobbyOwnerPlayer = getLobbyOwnerMethod.invoke(null);
            if (selfPlayer == null || lobbyOwnerPlayer == null) {
                return false;
            }
            return selfPlayer==lobbyOwnerPlayer;
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