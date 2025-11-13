package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class BongGigatonico extends BaseCard {
    public static final String ID = makeID("BongGigatonico");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 30;
    private static final int VULNERABLE_AMOUNT = 3;

    public BongGigatonico() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(VULNERABLE_AMOUNT);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded){
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            addToBot(new ApplyPowerAction(m,p, new VulnerablePower(m, this.magicNumber, false),this.magicNumber));
        }
        if (upgraded){
            int dano=0;
            if (!m.hasPower(VulnerablePower.POWER_ID)){
                dano = (int)(damage * 1.5);
            }  else{
                dano = damage;
            }
            addToBot(new ApplyPowerAction(m,p, new VulnerablePower(m, this.magicNumber, false),this.magicNumber));
            addToBot(new DamageAction(m, new DamageInfo(p, dano, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
    @Override
    public void upgrade() {
        super.upgrade();
    }
}