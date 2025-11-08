package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class ComeAi extends BaseCard {
    public static final String ID = makeID("ComeAi");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int LARICA_THRESHOLD = 3;

    public ComeAi() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (p.hasPower(LaricaPower.POWER_ID)) {
            int laricaStacks = p.getPower(LaricaPower.POWER_ID).amount;
            if (laricaStacks >= LARICA_THRESHOLD) {
                // Remove todos os stacks de Larica
                addToBot(new ReducePowerAction(p, p, LaricaPower.POWER_ID, laricaStacks));
                // Ganha 1 de energia por stack perdido
                addToBot(new GainEnergyAction(laricaStacks));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 10 → 14 de dano
            initializeDescription();
        }
    }
}