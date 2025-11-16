package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
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

public class ResgateDeLanche extends BaseCard {
    public static final String ID = makeID("ResgateDeLanche");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int HEAL_AMOUNT = 3;
    private static final int HEAL_UPG = 2;
    private static final int BLOCK = 8;
    private static final int BLOCK_UPG = 3;

    public ResgateDeLanche() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
        setMagic(HEAL_AMOUNT,HEAL_UPG);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        if (p.hasPower(ComidoPower.POWER_ID)) {
            int ComidoStacks = p.getPower(ComidoPower.POWER_ID).amount;
            if (ComidoStacks >= 1) {
                addToBot(new HealAction(p, p, this.magicNumber));
                addToBot(new ReducePowerAction(p, p, ComidoPower.POWER_ID, 1));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPG);
            upgradeMagicNumber(HEAL_UPG);
            initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=1;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ComidoPower.POWER_ID)
                && AbstractDungeon.player.getPower(ComidoPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}