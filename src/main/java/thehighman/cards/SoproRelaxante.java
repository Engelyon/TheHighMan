package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class SoproRelaxante extends BaseCard {
    public static final String ID = makeID("SoproRelaxante");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;
    private static final int CHAPADO_AMOUNT = 1;
    private static final int VULNERABLE_AMOUNT = 1;
    private static final int VULNERABLE = 1;
    private static final int UPG_VULNERABLE = 1;
    public int secondMagicNumber;
    public int baseSecondMagicNumber;
    public boolean upgradedSecondMagicNumber;

    public SoproRelaxante() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        setMagic(CHAPADO_AMOUNT); // continua sendo o chapado
        this.baseSecondMagicNumber = VULNERABLE;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.keywords.add("chapado");
        this.keywords.add("vulnerável");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.LIGHTNING));

        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, CHAPADO_AMOUNT), CHAPADO_AMOUNT));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.secondMagicNumber, false), this.secondMagicNumber));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 5 → 8
            this.baseSecondMagicNumber += UPG_VULNERABLE; // 1 → 2 de vulnerável
            this.secondMagicNumber = this.baseSecondMagicNumber;
            this.upgradedSecondMagicNumber = true;
            initializeDescription();
        }
    }
}