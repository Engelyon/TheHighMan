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
    private static final int UPG_TEMP_STRENGTH = 2;
    private static final int LARICA_LOSS = 1;
    private static final int UPG_LARICA_LOSS = 1;

    public Fritura() {
        super(ID, info);
        setMagic(TEMP_STRENGTH, UPG_TEMP_STRENGTH);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new StrengthPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p,p, new LoseStrengthPower(p, this.magicNumber)));
        int laricaLoss = LARICA_LOSS;
        if (upgraded) {
            laricaLoss += UPG_LARICA_LOSS;
        }
        addToBot(new ReducePowerAction(p,p, LaricaPower.POWER_ID, laricaLoss));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_TEMP_STRENGTH);
            initializeDescription();
        }
    }
}