package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class AtaqueFaminto extends BaseCard {
    public static final String ID = makeID("AtaqueFaminto");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public AtaqueFaminto() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Primeiro ataque
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // Verifica e consome Comido para repetir o ataque
        if (ComidoPower.consumirComido(p)) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // Aumenta o dano de 7 para 10
            initializeDescription();
        }
    }
}