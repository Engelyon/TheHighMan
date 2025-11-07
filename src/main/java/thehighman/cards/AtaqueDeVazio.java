package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
import thehighman.util.CardStats;

public class AtaqueDeVazio extends BaseCard {
    public static final String ID = makeID("AtaqueDeVazio");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int BASE_DAMAGE = 5;
    private static final int BONUS_DAMAGE = 10;

    public AtaqueDeVazio() {
        super(ID, info);
        setDamage(BASE_DAMAGE);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Dano base
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // Se estiver com Larica, causa dano extra e consome 1 stack
        if (p.hasPower(Larica.POWER_ID) && p.getPower(Larica.POWER_ID).amount >= 1) {
            addToBot(new DamageAction(m, new DamageInfo(p, BONUS_DAMAGE, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.FIRE));
            addToBot(new ReducePowerAction(p, p, Larica.POWER_ID, 1));
        }
    }
}