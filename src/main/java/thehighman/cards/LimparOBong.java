package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.SedaPower;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class LimparOBong extends BaseCard {
    public static final String ID = makeID("LimparOBong");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int ERVAS_COST = 10;
    private static final int SEDA_GAIN = 3;

    public LimparOBong() {
        super(ID, info);
        this.selfRetain = true;
        this.exhaust = true;
        this.keywords.add("erva");
        this.keywords.add("seda");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ErvaPower.POWER_ID) && p.getPower(ErvaPower.POWER_ID).amount >= ERVAS_COST) {
            addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, -ERVAS_COST), -ERVAS_COST));
            addToBot(new ApplyPowerAction(p, p, new SedaPower(p, p, SEDA_GAIN), SEDA_GAIN));
        }
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }

        if (!p.hasPower(ErvaPower.POWER_ID) || p.getPower(ErvaPower.POWER_ID).amount < ERVAS_COST) {
            this.cantUseMessage = "Você precisa de pelo menos " + ERVAS_COST + " Ervas.";
            return false;
        }

        return true;
    }
}