package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import thehighman.powers.ErvaPower;
@AutoAdd.Seen
public class IsqueiroInfinito extends BaseRelic {
    public static final String ID = "highman:IsqueiroInfinito";

    public IsqueiroInfinito() {
        super(ID,  RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        if (!AbstractDungeon.player.hasPower("thehighman:Erva")) {
            flash();
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new ErvaPower(AbstractDungeon.player, 1),
                    1
            ));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new IsqueiroInfinito();
    }
}