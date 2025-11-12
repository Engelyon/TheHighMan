package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
    private static final int COMIDO_GAIN= 3;
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
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));
        addToBot(new ApplyPowerAction(p, p, new SedaPower(p, p, SEDA_GAIN), SEDA_GAIN));
        if (upgraded){
            addToBot(new DrawCardAction(1));
        }
    }
    @Override
    public void upgrade() {
        super.upgrade();
    }
}