package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class Fritura extends BaseCard {
    public static final String ID = makeID("Fritura");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int TEMP_STRENGTH = 2;
    private static final int UPG_TEMP_STRENGTH = 1;
    private static final int LARICA_LOSS = 1;

    public Fritura() {
        super(ID, info);
        setMagic(TEMP_STRENGTH, UPG_TEMP_STRENGTH);
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p == null) return;
        int strengthToApply = this.magicNumber;
        int laricaToRemove = LARICA_LOSS;
        if (upgraded) {
            strengthToApply *= 2;
            laricaToRemove *= 2;
        }

        if (strengthToApply > 0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, strengthToApply), strengthToApply));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, strengthToApply), strengthToApply));
        }
        if (p.hasPower(LaricaPower.POWER_ID)) {
            int current = p.getPower(LaricaPower.POWER_ID).amount;
            int amountToRemove = Math.min(current, laricaToRemove);
            if (amountToRemove > 0) {
                addToBot(new ReducePowerAction(p, p, LaricaPower.POWER_ID, amountToRemove));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}