package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
@AutoAdd.Seen
public class MaconhaMedicinal extends BaseRelic {
    public static final String ID = "highman:MaconhaMedicinal";

    public MaconhaMedicinal() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return "Toda vez que você perder HP devido à Bad Trip, cure 2 de HP.";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MaconhaMedicinal();
    }

    // Este método deve ser chamado pela lógica que aplica dano da Bad Trip
    public void onBadTripDano() {
        flash();
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
    }
}