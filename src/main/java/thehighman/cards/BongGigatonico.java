package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
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
    private static final int UPG_DAMAGE = 8;
    private static final int VULNERABLE_AMOUNT = 3;

    public BongGigatonico() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano pesado
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.FIRE));

        // Se o inimigo tiver Larica, aplica Vulnerável
        if (m.hasPower(Larica.POWER_ID)) {
            addToBot(new ApplyPowerAction(m, p,
                    new com.megacrit.cardcrawl.powers.VulnerablePower(m, VULNERABLE_AMOUNT, false),
                    VULNERABLE_AMOUNT));
        }
    }
}