package thehighman.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class TiroCerto extends BaseCard {
    public static final String ID = makeID("TiroCerto");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;
    private static final int CHAPADO_THRESHOLD = 10;
    private static final int CHAPADO_Treshold_UPG = 5;

    public TiroCerto() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_THRESHOLD, CHAPADO_Treshold_UPG);
        this.keywords.add("chapado");
        this.keywords.add("força");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (m.hasPower(LaricaPower.POWER_ID) && m.getPower(LaricaPower.POWER_ID).amount >= magicNumber) {
            addToBot(new ApplyPowerAction(m, p, new StunMonsterPower(m, 1), 1));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(CHAPADO_Treshold_UPG);
            initializeDescription();
        }
    }


}