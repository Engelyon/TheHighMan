package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thehighman.character.TheHighman;

import static thehighman.InimigosDoSpire.makeID;
@AutoAdd.Seen
public class CinzeiroAbencoado extends BaseRelic {
    private static final String NAME = "CinzeiroAbencoado"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public CinzeiroAbencoado() {
        super(ID, NAME, RARITY, SOUND);
    }
}
