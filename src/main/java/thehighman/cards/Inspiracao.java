package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Erva;
import thehighman.util.CardStats;

public class Inspiracao extends BaseCard {
    public static final String ID = makeID("Inspiracao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int ERVA_GAIN = 2;
    private static final int UPG_ERVA = 1;

    public Inspiracao() {
        super(ID, info);
        setMagic(ERVA_GAIN, UPG_ERVA);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Erva(p, p, this.magicNumber), this.magicNumber));
    }
}