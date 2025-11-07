package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Erva;
import thehighman.powers.Chapado;
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
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 8 de Erva
        addToBot(new ApplyPowerAction(p, p, new Erva(p, p, ERVA_GAIN), ERVA_GAIN));

        // Se estiver com 3 ou mais de Chapado, compra 1 carta
        if (p.hasPower(Chapado.POWER_ID) && p.getPower(Chapado.POWER_ID).amount >= 3) {
            addToBot(new DrawCardAction(1));
        }
    }
}