package thehighman.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

/**
 * Represents the "Mao Santa" card in the game.
 * This card is a skill card that applies the "Seda" and "Erva" powers to the player.
 */
public class MaoSanta extends BaseCard {
    // Unique identifier for the card
    public static final String ID = makeID("MaoSanta");

    // Card statistics such as color, type, rarity, target, and cost
    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    // Base amount of the buff applied by the card
    private static final int BUFF_AMOUNT = 1;

    // Additional buff amount when the card is upgraded
    private static final int UPG_BUFF = 1;

    /**
     * Constructor for the "Mao Santa" card.
     * Initializes the card's properties, including its magic number and tooltips.
     */
    public MaoSanta() {
        super(ID, info);

        // Sets the magic number (buff amount) and its upgrade value
        setMagic(BUFF_AMOUNT, UPG_BUFF);
        this.rawDescription = "Ganhe !M! de Seda e !M! de Erva.";
        this.keywords.add("seda");
        this.keywords.add("erva");
        initializeDescription();
    }

    /**
     * Defines the actions performed when the card is used.
     * Applies the "Seda" and "Erva" powers to the player.
     *
     * @param p The player using the card.
     * @param m The target monster (not used as this card targets the player).
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Apply the Seda power to the player
        addToBot(new VFXAction(new InflameEffect(p)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new SedaPower(p, p, this.magicNumber), this.magicNumber)
        );

        // Apply the Erva power to the player
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ErvaPower(p, this.magicNumber), this.magicNumber)
        );
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_BUFF); // 1 → 2 de cada poder
            initializeDescription();
        }
    }
}