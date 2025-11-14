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

    /*private static boolean isHostOrSinglePlayer() {
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
    }*/

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

            // Esta é a nossa suposição: que o campo de localização se chama "location"
            Field locationField = playerClass.getField("location");
            // O método .equals() para comparar dois objetos NetworkLocation
            Method locationEqualsMethod = locationClass.getMethod("equals", Object.class);

            // --- 3. Pega NOSSOS dados (ID e Localização) ---
            Object selfPlayer = getSelfMethod.invoke(null);
            if (selfPlayer == null) return false; // Rede não está pronta

            Integer selfId = (Integer) idField.get(selfPlayer);
            Object selfLocation = locationField.get(selfPlayer); // Pega nosso objeto de localização

            if (selfId == null || selfLocation == null) return false; // Rede não está pronta

            // --- 4. Encontra o ID mais baixo NA NOSSA SALA ---
            Object iteratorObj = getAllPlayersMethod.invoke(null, true); // true = incluir a si mesmo
            Iterator<?> playerIterator = (Iterator<?>) iteratorObj;

            Integer lowestIdInRoom = null;

            while (playerIterator.hasNext()) {
                Object currentPlayer = playerIterator.next();

                // Pega a localização do jogador atual
                Object currentLocation = locationField.get(currentPlayer);
                if (currentLocation == null) continue; // Pula jogador se a localização for nula

                // Compara a localização dele com a NOSSA localização
                // (Equivalente a: currentLocation.equals(selfLocation))
                boolean inSameRoom = (Boolean) locationEqualsMethod.invoke(currentLocation, selfLocation);

                if (inSameRoom) {
                    // Se ele está na sala, pega o ID dele
                    Integer currentId = (Integer) idField.get(currentPlayer);

                    // Compara para ver se é o ID mais baixo encontrado até agora
                    if (lowestIdInRoom == null || (currentId != null && currentId < lowestIdInRoom)) {
                        lowestIdInRoom = currentId;
                    }
                }
            }

            // --- 5. Compara nosso ID com o ID mais baixo da sala ---
            // Se o ID mais baixo da sala for o nosso ID, nós somos o "Host da Sala"
            return selfId.equals(lowestIdInRoom);

        } catch (Exception e) {
            // Qualquer falha = Assume single-player
            // (Se o campo "location" não existir, vai cair aqui e funcionar no single-player)
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