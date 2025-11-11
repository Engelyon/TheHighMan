package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class Recompensa extends BaseCard {
    public static final String ID = makeID("Recompensa");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int VULNERABLE_AMOUNT = 2;
    private static final int VULNERABLE_AMOUNT_UPG = 2;

    public Recompensa() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(VULNERABLE_AMOUNT, VULNERABLE_AMOUNT_UPG);
        this.keywords.add("erva");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // Se tiver Erva, consome 1 e aplica Vulnerável
        if (p.hasPower(ErvaPower.POWER_ID) && p.getPower(ErvaPower.POWER_ID).amount >= 1) {
            addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, 1));
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(VULNERABLE_AMOUNT_UPG);
            initializeDescription();
        }
    }
}