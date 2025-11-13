package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class PizzaAtomica extends BaseCard {
    public static final String ID = makeID("PizzaAtomica");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int HIT_DAMAGE = 3;
    private static final int UPG_HIT_DAMAGE = 2;
    private static final int BONUS_DAMAGE = 7;
    private static final int HITS =1;

    public PizzaAtomica() {
        super(ID, info);
        setDamage(HIT_DAMAGE, UPG_HIT_DAMAGE);
        setMagic(HITS, 1);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (m.hasPower(LaricaPower.POWER_ID)){
            addToBot(new ReducePowerAction(m, p, LaricaPower.POWER_ID, 1));
            addToBot(new DamageAction(m, new DamageInfo(p, BONUS_DAMAGE, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }
    @Override
    public void upgrade() {
        super.upgrade();
    }
}