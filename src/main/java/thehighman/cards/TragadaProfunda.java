package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

public class TragadaProfunda extends BaseCard {
    public static final String ID = makeID("TragadaProfunda");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 15;
    private static final int Erva = 2;
    private static final int ErvaUpg = -1;
    private static final int LARICA_GAIN = 2;

    public TragadaProfunda() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(Erva, ErvaUpg);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (p.hasPower(ErvaPower.POWER_ID) && p.getPower(ErvaPower.POWER_ID).amount >= magicNumber) {
            addToBot(new ApplyPowerAction(p, p, new LaricaPower(p, LARICA_GAIN), LARICA_GAIN));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=2;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ErvaPower.POWER_ID)
                && AbstractDungeon.player.getPower(ErvaPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}