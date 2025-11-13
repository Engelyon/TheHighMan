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
    public AbstractRelic makeCopy() {
        return new MaconhaMedicinal();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}