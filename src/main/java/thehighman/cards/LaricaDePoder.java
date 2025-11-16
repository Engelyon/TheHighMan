package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

import java.util.ArrayList;

public class LaricaDePoder extends BaseCard {
    public static final String ID = makeID("LaricaDePoder");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 5;

    public LaricaDePoder() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.exhaust=true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new ApplyPowerAction(m,p, new LaricaPower(m, 1)));
        if (m.hasPower(LaricaPower.POWER_ID)){
            addToBot(new DamageAction(m, new DamageInfo(p,((m.getPower(LaricaPower.POWER_ID).amount+1)*5)*2, DamageInfo.DamageType.NORMAL)));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, 10, DamageInfo.DamageType.NORMAL)));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.exhaust=false;
    }

}