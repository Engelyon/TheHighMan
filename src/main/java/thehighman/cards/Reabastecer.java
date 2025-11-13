package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class Reabastecer extends BaseCard {
    public static final String ID = makeID("Reabastecer");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int ERVA_GAIN = 6;

    public Reabastecer() {
        super(ID, info);
        setMagic(ERVA_GAIN, 4);
        this.exhaust=true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
        addToBot(new DrawCardAction(p, 1));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            initializeDescription();
        }
    }
}