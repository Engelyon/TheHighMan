package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Erva;
import thehighman.powers.Seda;
import thehighman.powers.Comido;
import thehighman.util.CardStats;

public class ColheitaGenerosa extends BaseCard {
    public static final String ID = makeID("ColheitaGenerosa");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int ERVA_GAIN = 2;
    private static final int COMIDO_THRESHOLD = 3;
    private static final int SEDA_GAIN = 1;

    public ColheitaGenerosa() {
        super(ID, info);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 2 de Erva
        addToBot(new ApplyPowerAction(p, p, new Erva(p, p, ERVA_GAIN), ERVA_GAIN));

        // Se tiver 3 ou mais de Comido, ganha 1 de Seda
        if (p.hasPower(Comido.POWER_ID) && p.getPower(Comido.POWER_ID).amount >= COMIDO_THRESHOLD) {
            addToBot(new ApplyPowerAction(p, p, new Seda(p, p, SEDA_GAIN), SEDA_GAIN));
        }
    }
}