package thehighman.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static thehighman.InimigosDoSpire.makeID;

public class ErvaPower extends BasePower {
    public static final String POWER_ID = makeID("ErvaPower");
    //private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ErvaPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, amount);

        //this.img = ImageMaster.loadImage("thehighman/images/powers/large/ErvaPower.jpg");
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]+ this.amount + DESCRIPTIONS[2];
    }
}