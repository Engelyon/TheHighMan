package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class Reembolso extends BaseCard {
    public static final String ID = makeID("Reembolso");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int CHANGE = 1;
    private static final int CHANGE_UPG = 1;

    public Reembolso() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHANGE, CHANGE_UPG);
        this.exhaust=true;
        this.keywords.add("fume");
        this.keywords.add("seda");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (p.hasPower(ErvaPower.POWER_ID)) {
            addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, 1));
            new ApplyPowerAction(p, p, new SedaPower(p, p, this.magicNumber), this.magicNumber);
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(CHANGE_UPG);
            initializeDescription();
        }
    }
}