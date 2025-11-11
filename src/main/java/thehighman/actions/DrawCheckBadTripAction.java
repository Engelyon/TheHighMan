package thehighman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashSet;
import java.util.Set;

public class DrawCheckBadTripAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int initialDraw;
    private final int extraDrawIfFound;
    private final String targetCardID;
    private boolean started = false;
    private Set<String> handSnapshot;

    public DrawCheckBadTripAction(AbstractPlayer p, int initialDraw, int extraDrawIfFound, String targetCardID) {
        this.p = p;
        this.initialDraw = initialDraw;
        this.extraDrawIfFound = extraDrawIfFound;
        this.targetCardID = targetCardID;
        this.actionType = ActionType.DRAW;
        this.duration = 0.0f;
    }

    @Override
    public void update() {
        if (!started) {
            // captura snapshot dos UUIDs/IDs que já estão na mão
            handSnapshot = new HashSet<>();
            if (p != null && p.hand != null) {
                for (AbstractCard c : p.hand.group) {
                    // usa cardID porque queremos detectar tipo BadTrip; se quisesse instância usar c.uuid
                    handSnapshot.add(c.cardID + "|" + c.uuid);
                }
            }

            // coloca DrawCardAction na fila; este action continuará no próximo update após o draw terminar
            if (initialDraw > 0) {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, initialDraw));
            }
            started = true;
            return;
        }

        // Checagem ocorrendo após o DrawCardAction já ter sido executado
        boolean found = false;
        if (p != null && p.hand != null) {
            for (AbstractCard c : p.hand.group) {
                String key = c.cardID + "|" + c.uuid;
                if (!handSnapshot.contains(key)) {
                    // esta carta foi desenhada neste momento; verifica se é do tipo alvo
                    if (c.cardID.equals(targetCardID)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (found && extraDrawIfFound > 0) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, extraDrawIfFound));
        }

        this.isDone = true;
    }
}