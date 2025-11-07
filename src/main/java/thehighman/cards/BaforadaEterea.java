package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.powers.Erva;
import thehighman.util.CardStats;

public class BaforadaEterea extends BaseCard {
    public static final String ID = makeID("BaforadaEterea");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            -2 // custo especial: não usa energia
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int CHAPADO_AMOUNT = 2;

    public BaforadaEterea() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        this.exhaust = true;
        this.isEthereal = true;
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.hasPower(Erva.POWER_ID) && p.getPower(Erva.POWER_ID).amount >= 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Consome 1 de Erva
        addToBot(new ReducePowerAction(p, p, Erva.POWER_ID, 1));

        // Causa dano e aplica Chapado
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(m, p, new Chapado(m, this.magicNumber), this.magicNumber));

        // Cria uma cópia de si mesma
        AbstractCard copia = this.makeStatEquivalentCopy();
        addToBot(new MakeTempCardInHandAction(copia, 1));
    }
}