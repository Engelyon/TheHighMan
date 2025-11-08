package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class LaricaPesada extends BaseCard {
    public static final String ID = makeID("LaricaPesada");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int LARICA_AMOUNT = 2;

    public LaricaPesada() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(LARICA_AMOUNT);
        this.isMultiDamage = true;
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Dano em área
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        // Aplica Larica ao jogador
        addToBot(new ApplyPowerAction(p, p, new LaricaPower(p, this.magicNumber), this.magicNumber));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);     // 10 → 14 de dano em área
            initializeDescription();
        }
    }
}