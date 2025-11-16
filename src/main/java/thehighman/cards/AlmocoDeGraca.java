package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class AlmocoDeGraca extends BaseCard {
    public static final String ID = makeID("AlmocoDeGraca");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_GAIN = 2;
    private static final int ENERGY_GAIN = 2;
    private static final int COMIDO_MAX = 10;
    private static final int BLOCK = 5;

    public AlmocoDeGraca() {
        super(ID, info);
        setBlock(BLOCK);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));
        if (p.hasPower(ComidoPower.POWER_ID)) {
            int atual = p.getPower(ComidoPower.POWER_ID).amount;
            if (atual >= COMIDO_MAX) {
                addToBot(new GainEnergyAction(ENERGY_GAIN));
            }
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(0);
            initializeDescription();
        }
    }
    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=8;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ComidoPower.POWER_ID)
                && AbstractDungeon.player.getPower(ComidoPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}