package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class AtaqueDeVazio extends BaseCard {
    public static final String ID = makeID("AtaqueDeVazio");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int BASE_DAMAGE = 5;
    private static final int BASE_DAMAGE_UPG = 5;
    public AtaqueDeVazio() {
        super(ID, info);
        setDamage(BASE_DAMAGE,BASE_DAMAGE_UPG);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(LaricaPower.POWER_ID)){
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage*p.getPower(LaricaPower.POWER_ID).amount, DamageInfo.DamageType.NORMAL)));
        }else{
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        }
    }
    @Override
    public void upgrade() {
        super.upgrade();
        upgradeDamage(BASE_DAMAGE_UPG);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(LaricaPower.POWER_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}