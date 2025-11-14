package thehighman.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thehighman.cards.LimparOBong;
import thehighman.character.TheHighman;

import static thehighman.InimigosDoSpire.makeID;

public class BongLimpo extends BaseRelic {
    private static final String NAME = "BongLimpo";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public BongLimpo() {
        super(ID, NAME, TheHighman.Meta.CARD_COLOR, RARITY, SOUND);
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