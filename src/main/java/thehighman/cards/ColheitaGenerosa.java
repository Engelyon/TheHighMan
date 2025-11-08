package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.SedaPower;
import thehighman.powers.ComidoPower;
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
        this.keywords.add("erva");
        this.keywords.add("comido");
        this.keywords.add("seda");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 2 de Erva
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));

        // Se tiver 3 ou mais de Comido, ganha 1 de Seda
        if (p.hasPower(ComidoPower.POWER_ID) && p.getPower(ComidoPower.POWER_ID).amount >= COMIDO_THRESHOLD) {
            addToBot(new ApplyPowerAction(p, p, new SedaPower(p, p, SEDA_GAIN), SEDA_GAIN));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // 2 → 3 de Erva
            initializeDescription();
        }
    }
}