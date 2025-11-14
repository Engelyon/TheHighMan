package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import thehighman.character.TheHighman;

import static thehighman.InimigosDoSpire.makeID;

@AutoAdd.Seen
public class SacoDeLanchesSemFundo extends BaseRelic {
    private static final String NAME = "SacoDeLanchesSemFundo";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public SacoDeLanchesSemFundo() {
        super(ID, NAME, TheHighman.Meta.CARD_COLOR, RARITY, SOUND);
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SacoDeLanchesSemFundo();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}