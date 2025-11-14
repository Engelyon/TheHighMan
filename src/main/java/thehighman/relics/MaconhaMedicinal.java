package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import thehighman.character.TheHighman;

import static thehighman.InimigosDoSpire.makeID;

@AutoAdd.Seen
public class MaconhaMedicinal extends BaseRelic {
    private static final String NAME = "MaconhaMedicinal";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public MaconhaMedicinal() {
        super(ID, NAME, TheHighman.Meta.CARD_COLOR, RARITY, SOUND);
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