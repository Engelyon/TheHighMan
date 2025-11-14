package thehighman.relics;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehighman.character.TheHighman;
import thehighman.powers.SedaPower;

import static thehighman.InimigosDoSpire.makeID;
@AutoAdd.Seen
public class CinzeiroAbencoado extends BaseRelic {
    private static final String NAME = "CinzeiroAbencoado"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public CinzeiroAbencoado() {
        super(ID, NAME, TheHighman.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SedaPower(AbstractDungeon.player,AbstractDungeon.player,1)));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
