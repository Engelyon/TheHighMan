package thehighman.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import thehighman.powers.Erva;

public class IsqueiroInfinito extends AbstractRelic {
    public static final String ID = "thehighman:IsqueiroInfinito";

    public IsqueiroInfinito() {
        super(ID, "isqueiro.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return "No início de cada turno, se você tiver 0 de Erva, ganhe 1 de Erva.";
    }

    @Override
    public void atTurnStart() {
        if (!AbstractDungeon.player.hasPower("thehighman:Erva")) {
            flash();
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new Erva(AbstractDungeon.player, AbstractDungeon.player, 1),
                    1
            ));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new IsqueiroInfinito();
    }
}