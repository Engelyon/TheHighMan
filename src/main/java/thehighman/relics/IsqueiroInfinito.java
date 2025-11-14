package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;

import static thehighman.InimigosDoSpire.makeID;

@AutoAdd.Seen
public class IsqueiroInfinito extends BaseRelic {
    private static final String NAME = "BongLimpo";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public IsqueiroInfinito() {
        super(ID, NAME, TheHighman.Meta.CARD_COLOR, RARITY, SOUND);
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

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}