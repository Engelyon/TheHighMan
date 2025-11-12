package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class CheiroDeLanche extends BaseCard {
    public static final String ID = makeID("CheiroDeLanche");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int BASE_DAMAGE = 5;
    private static final int BASE_DAMAGE_UPG = 3;

    public CheiroDeLanche() {
        super(ID, info);
        setDamage(BASE_DAMAGE, BASE_DAMAGE_UPG);
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (!p.hasPower(LaricaPower.POWER_ID)){
            addToBot(new ApplyPowerAction(p,p, new LaricaPower(p,1),1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(BASE_DAMAGE_UPG);
            initializeDescription();
        }
    }
}