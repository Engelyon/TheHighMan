package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class ResgateDeLanche extends BaseCard {
    public static final String ID = makeID("ResgateDeLanche");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;
    private static final int HEAL_AMOUNT = 3;

    public ResgateDeLanche() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // Se o inimigo tiver Larica, cura o jogador
        if (m.hasPower(LaricaPower.POWER_ID)) {
            addToBot(new HealAction(p, p, HEAL_AMOUNT));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 5 → 8 de dano
            initializeDescription();
        }
    }
}