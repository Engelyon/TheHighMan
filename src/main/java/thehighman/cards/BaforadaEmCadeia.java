package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class BaforadaEmCadeia extends BaseCard {
    public static final String ID = makeID("BaforadaEmCadeia");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int CHAPADO_AMOUNT = 2;

    public BaforadaEmCadeia() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.FIRE));

        // Aplica Chapado
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));

        // Gera uma cópia da carta Baforada Etérea
        AbstractCard copia = new BaforadaEterea();
        if (this.upgraded) {
            copia.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(copia, 1));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // Aumenta o dano de 4 para 6
            initializeDescription();
        }
    }
}