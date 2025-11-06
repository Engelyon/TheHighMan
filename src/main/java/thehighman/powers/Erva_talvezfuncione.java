package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static thehighman.InimigosDoSpire.makeID;

public class Erva_talvezfuncione extends AbstractPower {
    public static final String POWER_ID = makeID("Erva");

    private static final PowerStrings powerStrings =
            com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public Erva_talvezfuncione(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public static int getErvaStacks(AbstractCreature creature) {
        AbstractPower power = creature.getPower(POWER_ID);
        return power != null ? power.amount : 0;
    }
}