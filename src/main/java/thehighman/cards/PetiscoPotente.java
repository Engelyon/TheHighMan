package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.EnergiaExtraProximoTurnoPower;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class PetiscoPotente extends BaseCard {
    public static final String ID = makeID("PetiscoPotente");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_GAIN = 1;
    private static final int UPG_COMIDO_GAIN = 1;
    private static final int COMIDO_THRESHOLD = 5;
    private static final int ENERGY_NEXT_TURN = 2;

    public PetiscoPotente() {
        super(ID, info);
        setMagic(COMIDO_GAIN, UPG_COMIDO_GAIN);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));
        if (p.hasPower(ComidoPower.POWER_ID) && p.getPower(ComidoPower.POWER_ID).amount >= COMIDO_THRESHOLD) {
            addToBot(new ApplyPowerAction(p, p, new EnergiaExtraProximoTurnoPower(p, ENERGY_NEXT_TURN), ENERGY_NEXT_TURN));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int x=5;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ComidoPower.POWER_ID)
                && AbstractDungeon.player.getPower(ComidoPower.POWER_ID).amount >= x) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }
}