package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Comido;
import thehighman.powers.ImuneABadTrip;
import thehighman.util.CardStats;

public class AlimentoDivino extends BaseCard {
    public static final String ID = makeID("AlimentoDivino");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int COMIDO_GAIN = 5;
    private static final int COMIDO_MAX = 10; // ajuste conforme o limite real

    public AlimentoDivino() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 5 de Comido
        addToBot(new ApplyPowerAction(p, p, new Comido(p, COMIDO_GAIN), COMIDO_GAIN));

        // Se Comido estiver no máximo, aplica imunidade a Bad Trip
        if (p.hasPower(Comido.POWER_ID) && p.getPower(Comido.POWER_ID).amount >= COMIDO_MAX) {
            addToBot(new ApplyPowerAction(p, p, new ImuneABadTrip(p), 1));
        }
    }
}