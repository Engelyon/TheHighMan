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
            // --- 1. Checa o estado da conexão (SpireTogetherMod.isConnected) ---
            Class<?> modClass = Class.forName("spireTogether.SpireTogetherMod");
            Field isConnectedField = modClass.getField("isConnected");
            boolean isConnected = isConnectedField.getBoolean(null);

            if (!isConnected) {
                return true; // Single-player, pode aplicar.
            }

            // --- 2. Prepara as classes e métodos de Reflexão ---
            Class<?> p2pClass = Class.forName("spireTogether.network.P2P.P2PManager");
            // A classe P2PPlayer é a superclasse de PFPlayer, SteamPlayer, etc.
            Class<?> playerClass = Class.forName("spireTogether.network.P2P.P2PPlayer");

            // Pega os métodos que vamos precisar
            Method getSelfMethod = p2pClass.getMethod("GetSelf");
            Method getAllPlayersMethod = p2pClass.getMethod("GetAllPlayers", boolean.class);
            Method inSameRoomMethod = playerClass.getMethod("IsPlayerInSameRoomAndAction");
            Field idField = playerClass.getField("id"); // O campo 'id'

            // --- 3. Pega o nosso próprio ID ---
            Object selfPlayer = getSelfMethod.invoke(null);
            if (selfPlayer == null) return false; // Rede não está pronta
            Integer selfId = (Integer) idField.get(selfPlayer);
            if (selfId == null) return false; // Rede não está pronta

            // --- 4. Encontra o ID mais baixo NA NOSSA SALA ---
            Object iteratorObj = getAllPlayersMethod.invoke(null, true); // true = incluir a si mesmo
            Iterator<?> playerIterator = (Iterator<?>) iteratorObj;

            Integer lowestIdInRoom = null;

            while (playerIterator.hasNext()) {
                Object currentPlayer = playerIterator.next();

                // Verifica se este jogador está na mesma sala/combate que nós
                boolean inRoom = (Boolean) inSameRoomMethod.invoke(currentPlayer);

                if (inRoom) {
                    // Se ele está na sala, pega o ID dele
                    Integer currentId = (Integer) idField.get(currentPlayer);

                    // Compara para ver se é o ID mais baixo encontrado até agora
                    if (lowestIdInRoom == null || currentId < lowestIdInRoom) {
                        lowestIdInRoom = currentId;
                    }
                }
            }

            // --- 5. Compara nosso ID com o ID mais baixo da sala ---
            // Se o ID mais baixo da sala for o nosso ID, nós somos o "Host da Sala"
            return selfId.equals(lowestIdInRoom);

        } catch (Exception e) {
            // Qualquer falha = Assume single-player
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