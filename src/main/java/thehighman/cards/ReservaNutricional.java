package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Comido;
import thehighman.powers.Erva;
import thehighman.util.CardStats;

public class ReservaNutricional extends BaseCard {
    public static final String ID = makeID("ReservaNutricional");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_GAIN = 2;
    private static final int ERVA_GAIN = 3;

    public ReservaNutricional() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Comido(p, COMIDO_GAIN), COMIDO_GAIN));
        addToBot(new ApplyPowerAction(p, p, new Erva(p, p, ERVA_GAIN), ERVA_GAIN));
    }
}