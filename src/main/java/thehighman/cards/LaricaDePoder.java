package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class LaricaDePoder extends BaseCard {
    public static final String ID = makeID("LaricaDePoder");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    private static final int BASE_DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;
    private static final int BONUS_PER_COMIDO = 5;

    public LaricaDePoder() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
        this.rawDescription = "Cause !D! de dano. Ganha +5 de dano para cada Comido. Remove todos os Comidos.";
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = 0;

        if (p.hasPower(ComidoPower.POWER_ID)) {
            int stacks = p.getPower(ComidoPower.POWER_ID).amount;
            bonus = stacks * BONUS_PER_COMIDO;

            // Remove todos os stacks de Comido
            addToBot(new ReducePowerAction(p, p, ComidoPower.POWER_ID, stacks));
        }

        // Causa dano base + bônus
        addToBot(new DamageAction(m,
                new DamageInfo(p, this.damage + bonus, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 15 → 20 de dano base
            initializeDescription();
        }
    }
}