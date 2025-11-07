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

    private static final int ERVA_GAIN = 8;

    public Reabastecer() {
        super(ID, info);
        setMagic(ERVA_GAIN, 2); // upgrade aumenta Erva de 8 → 10

        this.rawDescription = "Ganhe !M! de Erva. Se estiver com 3 ou mais de Chapado, compre 1 carta.";
        this.keywords.add("erva");
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 8 de Erva
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));

        // Se estiver com 3 ou mais de Chapado, compra 1 carta
        if (p.hasPower(ChapadoPower.POWER_ID) && p.getPower(ChapadoPower.POWER_ID).amount >= 3) {
            addToBot(new DrawCardAction(1));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2); // Erva: 8 → 10
            initializeDescription();
        }
    }
}