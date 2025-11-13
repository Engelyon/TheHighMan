package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.ErvaPower;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class ComeAi extends BaseCard {
    public static final String ID = makeID("ComeAi");
    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int COMIDO_THRESHOLD = 3;
    public ComeAi() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (p.hasPower(ComidoPower.POWER_ID)) {
            int comidoStacks = p.getPower(ComidoPower.POWER_ID).amount;
            if (comidoStacks >= COMIDO_THRESHOLD) {
                addToBot(new ReducePowerAction(p, p, ComidoPower.POWER_ID, comidoStacks));
                addToBot(new GainEnergyAction(comidoStacks));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            initializeDescription();
        }
    }
    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=3;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ComidoPower.POWER_ID)
                && AbstractDungeon.player.getPower(ComidoPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}