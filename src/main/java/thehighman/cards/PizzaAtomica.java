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

    public PizzaAtomica() {
        super(ID, info);
        setDamage(HIT_DAMAGE, UPG_HIT_DAMAGE);
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m == null) {
            return;
        }

        // Se o inimigo tiver Larica, remove 1 antes de causar dano
        if (m.hasPower(LaricaPower.POWER_ID)) {
            int current = m.getPower(LaricaPower.POWER_ID).amount;
            if (current > 0) {
                addToBot(new ReducePowerAction(m, p, LaricaPower.POWER_ID, 1));
            }
        }

        // Número de hits: 1 base, +1 se upada
        int hits = upgraded ? 2 : 1;

        for (int i = 0; i < hits; i++) {
            addToBot(new DamageAction(m,
                    new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            // pequeno delay visual opcional entre hits
            addToBot(new com.megacrit.cardcrawl.actions.utility.WaitAction(0.08f));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_HIT_DAMAGE); // 3 → 5 por golpe
            initializeDescription();
        }
    }
}