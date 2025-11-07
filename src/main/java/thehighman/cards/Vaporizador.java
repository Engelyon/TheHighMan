package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.powers.Seda;
import thehighman.util.CardStats;

public class Vaporizador extends BaseCard {
    public static final String ID = makeID("Vaporizador");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int CHAPADO_AMOUNT = 2;
    private static final int SEDA_GAIN = 1;

    public Vaporizador() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.FIRE));

        // Aplica Chapado no inimigo
        addToBot(new ApplyPowerAction(m, p, new Chapado(m, this.magicNumber), this.magicNumber));

        // Ganha Seda
        addToBot(new ApplyPowerAction(p, p, new Seda(p, p, SEDA_GAIN), SEDA_GAIN));
    }
}