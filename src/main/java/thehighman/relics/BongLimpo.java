package thehighman.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thehighman.cards.LimparOBong;

public class BongLimpo extends BaseRelic {
    public static final String ID = "highman:BongLimpo";

    public BongLimpo() {
        super(ID,
                RelicTier.STARTER,
                LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new LimparOBong(), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BongLimpo();
    }
}